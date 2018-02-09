package org.lds.ldssa.inject

import android.app.Application
import dagger.Component
import org.lds.ldssa.App
import org.lds.ldssa.analytics.DefaultAnalytics
import org.lds.ldssa.download.InitialContentDownloadTask
import org.lds.ldssa.media.exomedia.AudioPlaybackControlsManager
import org.lds.ldssa.media.exomedia.data.AudioItem
import org.lds.ldssa.media.exomedia.data.VideoItem
import org.lds.ldssa.media.exomedia.helper.GLAudioApi
import org.lds.ldssa.media.exomedia.service.MediaService
import org.lds.ldssa.media.texttospeech.TextToSpeechControlsManager
import org.lds.ldssa.media.texttospeech.TextToSpeechEngine
import org.lds.ldssa.media.texttospeech.TextToSpeechManager
import org.lds.ldssa.media.texttospeech.TextToSpeechService
import org.lds.ldssa.receiver.DownloadManagerReceiver
import org.lds.ldssa.search.SearchService
import org.lds.ldssa.service.BookmarkWidgetFactory
import org.lds.ldssa.ui.actionmode.NotesActionModeCallback
import org.lds.ldssa.ui.activity.AudioSettingsActivity
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.activity.BookmarkRouterActivity
import org.lds.ldssa.ui.activity.ContentSourceActivity
import org.lds.ldssa.ui.activity.HighlightPaletteActivity
import org.lds.ldssa.ui.activity.HighlightSelectionActivity
import org.lds.ldssa.ui.activity.LanguageChangeActivity
import org.lds.ldssa.ui.activity.NoteActivity
import org.lds.ldssa.ui.activity.ScreenSettingsActivity
import org.lds.ldssa.ui.activity.SettingsActivity
import org.lds.ldssa.ui.activity.StartupActivity
import org.lds.ldssa.ui.activity.UriRouterActivity
import org.lds.ldssa.ui.activity.VideoPlayerActivity
import org.lds.ldssa.ui.adapter.DownloadMediaDialogAdapter
import org.lds.ldssa.ui.adapter.NavigationTrailAdapter
import org.lds.ldssa.ui.adapter.RelatedContentAdapter
import org.lds.ldssa.ui.adapter.viewholder.ContentDirectoryViewHolder
import org.lds.ldssa.ui.dialog.DeleteAllMediaDialogFragment
import org.lds.ldssa.ui.dialog.DownloadMediaDialogFragment
import org.lds.ldssa.ui.dialog.ProgressDialogFragment
import org.lds.ldssa.ui.dialog.TextSizeDialogFragment
import org.lds.ldssa.ui.fragment.AudioSettingsFragment
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.ui.fragment.ScreenSettingsFragment
import org.lds.ldssa.ui.fragment.SettingsFragment
import org.lds.ldssa.ui.loader.AnnotationTagsLoader
import org.lds.ldssa.ui.loader.DownloadableMediaListLoader
import org.lds.ldssa.ui.loader.DownloadedMediaLoader
import org.lds.ldssa.ui.loader.HistoryLoader
import org.lds.ldssa.ui.loader.ScreensLoader
import org.lds.ldssa.ui.sidebar.SideBar
import org.lds.ldssa.ui.sidebar.SideBarAnnotation
import org.lds.ldssa.ui.sidebar.SideBarRelatedContent
import org.lds.ldssa.ui.sidebar.SideBarRelatedContentItem
import org.lds.ldssa.ui.sidebar.SideBarUri
import org.lds.ldssa.ui.web.ContentJsInterface
import org.lds.ldssa.ui.web.ContentTouchListener
import org.lds.ldssa.ui.web.ContentWebView
import org.lds.ldssa.ui.widget.AnnotationView
import org.lds.ldssa.ui.widget.BookmarkWidgetProvider
import org.lds.ldssa.ui.widget.ContentViewPager
import org.lds.ldssa.ui.widget.LDSCastButton
import org.lds.ldssa.ui.widget.MarkdownControls
import org.lds.ldssa.ui.widget.MediaFab
import org.lds.ldssa.ui.widget.MiniPlaybackControls
import org.lds.ldssa.ux.ViewModelModule
import org.lds.ldssa.ux.about.AboutActivity
import org.lds.ldssa.ux.about.AnnotationInfoActivity
import org.lds.ldssa.ux.about.AppInfoActivity
import org.lds.ldssa.ux.annotations.AnnotationsActivity
import org.lds.ldssa.ux.annotations.AnnotationsAdapter
import org.lds.ldssa.ux.annotations.AnnotationsFragment
import org.lds.ldssa.ux.annotations.SingleAnnotationActivity
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsFragment
import org.lds.ldssa.ux.annotations.links.LinkContentActivity
import org.lds.ldssa.ux.annotations.links.LinksActivity
import org.lds.ldssa.ux.annotations.notebooks.NotebooksAdapter
import org.lds.ldssa.ux.annotations.notebooks.NotebooksFragment
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionActivity
import org.lds.ldssa.ux.annotations.notes.NotesActivity
import org.lds.ldssa.ux.annotations.tags.TagsAdapter
import org.lds.ldssa.ux.annotations.tags.TagsFragment
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionActivity
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionAdapter
import org.lds.ldssa.ux.catalog.CatalogDirectoryActivity
import org.lds.ldssa.ux.catalog.CatalogDirectoryAdapter
import org.lds.ldssa.ux.content.directory.ContentDirectoryActivity
import org.lds.ldssa.ux.content.directory.ContentDirectoryAdapter
import org.lds.ldssa.ux.content.item.ContentActivity
import org.lds.ldssa.ux.content.item.ContentItemFragment
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsActivity
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsAdapter
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsActivity
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsAdapter
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryActivity
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaActivity
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaAdapter
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaCollectionsAdapter
import org.lds.ldssa.ux.locations.LocationsActivity
import org.lds.ldssa.ux.locations.LocationsPagerAdapter
import org.lds.ldssa.ux.locations.bookmarks.BookmarksAdapter
import org.lds.ldssa.ux.locations.bookmarks.BookmarksFragment
import org.lds.ldssa.ux.locations.history.HistoryAdapter
import org.lds.ldssa.ux.locations.history.HistoryFragment
import org.lds.ldssa.ux.locations.screens.ScreensAdapter
import org.lds.ldssa.ux.locations.screens.ScreensFragment
import org.lds.ldssa.ux.search.SearchActivity
import org.lds.ldssa.ux.share.ShareIntentActivity
import org.lds.ldssa.ux.signin.SignInActivity
import org.lds.ldssa.ux.study.items.StudyItemsActivity
import org.lds.ldssa.ux.study.items.StudyItemsAdapter
import org.lds.ldssa.ux.study.plans.StudyPlanListFragment
import org.lds.ldssa.ux.study.plans.StudyPlansActivity
import org.lds.ldssa.ux.study.plans.StudyPlansAdapter
import org.lds.ldssa.ux.study.plans.StudyPlansPagerAdapter
import org.lds.ldssa.ux.tips.lists.TipListPagerActivity
import org.lds.ldssa.ux.tips.lists.listitems.TipListFragment
import org.lds.ldssa.ux.tips.pages.TipsPagerActivity
import org.lds.ldssa.ux.tips.pages.tip.TipFragment
import org.lds.ldssa.ux.welcome.WelcomeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ViewModelModule::class))
interface AppComponent {
    // Exported for child-components.
    fun application(): Application

    fun inject(application: App)

    // misc
    fun inject(target: NotesActionModeCallback)
    fun inject(target: BookmarkWidgetProvider)
    fun inject(target: DownloadManagerReceiver)
    fun inject(target: BookmarkWidgetFactory)
    fun inject(target: DefaultAnalytics)

    // Media
    fun inject(target: MediaService)
    fun inject(target: GLAudioApi)
    fun inject(target: AudioItem)
    fun inject(target: VideoItem)

    // Activity / Fragments
    fun inject(target: AnnotationsActivity)
    fun inject(target: AnnotationsFragment)
    fun inject(target: StartupActivity)
    fun inject(target: SearchActivity)
    fun inject(target: BaseFragment)
    fun inject(target: CatalogDirectoryActivity)
    fun inject(target: ContentViewPager)
    fun inject(target: ContentDirectoryActivity)
    fun inject(target: ContentItemFragment)
    fun inject(target: ContentActivity)
    fun inject(target: DownloadedMediaActivity)
    fun inject(target: BaseActivity)
    fun inject(target: HighlightPaletteActivity)
    fun inject(target: NoteActivity)
    fun inject(target: ScreensFragment)
    fun inject(target: SettingsActivity)
    fun inject(target: SettingsFragment)
    fun inject(target: ShareIntentActivity)
    fun inject(target: TagSelectionActivity)
    fun inject(target: VideoPlayerActivity)
    fun inject(target: CurrentDownloadsActivity)
    fun inject(target: CustomCollectionsActivity)
    fun inject(target: NotebookSelectionActivity)
    fun inject(target: NotesActivity)
    fun inject(target: AllAnnotationsFragment)
    fun inject(target: NotebooksFragment)
    fun inject(target: TagsFragment)
    fun inject(target: HighlightSelectionActivity)
    fun inject(target: AboutActivity)
    fun inject(target: AppInfoActivity)
    fun inject(target: BookmarksFragment)
    fun inject(target: HistoryFragment)
    fun inject(target: CustomCollectionDirectoryActivity)
    fun inject(target: UriRouterActivity)
    fun inject(target: LocationsActivity)
    fun inject(target: SignInActivity)
    fun inject(target: WelcomeActivity)
    fun inject(target: TipFragment)
    fun inject(target: TipListPagerActivity)
    fun inject(target: TipListFragment)
    fun inject(target: LinkContentActivity)
    fun inject(target: LinksActivity)
    fun inject(target: SingleAnnotationActivity)
    fun inject(target: StudyPlansActivity)
    fun inject(target: StudyPlansPagerAdapter)
    fun inject(target: StudyPlanListFragment)
    fun inject(target: StudyItemsActivity)

    // Dialog
    fun inject(target: DeleteAllMediaDialogFragment)
    fun inject(target: DownloadMediaDialogFragment)
    fun inject(target: TextSizeDialogFragment)
    fun inject(target: ProgressDialogFragment)
    fun inject(target: ContentTouchListener)

    // Widget
    fun inject(target: ContentWebView)
    fun inject(target: MarkdownControls)
    fun inject(target: ContentJsInterface)
    fun inject(target: MediaFab)

    // Adapters
    fun inject(target: DownloadedMediaAdapter)
    fun inject(target: DownloadedMediaCollectionsAdapter)
    fun inject(target: CatalogDirectoryAdapter)
    fun inject(target: ContentDirectoryAdapter)
    fun inject(target: HistoryAdapter)
    fun inject(target: NavigationTrailAdapter)
    fun inject(target: ScreensAdapter)
    fun inject(target: CustomCollectionsAdapter)
    fun inject(target: TagSelectionAdapter)
    fun inject(target: NotebooksAdapter)
    fun inject(target: TagsAdapter)
    fun inject(target: DownloadMediaDialogAdapter)
    fun inject(target: AnnotationsAdapter)
    fun inject(target: LocationsPagerAdapter)
    fun inject(target: BookmarksAdapter)
    fun inject(target: StudyPlansAdapter)
    fun inject(target: StudyItemsAdapter)

    // View Holders
    fun inject(target: ContentDirectoryViewHolder)

    // Loaders
    fun inject(target: DownloadableMediaListLoader)
    fun inject(target: ScreensLoader)
    fun inject(target: DownloadedMediaLoader)
    fun inject(target: AnnotationTagsLoader)
    fun inject(target: HistoryLoader)

    // Other
    fun inject(target: InitialContentDownloadTask)
    fun inject(target: SideBar)
    fun inject(target: SideBarRelatedContentItem)
    fun inject(target: SideBarAnnotation)
    fun inject(target: SideBarUri)
    fun inject(target: LDSCastButton.ThemeCompliantDialogFactory)
    fun inject(target: LanguageChangeActivity)
    fun inject(target: TipsPagerActivity)
    fun inject(target: LDSCastButton)
    fun inject(target: ScreenSettingsFragment)
    fun inject(target: ScreenSettingsActivity)
    fun inject(target: AudioSettingsActivity)
    fun inject(target: AudioSettingsFragment)
    fun inject(target: SideBarRelatedContent)
    fun inject(target: RelatedContentAdapter)
    fun inject(target: ContentSourceActivity)
    fun inject(target: AnnotationView)
    fun inject(target: BookmarkRouterActivity)
    fun inject(target: AnnotationInfoActivity)
    fun inject(target: SearchService)
    fun inject(target: TextToSpeechControlsManager)
    fun inject(target: AudioPlaybackControlsManager)
    fun inject(target: MiniPlaybackControls)
    fun inject(target: TextToSpeechManager)
    fun inject(target: TextToSpeechService)
    fun inject(target: TextToSpeechEngine)
    fun inject(target: CurrentDownloadsAdapter)
}
