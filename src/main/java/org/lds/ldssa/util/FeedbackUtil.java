package org.lds.ldssa.util;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.text.format.DateUtils;

import com.google.android.gms.common.GoogleApiAvailability;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldssa.BuildConfig;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.util.LdsFeedbackUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FeedbackUtil {
    private static final long ERROR_DETAILS_EXPIRATION = TimeUnit.HOURS.toMillis(12);
    public static final int DATE_FORMAT_FLAGS = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;

    private final Prefs prefs;
    private final LDSAccountPrefs ldsAccountPrefs;
    private final Application application;
    private final LdsFeedbackUtil feedbackUtil;
    private final LanguageNameManager languageNameManager;
    private final GLFileUtil fileUtil;
    private final CatalogMetaDataManager catalogMetaDataManager;
    private final DownloadedItemManager downloadedItemManager;
    private final AnnotationManager annotationManager;
    private final NotebookManager notebookManager;
    private final NotebookAnnotationManager notebookAnnotationManager;
    private final ScreenUtil screenUtil;
    private final DeviceUtil deviceUtil;
    private final HighlightManager highlightManager;
    private final NoteManager noteManager;
    private final LinkManager linkManager;
    private final BookmarkManager bookmarkManager;
    private final TagManager tagManager;

    @Inject
    public FeedbackUtil(Prefs prefs, LDSAccountPrefs ldsAccountPrefs, Application application, LdsFeedbackUtil feedbackUtil,
                        LanguageNameManager languageNameManager, GLFileUtil fileUtil, CatalogMetaDataManager catalogMetaDataManager,
                        DownloadedItemManager downloadedItemManager, AnnotationManager annotationManager, NotebookManager notebookManager, NotebookAnnotationManager notebookAnnotationManager, ScreenUtil screenUtil,
                        DeviceUtil deviceUtil, HighlightManager highlightManager, NoteManager noteManager, LinkManager linkManager, BookmarkManager bookmarkManager, TagManager tagManager) {
        this.prefs = prefs;
        this.ldsAccountPrefs = ldsAccountPrefs;
        this.application = application;
        this.feedbackUtil = feedbackUtil;
        this.languageNameManager = languageNameManager;
        this.fileUtil = fileUtil;
        this.catalogMetaDataManager = catalogMetaDataManager;
        this.downloadedItemManager = downloadedItemManager;
        this.annotationManager = annotationManager;
        this.notebookManager = notebookManager;
        this.notebookAnnotationManager = notebookAnnotationManager;
        this.screenUtil = screenUtil;
        this.deviceUtil = deviceUtil;
        this.highlightManager = highlightManager;
        this.noteManager = noteManager;
        this.linkManager = linkManager;
        this.bookmarkManager = bookmarkManager;
        this.tagManager = tagManager;
    }

    public void sendAttachmentBugReport(Activity activity, long screenId) {
        sendAttachmentBugReport(activity, null, screenId);
    }

    public void sendAttachmentBugReport(Activity activity, String prePopulatedText, long screenId) {
        String username = ldsAccountPrefs.getUsername();
        String usernameToShow = StringUtils.isEmpty(username) ? "Unknown User" : username;

        String subject = application.getString(R.string.feedback) + " " + BuildConfig.APPLICATION_ID + " " + BuildConfig.VERSION_NAME + " (" + usernameToShow + ")";

        //Creates the object that holds the basic app info for the feedback
        LdsFeedbackUtil.AppInfo appInfo = new LdsFeedbackUtil.AppInfo();
        appInfo.setBuildTime(BuildConfig.BUILD_TIME);
        appInfo.setPackage(BuildConfig.APPLICATION_ID);
        appInfo.setVersionName(BuildConfig.VERSION_NAME);
        appInfo.setVersionCode(BuildConfig.VERSION_CODE);
        appInfo.setFileProviderAuthority(activity.getString(R.string.file_provider));

        //Creates the object that holds the additional feedback information
        LdsFeedbackUtil.FeedbackInfo feedbackInfo = new LdsFeedbackUtil.FeedbackInfo();
        feedbackInfo.setEmailAddress(new String[]{BuildConfig.FEEDBACK_EMAIL});
        feedbackInfo.setSubject(subject);

        if (StringUtils.isNotBlank(prePopulatedText)) {
            feedbackInfo.setPostScript(feedbackInfo.getPostScript() + prePopulatedText);
        }

        //Creates a list of additional details to add to the feedback
        List<LdsFeedbackUtil.Detail> additionalDetails = new LinkedList<>();

        // System
        additionalDetails.add(new LdsFeedbackUtil.Detail("Google Play Services", deviceUtil.getPackageVersionName(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
        if (deviceUtil.isPackageInstalled(DeviceUtil.ANDROID_SYSTEM_WEBVIEW_PACKAGE)) {
            additionalDetails.add(new LdsFeedbackUtil.Detail("Android System WebView Version", deviceUtil.getPackageVersionName(DeviceUtil.ANDROID_SYSTEM_WEBVIEW_PACKAGE)));
        } else {
            additionalDetails.add(new LdsFeedbackUtil.Detail("Android System WebView", "Not installed"));
        }
        additionalDetails.add(new LdsFeedbackUtil.Detail("Chrome", deviceUtil.getPackageVersionName(DeviceUtil.CHROME_APP_PACKAGE)));

        // Gospel Library specific
        additionalDetails.add(new LdsFeedbackUtil.Detail("Content Language", getCatalogLanguageName(screenId)));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Catalog Version", Long.toString(catalogMetaDataManager.findVersion())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Theme", prefs.getGeneralDisplayTheme().getHtmlScheme()));
        additionalDetails.add(new LdsFeedbackUtil.Detail("LDSAccount Username", usernameToShow));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Total Annotations", Long.toString(annotationManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Unsynced annotations", Long.toString(annotationManager.findUnsyncdCount(prefs.getAnnotationsLastSyncTs()))));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Bookmarks", Long.toString(bookmarkManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Highlights", Long.toString(highlightManager.findDistinctHighlightCountByAnnotationId())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Notes", Long.toString(noteManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Notebooks", Long.toString(notebookManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Notes in notebooks", Long.toString(notebookAnnotationManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Unique tags", Long.toString(tagManager.findDistinctNameCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Annotations with tags", Long.toString(tagManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Annotations with links", Long.toString(linkManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Highlight Segment Count", Long.toString(highlightManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Downloaded Content Items (Books)", Long.toString(downloadedItemManager.findCount())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Content Directory: ", fileUtil.getContentItemBaseDirectory().getAbsolutePath()));

        File downloadsDir = fileUtil.getDownloadsDir();
        if (downloadsDir != null) {
            additionalDetails.add(new LdsFeedbackUtil.Detail("Download Directory: ", downloadsDir.getAbsolutePath()));
        } else {
            additionalDetails.add(new LdsFeedbackUtil.Detail("Download Directory: FAILED:  Storage State = ", Environment.getExternalStorageState()));
        }
        additionalDetails.add(new LdsFeedbackUtil.Detail("Limit Mobile Network Use", Boolean.toString(prefs.isMobileNetworkLimited())));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Preferred Audio Voice", prefs.getAudioVoice().getPrefValue()));


        ScreenHistoryItem screenHistoryItem = screenUtil.getLastVisibleScreenHistoryItem();
        if (screenHistoryItem != null) {
            additionalDetails.add(new LdsFeedbackUtil.Detail("Last visible title", screenHistoryItem.getTitle()));
            additionalDetails.add(new LdsFeedbackUtil.Detail("Last visible item details", screenHistoryItem.getSourceType() + " / " + screenHistoryItem.getExtrasJson()));
        }

        additionalDetails.add(new LdsFeedbackUtil.Detail("Content Server", prefs.getContentServerType().toString()));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Annotation Sync Server", BuildConfig.ANNOTATION_SERVER_TYPE.toString()));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Last Sync Success", getLastAnnotationSync()));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Last Sync Result", prefs.getLatestAnnotationSyncResult()));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Last Crash", getLastCrash()));
        additionalDetails.add(new LdsFeedbackUtil.Detail("Last Download Failed", prefs.getLastDownloadFailedErrorMessage()));

        // create attachments list
        List<File> attachments = new ArrayList<>();

        // copy feedback file to external cache dir so that it can be attached to the email
        File lastSyncErrorFile = fileUtil.copyFeedbackLastSyncErrorFileToExternal();
        if (lastSyncErrorFile != null) {
            attachments.add(lastSyncErrorFile);
        }

        feedbackUtil.submitFeedback(activity, feedbackInfo, appInfo, additionalDetails, attachments);
    }

    private String getCatalogLanguageName(long screenId) {
        return languageNameManager.findLanguageName(screenUtil.getLanguageIdForScreen(screenId));
    }

    public String getLastAnnotationSync() {
        long lastSyncTime = prefs.getLastAnnotationFullSyncTime();
        return lastSyncTime <= 0 ? "Never" : DateUtils.formatDateTime(application, lastSyncTime, DATE_FORMAT_FLAGS);
    }

    private String getLastCrash() {
        String lastCrash = "Never";
        long lastErrorTime = prefs.getLastErrorTime();

        if (lastErrorTime > 0) {
            lastCrash = DateUtils.formatDateTime(application, lastErrorTime, DATE_FORMAT_FLAGS);

            if (System.currentTimeMillis() - lastErrorTime < ERROR_DETAILS_EXPIRATION) {
                lastCrash += "\n" + prefs.getLastErrorDetails();
            } else {
                lastCrash += "\n" + prefs.getLastErrorMessage();
            }
        }

        return lastCrash;
    }

}