package org.lds.ldssa.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.event.download.DownloadCompletedEvent;
import org.lds.ldssa.model.database.catalog.item.Item;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItem;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.types.CatalogItemSourceType;
import org.lds.ldssa.model.database.types.ItemMediaType;
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItem;
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.util.LdsZipUtil;
import org.threeten.bp.LocalDateTime;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import pocketbus.Bus;
import timber.log.Timber;

public class ContentItemUpdateUtil {
    private static final int MAX_ERROR_LOG_ZIPFILE_SIZE = 10000;

    private final Application application;
    private final Prefs prefs;
    private final Bus bus;
    private final Analytics analytics;
    private final GLFileUtil fileUtil;
    private final LdsZipUtil zipUtil;
    private final ItemManager itemManager;
    private final DownloadedItemManager downloadedItemManager;
    private final DownloadQueueItemManager downloadQueueItemManager;
    private final DownloadManagerHelper downloadManagerHelper;
    private final GLDownloadManager glDownloadManager;
    private final LibraryItemManager libraryItemManager;
    private final AnnotationSyncScheduler annotationSyncScheduler;
    private final SyncContentItemAnnotationsQueueItemManager syncAnnotationsForContentItemManager;
    private final ContentItemUtil contentItemUtil;

    @Inject
    public ContentItemUpdateUtil(Application application, Prefs prefs, Bus bus, Analytics analytics, GLFileUtil fileUtil, LdsZipUtil zipUtil, ItemManager itemManager, DownloadedItemManager downloadedItemManager, DownloadQueueItemManager downloadQueueItemManager, DownloadManagerHelper downloadManagerHelper, GLDownloadManager glDownloadManager, LibraryItemManager libraryItemManager, AnnotationSyncScheduler annotationSyncScheduler, SyncContentItemAnnotationsQueueItemManager syncAnnotationsForContentItemManager, ContentItemUtil contentItemUtil) {
        this.application = application;
        this.prefs = prefs;
        this.bus = bus;
        this.analytics = analytics;
        this.fileUtil = fileUtil;
        this.zipUtil = zipUtil;
        this.itemManager = itemManager;
        this.downloadedItemManager = downloadedItemManager;
        this.downloadQueueItemManager = downloadQueueItemManager;
        this.downloadManagerHelper = downloadManagerHelper;
        this.glDownloadManager = glDownloadManager;
        this.libraryItemManager = libraryItemManager;
        this.annotationSyncScheduler = annotationSyncScheduler;
        this.syncAnnotationsForContentItemManager = syncAnnotationsForContentItemManager;
        this.contentItemUtil = contentItemUtil;
    }

    public void installDownloadedContentItem(long androidDownloadId) {
        boolean success = false;
        long contentItemId = 0;
        try {
            DownloadQueueItem queueItem = downloadQueueItemManager.findByAndroidDownloadId(androidDownloadId);
            if (queueItem == null) {
                Timber.w("queueItem == null for androidDownloadId [%d]", androidDownloadId);
                return;
            }

            contentItemId = queueItem.getContentItemId();
            String destinationUri = downloadManagerHelper.getDestinationUri(androidDownloadId);

            if (destinationUri.isEmpty()) {
                Timber.e("destinationUri is empty for: %s", queueItem.getSourceUri());
                return;
            }

            Timber.i("Processing download for [%s]", destinationUri);

            // Install downloaded content
            if (installContentItem(androidDownloadId, contentItemId, destinationUri, queueItem.getCatalogItemSourceType())) {
                success = true;
            } else {
                showToast(application.getString(R.string.download_file_failed, itemManager.findTitleById(contentItemId)));
            }
        } finally {
            downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId);
            bus.post(new DownloadCompletedEvent(contentItemId, success, androidDownloadId));
        }
    }

    // todo remove with move to Kotlin and use ToastUtil
    private void showToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(application, message, Toast.LENGTH_SHORT).show());
    }

    public boolean installContentItem(long androidDownloadId, long contentItemId, String contentItemZipUri, CatalogItemSourceType catalogItemSourceType) {
        File file = StringUtils.isNotBlank(contentItemZipUri) ? new File(URI.create(contentItemZipUri)) : null;
        return installContentItem(androidDownloadId, contentItemId, file, catalogItemSourceType);
    }

    public boolean installContentItem(long androidDownloadId, long contentItemId, File contentItemZipFile, CatalogItemSourceType catalogItemSourceType) {
        Item contentItem = itemManager.findByRowId(contentItemId);

        if (contentItem == null) {
            Timber.e("Cannot install content because cannot find item id: [%d]", contentItemId);
            return false;
        }

        // 1. Validate there is actually downloaded content
        if (contentItemZipFile == null || !contentItemZipFile.exists()) {
            String zipFilename = contentItemZipFile != null ? contentItemZipFile.getAbsolutePath() : "null";
            Timber.e("Cannot install content for zip file that DOES NOT exist [" + zipFilename + "]");
            return false;
        }

        // 2. Extract the database to a TEMP folder, then move to the final location
        if (!extractDatabase(androidDownloadId, contentItemId, contentItemZipFile)) {
            return false;
        }

        // 3. Indicate that the content was downloaded and installed
        saveDownloadItem(contentItem, catalogItemSourceType);

        // 4. sync annotations for update book (as needed)
        startAnnotationSync(contentItemId);

        // Finalize
        logAnalytics(contentItem);

        return true;
    }

    private boolean extractDatabase(long androidDownloadId, long contentItemId, @NonNull File sourceZipFile) {
        String sourceZipFilename = sourceZipFile.getAbsolutePath();
        String tempDestDirectoryName = fileUtil.getContentItemTempDir(contentItemId).getAbsolutePath();
        File tempDestDirectory = new File(tempDestDirectoryName);

        try {
            Timber.d("Extracting [%s] to [%s]", sourceZipFilename, tempDestDirectoryName);

            // make sure the temp directory is removed (if exists)
            if (tempDestDirectory.exists()) {
                FileUtils.deleteQuietly(tempDestDirectory);
            }

            // 1. Extract all the contents from the zip file
            long startCompressMs = System.currentTimeMillis();
            if (!zipUtil.unZip(sourceZipFilename, tempDestDirectoryName)) {
                return false;
            }
            Timber.d("Unzip time for [%s]: %d ms", sourceZipFilename, (System.currentTimeMillis() - startCompressMs));

            // 2. Verify that the expected files exist
            File tempContentDatabaseFile = fileUtil.getContentItemTempDatabase(contentItemId);
            if (!tempContentDatabaseFile.exists()) {
                String errorMessage = "Failed to extract database from zip file: [" + sourceZipFilename + "]  error: [" + tempContentDatabaseFile.getAbsolutePath() + "  does NOT exist after unzip]";

                logExtractError(errorMessage, sourceZipFile);

                return false;
            }

            // 3. Swap content
            File destDirectory = fileUtil.getContentItemDir(contentItemId);

            // a. close and delete the existing content database (MUST be done BEFORE saveDownloadItem(...)... so that we delete the OLD content, not the new
            contentItemUtil.deleteContentItem(contentItemId);

            try {
                // b. Move temp directory to final directory
                FileUtils.moveDirectory(tempDestDirectory, destDirectory);
            } catch (IOException e) {
                Timber.e(e, "Failed to move contentItem temp directory [%s] to destination directory [%s]", tempDestDirectoryName, destDirectory.getAbsolutePath());
                return false;
            }
        } finally {
            // 4. Cleanup

            // delete the zip file
            FileUtils.deleteQuietly(new File(sourceZipFilename));

            // delete the temp directory
            FileUtils.deleteQuietly(tempDestDirectory);

            // remove download entry (this also may try to delete the zip file)
            glDownloadManager.removeDownload(androidDownloadId);
        }

        return true;
    }

    public void saveDownloadItem(Item contentItem, CatalogItemSourceType catalogItemSourceType) {
        DownloadedItem downloadedItem = downloadedItemManager.findByContentItemId(contentItem.getId());
        if (downloadedItem == null) {
            downloadedItem = new DownloadedItem();
            downloadedItem.setContentItemId(contentItem.getId());
            downloadedItem.setCatalogItemSourceType(catalogItemSourceType);
            downloadedItem.setExternalId(contentItem.getExternalId());
            downloadedItem.setLibrarySectionId(libraryItemManager.findItemSectionId(contentItem.getId()));
        }

        downloadedItem.setInstalledVersion(contentItem.getVersion());
        downloadedItemManager.beginTransaction();
        downloadedItemManager.save(downloadedItem);
        downloadedItemManager.endTransaction(true);
    }

    private void startAnnotationSync(long contentItemId) {
        // add task to sync annotations for this content item
        SyncContentItemAnnotationsQueueItem queueItem = new SyncContentItemAnnotationsQueueItem();
        queueItem.setContentItemId(contentItemId);
        syncAnnotationsForContentItemManager.save(queueItem);

        annotationSyncScheduler.scheduleSync();
    }

    private void logAnalytics(Item contentItem) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, String.valueOf(contentItem.getLanguageId()));
        attributes.put(Analytics.Attribute.CONTENT_URI, contentItem.getUri());
        attributes.put(Analytics.Attribute.CONTENT_TYPE, ItemMediaType.CONTENT.name());
        analytics.postEvent(Analytics.Event.ITEM_INSTALLED, attributes);
    }

    private void logExtractError(String errorMessage, File sourceZipFile) {
        String sourceZipContent;

        // Check to make sure the file exists
        if (sourceZipFile == null || !sourceZipFile.exists()) {
            Crashlytics.log(1, "sourceZipFileName", sourceZipFile != null ? sourceZipFile.getAbsolutePath() : "null file");
            Crashlytics.log(1, "extract-content-database-content", errorMessage);
            Timber.e("log extract error: file is null or does not exist");
            return;
        }

        // if possible, try to get text for what was downloaded (in case it is a error message from an Internet service provider)
        if (FileUtils.sizeOf(sourceZipFile) < MAX_ERROR_LOG_ZIPFILE_SIZE) {
            try {
                sourceZipContent = FileUtils.readFileToString(sourceZipFile, Charset.defaultCharset());
            } catch (IOException e) {
                sourceZipContent = "Failed to read source zip content" + e.getMessage();
                Timber.e(e, "Failed to read source zip content");
            }
        } else {
            sourceZipContent = "Too large to read";
        }

        // send to log
        Timber.e("%s source zip content [%s]", errorMessage, sourceZipContent);

        // send to crashlytics
        Crashlytics.log(1, "extract-content-database-msg", sourceZipContent);
        Crashlytics.log(1, "extract-content-database-content", errorMessage);
        Timber.e("extract-content-database error");

        // save for feedback (only save a portion of the error text to the prefs)
        prefs.setLastDownloadFailedErrorMessage("DATE ["+ LocalDateTime.now() +"]   ERROR MESSAGE ["+errorMessage+"]   ERROR CONTENT ["+  StringUtils.substring(sourceZipContent, 0, 100)+"]");
    }
}
