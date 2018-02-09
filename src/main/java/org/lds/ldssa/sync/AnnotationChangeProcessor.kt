package org.lds.ldssa.sync

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemManager
import org.lds.ldssa.model.webservice.ServiceModule
import org.lds.ldssa.model.webservice.annotation.dto.DtoError
import org.lds.ldssa.model.webservice.annotation.dto.annotationchanges.DtoSyncId
import org.lds.ldssa.model.webservice.annotation.dto.annotationrequest.DtoAnnotationRequest
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.mobile.util.LdsTimeUtil
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

class AnnotationChangeProcessor @Inject
constructor(private val fileUtil: GLFileUtil,
            private val timeUtil: LdsTimeUtil,
            private val subitemMetadataManager: SubItemMetadataManager,
            private val annotationManager: AnnotationManager,
            private val itemManager: ItemManager,
            private val syncContentItemAnnotationsQueueItemManager: SyncContentItemAnnotationsQueueItemManager,
            private val gson: Gson) {

    var changeCount = 0
        private set
    var beforeTs: String? = null
        private set
    var errors = ArrayList<DtoError>()
        private set

    @Throws(IOException::class)
    fun processChangesAndProduceRequestFile() {
        errors = ArrayList()
        changeCount = 0

        // ========== PREPARE OUTGOING REQUEST FILE ===========
        val annotationRequestFile = fileUtil.syncAnnotations3OutFile

        val writer = JsonWriter(FileWriter(annotationRequestFile))
        ServiceModule.setupJsonWriter(writer)

        // Header
        writer.beginObject()

        writer.name("xformAnnoVers")
        writer.beginObject()

        //        writer.name("@xmlns).value(http://lds.org/schema/folder"); // NOSONAR tebbs "not needed"
        //        writer.name("schemaDate).value(2011-04-13T13:00:00.000-06:00"); // NOSONAR tebbs "not needed"
        writer.name("clientTime").value(ThreeTenUtil.formatIso(LocalDateTime.now()))

        // ========== READ CHANGE FILE and WRITE TO REQUEST FILE ===========
        writer.name("annoVers")
        writer.beginArray()

        val changesFile = fileUtil.syncAnnotations2InFile

        processAnnotationChangesFile(changesFile, writer)
        addContentItemVersionChanges(writer)

        writer.endArray()

        // ========== FINALIZE REQUEST FILE ===========
        // Footer
        writer.endObject()
        writer.endObject()
        writer.close()

        if (changeCount == 0) {
            // delete file uneeded file (request should not be made)... also deleting the file helps with Unit tests (to verify no request occurred)
            FileUtils.deleteQuietly(annotationRequestFile)
        }
    }

    private fun processAnnotationChangesFile(changesFile: File, writer: JsonWriter) {
        val startMs = System.currentTimeMillis()

        var inputStream: InputStream? = null
        var reader: JsonReader? = null

        try {
            inputStream = FileInputStream(changesFile)
            reader = JsonReader(InputStreamReader(inputStream, "UTF-8"))

            reader.beginObject()
            reader.nextName() // "syncAnnotationsIds"
            reader.beginObject()

            // read all elements for "syncAnnotationsIds"
            while (reader.peek() != JsonToken.END_OBJECT) {
                if (reader.peek() == JsonToken.NAME) {
                    processField(reader, writer)
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse annotations")
        } finally {
            IOUtils.closeQuietly(inputStream)
            IOUtils.closeQuietly(reader)
        }

        timeUtil.logTimeElapsedFromNow("ProcessAnnotationChanges", "Annotation Changes received[$changeCount] ", startMs)
    }

    @Throws(IOException::class)
    private fun processField(reader: JsonReader, writer: JsonWriter) {
        val currentField = reader.nextName()
        when (currentField) {
            "syncIds" -> processAnnotationChanges(reader, writer)
            "before" -> beforeTs = reader.nextString()
            "errors" -> processErrors(reader)
            "count" // ignored
                , "since" // "before" should be used... so "since" is ignored
                , "timeDiff" // not used
            -> reader.skipValue()
            else -> {
                Timber.w("Skipped value for [%s]", currentField)
                reader.skipValue()
            }
        }
    }

    @Throws(IOException::class)
    private fun processAnnotationChanges(reader: JsonReader, writer: JsonWriter) {
        reader.beginArray()

        val docVersionMap = HashMap<String, Int>()

        while (reader.peek() == JsonToken.BEGIN_OBJECT) {
            val syncId = gson.fromJson<DtoSyncId>(reader, DtoSyncId::class.java)

            val annotationRequest = DtoAnnotationRequest(syncId.aid)

            // check to see if there is a version
            if (syncId.hasDocId()) {
                val docId = syncId.docId

                // Try to get the doc version from the Map... otherwise do a database search
                var version: Int? = docVersionMap[docId]
                if (version == null) {
                    version = subitemMetadataManager.findDocVersionByDocId(syncId.docId)
                    docVersionMap.put(docId, version)
                }

                // Add the version
                if (version >= 0) {
                    annotationRequest.ver = version
                }
            }

            gson.toJson(annotationRequest, DtoAnnotationRequest::class.java, writer)
            changeCount++
        }

        reader.endArray()
    }

    @Throws(IOException::class)
    private fun processErrors(reader: JsonReader) {
        // start array
        reader.beginArray()

        // parse error array
        val token = reader.peek()
        while (token == JsonToken.BEGIN_OBJECT) {
            val error = gson.fromJson<DtoError>(reader, DtoError::class.java)
            errors.add(error)

            // mark annotation (that has an error) last modified timestamp updated so that it will try to sync again on next sync
            val errorId = error.id
            if (errorId != null) {
                annotationManager.syncUpdateLastModified(errorId)
            }
        }

        reader.endArray()
    }

    /**
     * Find any mismatches of annotations versions to content versions
     */
    @Throws(IOException::class)
    private fun addContentItemVersionChanges(writer: JsonWriter) {
        // Find pending contentItemsIds (content items that were recently downloaded and awaiting for annotations to be updated to match update)
        syncContentItemAnnotationsQueueItemManager.beginTransaction()
        val contentItemIds = syncContentItemAnnotationsQueueItemManager.findAllContentItemIds()

        // Go through each content Item and annotations for that content, to determine if we need to update any of those annotations
        for (contentItemId in contentItemIds) {
            val contentItemTitle = itemManager.findTitleById(contentItemId)

            // Find all the docIds for this content Item
            val contentItemDocVersions = subitemMetadataManager.findAllByItemId(contentItemId)

            var updateContentCount: Long = 0
            for (contentItemDocVersion in contentItemDocVersions) {
                val currentDocVersion = contentItemDocVersion.docVersion

                // Find any annotations that don't match the version of the recently updated content version
                val oldUniqueIds = annotationManager.findAllOldUniqueIds(contentItemDocVersion.docId, currentDocVersion)

                // If there is a updated needed, add it to the request
                if (!oldUniqueIds.isEmpty()) {
                    for (oldUniqueId in oldUniqueIds) {
                        val annotationRequest = DtoAnnotationRequest(oldUniqueId)
                        annotationRequest.ver = currentDocVersion

                        gson.toJson(annotationRequest, DtoAnnotationRequest::class.java, writer)
                        changeCount++

                        updateContentCount++
                    }
                }
            }
            Timber.i("%d annotation update(s) needed for [%s] for version", updateContentCount, contentItemTitle)
        }

        Timber.i("Finished checking for annotation updates for [%d] content item(s)", contentItemIds.size)

        // remove pending contentItemIds
        syncContentItemAnnotationsQueueItemManager.deleteAll()
        syncContentItemAnnotationsQueueItemManager.endTransaction(true)
    }

    fun hasErrors() = !errors.isEmpty()
}
