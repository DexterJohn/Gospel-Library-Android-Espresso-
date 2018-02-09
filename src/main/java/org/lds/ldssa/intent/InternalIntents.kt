package org.lds.ldssa.intent

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.CheckContentVersionsTask
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager
import org.lds.ldssa.model.database.content.navcollection.NavCollection
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webview.content.dto.DtoHighlightInfo
import org.lds.ldssa.model.webview.content.dto.DtoImage
import org.lds.ldssa.ui.activity.AudioSettingsActivity
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.activity.BookmarkRouterActivity
import org.lds.ldssa.ui.activity.ContentSourceActivity
import org.lds.ldssa.ui.activity.HighlightPaletteActivity
import org.lds.ldssa.ui.activity.HighlightSelectionActivity
import org.lds.ldssa.ui.activity.ImageViewerActivity
import org.lds.ldssa.ui.activity.LanguageChangeActivity
import org.lds.ldssa.ui.activity.NoteActivity
import org.lds.ldssa.ui.activity.ScreenSettingsActivity
import org.lds.ldssa.ui.activity.SettingsActivity
import org.lds.ldssa.ui.activity.StartupActivity
import org.lds.ldssa.ui.activity.VideoPlayerActivity
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.ldssa.util.UriUtil
import org.lds.ldssa.ux.about.AnnotationInfoActivity
import org.lds.ldssa.ux.about.AppInfoActivity
import org.lds.ldssa.ux.annotations.AnnotationsActivity
import org.lds.ldssa.ux.annotations.SingleAnnotationActivity
import org.lds.ldssa.ux.annotations.links.LinkContentActivity
import org.lds.ldssa.ux.annotations.links.LinksActivity
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionActivity
import org.lds.ldssa.ux.annotations.notes.NotesActivity
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionActivity
import org.lds.ldssa.ux.catalog.CatalogDirectoryActivity
import org.lds.ldssa.ux.content.directory.ContentDirectoryActivity
import org.lds.ldssa.ux.content.item.ContentActivity
import org.lds.ldssa.ux.content.item.ContentRequestCode
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsActivity
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsActivity
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryActivity
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaActivity
import org.lds.ldssa.ux.locations.LocationsActivity
import org.lds.ldssa.ux.search.SearchActivity
import org.lds.ldssa.ux.search.SearchContract
import org.lds.ldssa.ux.search.SearchMode
import org.lds.ldssa.ux.signin.SignInActivity
import org.lds.ldssa.ux.study.items.StudyItemsActivity
import org.lds.ldssa.ux.study.plans.StudyPlansActivity
import org.lds.ldssa.ux.tips.lists.TipListPagerActivity
import org.lds.ldssa.ux.tips.pages.TipsPagerActivity
import org.lds.ldssa.ux.welcome.WelcomeActivity
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class InternalIntents @Inject
constructor(private val application: Application,
            private val screenUtil: ScreenUtil,
            private val prefs: Prefs,
            private val languageManager: LanguageManager,
            private val languageUtil: LanguageUtil,
            private val annotationManager: AnnotationManager,
            private val bookmarkManager: BookmarkManager,
            private val subItemMetadataManager: SubItemMetadataManager,
            private val navCollectionManager: NavCollectionManager,
            private val downloadQueueItemManager: DownloadQueueItemManager,
            private val contentItemUtil: ContentItemUtil,
            private val downloadManager: GLDownloadManager,
            private val itemManager: ItemManager,
            private val subItemManager: SubItemManager,
            private val uriUtil: UriUtil,
            private val contentMetaDataManager: ContentMetaDataManager,
            private val checkContentVersionsTaskProvider: Provider<CheckContentVersionsTask>,
            private val libraryCollectionManager: LibraryCollectionManager,
            private val toastUtil: ToastUtil) {

    fun showSettings(activity: Activity?, screenId: Long) {
        activity ?: return
        val intent = Intent(activity, SettingsActivity::class.java)
        startBaseActivityIntent(activity, intent, screenId, false)
    }

    fun showScreenSettings(activity: Activity?) {
        activity ?: return
        val intent = Intent(activity, ScreenSettingsActivity::class.java)
        activity.startActivity(intent)
    }

    fun showAudioSettings(context: Context) {
        val intent = Intent(context, AudioSettingsActivity::class.java)
        context.startActivity(intent)
    }

    fun showTags(activity: Activity?, screenId: Long, tagText: String) {
        showAnnotations(activity, screenId, tagText = tagText)
    }

    fun showNotebook(activity: Activity?, screenId: Long, notebookId: Long) {
        showAnnotations(activity, screenId, notebookId = notebookId)
    }

    private fun showAnnotations(activity: Activity?, screenId: Long, tagText: String = "", notebookId: Long = 0L) {
        showAnnotations(activity, screenId, tagText, notebookId, false)
    }

    fun showAnnotations(activity: Activity?, screenId: Long, tagText: String = "", notebookId: Long = 0L, fromClickOnScreen: Boolean = false) {
        activity ?: return
        val intent = Intent(activity, AnnotationsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        with(AnnotationsActivity.IntentOptions) {
            intent.notebookId = notebookId
            intent.tagText = tagText
        }
        startBaseActivityIntent(activity, intent, screenId, true, fromClickOnScreen)
    }

    @JvmOverloads
    fun showAnnotationView(activity: Activity, screenId: Long, annotationId: Long, fromClickOnScreen: Boolean, highlightNoteText: String = "") {
        val intent = Intent(activity, SingleAnnotationActivity::class.java)
        with(SingleAnnotationActivity.IntentOptions) {
            intent.annotationId = annotationId
            intent.highlightNoteText = highlightNoteText
        }
        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    fun showAppInfo(activity: Activity?) {
        activity ?: return
        val intent = Intent(activity, AppInfoActivity::class.java)
        activity.startActivity(intent)
    }

    fun showAnnotationInfo(activity: Activity, annotationId: Long) {
        AnnotationInfoActivity.start(activity) {
            it.annotationId = annotationId
        }
    }

    fun showContentSource(activity: Activity?, contentItemId: Long, subItemId: Long) {
        activity ?: return
        val intent = Intent(activity, ContentSourceActivity::class.java)
        intent.putExtra(ContentSourceActivity.EXTRA_CONTENT_ITEM_ID, contentItemId)
        intent.putExtra(ContentSourceActivity.EXTRA_SUB_ITEM_ID, subItemId)
        activity.startActivity(intent)
    }

    fun showImage(activity: Activity?, screenId: Long, image: DtoImage) {
        activity ?: return
        val intent = Intent(activity, ImageViewerActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        intent.putExtra(ImageViewerActivity.EXTRA_IMAGE, image)
        activity.startActivity(intent)
    }

    fun showManageCustomCollections(activity: Activity, screenId: Long) {
        val intent = Intent(activity, CustomCollectionsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        activity.startActivityForResult(intent, COLLECTIONS_REQUEST)
    }

    fun showAddToCustomCollection(activity: Activity, screenId: Long, contentItemExternalIds: Array<String?>?) {
        val intent = Intent(activity, CustomCollectionsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        with(CustomCollectionsActivity.IntentOptions) {
            intent.contentItemExternalIds = contentItemExternalIds
        }
        activity.startActivityForResult(intent, COLLECTIONS_REQUEST)
    }

    fun showSignIn(activity: Activity) {
        val intent = Intent(activity, SignInActivity::class.java)
        activity.startActivity(intent)
    }

    fun showWelcomeScreen(activity: Activity, welcomeTipIds: List<Long>) {
        WelcomeActivity.start(activity) { intent ->
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.tipIds = welcomeTipIds as ArrayList<Long>
        }
    }

    fun showAllTipDetails(activity: Activity?, tipId: Long, tipIds: List<Long>) {
        activity ?: return
        TipsPagerActivity.start(activity) { intent ->
            intent.showTipId = tipId
            intent.tipIds = tipIds as ArrayList<Long>
        }
    }

    fun showStudyPlans(activity: Activity, screenId: Long, selectedTab: Int = prefs.studyPlansPagerPosition, fromClickOnScreen: Boolean = false) {
        val intent = Intent(activity, StudyPlansActivity::class.java)
        with(StudyPlansActivity.IntentOptions) {
            intent.selectedTab = selectedTab
        }
        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    fun showStudyItems(activity: Activity, screenId: Long, studyPlanId: Long, scrollPosition: Int = 0, fromClickOnScreen: Boolean = false) {
        val intent = Intent(activity, StudyItemsActivity::class.java)
        with(StudyItemsActivity.IntentOptions) {
            intent.studyPlanId = studyPlanId
            intent.scrollPosition = scrollPosition
        }
        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    fun showTipList(activity: Activity, screenId: Long, fromClickOnScreen: Boolean) {
        showTipList(activity, screenId, 0, fromClickOnScreen)
    }

    fun showTipList(activity: Activity, screenId: Long, selectedTab: Int, fromClickOnScreen: Boolean) {
        val intent = Intent(activity, TipListPagerActivity::class.java)
        with (TipListPagerActivity.IntentOptions) {
            intent.selectedTab = selectedTab
        }
        startBaseActivityIntent(activity, intent, screenId, false, fromClickOnScreen)
    }

    // ********** Restart app to StartupActivity ************
    fun restart() {
        val intent = Intent(application, StartupActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(intent)
    }

    // ********** Catalog Directory **********
    fun relaunch(activity: Activity) {
        val intent = Intent(activity, StartupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    @JvmOverloads
    fun showCatalogRoot(activity: Activity, screenId: Long, fromClickOnScreen: Boolean = false) {
        val intent = createCatalogRootIntent(activity, screenUtil.getLanguageIdForScreen(screenId))
        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    private fun createCatalogRootIntent(activity: Activity, languageId: Long): Intent {
        val intent = Intent(activity, CatalogDirectoryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        val collectionId = languageManager.findRootCollectionIdByLanguageId(languageId)
        with(CatalogDirectoryActivity.IntentOptions) {
            intent.collectionId = collectionId
        }

        return intent
    }

    fun createLanguageChangeCatalogRootIntent(activity: Activity, screenId: Long, languageId: Long, uri: String?): Intent {
        val intent = Intent(activity, LanguageChangeActivity::class.java)
        intent.putExtra(LanguageChangeActivity.EXTRA_SCREEN_ID, screenId)
        intent.putExtra(LanguageChangeActivity.EXTRA_LANGUAGE_ID, languageId)
        intent.putExtra(LanguageChangeActivity.EXTRA_URI, uri)
        return intent
    }

    @JvmOverloads
    fun showCatalog(activity: Activity, screenId: Long, collectionId: Long, scrollToItemId: Long = 0L, fromClickOnScreen: Boolean = false) {
        showCatalog(activity, screenId, collectionId, 0, scrollToItemId, fromClickOnScreen)
    }

    fun showCatalog(activity: Activity, screenId: Long, collectionId: Long, scrollPosition: Int, scrollToContentItemId: Long = 0L, fromClickOnScreen: Boolean) {
        val intent = Intent(activity, CatalogDirectoryActivity::class.java)
        with(CatalogDirectoryActivity.IntentOptions) {
            intent.collectionId = collectionId
            intent.scrollPosition = scrollPosition
            intent.scrollToContentItemId = scrollToContentItemId
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }

        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    @JvmOverloads
    fun showCustomCollection(activity: Activity, screenId: Long, customCollectionId: Long, fromClickOnScreen: Boolean = false) {
        showCustomCollection(activity, screenId, customCollectionId, 0, fromClickOnScreen)
    }

    fun showCustomCollection(activity: Activity, screenId: Long, customCollectionId: Long, scrollPosition: Int, fromClickOnScreen: Boolean) {
        val intent = Intent(activity, CustomCollectionDirectoryActivity::class.java)
        with(CustomCollectionDirectoryActivity.IntentOptions) {
            intent.customCollectionId = customCollectionId
            intent.scrollPosition = scrollPosition
        }
        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    // ********** Content Directory **********

    fun showContentDirectory(activity: FragmentActivity, screenId: Long, contentItemId: Long, promptDownload: Boolean, fromClickOnScreen: Boolean, referrer: Analytics.Referrer) {
        showContentDirectory(activity, screenId, contentItemId, NavCollection.ROOT_NAV_COLLECTION_ID, promptDownload, fromClickOnScreen, referrer)
    }

    fun showContentDirectory(activity: FragmentActivity, screenId: Long, contentItemId: Long, navCollectionUri: String, promptDownload: Boolean, scrollPosition: Int, fromClickOnScreen: Boolean, referrer: Analytics.Referrer) {
        val navCollectionId = navCollectionManager.findIdByUri(contentItemId, navCollectionUri)
        showContentDirectory(activity, screenId, contentItemId, navCollectionId, promptDownload, scrollPosition, fromClickOnScreen, referrer)
    }

    fun showContentDirectory(activity: FragmentActivity, screenId: Long, contentItemId: Long, navCollectionId: Long, promptDownload: Boolean, fromClickOnScreen: Boolean, referrer: Analytics.Referrer) {
        showContentDirectory(activity, screenId, contentItemId, navCollectionId, promptDownload, 0, fromClickOnScreen, referrer)
    }

    private fun showContentDirectory(activity: FragmentActivity, screenId: Long, contentItemId: Long, navCollectionId: Long, promptDownload: Boolean, scrollPosition: Int, fromClickOnScreen: Boolean, referrer: Analytics.Referrer) {
        if (downloadQueueItemManager.isDownloading(contentItemId, ItemMediaType.CONTENT)) {
            showCurrentDownloads(activity, screenId)
            return
        }

        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            // check to see if the content version matches the catalog
            if (isContentWrongVersion(contentItemId)) {
                checkContentVersionsTaskProvider.get().execute()
            }

            var navId = navCollectionId
            if (navCollectionId == NavCollection.ROOT_NAV_COLLECTION_ID) {
                navId = navCollectionManager.findRootCollectionId(contentItemId)
            }
            if (navCollectionManager.findCountOfItemsByNavCollectionId(contentItemId, navId) == 1L) {
                val contentIntentParams = ContentIntentParams(screenId, contentItemId, navCollectionManager.findFirstItemByNavCollectionId(contentItemId, navId), referrer)
                contentIntentParams.fromClickOnScreen = fromClickOnScreen
                showContent(activity, contentIntentParams)


            } else {
                val intent = Intent(activity, ContentDirectoryActivity::class.java)
                with(ContentDirectoryActivity.IntentOptions) {
                    intent.contentItemId = contentItemId
                    intent.navCollectionId = navCollectionId
                    intent.scrollPosition = scrollPosition
                }
                startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
            }
        } else { // content item is not on device
            if (promptDownload) {
                showDownloadContentItemDialog(activity, contentItemId, screenId, false)
            } else {
                downloadManager.downloadContent(contentItemId)
            }
        }
    }

    private fun isContentWrongVersion(contentItemId: Long): Boolean {
        val catalogVersion = itemManager.findVersionById(contentItemId)
        val contentVersion = java.lang.Long.parseLong(contentMetaDataManager.findItemPackageVersion(contentItemId))
        return catalogVersion != contentVersion
    }

    fun verifyContentDownloaded(activity: Activity, contentItemId: Long, screenId: Long): Boolean {
        return if (!contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            showDownloadContentItemDialog(activity, contentItemId, screenId, false)
            false
        } else {
            true
        }
    }

    /**
     * Prompts the user to determine if they want to download the selected content

     * @param contentItemId The id for the content item to download
     */
    private fun showDownloadContentItemDialog(activity: Activity, contentItemId: Long, screenId: Long, showDownloadInCatalog: Boolean) {
        val title = itemManager.findTitleById(contentItemId)

        MaterialDialog.Builder(activity)
                .title(R.string.download_item)
                .content(activity.getString(R.string.download_item_message, title))
                .positiveText(R.string.download)
                .negativeText(R.string.cancel)
                .onPositive { _, _ ->
                    downloadManager.downloadContent(contentItemId)
                    if (showDownloadInCatalog) {
                        val collectionId = libraryCollectionManager.findDefaultIdByContentItemId(contentItemId)
                        showCatalog(activity, screenId, collectionId, contentItemId, false)
                    }
                }
                .onNegative { _, _ ->
                    if (showDownloadInCatalog) {
                        // Can't stay in limited activity (LanguageChange or UriRouter) so go back
                        activity.onBackPressed()
                    }
                }
                .show()
    }

    /**
     * Prompts the user to determine if they want to download the selected content and then redirects them to the bookmarks

     * @param contentItemId The id for the content item to download
     */
    private fun showBookmarkWidgetDownloadItemDialog(activity: FragmentActivity, contentItemId: Long, screenId: Long) {
        val title = itemManager.findTitleById(contentItemId)

        MaterialDialog.Builder(activity)
                .title(R.string.download_item)
                .content(activity.getString(R.string.download_item_message, title))
                .positiveText(R.string.download)
                .negativeText(R.string.cancel)
                .onPositive { _, _ ->
                    downloadManager.downloadContent(contentItemId)
                    showBookmarks(activity, screenId)
                }
                .onNegative { _, _ ->
                    showBookmarks(activity, screenId)
                }
                .show()
    }

    // ********** Content **********

    fun showContentForAnnotation(activity: FragmentActivity, screenId: Long, contentItemId: Long, subItemId: Long, scrollToParagraphAid: String?, fromClickOnScreen: Boolean, referrer: Analytics.Referrer) {
        val contentIntentParams = ContentIntentParams(screenId, contentItemId, subItemId, referrer)
        contentIntentParams.scrollToParagraphAid = scrollToParagraphAid
        contentIntentParams.fromClickOnScreen = fromClickOnScreen
        showContent(activity, contentIntentParams)
    }

    fun showContentFromBookmark(activity: Activity?, screenId: Long, bookmarkId: Long, fromWidget: Boolean) {
        activity ?: return
        val bookmark = bookmarkManager.findByRowId(bookmarkId) ?: return

        val docId = annotationManager.findDocIdById(bookmark.annotationId) ?: return

        val subItemMetadata = subItemMetadataManager.findByDocId(docId) ?: return

        val contentIntentParams = ContentIntentParams(screenId, subItemMetadata.itemId, subItemMetadata.subitemId, Analytics.Referrer.BOOKMARK)
        contentIntentParams.scrollToParagraphAid = bookmark.paragraphAid
        contentIntentParams.isFromWidget = fromWidget
        showContent(activity, contentIntentParams)
    }

    fun showContentFromBookmarkWidget(context: Context, bookmarkId: Long) {
        val intent = Intent(context, BookmarkRouterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(BookmarkRouterActivity.EXTRA_BOOKMARK_ID, bookmarkId)
        context.startActivity(intent)
    }

    fun showContentForLink(activity: FragmentActivity, screenId: Long,
                           contentItemId: Long, subItemId: Long,
                           scrollToParagraphAid: String?,
                           markParagraphAids: Array<String?>?,
                           fromClickOnScreen: Boolean, startNewTask: Boolean, referrer: Analytics.Referrer) {
        val contentIntentParams = ContentIntentParams(screenId, contentItemId, subItemId, referrer)
        contentIntentParams.scrollToParagraphAid = scrollToParagraphAid
        contentIntentParams.markParagraphAids = markParagraphAids
        contentIntentParams.fromClickOnScreen = fromClickOnScreen
        contentIntentParams.startNewTask = startNewTask
        showContent(activity, contentIntentParams)
    }

    fun showContent(context: Activity?, params: ContentIntentParams) {
        context ?: return
        val contentItemId = params.contentItemId

        if (downloadQueueItemManager.isDownloading(contentItemId, ItemMediaType.CONTENT)) {
            if (params.showRootCatalogOnFail) {
                Timber.w("Failed to show content because content is downloading... showing root catalog instead")
                showCatalogRoot(context, params.screenId)
                return
            } else {
                Timber.w("Failed to show content because content is downloading")
                // do nothing
                return
            }
        }

        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            if (params.subItemId <= 0) {
                if (params.subItemUri?.isBlank() != false) {
                    Timber.e("MISSING subItemId or subItemUri")
                    return
                }

                val subItemId = subItemManager.findIdByUri(contentItemId, params.subItemUri!!)
                params.subItemId = subItemId
            }

            val intent = getShowContentIntent(context, params)
            startBaseActivityIntent(context, intent, params.screenId, true, params.fromClickOnScreen)
        } else if (context is FragmentActivity) { // content item is not on device
            if (params.isFromWidget) {
                showBookmarkWidgetDownloadItemDialog(context,
                        contentItemId,
                        params.subItemId)
                return
            }
            showDownloadContentItemDialog(context, contentItemId, params.screenId, false)
        }
    }

    /**
     * This is used for the `showContent(...)` methods and by the
     * Media Notification Providers to create the correct
     * PendingIntent

     * @return The Intent to show content
     */
    fun getShowContentIntent(context: Context, params: ContentIntentParams): Intent {
        val intent = Intent(context, ContentActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, params.screenId)

        with(ContentActivity.IntentOptions) {
            intent.contentItemId = params.contentItemId
            intent.subItemId = params.subItemId
            intent.analyticsReferrer = params.referrer

            // optional intent items
            if (params.scrollPosition > 0) {
                intent.scrollPosition = params.scrollPosition
            }

            if (!params.findOnPageText.isNullOrBlank()) {
                intent.findOnPageText = params.findOnPageText ?: ""
            }

            if (!params.markTextOffsetSqliteOffsets.isNullOrBlank()) {
                intent.markTextSqliteOffsets = params.markTextOffsetSqliteOffsets ?: ""
            }

            intent.markTextSqliteExactPhrase = params.markTextOffsetSqliteExactPhrase

            params.markParagraphAids?.let { markParagraphAids ->
                if (markParagraphAids.isNotEmpty()) {
                    intent.markParagraphAids = markParagraphAids
                }
            }

            if (!params.scrollToParagraphAid.isNullOrBlank()) {
                intent.scrollToParagraphAid = params.scrollToParagraphAid ?: ""
            }

            if (params.isFromWidget) {
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

            if (params.startNewTask) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }

        return intent
    }

    fun showBookmarks(activity: Activity, screenId: Long) {
        val intent = Intent(activity, LocationsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)

        activity.startActivity(intent)
    }

    fun showBookmarksFromContent(fragment: Fragment, screenId: Long, contentItemId: Long, subItemId: Long, referenceParagraphAid: String = "") {
        val intent = Intent(fragment.activity, LocationsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        with(LocationsActivity.IntentOptions) {
            intent.contentItemId = contentItemId
            intent.subItemId = subItemId
            intent.referenceParagraphAid = referenceParagraphAid
        }

        fragment.startActivityForResult(intent, ContentRequestCode.REQUEST_BOOKMARK.ordinal)
    }

    @SuppressLint("InlinedApi") //isScreensInOverview() already checks the build version
    fun showCurrentDownloads(context: Context) {
        val intent = Intent(context, CurrentDownloadsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenUtil.lastVisibleScreenId)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (prefs.isScreensInOverview) {
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        }

        context.startActivity(intent)
    }

    fun showCurrentDownloads(activity: Activity, screenId: Long) {
        val intent = Intent(activity, CurrentDownloadsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        activity.startActivity(intent)
    }

    fun showHighlightPalette(fragment: Fragment, screenId: Long, annotation: Annotation) {
        val intent = Intent(fragment.activity, HighlightPaletteActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        with(HighlightPaletteActivity.IntentOptions) {
            intent.annotation = annotation
        }

        fragment.startActivityForResult(intent, ContentRequestCode.REQUEST_HIGHLIGHT_PALETTE.ordinal)
    }

    @JvmOverloads
    fun editNote(activity: Activity, screenId: Long, annotationId: Long, addJournal: Boolean = false, notebookId: Long = 0) {
        activity.startActivityForResult(editNoteIntent(activity, screenId, annotationId, addJournal, notebookId), ContentRequestCode.REQUEST_NOTE.ordinal)
    }

    fun editNote(fragment: Fragment, screenId: Long, annotationId: Long, addJournal: Boolean = false, notebookId: Long = 0) {
        fragment.activity?.let { context ->
            fragment.startActivityForResult(editNoteIntent(context, screenId, annotationId, addJournal, notebookId), ContentRequestCode.REQUEST_NOTE.ordinal)
        }
    }

    private fun editNoteIntent(context: Context, screenId: Long, annotationId: Long, addJournal: Boolean = false, notebookId: Long = 0): Intent {
        val noteIntent = Intent(context, NoteActivity::class.java)
        noteIntent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        noteIntent.putExtra(NoteActivity.EXTRA_ANNOTATION_ID, annotationId)
        noteIntent.putExtra(NoteActivity.EXTRA_NOTEBOOK_ID, notebookId)
        noteIntent.putExtra(NoteActivity.EXTRA_ADD_JOURNAL, addJournal)

        return noteIntent
    }

    fun showTagSelection(activity: Activity, screenId: Long, annotationId: Long) {
        activity.startActivityForResult(showTagSelectionIntent(activity, screenId, annotationId), ContentRequestCode.REQUEST_TAG.ordinal)
    }

    fun showTagSelection(fragment: Fragment, screenId: Long, annotationId: Long) {
        fragment.activity?.let { context ->
            fragment.startActivityForResult(showTagSelectionIntent(context, screenId, annotationId), ContentRequestCode.REQUEST_TAG.ordinal)
        }
    }

    private fun showTagSelectionIntent(context: Context, screenId: Long, annotationId: Long): Intent {
        val tagIntent = Intent(context, TagSelectionActivity::class.java)
        tagIntent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        with(TagSelectionActivity.IntentOptions) {
            tagIntent.annotationId = annotationId
        }
        return tagIntent
    }

    fun showNotebookSelection(activity: Activity, screenId: Long, annotationId: Long) {
        activity.startActivityForResult(showNotebookSelectionIntent(activity, screenId, annotationId), ContentRequestCode.REQUEST_NOTEBOOK_SELECTION.ordinal)
    }

    fun showNotebookSelection(fragment: Fragment, screenId: Long, annotationId: Long) {
        fragment.activity?.let { context ->
            fragment.startActivityForResult(showNotebookSelectionIntent(context, screenId, annotationId), ContentRequestCode.REQUEST_NOTEBOOK_SELECTION.ordinal)
        }
    }

    private fun showNotebookSelectionIntent(context: Context, screenId: Long, annotationId: Long): Intent {
        val intent = Intent(context, NotebookSelectionActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        with (NotebookSelectionActivity.IntentOptions) {
            intent.annotationId = annotationId
        }

        return intent
    }

    @JvmOverloads
    fun showNotes(context: Context, screenId: Long, selectedTabIndex: Int = prefs.notesPagerPosition, fromClickOnScreen: Boolean = false) {
        val intent = Intent(context, NotesActivity::class.java)
        with(NotesActivity.IntentOptions) {
            intent.selectedTabIndex = selectedTabIndex
        }
        startBaseActivityIntent(context, intent, screenId, fromClickOnScreen)
    }

    fun showMultipleAnnotationSelection(fragment: Fragment, highlightInfo: List<DtoHighlightInfo>, requestCode: Int) {
        val intent = Intent(fragment.activity, HighlightSelectionActivity::class.java)
        with(HighlightSelectionActivity.IntentOptions) {
            intent.highlights = highlightInfo as ArrayList<DtoHighlightInfo>
        }
        fragment.startActivityForResult(intent, requestCode)
    }

    fun showVideoPlayer(context: Context?, screenId: Long, contentItemId: Long, subItemId: Long, videoId: String?, referrer: Analytics.Referrer) {
        context ?: return
        val intent = Intent(context, VideoPlayerActivity::class.java)
        with(VideoPlayerActivity.IntentOptions) {
            intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
            intent.contentItemId = contentItemId
            intent.subItemId = subItemId
            intent.videoId = videoId ?: ""
            intent.analyticsReferrer = referrer
        }
        context.startActivity(intent)
    }

    fun showMediaFileManager(context: Context, screenId: Long) {
        val intent = Intent(context, DownloadedMediaActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        context.startActivity(intent)
    }

    fun showLinks(activity: Activity, screenId: Long, annotationId: Long) {
        activity.startActivityForResult(showLinksIntent(activity, screenId, annotationId), ContentRequestCode.REQUEST_LINK.ordinal)
    }

    fun showLinks(fragment: Fragment, screenId: Long, annotationId: Long) {
        fragment.activity?.let { context ->
            fragment.startActivityForResult(showLinksIntent(context, screenId, annotationId), ContentRequestCode.REQUEST_LINK.ordinal)
        }
    }

    private fun showLinksIntent(activity: Activity, screenId: Long, annotationId: Long): Intent {
        val intent = Intent(activity, LinksActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        intent.putExtra(LinksActivity.EXTRA_ANNOTATION_ID, annotationId)
        return intent
    }

    @JvmOverloads
    fun showSearchActivity(activity: Activity?, screenId: Long,
                           searchText: String = "",
                           mode: SearchMode = SearchMode.HISTORY,
                           modeCollectionId: Long = 0,
                           modeContentItemId: Long = 0,
                           glContentContext: GLContentContext,
                           scrollPosition: Int = 0,
                           fromClickOnScreen: Boolean = false) {
        activity ?: return

        val intent = Intent(activity, SearchActivity::class.java)
        with(SearchContract.IntentOptions) {
            intent.screenId = screenId
            intent.searchText = searchText
            intent.mode = mode
            intent.modeCollectionId = modeCollectionId
            intent.modeContentItemId = modeContentItemId
            intent.contextLibraryCollectionId = glContentContext.libraryCollectionId
            intent.contextContentItemId = glContentContext.contentItemId
            intent.contextNavCollectionId = glContentContext.navCollectionId
            intent.contextSubItemId = glContentContext.subItemId
            intent.scrollPosition = scrollPosition
        }

        startBaseActivityIntent(activity, intent, screenId, fromClickOnScreen)
    }

    fun showLinkContentActivity(activity: Activity, screenId: Long, contentItemId: Long, subItemId: Long, annotationId: Long, requestCode: Int, linkId: Long = -1) {
        activity.startActivityForResult(showLinkContentActivityIntent(activity, screenId, contentItemId, subItemId, annotationId, linkId), requestCode)
    }

    private fun showLinkContentActivityIntent(context: Context, screenId: Long, contentItemId: Long, subItemId: Long, annotationId: Long, linkId: Long): Intent {
        val intent = Intent(context, LinkContentActivity::class.java)
        with(LinkContentActivity.IntentOptions) {
            intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
            intent.contentItemId = contentItemId
            intent.subItemId = subItemId
            intent.annotationId = annotationId
            intent.linkId = linkId
        }
        return intent
    }

    fun showUriActivity(activity: FragmentActivity, screenId: Long, uri: String, showDownloadInCatalog: Boolean,
                        showRootCatalogIfNotAvailable: Boolean, startNewTask: Boolean, referrer: Analytics.Referrer) {
        try {
            showUriActivity(activity, screenId, URI(uri), showDownloadInCatalog, showRootCatalogIfNotAvailable, startNewTask, referrer)
        } catch (e: URISyntaxException) {
            Timber.e(e, "Failed to parse intentUri")
            showCatalogRoot(activity, screenId, false)
        }

    }

    private fun showUriActivity(activity: FragmentActivity, screenId: Long, uri: URI, showDownloadInCatalog: Boolean,
                                showRootCatalogIfNotAvailable: Boolean, startNewTask: Boolean, referrer: Analytics.Referrer) {
        // Check contentItemId
        val contentItemId = uriUtil.findContentItemIdByUri(screenUtil.getLanguageIdForScreen(screenId), uri)

        if (contentItemId <= 0) {
            // currently there is no URI in Catalog.library_collection... so it is impossible to go directly to a collection via uri
            showCatalogRootWithMessage(activity, screenId, null, false)
            return
        }

        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            var path = uri.path
            // Handle the old url form
            if (path.contains(UriUtil.LEGACY_VERSE_SEPARATOR)) {
                path = path.substring(0, path.indexOf(UriUtil.LEGACY_VERSE_SEPARATOR))
            }

            // Check the subItemId
            val subItemId = subItemManager.findIdByUri(contentItemId, path)

            if (subItemId <= 0) {
                // Check to see if we can open a collection in a book (such a Alma)...
                // - NOTE: Because nav_collection uri's are currently in the form of "/scriptures/bofm#map10" (instead of /scriptures/bofm/alma...),
                //   external navigation may be impossible using traditional uri's (such as /scriptures/bofm/alma)
                val navCollectionId = navCollectionManager.findIdByUri(contentItemId, path)
                if (navCollectionId > NavCollection.ROOT_NAV_COLLECTION_ID) {
                    // show content/book collection
                    showContentDirectory(activity, screenId, contentItemId, navCollectionId, true, false, referrer)
                } else {
                    // show content/book root collection
                    showContentDirectory(activity, screenId, contentItemId, true, false, referrer)
                }
                return
            }

            // Check the paragraph ids
            val scrollToParagraphAid = uriUtil.getScrollToParagraphAidFromUri(contentItemId, subItemId, uri)

            val paragraphAidList = uriUtil.findAidsByUri(contentItemId, subItemId, uri, true, false)
            val markParagraphAids = paragraphAidList.toTypedArray()

            // start the intent
            showContentForLink(activity, screenId, contentItemId, subItemId, scrollToParagraphAid, markParagraphAids, false, startNewTask, referrer)
        } else {
            //todo remove showRootCatalogIfNotAvailable and test well to be sure it's safe to remove
            if (showRootCatalogIfNotAvailable) {
                // Showing a dialog to prompt download when using "UriRouterActivity" seems to lock GL... so for now just show a message
                val title = itemManager.findTitleById(contentItemId)
                showCatalogRootWithMessage(activity, screenId, activity.getString(R.string.content_not_downloaded, title), false)
            } else {
                showDownloadContentItemDialog(activity, contentItemId, screenId, showDownloadInCatalog)
            }
        }
    }

    private fun showCatalogRootWithMessage(activity: FragmentActivity, screenId: Long, message: String?, fromClickOnScreen: Boolean) {
        if (message != null && message.isNotBlank()) {
            toastUtil.showLong(message)
        }
        showCatalogRoot(activity, screenId, fromClickOnScreen)
        activity.finish()
    }

    /**
     * BaseActivities that get recorded in tab history need to finish() previous activities
     */
    private fun startBaseActivityIntent(context: Context, intent: Intent, screenId: Long, fromClickOnScreen: Boolean) {
        startBaseActivityIntent(context, intent, screenId, true, fromClickOnScreen)
    }

    /**
     * BaseActivities that get recorded in tab history need to finish() previous activities
     * @param fromClickOnScreen if the user was on the Tab/Screens and clicked on a screen... AND isScreensInOverview is enabled, then open a new task
     */
    @SuppressLint("InlinedApi") // unsupported flags will get ignored
    private fun startBaseActivityIntent(context: Context, intent: Intent, screenId: Long, finishActivity: Boolean, fromClickOnScreen: Boolean) {
        val newScreen = screenId == ScreenUtil.NEW_SCREEN_ID
        val startupScreen = screenId == ScreenUtil.STARTUP_SCREEN_ID

        // if this is for a new screen.... ignore the screenId passed in and create a new screen
        if (newScreen || startupScreen) {
            intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenUtil.newScreenFromScreenId(screenId))
        } else {
            intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        }

        if ((fromClickOnScreen || newScreen) && prefs.isScreensInOverview) {
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            // Android N: if split screen is available... try to place there (ignored otherwise)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)

            // be sure to NOT finish the previous activity (it is on another android task)
            context.startActivity(intent)
        } else {
            context.startActivity(intent)

            if (finishActivity && context is Activity) {
                context.finish()
                context.overridePendingTransition(0, 0)
            }
        }
    }

    companion object {
        const val COLLECTIONS_REQUEST = 7
    }
}
