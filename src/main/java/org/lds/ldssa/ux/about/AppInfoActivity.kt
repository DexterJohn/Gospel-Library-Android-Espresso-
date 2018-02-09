package org.lds.ldssa.ux.about

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.format.DateUtils
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_app_info.*
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.math.NumberUtils
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.DeviceUtil
import org.lds.ldssa.util.FeedbackUtil
import org.lds.ldssa.util.FeedbackUtil.DATE_FORMAT_FLAGS
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.util.LdsDeviceUtil
import org.lds.mobile.util.LdsUiUtil
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class AppInfoActivity : AppCompatActivity() {

    @Inject
    lateinit var catalogMetaDataManager: CatalogMetaDataManager
    @Inject
    lateinit var downloadedItemManager: DownloadedItemManager
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var notebookManager: NotebookManager
    @Inject
    lateinit var notebookAnnotationManager: NotebookAnnotationManager
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var deviceUtil: DeviceUtil
    @Inject
    lateinit var feedbackUtil: FeedbackUtil
    @Inject
    lateinit var fileUtil: GLFileUtil
    @Inject
    lateinit var uiUtil: LdsUiUtil
    @Inject
    lateinit var ldsDeviceUtil: LdsDeviceUtil
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var contentMetaDataManager: ContentMetaDataManager
    @Inject
    lateinit var highlightManager: HighlightManager
    @Inject
    lateinit var noteManager: NoteManager
    @Inject
    lateinit var linkManager: LinkManager
    @Inject
    lateinit var bookmarkManager: BookmarkManager
    @Inject
    lateinit var tagManager: TagManager

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)

        showAppInfo()

        includeContentVersionCheckBox.setOnClickListener { showAppInfo() }
    }

    override fun onResume() {
        super.onResume()
        analytics.postScreen(Analytics.Screen.APP_INFO)
    }

    private fun showAppInfo() {
        val sb = StringBuilder()

        sb.append("=== System Info ===\n")
        sb.append("Android Version: ").append(Build.VERSION.RELEASE).append(" (SDK: ").append(Build.VERSION.SDK_INT).append(")").append("\n")
        sb.append("Kernel: ").append(System.getProperty("os.version")).append("\n")
        sb.append("Google Play Services: ").append(deviceUtil.getPackageVersionName(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)).append("\n")
        sb.append("Android System WebView: ").append(deviceUtil.getPackageVersionName(DeviceUtil.ANDROID_SYSTEM_WEBVIEW_PACKAGE)).append("\n")
        sb.append("Chrome Version: ").append(deviceUtil.getPackageVersionName(DeviceUtil.CHROME_APP_PACKAGE)).append("\n")
        sb.append("Device Manufacturer: ").append(Build.MANUFACTURER).append("\n")
        sb.append("Device Brand: ").append(Build.BRAND).append("\n")
        sb.append("Device Display: ").append(ldsDeviceUtil.getDisplaySizeText(this)).append("\n")
        sb.append("Screen density: ").append(uiUtil.getDeviceDisplayDensity(this)).append("\n")
        sb.append("Theme: ").append(prefs.generalDisplayTheme.htmlScheme).append("\n")
        sb.append('\n')

        sb.append("=== Gospel Library ===\n")
        sb.append("App Id: ").append(BuildConfig.APPLICATION_ID).append("\n")
        sb.append("Version: ").append(BuildConfig.VERSION_NAME).append("\n")
        sb.append("Version Code: ").append(BuildConfig.VERSION_CODE).append("\n")
        sb.append("Build Time: ").append(DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DATE_FORMAT_FLAGS)).append("\n")
        sb.append("Install Source: ").append(ldsDeviceUtil.installSource).append("\n")
        sb.append("Limit Mobile Network Use: ").append(prefs.isMobileNetworkLimited).append("\n")
        sb.append("Preferred Audio Voice: ").append(prefs.audioVoice.prefValue).append("\n")
        sb.append('\n')

        sb.append("=== Release Status ===\n")
        sb.append("Default Content Environment: ").append(BuildConfig.DEFAULT_CONTENT_SERVER_TYPE).append("\n")
        sb.append("Current Content Environment: ").append(prefs.contentServerType).append("\n")
        sb.append("Annotation Environment: ").append(BuildConfig.ANNOTATION_SERVER_TYPE).append("\n")
        sb.append("Minimum Android Version: SDK ").append(BuildConfig.MIN_SUPPORTED_SDK).append("\n")
        sb.append('\n')

        sb.append("=== Annotations ===\n")
        sb.append("Total Annotations: " + annotationManager.findCount()).append("\n")
        sb.append("Unsynced annotations: " + annotationManager.findUnsyncdCount(prefs.annotationsLastSyncTs)).append("\n")
        sb.append("\n")
        sb.append("Bookmarks: " + bookmarkManager.findCount()).append("\n")
        sb.append("Highlights: " + highlightManager.findDistinctHighlightCountByAnnotationId()).append(" / Segments: " + highlightManager.findCount()).append("\n")
        sb.append("Notes: " + noteManager.findCount()).append("\n")
        sb.append("Notebooks: " + notebookManager.findCount()).append("\n")
        sb.append("Notes in notebooks: " + notebookAnnotationManager.findCount()).append("\n")
        sb.append("Unique Tags: " + tagManager.findDistinctNameCount()).append(" / Annotations with tags: " + tagManager.findCount()).append("\n")
        sb.append("Annotations with Links: " + linkManager.findCount()).append("\n")
        sb.append('\n')

        sb.append("=== Catalog ===\n")
        sb.append("Current Catalog Version: ").append(catalogMetaDataManager.findVersion()).append("\n")
        sb.append("Force Catalog Version: ").append(prefs.developerCatalogVersion).append("\n")
        sb.append('\n')

        sb.append("=== Content ===\n")
        sb.append("Downloaded Items: ").append(downloadedItemManager.findCount()).append("\n")
        sb.append("Content Directory: ").append(fileUtil.contentItemBaseDirectory.absolutePath).append("\n")

        val downloadsDir = fileUtil.downloadsDir
        if (downloadsDir != null) {
            sb.append("Download Directory: ").append(downloadsDir.absolutePath).append("\n")
        } else {
            sb.append("Download Directory: FAILED:  Storage State = [").append(Environment.getExternalStorageState()).append("]").append("\n")
        }
        sb.append('\n')

        if (includeContentVersionCheckBox.isChecked) {
            val allInstalledItems = downloadedItemManager.findAll()
            for (installedItem in allInstalledItems) {
                val contentItemId = installedItem.contentItemId

                val contentItem = itemManager.findByRowId(installedItem.contentItemId)

                val installedVersion = installedItem.installedVersion
                val catalogVersion = contentItem?.version?.toLong() ?: -1L
                val contentDbVersion = contentMetaDataManager.findItemPackageVersion(contentItemId)
                val contentVersion = NumberUtils.toLong(contentDbVersion)

                if (installedVersion == catalogVersion && installedVersion == contentVersion) {
                    sb.append("Name [").append(contentItem?.title ?: "Not found in Catalog.Item table").append("] ")
                    sb.append("Version: [").append(installedVersion).append("] ")
                    sb.append("\n")
                } else {
                    sb.append("WARNING!  VERSION MISMATCH!  ")
                    sb.append("Id: [").append(contentItemId).append("] ")
                    sb.append("Catalog Name: [").append(contentItem?.title ?: "Not found in Catalog.Item table").append("] ")
                    sb.append("Catalog Version: [").append(catalogVersion).append("] ")
                    sb.append("Content Version: [").append(contentVersion).append("] ")
                    sb.append("GL Installed Version: [").append(installedItem.installedVersion).append("] ")
                    sb.append("\n\n")
                }
            }
            sb.append('\n')
        }

        sb.append("=== Sync ===\n")
        sb.append("Last Sync Success: " + feedbackUtil.lastAnnotationSync).append("\n")
        sb.append("Last Sync Result: " + prefs.latestAnnotationSyncResult).append("\n")
        sb.append("Last Download Error: " + prefs.lastDownloadFailedErrorMessage).append("\n\n")
        sb.append('\n')

        sb.append("=== Last Error ===\n")
        sb.append("\nLog File: \n\n" + loadErrorLogFromFile())

        sb.append('\n')

        infoTextView.text = sb.toString()
    }

    private fun loadErrorLogFromFile(): String {
        try {
            return FileUtils.readFileToString(fileUtil.feedbackLastSyncErrorFile, Charset.defaultCharset())
        } catch (e: IOException) {
            return ""
        }
    }
}
