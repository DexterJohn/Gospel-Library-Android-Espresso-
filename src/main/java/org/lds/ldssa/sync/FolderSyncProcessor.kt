package org.lds.ldssa.sync

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.lds.ldssa.model.database.types.AnnotationChangeType
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotation
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webservice.ServiceModule
import org.lds.ldssa.model.webservice.annotation.dto.DtoError
import org.lds.ldssa.model.webservice.annotation.dto.folder.DtoFolder
import org.lds.ldssa.model.webservice.annotation.dto.folder.DtoFolderChange
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.mobile.util.LdsTimeUtil
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.ArrayList
import javax.inject.Inject

class FolderSyncProcessor @Inject
constructor(private val timeUtil: LdsTimeUtil, private val notebookManager: NotebookManager, private val notebookAnnotationManager: NotebookAnnotationManager, private val gson: Gson) {

    var foldersSent = 0
        private set
    var foldersReceived = 0
        private set
    var foldersAdded = 0
        private set
    var foldersUpdated = 0
        private set
    var foldersRemoved = 0
        private set
    val errors = ArrayList<DtoError>()
    // value sent from server
    var beforeTs = ""
        private set
    // value to be used in preferences, and to be used to determined changed folders
    var newLastSyncTs: LocalDateTime = LocalDateTime.now()
        private set

    fun reset() {
        foldersSent = 0
        foldersReceived = 0
        foldersAdded = 0
        foldersUpdated = 0
        foldersRemoved = 0

        errors.clear()
    }

    @Throws(IOException::class)
    private fun folderChangesToDb(reader: JsonReader) {
        reader.beginArray()

        while (reader.peek() == JsonToken.BEGIN_OBJECT) {
            val dtoFolderChange = gson.fromJson<DtoFolderChange>(reader, DtoFolderChange::class.java)
            folderChangesToDb(dtoFolderChange)
        }

        reader.endArray()
    }

    private fun folderChangesToDb(dtoFolderChange: DtoFolderChange) {
        foldersReceived++
        val uniqueId = dtoFolderChange.folderId
        val changeType = dtoFolderChange.changeType

        if (changeType != null) {
            when (changeType) {
                AnnotationChangeType.NEW -> if (uniqueId != null) {
                    val deletedExisting = notebookManager.purgeNotebook(uniqueId)
                    saveNewFolder(dtoFolderChange.folder)

                    // log
                    if (deletedExisting) {
                        foldersUpdated++
                    } else {
                        foldersAdded++
                    }
                }
                AnnotationChangeType.TRASH, AnnotationChangeType.DELETE -> if (uniqueId != null && notebookManager.purgeNotebook(uniqueId)) {
                    foldersRemoved++ // log
                }
                else -> throw IllegalStateException("Unknown change type for folder id: " + uniqueId!!)
            }
        }
    }

    private fun saveNewFolder(dtoFolder: DtoFolder?) {
        val notebook = dtoFolder!!.createNotebookFromDto()
        notebookManager.syncSave(notebook)

        // Folder Annotations
        val dtoFolderOrder = dtoFolder.order
        if (dtoFolderOrder != null) {
            for ((order, annotationId) in dtoFolderOrder.id.withIndex()) {
                val folderAnnotation = NotebookAnnotation()
                folderAnnotation.notebookId = notebook.id
                folderAnnotation.uniqueAnnotationId = annotationId
                folderAnnotation.displayOrder = order

                notebookAnnotationManager.save(folderAnnotation)
            }
        }
    }

    @Throws(IOException::class)
    fun folderChangesToJson(outputFile: File, serverSince: String, deviceChangesSince: LocalDateTime) {
        val startMs = System.currentTimeMillis()

        Timber.d("Gathering folder updates since (serverTs: [%s])...", serverSince)

        val writer = JsonWriter(FileWriter(outputFile))
        ServiceModule.setupJsonWriter(writer)

        // Header
        writer.beginObject()

        writer.name("syncFolders")
        writer.beginObject()
        //        writer.name("@xmlns).value(http://lds.org/schema/folder"); // NOSONAR tebbs "not needed"
        //        writer.name("schemaDate).value(2011-04-13T13:00:00.000-06:00"); // NOSONAR tebbs "not needed"
        writer.name("since").value(serverSince)
        writer.name("clientTime").value(ThreeTenUtil.formatIso(LocalDateTime.now()))

        // if this is a first time sync, then don't bother to get the deleted annotations
        if (serverSince == Prefs.DAWN_OF_TIME_TEXT) {
            writer.name("syncStatus").value("normal")
        }

        // Content
        writeFolders(writer, deviceChangesSince)

        // Footer
        writer.endObject()
        writer.endObject()
        writer.close()

        timeUtil.logTimeElapsedFromNow("folderToJson", "Finished gathering folder updates", startMs)
    }

    @Throws(IOException::class)
    private fun writeFolders(writer: JsonWriter, deviceChangesSince: LocalDateTime) {
        // Save the current timestamp to make sure that we have overlap from findAllChangesSince (just in case the user is making changes during the sync)
        newLastSyncTs = LocalDateTime.now()

        val modifiedFolders = notebookManager.findCursorAllChangesSince(deviceChangesSince)
        if (modifiedFolders.isEmpty()) {
            return
        }

        foldersSent = 0

        Timber.d("Notebook change count: %d", modifiedFolders.size)

        writer.name("changes")
        writer.beginArray()
        for (modifiedNotebook in modifiedFolders) {
            val annotationIdDisplayOrder = notebookAnnotationManager.findAnnotationIdDisplayOrder(modifiedNotebook.id)
            val dtoFolderChange = DtoFolderChange(modifiedNotebook, annotationIdDisplayOrder)

            // write to file
            gson.toJson(dtoFolderChange, DtoFolderChange::class.java, writer)

            foldersSent++
        }

        writer.endArray()
    }

    @Throws(IOException::class)
    fun processFolderField(reader: JsonReader) {
        val currentField = reader.nextName()
        when (currentField) {
            "changes" -> folderChangesToDb(reader)
            "before" -> beforeTs = reader.nextString()
            "errors" -> processFolderErrors(reader)
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
    private fun processFolderErrors(reader: JsonReader) {
        reader.beginArray()

        val token = reader.peek()
        while (token == JsonToken.BEGIN_OBJECT) {
            val error = gson.fromJson<DtoError>(reader, DtoError::class.java)
            errors.add(error)

            // mark folder (that has an error) last modified timestamp updated so that it will try to sync again on next sync

            val errorId = error.id
            if (errorId != null) {
                notebookManager.syncUpdateLastModified(errorId)
            }
        }

        reader.endArray()
    }

    fun hasChanges() = foldersAdded > 0 || foldersUpdated > 0 || foldersRemoved > 0

    fun hasErrors() =  !errors.isEmpty()
}
