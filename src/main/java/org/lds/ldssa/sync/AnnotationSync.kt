package org.lds.ldssa.sync

import android.app.Application
import android.text.format.DateUtils
import com.crashlytics.android.Crashlytics
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import kotlinx.coroutines.experimental.launch
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.lds.ldsaccount.AuthStatus
import org.lds.ldsaccount.LDSAccountPrefs
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.R
import org.lds.ldssa.event.sync.AnnotationSyncFinishedEvent
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webservice.ServiceModule
import org.lds.ldssa.model.webservice.annotation.LDSAnnotationService
import org.lds.ldssa.model.webservice.annotation.dto.test.DtoTestSyncResults
import org.lds.ldssa.task.AnnotationFixTask
import org.lds.ldssa.ui.notification.AnnotationSyncNotification
import org.lds.ldssa.ui.notification.AuthenticationFailureNotification
import org.lds.ldssa.util.CatalogUpdateUtil
import org.lds.ldssa.util.FeedbackUtil
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.ldssa.util.UserdataDbUtil
import org.lds.ldssa.util.WebServiceUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ext.deleteQuietly
import org.lds.mobile.util.LdsNetworkUtil
import org.lds.mobile.util.LdsThreadUtil
import org.lds.mobile.util.LdsTimeUtil
import org.threeten.bp.LocalDateTime
import pocketbus.Bus
import retrofit2.Response
import timber.log.Timber
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Sync both Annotations and Folders (Notebooks)
 *
 *
 * Sync files:
 * folder-1-out.json (send changes to server and request changes from server)
 * folder-2-in.json (response from server with a list of changed folders)
 *
 *
 * annotation-1-out.json (send changes to server and request for changed IDs from server)
 * annotation-2-changes.json (received changed annotation IDs from server)
 * annotation-3-requested-out.json (list of changes and docId version for each annotation Id)
 * annotation-4-in.json (response from server with a list of changed annotations)
 */
@Singleton
class AnnotationSync @Inject
constructor(
        private val application: Application,
        private val prefs: Prefs,
        private val cc: CoroutineContextProvider,
        private val ldsAccountPrefs: LDSAccountPrefs,
        private val bus: Bus,
        private val annotationService: LDSAnnotationService,
        private val annotationSyncProcessor: AnnotationSyncProcessor,
        private val annotationChangeProcessor: AnnotationChangeProcessor,
        private val folderSyncProcessor: FolderSyncProcessor,
        private val networkUtil: LdsNetworkUtil,
        private val fileUtil: GLFileUtil,
        private val ldsAccountUtil: LDSAccountUtil,
        private val annotationManager: AnnotationManager,
        private val notebookManager: NotebookManager,
        private val notebookAnnotationManager: NotebookAnnotationManager,
        private val databaseManager: DatabaseManager,
        private val userdataDbUtil: UserdataDbUtil,
        private val annotationSyncNotification: AnnotationSyncNotification,
        private val timeUtil: LdsTimeUtil,
        private val authenticationFailureNotification: AuthenticationFailureNotification,
        private val webServiceUtil: WebServiceUtil,
        private val catalogUpdateUtil: CatalogUpdateUtil,
        private val annotationFixTaskProvider: Provider<AnnotationFixTask>,
        private val gson: Gson,
        private val threadUtil: LdsThreadUtil) {

    private val syncInProgress = AtomicBoolean(false)
    private var cleanupAfterSync = true

    /**
     * @param downloadRoleBasedCatalogPostSync check for content updates after sync
     */
    fun sync(downloadRoleBasedCatalogPostSync: Boolean = false) {
        threadUtil.assertNotMainThread("AnnotationSync.sync()")

        if (!syncInProgress.compareAndSet(false, true)) {
            Timber.w("Annotation Sync: FAILED (sync already in progress)")
            return
        }

        try {
            syncInternal(false)

            if (downloadRoleBasedCatalogPostSync) {
                catalogUpdateUtil.updateCatalog(false, true)
            }
        } finally {
            syncInProgress.set(false)
        }
    }

    private fun syncInternal(cleanSync: Boolean) {
        val startMs = System.currentTimeMillis()
        Timber.i("Annotation Sync: Started (cleanSync: %b)", cleanSync)

        var success = true

        try {
            if (!canPerformSync()) {
                return
            }

            FileUtils.deleteQuietly(fileUtil.annotationSyncDir)
            prefs.lastSyncErrorCount = 0
            annotationSyncProcessor.reset()
            folderSyncProcessor.reset()

            // write out changed folders and annotations to file
            annotationSyncNotification.updateNotification(R.string.sync_notification_gathering_changes)

            if (cleanSync) {
                prefs.resetAnnotationSyncPrefs()
            }

            folderSyncProcessor.folderChangesToJson(fileUtil.syncFolders1OutFile, prefs.foldersServerSinceTs, prefs.foldersLastSyncTs)
            annotationSyncProcessor.annotationChangesToJson(fileUtil.syncAnnotations1OutFile, prefs.annotationsServerSinceTs, prefs.annotationsLastSyncTs)

            // execute service call download send and receive folders and annotations
            if (!syncFoldersToServer() || !syncAnnotationsToServer()) {
                return
            }

            // if we are doing a clean sync, then wipe the existing user database
            if (cleanSync) {
                databaseManager.deleteDatabase(userdataDbUtil.currentDatabaseName)
            }

            // process downloaded files

            annotationSyncNotification.updateNotification(R.string.sync_notification_processing_changes)
            syncFolderJsonToDatabase(FileInputStream(fileUtil.syncFolders2InFile))
            syncAnnotationsJsonToDatabase()

            // used to determine which annotations have been modified since last sync
            // NOTE: Do this IMMEDIATELY after doing the writing of data from the sync service (everything after this point could make modifications that could cause a new sync)
            prefs.annotationsLastSyncTs = annotationSyncProcessor.newLastSyncTs
            prefs.foldersLastSyncTs = folderSyncProcessor.newLastSyncTs
            // record sync success
            prefs.updateLastAnnotationFullSyncTime()

            // after all folders and annotations are saved... associated the pk's for notebooks and annotations
            annotationManager.beginTransaction()
            notebookAnnotationManager.updateNotebookAnnotationAssociations()
            annotationManager.endTransaction(true)

            // update error prefs
            if (prefs.lastSyncErrorCount == 0) {
                prefs.setLatestAnnotationSyncResultSuccess()
            }
        } catch (e: Exception) {
            Timber.e(e, "Annotation sync error")

            // Don't show notification... because the e.getMessage() is too cryptic for users
            // annotationSyncFailureNotification.show(e.getMessage());

            prefs.setLatestAnnotationSyncResultError("Error: [" + e.message + "]")
            success = false
        } finally {
            // cleanup
            if (cleanupAfterSync) {
                FileUtils.deleteQuietly(fileUtil.annotationSyncDir)
            }

            // remove the notification bar update
            annotationSyncNotification.cancelNotification()

            timeUtil.logTimeElapsedFromNow("AnnotationSync", "Annotation Sync Finished", startMs)
            if (success) {
                bus.post(AnnotationSyncFinishedEvent(true, folderSyncProcessor.hasChanges() || annotationSyncProcessor.hasChanges()))
            } else {
                bus.post(AnnotationSyncFinishedEvent(false, false))
            }
        }
    }

    fun testSyncDataWithServer(): Boolean {
        val folderSuccess: Boolean
        val annotationSuccess: Boolean
        try {
            FileUtils.deleteQuietly(fileUtil.annotationSyncDir)

            // gather ALL folders and annotations
            folderSyncProcessor.folderChangesToJson(fileUtil.syncFoldersTest1OutFile, Prefs.DAWN_OF_TIME_TEXT, ThreeTenUtil.getDawnOfTime())
            annotationSyncProcessor.annotationChangesToJson(fileUtil.syncAnnotationsTest1OutFile, Prefs.DAWN_OF_TIME_TEXT, ThreeTenUtil.getDawnOfTime())

            // send ALL FOLDER data to the server
            val folderCall = annotationService.testFolders(RequestBody.create(WebServiceUtil.JSON_MEDIA_TYPE, fileUtil.syncFoldersTest1OutFile))
            val folderResponse = folderCall.execute()

            // verify
            folderSuccess = if (folderResponse.isSuccessful) {
                webServiceUtil.saveResponseToFile(folderResponse, fileUtil.syncFoldersTest2InFile)

                val bufferedReader = BufferedReader(FileReader(fileUtil.syncFoldersTest2InFile))
                parseTestResults("FOLDER", gson.fromJson(bufferedReader, DtoTestSyncResults::class.java))
            } else {
                logFailedResponse(folderResponse)
                false
            }

            // send ALL ANNOTATION data to the server
            val annotationCall = annotationService.testSendAnnotations(RequestBody.create(WebServiceUtil.JSON_MEDIA_TYPE, fileUtil.syncAnnotationsTest1OutFile))
            val annotationResponse = annotationCall.execute()

            // verify
            annotationSuccess = if (annotationResponse.isSuccessful) {
                webServiceUtil.saveResponseToFile(annotationResponse, fileUtil.syncAnnotationsTest2InFile)
                val bufferedReader = BufferedReader(FileReader(fileUtil.syncAnnotationsTest2InFile))
                parseTestResults("ANNOTATION", gson.fromJson(bufferedReader, DtoTestSyncResults::class.java))
            } else {
                logFailedResponse(annotationResponse)
                false
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to test sync data")
            return false
        }

        return folderSuccess && annotationSuccess
    }

    /**
     * @return true if there are NO errors
     */
    private fun parseTestResults(syncType: String, dtoTestSyncResults: DtoTestSyncResults): Boolean {
        val dtoSyncAnnotationsIds = dtoTestSyncResults.syncAnnotationsIds
        Timber.e("Sync Test Count (%s): %d", syncType, dtoSyncAnnotationsIds.count)

        if (!dtoSyncAnnotationsIds.errors.isEmpty()) {
            for (dtoTestSyncError in dtoSyncAnnotationsIds.errors) {
                Timber.e("Failed to test sync data syncType [%s] id [%s] message [%s]", syncType, dtoTestSyncError.id, dtoTestSyncError.message)
            }
            return false
        } else {
            Timber.e("Sync Test: SUCCESS (%s)", syncType)
        }

        return true
    }

    @Throws(IOException::class)
    fun syncFoldersToServer(): Boolean {
        Timber.d("Send/Receive folder changes...")
        annotationSyncNotification.updateNotification(R.string.syncing_notebooks)
        val startMs = System.currentTimeMillis()

        val call = annotationService.folders(RequestBody.create(WebServiceUtil.JSON_MEDIA_TYPE, fileUtil.syncFolders1OutFile))
        val response = call.execute()

        var success = false
        if (response.isSuccessful) {
            webServiceUtil.saveResponseToFile(response, fileUtil.syncFolders2InFile)
            success = true
        } else {
            logFailedResponse(response)
        }

        timeUtil.logTimeElapsedFromNow("syncFoldersToServer", "Send/Receive folder changes FINISHED [success: $success]", startMs)
        return success
    }

    @Throws(IOException::class)
    private fun syncAnnotationsToServer(): Boolean {
        Timber.d("Send/Receive annotation changes...")
        val startMs = System.currentTimeMillis()
        var success = false

        if (sendAnnotationsToServer()) {
            // - process json response file from the "send" request
            // - prepare json file for annotation "get" request
            annotationChangeProcessor.processChangesAndProduceRequestFile()
            success = if (annotationChangeProcessor.changeCount > 0) {
                requestAnnotationsFromServer()
            } else {
                Timber.d("No incoming changes... skipping request for incoming annotations")
                true
            }
        }

        timeUtil.logTimeElapsedFromNow("syncAnnotationsToServer", "Send/Receive annotation changes FINISHED [success: $success]", startMs)
        return success
    }


    @Throws(IOException::class)
    fun sendAnnotationsToServer(): Boolean {
        Timber.d("Sending annotation changes...")
        val startMs = System.currentTimeMillis()

        /*
         * Send: All annotation changes
         * Receive: All annotations that need to be requested
         */
        annotationSyncNotification.updateNotification(R.string.sync_notification_sending_changes)
        val call = annotationService.sendAnnotations(RequestBody.create(WebServiceUtil.JSON_MEDIA_TYPE, fileUtil.syncAnnotations1OutFile))
        val response = call.execute()

        var success = false
        if (response.isSuccessful) {
            annotationSyncNotification.updateNotification(R.string.sync_notification_receiving_update_list)
            webServiceUtil.saveResponseToFile(response, fileUtil.syncAnnotations2InFile)
            success = true
        } else {
            logFailedResponse(response)
        }

        timeUtil.logTimeElapsedFromNow("sendAnnotationsToServer", "Send annotation changes FINISHED [success: $success]", startMs)
        return success
    }

    @Throws(IOException::class)
    fun requestAnnotationsFromServer(): Boolean {
        Timber.d("Retrieving annotation changes...")
        val startMs = System.currentTimeMillis()

        /*
         * Send: A request for annotations that are new/changed (result from sendAnnotationsToServer)
         * Receive: All annotations to process
         */
        annotationSyncNotification.updateNotification(R.string.sync_notification_sending_update_request)
        val call = annotationService.getAnnotations(RequestBody.create(WebServiceUtil.JSON_MEDIA_TYPE, fileUtil.syncAnnotations3OutFile))
        val response = call.execute()

        var success = false
        if (response.isSuccessful) {
            annotationSyncNotification.updateNotification(R.string.sync_notification_receiving_changes)
            webServiceUtil.saveResponseToFile(response, fileUtil.syncAnnotations4InFile)
            success = true
        } else {
            logFailedResponse(response)
        }

        timeUtil.logTimeElapsedFromNow("requestAnnotationsFromServer", "Receive annotation changes FINISHED [success: $success]", startMs)
        return success
    }

    /**
     * ANNOTATIONS
     */

    @Throws(IOException::class)
    private fun syncAnnotationsJsonToDatabase() {
        val startMs = System.currentTimeMillis()

        // if there was no incoming changes, then Annotations4InFile will/should not exist
        if (annotationChangeProcessor.changeCount > 0) {
            val inputStream = FileInputStream(fileUtil.syncAnnotations4InFile)
            val reader = JsonReader(InputStreamReader(inputStream, "UTF-8"))

            annotationManager.beginTransaction()
            try {
                reader.beginObject()
                reader.nextName() // "syncAnnotations"
                reader.beginObject()

                while (reader.peek() == JsonToken.NAME) {
                    annotationSyncProcessor.processAnnotationField(reader)
                }

                reader.endObject()

            } catch (e: Exception) {
                Timber.e(e, "Failed to parse annotations")
                annotationManager.endTransaction(false)
            } finally {
                annotationManager.endTransaction(true)
                IOUtils.closeQuietly(inputStream)
                reader.close()
            }
        }

        // delete any left-over annotations with the deleted flag
        annotationManager.beginTransaction()
        annotationManager.purgeOrphanDeletedTrashed()
        annotationManager.endTransaction(true)

        // if all was successful, then record the since timestamp
        val beforeTs = annotationChangeProcessor.beforeTs
        Timber.i("Saving annotation server since: [serverTs: %s]", beforeTs)
        prefs.setAnnotationsServerSinceTs(beforeTs)  // set to "before" to ensure the correct "since" time for the next sync

        timeUtil.logTimeElapsedFromNow("syncAnnotationsJsonToDatabase", "Annotations " +
                "sent[" + annotationSyncProcessor.annotationsSent + "] " +
                "received[" + annotationSyncProcessor.annotationsReceived + "] " +
                "(added[" + annotationSyncProcessor.annotationsAdded + "] " +
                "updated[" + annotationSyncProcessor.annotationsUpdated + "] " +
                "removed[" + annotationSyncProcessor.annotationsRemoved + "])",
                startMs)

        showErrors()
    }


    private fun showErrors() {
        if (annotationChangeProcessor.hasErrors() || folderSyncProcessor.hasErrors() || annotationSyncProcessor.hasErrors()) {
            val fileLog = StringBuilder()
            logError(fileLog, "Errors from sync performed on: [${LocalDateTime.now()}]", false)

            val annotationChangeErrors = annotationChangeProcessor.errors
            logError(fileLog, "Annotation Change Errors: ${annotationChangeErrors.size}", false)
            for (errorItem in annotationChangeErrors) {
                var errorMessage = "    AppVersion: [${BuildConfig.VERSION_NAME}] Username: [${ldsAccountPrefs.username}]"
                val errorId = if (errorItem.id == null) "" else errorItem.id
                errorMessage += " Error Annotation Id: [${errorItem.id}] Message: [${errorItem.msg}] JSON: [\n${annotationManager.findAnnotationLogTextByUniqueId(errorId!!)}]\n"

                Crashlytics.log(1, "annotation-change", errorMessage)
                logError(fileLog, errorMessage, shouldPostError(errorItem.msg))
            }

            val annotationSyncErrors = annotationSyncProcessor.errors
            logError(fileLog, "Annotation Sync Errors: ${annotationSyncErrors.size}", false)
            for (errorItem in annotationSyncErrors) {
                var errorMessage = "    AppVersion: [${BuildConfig.VERSION_NAME}] Username: [${ldsAccountPrefs.username}]"
                val errorId = if (errorItem.id == null) "" else errorItem.id
                errorMessage += "    Error Annotation Id: [${errorItem.id}] Message: [${errorItem.msg}]  JSON: [\n${annotationManager.findAnnotationLogTextByUniqueId(errorId!!)}]\n"
                Crashlytics.log(1, "annotation-sync", errorMessage)
                logError(fileLog, errorMessage, shouldPostError(errorItem.msg))
            }

            val folderErrors = folderSyncProcessor.errors
            logError(fileLog, "Folder Errors: ${folderErrors.size}", false)
            for (errorItem in folderErrors) {
                var errorMessage = "    AppVersion: [${BuildConfig.VERSION_NAME}] Username: [${ldsAccountPrefs.username}]"
                errorMessage += "    Error folder Id: [${errorItem.id}] Message: [${errorItem.msg}]"
                Crashlytics.log(1, "folder-sync", errorMessage)
                logError(fileLog, errorMessage, shouldPostError(errorItem.msg))
            }

            val totalErrorCount = annotationChangeErrors.size + annotationSyncErrors.size + folderErrors.size
            prefs.updateLastErrorTime()
            prefs.lastSyncErrorCount = totalErrorCount
            prefs.setLatestAnnotationSyncResultError("Sync Error Count: $totalErrorCount on ${DateUtils.formatDateTime(application, System.currentTimeMillis(), FeedbackUtil.DATE_FORMAT_FLAGS)}")

            // try to fix the annotations in background
            launch(cc.commonPool) {
                annotationFixTaskProvider.get().execute()
            }

            // write file
            writeFeedbackLog(fileLog.toString())
        } else {
            Timber.d("Sync Errors: 0")
            fileUtil.feedbackLastSyncErrorFile.deleteQuietly() // no need for the error log file
        }
    }

    fun writeFeedbackLog(fileLog: String) {
        try {
            fileUtil.feedbackLastSyncErrorFile.writeText(fileLog)
        } catch (e: IOException) {
            Timber.e(e, "Failed to write error log file")
        }
    }

    private fun logError(fileLog: StringBuilder, message: String, postError: Boolean) {
        if (postError) {
            Timber.e(message)
        } else {
            Timber.w(message)
        }
        fileLog.append(message).append("\n")
    }

    private fun shouldPostError(message: String?): Boolean {
        // The sync service will not accept future dates as valid. We do not want to track them either.
        return !message!!.contains("[Invalid timestamp]")
    }

    /**
     * FOLDERS (Notebooks)
     */
    @Throws(IOException::class)
    private fun syncFolderJsonToDatabase(inputStream: InputStream) {
        val startMs = System.currentTimeMillis()

        val reader = JsonReader(InputStreamReader(inputStream, "UTF-8"))

        notebookManager.beginTransaction()
        try {
            reader.beginObject()
            reader.nextName() // "syncFolders"
            reader.beginObject()

            while (reader.peek() == JsonToken.NAME) {
                folderSyncProcessor.processFolderField(reader)
            }

            reader.endObject()

        } catch (e: Exception) {
            Timber.e(e, "Failed to parse folders")
            notebookManager.endTransaction(false)
        } finally {
            notebookManager.endTransaction(true)
            IOUtils.closeQuietly(inputStream)
            reader.close()
        }

        // delete any left-over annotations with the deleted flag
        notebookManager.beginTransaction()
        notebookManager.purgeOrphanDeletedTrashed()
        notebookManager.endTransaction(true)

        // if all was successful, then record the since timestamp
        Timber.i("Saving folder server since: [serverTs: %s]", folderSyncProcessor.beforeTs)
        prefs.foldersServerSinceTs = folderSyncProcessor.beforeTs  // set to "before" to ensure the correct "since" time for the next sync

        timeUtil.logTimeElapsedFromNow("syncFolderJsonToDb", "Folders sent[${folderSyncProcessor.foldersSent}] received[${folderSyncProcessor.foldersReceived}] (added[${folderSyncProcessor.foldersAdded}] updated[${folderSyncProcessor.foldersUpdated}] removed[${folderSyncProcessor.foldersRemoved}])", startMs)
    }

    private fun canPerformSync(): Boolean {
        if (!networkUtil.isConnected()) {
            prefs.setLatestAnnotationSyncResultError(application.getString(R.string.sync_error_no_network))
            Timber.w("SYNC FAILED: Not connected to the Internet")
            return false
        }

        if (!ldsAccountUtil.hasCredentials()) {
            prefs.setLatestAnnotationSyncResultError(application.getString(R.string.sync_error_invalid_credentials))
            Timber.w("SYNC FAILED: Not signed in")
            return false
        }

        if (ldsAccountUtil.checkAuthenticatedConnection(ServiceModule.CURRENT_ENVIRONMENT) == AuthStatus.INVALID_CREDENTIALS) {
            prefs.setLatestAnnotationSyncResultError(application.getString(R.string.sync_error_invalid_credentials))

            Timber.w("SYNC FAILED: Invalid Credentials")
            authenticationFailureNotification.show()
            return false
        }

        if (ldsAccountUtil.checkAuthenticatedConnection(ServiceModule.CURRENT_ENVIRONMENT) != AuthStatus.SUCCESS) {
            Timber.w("SYNC FAILED: Unable to authenticate")
            return false
        }

        // Authentication was successful. Dismiss the notification if it is showing.
        authenticationFailureNotification.dismiss()

        return true
    }

    private fun logFailedResponse(response: Response<*>) {
        val message = "Failed request code: [" + response.code() + "] [" + response.message() + "]"

        Crashlytics.log(1, "message", message)
        Crashlytics.log(1, "request Body", requestBodyToStringFromResponse(response))
        Timber.e(message)

        // try to fix the annotations
        annotationFixTaskProvider.get().execute()

    }

    private fun requestBodyToStringFromResponse(response: Response<*>): String {
        val raw = response.raw()
        return if (raw != null) {
            bodyToString(raw.request())
        } else {
            "Raw == null"
        }
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            val body = copy.body()
            body?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: Exception) {
            "Failed to get read request body.  Reason [" + e.message + "]"
        }

    }

    val isSyncInProgress: Boolean
        get() = syncInProgress.get()

    fun setCleanupAfterSync(cleanupAfterSync: Boolean) {
        this.cleanupAfterSync = cleanupAfterSync
    }

    val annotationsReceived: Long
        get() = annotationSyncProcessor.annotationsReceived.toLong()

    val annotationsAdded: Long
        get() = annotationSyncProcessor.annotationsAdded.toLong()

    val annotationsUpdated: Long
        get() = annotationSyncProcessor.annotationsUpdated.toLong()

    val annotationsRemoved: Long
        get() = annotationSyncProcessor.annotationsRemoved.toLong()
}
