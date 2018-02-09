package org.lds.ldssa.inject;

import android.app.ActivityManager;
import android.app.Application;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.google.gson.Gson;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.MapProviderFactory;
import dagger.internal.Preconditions;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldsaccount.CredentialsManager;
import org.lds.ldsaccount.LDSAccountAuth;
import org.lds.ldsaccount.LDSAccountAuth_Factory;
import org.lds.ldsaccount.LDSAccountInterceptor;
import org.lds.ldsaccount.LDSAccountLogger;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldsaccount.LDSAccountPrefs_Factory;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldsaccount.LDSAccountUtil_Factory;
import org.lds.ldsaccount.NetworkConnectionManager;
import org.lds.ldsaccount.inject.LDSAccountModule;
import org.lds.ldsaccount.inject.LDSAccountModule_ProvideLDSAccountLoggerFactory;
import org.lds.ldsaccount.inject.LDSAccountModule_ProvideSharedPreferencesFactory;
import org.lds.ldssa.App;
import org.lds.ldssa.App_MembersInjector;
import org.lds.ldssa.GLUpdateLifecycle;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.analytics.DefaultAnalytics;
import org.lds.ldssa.analytics.DefaultAnalytics_MembersInjector;
import org.lds.ldssa.download.AllAudioDownloader;
import org.lds.ldssa.download.AllAudioDownloader_Factory;
import org.lds.ldssa.download.CancelDownloadsTask;
import org.lds.ldssa.download.CancelDownloadsTask_Factory;
import org.lds.ldssa.download.CatalogDownloader;
import org.lds.ldssa.download.CatalogDownloader_Factory;
import org.lds.ldssa.download.CheckContentVersionsTask;
import org.lds.ldssa.download.CheckContentVersionsTask_Factory;
import org.lds.ldssa.download.CheckDownloadsTask;
import org.lds.ldssa.download.CheckDownloadsTask_Factory;
import org.lds.ldssa.download.ContentDownloader;
import org.lds.ldssa.download.ContentDownloader_Factory;
import org.lds.ldssa.download.DirectDownloadTask_Factory;
import org.lds.ldssa.download.DownloadReceivedTask;
import org.lds.ldssa.download.DownloadReceivedTask_Factory;
import org.lds.ldssa.download.DownloadedCatalogProcessor;
import org.lds.ldssa.download.DownloadedCatalogProcessor_Factory;
import org.lds.ldssa.download.DownloadedContentProcessor;
import org.lds.ldssa.download.DownloadedContentProcessor_Factory;
import org.lds.ldssa.download.DownloadedMediaProcessor;
import org.lds.ldssa.download.DownloadedMediaProcessor_Factory;
import org.lds.ldssa.download.DownloadedTipsProcessor;
import org.lds.ldssa.download.DownloadedTipsProcessor_Factory;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.download.GLDownloadManager_Factory;
import org.lds.ldssa.download.InitialContentDownloadTask;
import org.lds.ldssa.download.InitialContentDownloadTask_Factory;
import org.lds.ldssa.download.MediaDownloader;
import org.lds.ldssa.download.MediaDownloader_Factory;
import org.lds.ldssa.download.RepairMissingDownloadedItems;
import org.lds.ldssa.download.RepairMissingDownloadedItems_Factory;
import org.lds.ldssa.download.TipsDownloader;
import org.lds.ldssa.download.TipsDownloader_Factory;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.intent.ExternalIntents_Factory;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.intent.InternalIntents_Factory;
import org.lds.ldssa.job.AnnotationSyncJob;
import org.lds.ldssa.job.AnnotationSyncJob_Factory;
import org.lds.ldssa.job.AppJobCreator;
import org.lds.ldssa.job.AppJobCreator_Factory;
import org.lds.ldssa.media.exomedia.AudioPlaybackControlsManager;
import org.lds.ldssa.media.exomedia.AudioPlaybackControlsManager_MembersInjector;
import org.lds.ldssa.media.exomedia.data.AudioItem;
import org.lds.ldssa.media.exomedia.data.AudioItem_MembersInjector;
import org.lds.ldssa.media.exomedia.data.VideoItem;
import org.lds.ldssa.media.exomedia.data.VideoItem_MembersInjector;
import org.lds.ldssa.media.exomedia.helper.GLAudioApi;
import org.lds.ldssa.media.exomedia.helper.GLAudioApi_MembersInjector;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager_Factory;
import org.lds.ldssa.media.exomedia.service.MediaService;
import org.lds.ldssa.media.exomedia.service.MediaService_MembersInjector;
import org.lds.ldssa.media.texttospeech.TextToSpeechControlsManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechControlsManager_MembersInjector;
import org.lds.ldssa.media.texttospeech.TextToSpeechEngine;
import org.lds.ldssa.media.texttospeech.TextToSpeechGenerator;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager_Factory;
import org.lds.ldssa.media.texttospeech.TextToSpeechService;
import org.lds.ldssa.media.texttospeech.TextToSpeechService_MembersInjector;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.DatabaseManager_Factory;
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager;
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager_Factory;
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager;
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager_Factory;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager_Factory;
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager;
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager_Factory;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager_Factory;
import org.lds.ldssa.model.database.catalog.itemcategory.ItemCategoryManager;
import org.lds.ldssa.model.database.catalog.itemcategory.ItemCategoryManager_Factory;
import org.lds.ldssa.model.database.catalog.itemcollectionview.ItemCollectionViewManager;
import org.lds.ldssa.model.database.catalog.itemcollectionview.ItemCollectionViewManager_Factory;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager_Factory;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager_Factory;
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager;
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager_Factory;
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemManager;
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemManager_Factory;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager_Factory;
import org.lds.ldssa.model.database.catalog.searchgotoquery.SearchGotoQueryManager;
import org.lds.ldssa.model.database.catalog.searchgotoquery.SearchGotoQueryManager_Factory;
import org.lds.ldssa.model.database.catalog.stopword.StopWordManager;
import org.lds.ldssa.model.database.catalog.stopword.StopWordManager_Factory;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager_Factory;
import org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery.AllSubItemsInNavCollectionQueryManager;
import org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery.AllSubItemsInNavCollectionQueryManager_Factory;
import org.lds.ldssa.model.database.content.author.AuthorManager;
import org.lds.ldssa.model.database.content.author.AuthorManager_Factory;
import org.lds.ldssa.model.database.content.availablerelatedtypequery.AvailableRelatedTypeQueryManager;
import org.lds.ldssa.model.database.content.availablerelatedtypequery.AvailableRelatedTypeQueryManager_Factory;
import org.lds.ldssa.model.database.content.contentdirectoryitemquery.ContentDirectoryItemQueryManager;
import org.lds.ldssa.model.database.content.contentdirectoryitemquery.ContentDirectoryItemQueryManager_Factory;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager_Factory;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager_Factory;
import org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntryManager;
import org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntryManager_Factory;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager_Factory;
import org.lds.ldssa.model.database.content.navsection.NavSectionManager;
import org.lds.ldssa.model.database.content.navsection.NavSectionManager_Factory;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager_Factory;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager_Factory;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager_Factory;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemManager;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemManager_Factory;
import org.lds.ldssa.model.database.content.relatedvideoitem.RelatedVideoItemManager;
import org.lds.ldssa.model.database.content.relatedvideoitem.RelatedVideoItemManager_Factory;
import org.lds.ldssa.model.database.content.relatedvideoitemsource.RelatedVideoItemSourceManager;
import org.lds.ldssa.model.database.content.relatedvideoitemsource.RelatedVideoItemSourceManager_Factory;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager_Factory;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager_Factory;
import org.lds.ldssa.model.database.content.subitemsearchquery.SubItemSearchQueryManager;
import org.lds.ldssa.model.database.content.subitemsearchquery.SubItemSearchQueryManager_Factory;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager_Factory;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager_Factory;
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager;
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager_Factory;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager_Factory;
import org.lds.ldssa.model.database.gl.history.HistoryManager;
import org.lds.ldssa.model.database.gl.history.HistoryManager_Factory;
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager;
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager_Factory;
import org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageManager;
import org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageManager_Factory;
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager;
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager_Factory;
import org.lds.ldssa.model.database.gl.sidebarhistoryitem.SidebarHistoryItemManager;
import org.lds.ldssa.model.database.gl.sidebarhistoryitem.SidebarHistoryItemManager_Factory;
import org.lds.ldssa.model.database.gl.tab.TabManager;
import org.lds.ldssa.model.database.gl.tab.TabManager_Factory;
import org.lds.ldssa.model.database.gl.tabhistoryitem.TabHistoryItemManager;
import org.lds.ldssa.model.database.gl.tabhistoryitem.TabHistoryItemManager_Factory;
import org.lds.ldssa.model.database.gl.tipviewed.TipViewedManager;
import org.lds.ldssa.model.database.gl.tipviewed.TipViewedManager_Factory;
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCountManager;
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCountManager_Factory;
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager;
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager_Factory;
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager;
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager_Factory;
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager;
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager_Factory;
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager;
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager_Factory;
import org.lds.ldssa.model.database.search.searchhistory.SearchHistoryManager;
import org.lds.ldssa.model.database.search.searchhistory.SearchHistoryManager_Factory;
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager;
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager_Factory;
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager;
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager_Factory;
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestionManager;
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestionManager_Factory;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.database.tips.tip.TipManager_Factory;
import org.lds.ldssa.model.database.tips.tipquery.TipQueryManager;
import org.lds.ldssa.model.database.tips.tipquery.TipQueryManager_Factory;
import org.lds.ldssa.model.database.tips.tipsmetadata.TipsMetaDataManager;
import org.lds.ldssa.model.database.tips.tipsmetadata.TipsMetaDataManager_Factory;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager_Factory;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager_Factory;
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQueryManager;
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQueryManager_Factory;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager_Factory;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager_Factory;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager_Factory;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager_Factory;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager_Factory;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager_Factory;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager_Factory;
import org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager;
import org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager_Factory;
import org.lds.ldssa.model.database.userdata.notesearchquery.NoteSearchQueryManager;
import org.lds.ldssa.model.database.userdata.notesearchquery.NoteSearchQueryManager_Factory;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager_Factory;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager_Factory;
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemManager;
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemManager_Factory;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.model.database.userdata.tag.TagManager_Factory;
import org.lds.ldssa.model.database.userdata.tagview.TagViewManager;
import org.lds.ldssa.model.database.userdata.tagview.TagViewManager_Factory;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.PrefsModule;
import org.lds.ldssa.model.prefs.PrefsModule_ProvideBackedUpSharedPreferencesFactory;
import org.lds.ldssa.model.prefs.PrefsModule_ProvideDefaultSharedPreferencesFactory;
import org.lds.ldssa.model.prefs.Prefs_Factory;
import org.lds.ldssa.model.webservice.ServiceModule;
import org.lds.ldssa.model.webservice.ServiceModule_GetAnnotationServiceFactory;
import org.lds.ldssa.model.webservice.ServiceModule_GetAuthenticatedClientFactory;
import org.lds.ldssa.model.webservice.ServiceModule_GetStandardClientFactory;
import org.lds.ldssa.model.webservice.ServiceModule_ProvideAppConfigServiceFactory;
import org.lds.ldssa.model.webservice.ServiceModule_ProvideCatalogServiceFactory;
import org.lds.ldssa.model.webservice.ServiceModule_ProvideRoleBasedContentServiceFactory;
import org.lds.ldssa.model.webservice.ServiceModule_ProvideRoleCatalogServiceFactory;
import org.lds.ldssa.model.webservice.ServiceModule_ProvideTipsServiceFactory;
import org.lds.ldssa.model.webservice.annotation.LDSAnnotationService;
import org.lds.ldssa.model.webservice.app.AppConfigService;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.ldssa.model.webservice.tips.TipsService;
import org.lds.ldssa.receiver.DownloadManagerReceiver;
import org.lds.ldssa.receiver.DownloadManagerReceiver_MembersInjector;
import org.lds.ldssa.search.SearchEngineLocal;
import org.lds.ldssa.search.SearchEngineLocal_Factory;
import org.lds.ldssa.search.SearchService;
import org.lds.ldssa.search.SearchService_MembersInjector;
import org.lds.ldssa.search.SearchUtil;
import org.lds.ldssa.search.SearchUtil_Factory;
import org.lds.ldssa.service.BookmarkWidgetFactory;
import org.lds.ldssa.service.BookmarkWidgetFactory_MembersInjector;
import org.lds.ldssa.sync.AnnotationChangeProcessor;
import org.lds.ldssa.sync.AnnotationChangeProcessor_Factory;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.sync.AnnotationSyncProcessor;
import org.lds.ldssa.sync.AnnotationSyncProcessor_Factory;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.ldssa.sync.AnnotationSyncScheduler_Factory;
import org.lds.ldssa.sync.AnnotationSync_Factory;
import org.lds.ldssa.sync.FolderSyncProcessor;
import org.lds.ldssa.sync.FolderSyncProcessor_Factory;
import org.lds.ldssa.task.AnnotationFixTask;
import org.lds.ldssa.task.AnnotationFixTask_Factory;
import org.lds.ldssa.task.StartupTask;
import org.lds.ldssa.task.StartupTask_Factory;
import org.lds.ldssa.task.TipsUpdateTask;
import org.lds.ldssa.task.TipsUpdateTask_Factory;
import org.lds.ldssa.ui.actionmode.NotesActionModeCallback;
import org.lds.ldssa.ui.activity.AudioSettingsActivity;
import org.lds.ldssa.ui.activity.AudioSettingsActivity_MembersInjector;
import org.lds.ldssa.ui.activity.BaseActivity;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.activity.BookmarkRouterActivity;
import org.lds.ldssa.ui.activity.BookmarkRouterActivity_MembersInjector;
import org.lds.ldssa.ui.activity.ContentSourceActivity;
import org.lds.ldssa.ui.activity.ContentSourceActivity_MembersInjector;
import org.lds.ldssa.ui.activity.HighlightPaletteActivity;
import org.lds.ldssa.ui.activity.HighlightPaletteActivity_MembersInjector;
import org.lds.ldssa.ui.activity.HighlightSelectionActivity;
import org.lds.ldssa.ui.activity.HighlightSelectionActivity_MembersInjector;
import org.lds.ldssa.ui.activity.LanguageChangeActivity;
import org.lds.ldssa.ui.activity.LanguageChangeActivity_MembersInjector;
import org.lds.ldssa.ui.activity.NoteActivity;
import org.lds.ldssa.ui.activity.NoteActivity_MembersInjector;
import org.lds.ldssa.ui.activity.ScreenSettingsActivity;
import org.lds.ldssa.ui.activity.ScreenSettingsActivity_MembersInjector;
import org.lds.ldssa.ui.activity.SettingsActivity;
import org.lds.ldssa.ui.activity.SettingsActivity_MembersInjector;
import org.lds.ldssa.ui.activity.StartupActivity;
import org.lds.ldssa.ui.activity.StartupActivity_MembersInjector;
import org.lds.ldssa.ui.activity.UriRouterActivity;
import org.lds.ldssa.ui.activity.UriRouterActivity_MembersInjector;
import org.lds.ldssa.ui.activity.VideoPlayerActivity;
import org.lds.ldssa.ui.activity.VideoPlayerActivity_MembersInjector;
import org.lds.ldssa.ui.adapter.DownloadMediaDialogAdapter;
import org.lds.ldssa.ui.adapter.NavigationTrailAdapter;
import org.lds.ldssa.ui.adapter.RelatedContentAdapter;
import org.lds.ldssa.ui.adapter.viewholder.ContentDirectoryViewHolder;
import org.lds.ldssa.ui.dialog.DeleteAllMediaDialogFragment;
import org.lds.ldssa.ui.dialog.DeleteAllMediaDialogFragment_MembersInjector;
import org.lds.ldssa.ui.dialog.DownloadMediaDialogFragment;
import org.lds.ldssa.ui.dialog.DownloadMediaDialogFragment_MembersInjector;
import org.lds.ldssa.ui.dialog.ProgressDialogFragment;
import org.lds.ldssa.ui.dialog.TextSizeDialogFragment;
import org.lds.ldssa.ui.dialog.TextSizeDialogFragment_MembersInjector;
import org.lds.ldssa.ui.fragment.AudioSettingsFragment;
import org.lds.ldssa.ui.fragment.AudioSettingsFragment_MembersInjector;
import org.lds.ldssa.ui.fragment.BaseFragment;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.ui.fragment.ScreenSettingsFragment;
import org.lds.ldssa.ui.fragment.ScreenSettingsFragment_MembersInjector;
import org.lds.ldssa.ui.fragment.SettingsFragment;
import org.lds.ldssa.ui.fragment.SettingsFragment_MembersInjector;
import org.lds.ldssa.ui.loader.AnnotationTagsLoader;
import org.lds.ldssa.ui.loader.AnnotationTagsLoader_MembersInjector;
import org.lds.ldssa.ui.loader.DownloadableMediaListLoader;
import org.lds.ldssa.ui.loader.DownloadableMediaListLoader_MembersInjector;
import org.lds.ldssa.ui.loader.DownloadedMediaLoader;
import org.lds.ldssa.ui.loader.DownloadedMediaLoader_MembersInjector;
import org.lds.ldssa.ui.loader.HistoryLoader;
import org.lds.ldssa.ui.loader.HistoryLoader_MembersInjector;
import org.lds.ldssa.ui.loader.ScreensLoader;
import org.lds.ldssa.ui.loader.ScreensLoader_MembersInjector;
import org.lds.ldssa.ui.menu.CatalogDirectoryMenu;
import org.lds.ldssa.ui.menu.CatalogDirectoryMenu_Factory;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.ui.menu.CommonMenu_Factory;
import org.lds.ldssa.ui.menu.CustomCollectionDirectoryMenu;
import org.lds.ldssa.ui.menu.CustomCollectionDirectoryMenu_Factory;
import org.lds.ldssa.ui.notification.AnnotationSyncNotification;
import org.lds.ldssa.ui.notification.AnnotationSyncNotification_Factory;
import org.lds.ldssa.ui.notification.AppUpdateNotification;
import org.lds.ldssa.ui.notification.AppUpdateNotification_Factory;
import org.lds.ldssa.ui.notification.AuthenticationFailureNotification;
import org.lds.ldssa.ui.notification.AuthenticationFailureNotification_Factory;
import org.lds.ldssa.ui.sidebar.SideBar;
import org.lds.ldssa.ui.sidebar.SideBarAnnotation;
import org.lds.ldssa.ui.sidebar.SideBarAnnotation_MembersInjector;
import org.lds.ldssa.ui.sidebar.SideBarRelatedContent;
import org.lds.ldssa.ui.sidebar.SideBarRelatedContentItem;
import org.lds.ldssa.ui.sidebar.SideBarRelatedContentItem_MembersInjector;
import org.lds.ldssa.ui.sidebar.SideBarRelatedContent_MembersInjector;
import org.lds.ldssa.ui.sidebar.SideBarUri;
import org.lds.ldssa.ui.sidebar.SideBarUri_MembersInjector;
import org.lds.ldssa.ui.sidebar.SideBar_MembersInjector;
import org.lds.ldssa.ui.web.ContentJsInterface;
import org.lds.ldssa.ui.web.ContentJsInvoker;
import org.lds.ldssa.ui.web.ContentJsInvoker_Factory;
import org.lds.ldssa.ui.web.ContentTouchListener;
import org.lds.ldssa.ui.web.ContentTouchListener_MembersInjector;
import org.lds.ldssa.ui.web.ContentWebView;
import org.lds.ldssa.ui.web.ContentWebView_MembersInjector;
import org.lds.ldssa.ui.widget.AnnotationView;
import org.lds.ldssa.ui.widget.AnnotationView_MembersInjector;
import org.lds.ldssa.ui.widget.BookmarkWidgetProvider;
import org.lds.ldssa.ui.widget.BookmarkWidgetProvider_MembersInjector;
import org.lds.ldssa.ui.widget.ContentViewPager;
import org.lds.ldssa.ui.widget.LDSCastButton;
import org.lds.ldssa.ui.widget.LDSCastButton_MembersInjector;
import org.lds.ldssa.ui.widget.LDSCastButton_ThemeCompliantDialogFactory_MembersInjector;
import org.lds.ldssa.ui.widget.MarkdownControls;
import org.lds.ldssa.ui.widget.MediaFab;
import org.lds.ldssa.ui.widget.MediaFab_MembersInjector;
import org.lds.ldssa.ui.widget.MiniPlaybackControls;
import org.lds.ldssa.ui.widget.MiniPlaybackControls_MembersInjector;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AccountUtil_Factory;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.AnalyticsUtil_Factory;
import org.lds.ldssa.util.AnnotationUiUtil;
import org.lds.ldssa.util.AnnotationUiUtil_Factory;
import org.lds.ldssa.util.AppUpdateUtil;
import org.lds.ldssa.util.AppUpdateUtil_Factory;
import org.lds.ldssa.util.AppUpgradeUtil;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.ldssa.util.CatalogUpdateUtil_Factory;
import org.lds.ldssa.util.CatalogUtil;
import org.lds.ldssa.util.CatalogUtil_Factory;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.CitationUtil_Factory;
import org.lds.ldssa.util.ContentItemUpdateUtil;
import org.lds.ldssa.util.ContentItemUpdateUtil_Factory;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.ContentItemUtil_Factory;
import org.lds.ldssa.util.ContentParagraphUtil;
import org.lds.ldssa.util.ContentParagraphUtil_Factory;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.ContentRenderer_Factory;
import org.lds.ldssa.util.CustomCollectionUtil;
import org.lds.ldssa.util.CustomCollectionUtil_Factory;
import org.lds.ldssa.util.DatabaseUtil;
import org.lds.ldssa.util.DatabaseUtil_Factory;
import org.lds.ldssa.util.DeviceUtil;
import org.lds.ldssa.util.DeviceUtil_Factory;
import org.lds.ldssa.util.DownloadedMediaUtil;
import org.lds.ldssa.util.FeedbackUtil;
import org.lds.ldssa.util.FeedbackUtil_Factory;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.GLFileUtil_Factory;
import org.lds.ldssa.util.ImageUtil;
import org.lds.ldssa.util.ImageUtil_Factory;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.LanguageUtil_Factory;
import org.lds.ldssa.util.LdsMusicUtil;
import org.lds.ldssa.util.LdsMusicUtil_Factory;
import org.lds.ldssa.util.MarkdownUtil;
import org.lds.ldssa.util.MarkdownUtil_Factory;
import org.lds.ldssa.util.NotebookUtil;
import org.lds.ldssa.util.NotebookUtil_Factory;
import org.lds.ldssa.util.RelatedAudioAvailableUtil;
import org.lds.ldssa.util.RelatedVideoUtil;
import org.lds.ldssa.util.RelatedVideoUtil_Factory;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenLauncherUtil_Factory;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ScreenUtil_Factory;
import org.lds.ldssa.util.ShareUtil;
import org.lds.ldssa.util.ShareUtil_Factory;
import org.lds.ldssa.util.TextHandleUtil;
import org.lds.ldssa.util.TextHandleUtil_Factory;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.ThemeUtil_Factory;
import org.lds.ldssa.util.TipsUpdateUtil;
import org.lds.ldssa.util.TipsUpdateUtil_Factory;
import org.lds.ldssa.util.TipsUtil;
import org.lds.ldssa.util.TipsUtil_Factory;
import org.lds.ldssa.util.ToastUtil;
import org.lds.ldssa.util.ToastUtil_Factory;
import org.lds.ldssa.util.UriUtil;
import org.lds.ldssa.util.UriUtil_Factory;
import org.lds.ldssa.util.UserdataDbUtil;
import org.lds.ldssa.util.UserdataDbUtil_Factory;
import org.lds.ldssa.util.VideoUtil;
import org.lds.ldssa.util.VideoUtil_Factory;
import org.lds.ldssa.util.WebServiceUtil;
import org.lds.ldssa.util.WebServiceUtil_Factory;
import org.lds.ldssa.util.annotations.AnnotationListUtil;
import org.lds.ldssa.util.annotations.AnnotationListUtil_Factory;
import org.lds.ldssa.util.annotations.BookmarkUtil;
import org.lds.ldssa.util.annotations.BookmarkUtil_Factory;
import org.lds.ldssa.util.annotations.HighlightUtil;
import org.lds.ldssa.util.annotations.HighlightUtil_Factory;
import org.lds.ldssa.util.annotations.LinkUtil;
import org.lds.ldssa.util.annotations.LinkUtil_Factory;
import org.lds.ldssa.util.annotations.NoteUtil;
import org.lds.ldssa.util.annotations.NoteUtil_Factory;
import org.lds.ldssa.util.annotations.TagUtil;
import org.lds.ldssa.util.annotations.TagUtil_Factory;
import org.lds.ldssa.ux.about.AboutActivity;
import org.lds.ldssa.ux.about.AboutActivity_MembersInjector;
import org.lds.ldssa.ux.about.AnnotationInfoActivity;
import org.lds.ldssa.ux.about.AnnotationInfoActivity_MembersInjector;
import org.lds.ldssa.ux.about.AppInfoActivity;
import org.lds.ldssa.ux.about.AppInfoActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.AnnotationsActivity;
import org.lds.ldssa.ux.annotations.AnnotationsActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.AnnotationsAdapter;
import org.lds.ldssa.ux.annotations.AnnotationsFragment;
import org.lds.ldssa.ux.annotations.AnnotationsFragment_MembersInjector;
import org.lds.ldssa.ux.annotations.AnnotationsViewModel;
import org.lds.ldssa.ux.annotations.AnnotationsViewModel_Factory;
import org.lds.ldssa.ux.annotations.SingleAnnotationActivity;
import org.lds.ldssa.ux.annotations.SingleAnnotationActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsFragment;
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsFragment_MembersInjector;
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsViewModel;
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsViewModel_Factory;
import org.lds.ldssa.ux.annotations.links.LinkContentActivity;
import org.lds.ldssa.ux.annotations.links.LinkContentActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.links.LinksActivity;
import org.lds.ldssa.ux.annotations.links.LinksActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.links.LinksViewModel;
import org.lds.ldssa.ux.annotations.links.LinksViewModel_Factory;
import org.lds.ldssa.ux.annotations.notebooks.NotebooksAdapter;
import org.lds.ldssa.ux.annotations.notebooks.NotebooksFragment;
import org.lds.ldssa.ux.annotations.notebooks.NotebooksFragment_MembersInjector;
import org.lds.ldssa.ux.annotations.notebooks.NotebooksViewModel;
import org.lds.ldssa.ux.annotations.notebooks.NotebooksViewModel_Factory;
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionActivity;
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionViewModel;
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionViewModel_Factory;
import org.lds.ldssa.ux.annotations.notes.NotesActivity;
import org.lds.ldssa.ux.annotations.notes.NotesActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.notes.RestoreJournalUtil;
import org.lds.ldssa.ux.annotations.notes.RestoreJournalUtil_Factory;
import org.lds.ldssa.ux.annotations.tags.TagsAdapter;
import org.lds.ldssa.ux.annotations.tags.TagsFragment;
import org.lds.ldssa.ux.annotations.tags.TagsFragment_MembersInjector;
import org.lds.ldssa.ux.annotations.tags.TagsViewModel;
import org.lds.ldssa.ux.annotations.tags.TagsViewModel_Factory;
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionActivity;
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionActivity_MembersInjector;
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionAdapter;
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionViewModel;
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionViewModel_Factory;
import org.lds.ldssa.ux.catalog.CatalogDirectoryActivity;
import org.lds.ldssa.ux.catalog.CatalogDirectoryActivity_MembersInjector;
import org.lds.ldssa.ux.catalog.CatalogDirectoryAdapter;
import org.lds.ldssa.ux.catalog.CatalogDirectoryAdapter_MembersInjector;
import org.lds.ldssa.ux.catalog.CatalogDirectoryViewModel;
import org.lds.ldssa.ux.catalog.CatalogDirectoryViewModel_Factory;
import org.lds.ldssa.ux.content.directory.ContentDirectoryActivity;
import org.lds.ldssa.ux.content.directory.ContentDirectoryActivity_MembersInjector;
import org.lds.ldssa.ux.content.directory.ContentDirectoryAdapter;
import org.lds.ldssa.ux.content.directory.ContentDirectoryAdapter_MembersInjector;
import org.lds.ldssa.ux.content.directory.ContentDirectoryViewModel;
import org.lds.ldssa.ux.content.directory.ContentDirectoryViewModel_Factory;
import org.lds.ldssa.ux.content.item.ContentActivity;
import org.lds.ldssa.ux.content.item.ContentActivity_MembersInjector;
import org.lds.ldssa.ux.content.item.ContentItemFragment;
import org.lds.ldssa.ux.content.item.ContentItemFragment_MembersInjector;
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsActivity;
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsActivity_MembersInjector;
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsAdapter;
import org.lds.ldssa.ux.currentdownloads.CurrentDownloadsAdapter_MembersInjector;
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsActivity;
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsActivity_MembersInjector;
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsAdapter;
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsViewModel;
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsViewModel_Factory;
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryActivity;
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryActivity_MembersInjector;
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryViewModel;
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryViewModel_Factory;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaActivity;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaActivity_MembersInjector;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaAdapter;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaAdapter_MembersInjector;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaCollectionsAdapter;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaCollectionsAdapter_MembersInjector;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaViewModel;
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaViewModel_Factory;
import org.lds.ldssa.ux.locations.LocationsActivity;
import org.lds.ldssa.ux.locations.LocationsPagerAdapter;
import org.lds.ldssa.ux.locations.LocationsPagerAdapter_MembersInjector;
import org.lds.ldssa.ux.locations.bookmarks.BookmarksAdapter;
import org.lds.ldssa.ux.locations.bookmarks.BookmarksAdapter_MembersInjector;
import org.lds.ldssa.ux.locations.bookmarks.BookmarksFragment;
import org.lds.ldssa.ux.locations.bookmarks.BookmarksFragment_MembersInjector;
import org.lds.ldssa.ux.locations.bookmarks.BookmarksViewModel;
import org.lds.ldssa.ux.locations.bookmarks.BookmarksViewModel_Factory;
import org.lds.ldssa.ux.locations.history.HistoryAdapter;
import org.lds.ldssa.ux.locations.history.HistoryAdapter_MembersInjector;
import org.lds.ldssa.ux.locations.history.HistoryFragment;
import org.lds.ldssa.ux.locations.history.HistoryFragment_MembersInjector;
import org.lds.ldssa.ux.locations.screens.ScreensAdapter;
import org.lds.ldssa.ux.locations.screens.ScreensAdapter_MembersInjector;
import org.lds.ldssa.ux.locations.screens.ScreensFragment;
import org.lds.ldssa.ux.locations.screens.ScreensFragment_MembersInjector;
import org.lds.ldssa.ux.search.SearchActivity;
import org.lds.ldssa.ux.search.SearchActivity_MembersInjector;
import org.lds.ldssa.ux.search.SearchPresenter;
import org.lds.ldssa.ux.share.ShareIntentActivity;
import org.lds.ldssa.ux.signin.SignInActivity;
import org.lds.ldssa.ux.signin.SignInActivity_MembersInjector;
import org.lds.ldssa.ux.study.items.StudyItemsActivity;
import org.lds.ldssa.ux.study.items.StudyItemsActivity_MembersInjector;
import org.lds.ldssa.ux.study.items.StudyItemsAdapter;
import org.lds.ldssa.ux.study.items.StudyItemsAdapter_MembersInjector;
import org.lds.ldssa.ux.study.items.StudyItemsViewModel;
import org.lds.ldssa.ux.study.items.StudyItemsViewModel_Factory;
import org.lds.ldssa.ux.study.plans.StudyPlanListFragment;
import org.lds.ldssa.ux.study.plans.StudyPlanListFragment_MembersInjector;
import org.lds.ldssa.ux.study.plans.StudyPlansActivity;
import org.lds.ldssa.ux.study.plans.StudyPlansActivity_MembersInjector;
import org.lds.ldssa.ux.study.plans.StudyPlansAdapter;
import org.lds.ldssa.ux.study.plans.StudyPlansAdapter_MembersInjector;
import org.lds.ldssa.ux.study.plans.StudyPlansPagerAdapter;
import org.lds.ldssa.ux.study.plans.StudyPlansPagerAdapter_MembersInjector;
import org.lds.ldssa.ux.study.plans.StudyPlansViewModel;
import org.lds.ldssa.ux.study.plans.StudyPlansViewModel_Factory;
import org.lds.ldssa.ux.tips.lists.TipListPagerActivity;
import org.lds.ldssa.ux.tips.lists.TipListPagerActivity_MembersInjector;
import org.lds.ldssa.ux.tips.lists.TipListPagerViewModel;
import org.lds.ldssa.ux.tips.lists.TipListPagerViewModel_Factory;
import org.lds.ldssa.ux.tips.lists.listitems.TipListFragment;
import org.lds.ldssa.ux.tips.lists.listitems.TipListFragment_MembersInjector;
import org.lds.ldssa.ux.tips.lists.listitems.TipListViewModel;
import org.lds.ldssa.ux.tips.lists.listitems.TipListViewModel_Factory;
import org.lds.ldssa.ux.tips.pages.TipsPagerActivity;
import org.lds.ldssa.ux.tips.pages.TipsPagerActivity_MembersInjector;
import org.lds.ldssa.ux.tips.pages.TipsPagerViewModel;
import org.lds.ldssa.ux.tips.pages.TipsPagerViewModel_Factory;
import org.lds.ldssa.ux.tips.pages.tip.TipFragment;
import org.lds.ldssa.ux.tips.pages.tip.TipFragment_MembersInjector;
import org.lds.ldssa.ux.tips.pages.tip.TipViewModel;
import org.lds.ldssa.ux.tips.pages.tip.TipViewModel_Factory;
import org.lds.ldssa.ux.welcome.WelcomeActivity;
import org.lds.ldssa.ux.welcome.WelcomeActivity_MembersInjector;
import org.lds.ldssa.ux.welcome.WelcomeViewModel;
import org.lds.ldssa.ux.welcome.WelcomeViewModel_Factory;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.inject.LDSMobileCommonsModule;
import org.lds.mobile.inject.viewmodel.ViewModelFactory;
import org.lds.mobile.inject.viewmodel.ViewModelFactory_Factory;
import org.lds.mobile.media.cast.CastManager;
import org.lds.mobile.media.dagger.LDSMobileMediaModule;
import org.lds.mobile.util.EncryptUtil;
import org.lds.mobile.util.EncryptUtil_Factory;
import org.lds.mobile.util.LdsDeviceUtil;
import org.lds.mobile.util.LdsDeviceUtil_Factory;
import org.lds.mobile.util.LdsFeedbackUtil;
import org.lds.mobile.util.LdsFeedbackUtil_Factory;
import org.lds.mobile.util.LdsKeyboardUtil;
import org.lds.mobile.util.LdsKeyboardUtil_Factory;
import org.lds.mobile.util.LdsNetworkUtil;
import org.lds.mobile.util.LdsNetworkUtil_Factory;
import org.lds.mobile.util.LdsStorageUtil;
import org.lds.mobile.util.LdsStorageUtil_Factory;
import org.lds.mobile.util.LdsThreadUtil;
import org.lds.mobile.util.LdsThreadUtil_Factory;
import org.lds.mobile.util.LdsTimeUtil;
import org.lds.mobile.util.LdsTimeUtil_Factory;
import org.lds.mobile.util.LdsUiUtil;
import org.lds.mobile.util.LdsUiUtil_Factory;
import org.lds.mobile.util.LdsZipUtil;
import org.lds.mobile.util.LdsZipUtil_Factory;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<Application> provideApplicationProvider;

  private Provider<Analytics> provideAnalyticsProvider;

  private Provider<SharedPreferences> provideDefaultSharedPreferencesProvider;

  private Provider<SharedPreferences> provideBackedUpSharedPreferencesProvider;

  private Provider<Prefs> prefsProvider;

  private Provider<CoroutineContextProvider> provideCoroutineContextProvider;

  private Provider<SharedPreferences> provideSharedPreferencesProvider;

  private Provider<EncryptUtil> encryptUtilProvider;

  private Provider<LDSAccountPrefs> lDSAccountPrefsProvider;

  private Provider<Bus> provideEventBusProvider;

  private Provider<LDSAccountLogger> provideLDSAccountLoggerProvider;

  private Provider<CredentialsManager> provideCredentialsManagerProvider;

  private Provider<ConnectivityManager> provideConnectivityManagerProvider;

  private Provider<NetworkConnectionManager> provideNetworkConnectionManagerProvider;

  private Provider<LDSAccountAuth> lDSAccountAuthProvider;

  private Provider<LDSAccountUtil> lDSAccountUtilProvider;

  private Provider<LDSAccountInterceptor> provideLDSAccountInterceptorProvider;

  private Provider<OkHttpClient> getAuthenticatedClientProvider;

  private Provider<LDSAnnotationService> getAnnotationServiceProvider;

  private Provider<AssetManager> provideAssetManagerProvider;

  private Provider<LdsZipUtil> ldsZipUtilProvider;

  private Provider<ToastUtil> toastUtilProvider;

  private Provider<GLFileUtil> gLFileUtilProvider;

  private Provider<DatabaseConfig> provideDatabaseConfigProvider;

  private Provider<DatabaseManager> databaseManagerProvider;

  private Provider<Gson> provideGsonProvider;

  private Provider<AnnotationSyncScheduler> annotationSyncSchedulerProvider;

  private Provider<UserdataDbUtil> userdataDbUtilProvider;

  private Provider<HighlightManager> highlightManagerProvider;

  private Provider<BookmarkManager> bookmarkManagerProvider;

  private Provider<NoteManager> noteManagerProvider;

  private Provider<LinkManager> linkManagerProvider;

  private Provider<TagManager> tagManagerProvider;

  private Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider;

  private Provider<NotebookManager> notebookManagerProvider;

  private Provider<AnnotationManager> annotationManagerProvider;

  private Provider<LdsTimeUtil> ldsTimeUtilProvider;

  private Provider<ItemManager> itemManagerProvider;

  private Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider;

  private Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private Provider<DownloadManager> provideAndroidDownloadManagerProvider;

  private Provider<DownloadManagerHelper> provideDownloadManagerHelperProvider;

  private Provider<LdsNetworkUtil> ldsNetworkUtilProvider;

  private Provider<LibraryItemManager> libraryItemManagerProvider;

  private Provider<SyncContentItemAnnotationsQueueItemManager>
      syncContentItemAnnotationsQueueItemManagerProvider;

  private Provider<ContentItemUpdateUtil> contentItemUpdateUtilProvider;

  private Provider<DownloadedContentProcessor> downloadedContentProcessorProvider;

  private Provider<DownloadedMediaProcessor> downloadedMediaProcessorProvider;

  private Provider<ScreenHistoryItemManager> screenHistoryItemManagerProvider;

  private Provider<ScreenManager> screenManagerProvider;

  private Provider<CastManager> provideCastManagerProvider;

  private Provider<PlaylistManager> playlistManagerProvider;

  private Provider<ImageUtil> imageUtilProvider;

  private Provider<ActivityManager> provideActivityManagerProvider;

  private Provider<LanguageManager> languageManagerProvider;

  private Provider<LanguageUtil> languageUtilProvider;

  private Provider<RecentLanguageManager> recentLanguageManagerProvider;

  private Provider<SearchCollectionManager> searchCollectionManagerProvider;

  private Provider<SearchCountAllNotesManager> searchCountAllNotesManagerProvider;

  private Provider<SearchCountContentManager> searchCountContentManagerProvider;

  private Provider<SearchContentCollectionMapManager> searchContentCollectionMapManagerProvider;

  private Provider<SearchPreviewNoteManager> searchPreviewNoteManagerProvider;

  private Provider<SearchPreviewSubitemManager> searchPreviewSubitemManagerProvider;

  private Provider<StopWordManager> stopWordManagerProvider;

  private Provider<SearchUtil> searchUtilProvider;

  private Provider<ScreenUtil> screenUtilProvider;

  private Provider<SubItemMetadataManager> subItemMetadataManagerProvider;

  private Provider<NavCollectionManager> navCollectionManagerProvider;

  private Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private Provider<UriUtil> uriUtilProvider;

  private Provider<ContentMetaDataManager> contentMetaDataManagerProvider;

  private Provider<CheckContentVersionsTask> checkContentVersionsTaskProvider;

  private Provider<LibraryCollectionManager> libraryCollectionManagerProvider;

  private Provider<InternalIntents> internalIntentsProvider;

  private Provider<OkHttpClient> getStandardClientProvider;

  private Provider<CatalogService> provideCatalogServiceProvider;

  private Provider<RoleCatalogService> provideRoleCatalogServiceProvider;

  private Provider<RoleBasedContentService> provideRoleBasedContentServiceProvider;

  private Provider<CatalogUtil> catalogUtilProvider;

  private Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider;

  private Provider<RoleCatalogManager> roleCatalogManagerProvider;

  private Provider<CatalogDownloader> catalogDownloaderProvider;

  private Provider<CatalogSourceManager> catalogSourceManagerProvider;

  private Provider<DatabaseUtil> databaseUtilProvider;

  private Provider<CatalogUpdateUtil> catalogUpdateUtilProvider;

  private Provider<DownloadedCatalogProcessor> downloadedCatalogProcessorProvider;

  private Provider<DownloadedTipsProcessor> downloadedTipsProcessorProvider;

  private Provider<MediaDownloader> mediaDownloaderProvider;

  private Provider<NavItemManager> navItemManagerProvider;

  private Provider<AllAudioDownloader> allAudioDownloaderProvider;

  private Provider<ContentDownloader> contentDownloaderProvider;

  private Provider<CancelDownloadsTask> cancelDownloadsTaskProvider;

  private Provider<RepairMissingDownloadedItems> repairMissingDownloadedItemsProvider;

  private Provider<GLDownloadManager> gLDownloadManagerProvider;

  private Provider<ContentItemUtil> contentItemUtilProvider;

  private Provider<SubItemManager> subItemManagerProvider;

  private Provider<CitationUtil> citationUtilProvider;

  private Provider<NotificationManager> provideNotificationManagerProvider;

  private Provider<AnnotationSyncNotification> annotationSyncNotificationProvider;

  private Provider<AnnotationSyncProcessor> annotationSyncProcessorProvider;

  private Provider<AnnotationChangeProcessor> annotationChangeProcessorProvider;

  private Provider<FolderSyncProcessor> folderSyncProcessorProvider;

  private Provider<AuthenticationFailureNotification> authenticationFailureNotificationProvider;

  private Provider<WebServiceUtil> webServiceUtilProvider;

  private Provider<AnnotationFixTask> annotationFixTaskProvider;

  private Provider<LdsThreadUtil> ldsThreadUtilProvider;

  private Provider<AnnotationSync> annotationSyncProvider;

  private Provider<AnnotationSyncJob> annotationSyncJobProvider;

  private Provider<AppJobCreator> appJobCreatorProvider;

  private Provider<AccountUtil> accountUtilProvider;

  private Provider<TabHistoryItemManager> tabHistoryItemManagerProvider;

  private Provider<TabManager> tabManagerProvider;

  private Provider<AppConfigService> provideAppConfigServiceProvider;

  private Provider<AppUpdateNotification> appUpdateNotificationProvider;

  private Provider<AppUpdateUtil> appUpdateUtilProvider;

  private Provider<TipsService> provideTipsServiceProvider;

  private Provider<TipManager> tipManagerProvider;

  private Provider<TipsUtil> tipsUtilProvider;

  private Provider<TipsMetaDataManager> tipsMetaDataManagerProvider;

  private Provider<TipsDownloader> tipsDownloaderProvider;

  private Provider<TipsUpdateUtil> tipsUpdateUtilProvider;

  private Provider<TipsUpdateTask> tipsUpdateTaskProvider;

  private AppModule appModule;

  private Provider<DownloadReceivedTask> downloadReceivedTaskProvider;

  private Provider<ItemCategoryManager> itemCategoryManagerProvider;

  private Provider<LanguageNameManager> languageNameManagerProvider;

  private Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider;

  private Provider<VideoUtil> videoUtilProvider;

  private Provider<CommonMenu> commonMenuProvider;

  private Provider<ThemeUtil> themeUtilProvider;

  private Provider<AnalyticsUtil> analyticsUtilProvider;

  private Provider<SubItemContentManager> subItemContentManagerProvider;

  private Provider<ContentParagraphUtil> contentParagraphUtilProvider;

  private Provider<HighlightUtil> highlightUtilProvider;

  private Provider<MarkdownUtil> markdownUtilProvider;

  private Provider<AnnotationUiUtil> annotationUiUtilProvider;

  private Provider<ShareUtil> shareUtilProvider;

  private Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider;

  private Provider<CatalogItemQueryManager> catalogItemQueryManagerProvider;

  private Provider<CustomCollectionItemManager> customCollectionItemManagerProvider;

  private Provider<CustomCollectionManager> customCollectionManagerProvider;

  private Provider<CatalogDirectoryViewModel> catalogDirectoryViewModelProvider;

  private Provider<CustomCollectionsViewModel> customCollectionsViewModelProvider;

  private Provider<CustomCollectionDirectoryViewModel> customCollectionDirectoryViewModelProvider;

  private Provider<ContentDirectoryItemQueryManager> contentDirectoryItemQueryManagerProvider;

  private Provider<NavCollectionIndexEntryManager> navCollectionIndexEntryManagerProvider;

  private Provider<ContentDirectoryViewModel> contentDirectoryViewModelProvider;

  private Provider<AllAnnotationsViewModel> allAnnotationsViewModelProvider;

  private Provider<AnnotationsViewModel> annotationsViewModelProvider;

  private Provider<TagViewManager> tagViewManagerProvider;

  private Provider<TagUtil> tagUtilProvider;

  private Provider<TagSelectionViewModel> tagSelectionViewModelProvider;

  private Provider<TagsViewModel> tagsViewModelProvider;

  private Provider<NotebookViewManager> notebookViewManagerProvider;

  private Provider<NotebookSelectionViewModel> notebookSelectionViewModelProvider;

  private Provider<NotebooksViewModel> notebooksViewModelProvider;

  private Provider<BookmarkQueryManager> bookmarkQueryManagerProvider;

  private Provider<BookmarkUtil> bookmarkUtilProvider;

  private Provider<BookmarksViewModel> bookmarksViewModelProvider;

  private Provider<LinkUtil> linkUtilProvider;

  private Provider<NavSectionManager> navSectionManagerProvider;

  private Provider<SearchGotoQueryManager> searchGotoQueryManagerProvider;

  private Provider<SearchSuggestionManager> searchSuggestionManagerProvider;

  private Provider<LinksViewModel> linksViewModelProvider;

  private Provider<DownloadedMediaCollectionManager> downloadedMediaCollectionManagerProvider;

  private Provider<DownloadedMediaViewModel> downloadedMediaViewModelProvider;

  private Provider<StudyPlansViewModel> studyPlansViewModelProvider;

  private Provider<StudyItemsViewModel> studyItemsViewModelProvider;

  private Provider<TipViewedManager> tipViewedManagerProvider;

  private Provider<TipViewModel> tipViewModelProvider;

  private Provider<TipsPagerViewModel> tipsPagerViewModelProvider;

  private Provider<WelcomeViewModel> welcomeViewModelProvider;

  private Provider<TipQueryManager> tipQueryManagerProvider;

  private Provider<TipListViewModel> tipListViewModelProvider;

  private Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>>
      mapOfClassOfAndProviderOfViewModelProvider;

  private Provider<ViewModelFactory> viewModelFactoryProvider;

  private Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private Provider<HistoryManager> historyManagerProvider;

  private Provider<CheckDownloadsTask> checkDownloadsTaskProvider;

  private Provider<InitialContentDownloadTask> initialContentDownloadTaskProvider;

  private Provider<StartupTask> startupTaskProvider;

  private Provider<InputMethodManager> provideInputMethodManagerProvider;

  private Provider<LdsKeyboardUtil> ldsKeyboardUtilProvider;

  private Provider<SearchHistoryManager> searchHistoryManagerProvider;

  private Provider<AllSubItemsInNavCollectionQueryManager>
      allSubItemsInNavCollectionQueryManagerProvider;

  private Provider<SubItemSearchQueryManager> subItemSearchQueryManagerProvider;

  private Provider<SearchAllCountManager> searchAllCountManagerProvider;

  private Provider<CustomCollectionUtil> customCollectionUtilProvider;

  private Provider<CatalogDirectoryMenu> catalogDirectoryMenuProvider;

  private Provider<AllItemsInCollectionQueryManager> allItemsInCollectionQueryManagerProvider;

  private Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider;

  private Provider<PackageManager> providePackageManagerProvider;

  private Provider<LdsMusicUtil> ldsMusicUtilProvider;

  private Provider<ExternalIntents> externalIntentsProvider;

  private Provider<RelatedVideoItemManager> relatedVideoItemManagerProvider;

  private Provider<RelatedVideoItemSourceManager> relatedVideoItemSourceManagerProvider;

  private Provider<RelatedVideoUtil> relatedVideoUtilProvider;

  private Provider<ContentRenderer> contentRendererProvider;

  private Provider<AuthorManager> authorManagerProvider;

  private Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider;

  private Provider<TextToSpeechManager> textToSpeechManagerProvider;

  private Provider<AvailableRelatedTypeQueryManager> availableRelatedTypeQueryManagerProvider;

  private Provider<NoteUtil> noteUtilProvider;

  private Provider<LdsStorageUtil> ldsStorageUtilProvider;

  private Provider<LdsDeviceUtil> ldsDeviceUtilProvider;

  private Provider<WindowManager> provideWindowManagerProvider;

  private Provider<LdsUiUtil> ldsUiUtilProvider;

  private Provider<LdsFeedbackUtil> ldsFeedbackUtilProvider;

  private Provider<DeviceUtil> deviceUtilProvider;

  private Provider<FeedbackUtil> feedbackUtilProvider;

  private Provider<NotebookUtil> notebookUtilProvider;

  private Provider<RestoreJournalUtil> restoreJournalUtilProvider;

  private Provider<CustomCollectionDirectoryMenu> customCollectionDirectoryMenuProvider;

  private Provider<TextHandleUtil> textHandleUtilProvider;

  private Provider<ContentJsInvoker> contentJsInvokerProvider;

  private Provider<SidebarHistoryItemManager> sidebarHistoryItemManagerProvider;

  private Provider<RelatedContentItemManager> relatedContentItemManagerProvider;

  private Provider<ItemCollectionViewManager> itemCollectionViewManagerProvider;

  private Provider<NoteSearchQueryManager> noteSearchQueryManagerProvider;

  private Provider<SearchEngineLocal> searchEngineLocalProvider;

  private DaggerAppComponent(Builder builder) {
    initialize(builder);
    initialize2(builder);
    initialize3(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  private AppUpgradeUtil getAppUpgradeUtil() {
    return new AppUpgradeUtil(
        provideApplicationProvider.get(),
        prefsProvider.get(),
        lDSAccountPrefsProvider.get(),
        encryptUtilProvider.get(),
        gLFileUtilProvider.get(),
        ldsTimeUtilProvider.get(),
        accountUtilProvider.get(),
        ldsZipUtilProvider.get(),
        annotationManagerProvider.get(),
        highlightManagerProvider.get(),
        tabManagerProvider.get(),
        screenManagerProvider.get(),
        screenHistoryItemManagerProvider.get(),
        userdataDbUtilProvider.get());
  }

  private GLUpdateLifecycle getGLUpdateLifecycle() {
    return new GLUpdateLifecycle(
        prefsProvider.get(),
        provideCoroutineContextProvider.get(),
        lDSAccountUtilProvider.get(),
        appUpdateUtilProvider.get(),
        catalogUpdateUtilProvider.get(),
        tipsUpdateTaskProvider,
        annotationSyncProvider.get(),
        internalIntentsProvider.get());
  }

  private ActivityManager getActivityManager() {
    return Preconditions.checkNotNull(
        appModule.provideActivityManager(provideApplicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  private ScreenUtil getScreenUtil() {
    return new ScreenUtil(
        screenManagerProvider.get(),
        prefsProvider.get(),
        screenHistoryItemManagerProvider.get(),
        playlistManagerProvider.get(),
        imageUtilProvider.get(),
        getActivityManager(),
        languageUtilProvider.get(),
        recentLanguageManagerProvider.get(),
        searchUtilProvider.get(),
        provideGsonProvider.get());
  }

  private AnalyticsUtil getAnalyticsUtil() {
    return new AnalyticsUtil(
        provideAnalyticsProvider.get(),
        contentMetaDataManagerProvider.get(),
        contentItemUtilProvider.get(),
        itemManagerProvider.get(),
        itemCategoryManagerProvider.get(),
        languageNameManagerProvider.get(),
        subItemManagerProvider.get(),
        subItemMetadataManagerProvider.get(),
        annotationManagerProvider.get(),
        getScreenUtil(),
        bookmarkManagerProvider.get(),
        noteManagerProvider.get(),
        tipManagerProvider.get());
  }

  private DownloadedMediaUtil getDownloadedMediaUtil() {
    return new DownloadedMediaUtil(
        gLFileUtilProvider.get(),
        downloadedMediaManagerProvider.get(),
        gLDownloadManagerProvider.get());
  }

  private ScreenLauncherUtil getScreenLauncherUtil() {
    return new ScreenLauncherUtil(
        provideApplicationProvider.get(),
        screenHistoryItemManagerProvider.get(),
        internalIntentsProvider.get(),
        contentItemUtilProvider.get(),
        getScreenUtil(),
        gLDownloadManagerProvider.get(),
        provideGsonProvider.get());
  }

  private SearchPresenter getSearchPresenter() {
    return new SearchPresenter(
        provideEventBusProvider.get(),
        provideAnalyticsProvider.get(),
        provideCoroutineContextProvider.get(),
        searchUtilProvider.get(),
        itemManagerProvider.get(),
        searchHistoryManagerProvider.get(),
        navSectionManagerProvider.get(),
        navItemManagerProvider.get(),
        paragraphMetadataManagerProvider.get(),
        searchPreviewSubitemManagerProvider.get(),
        subItemSearchQueryManagerProvider.get(),
        searchPreviewNoteManagerProvider.get(),
        searchSuggestionManagerProvider.get(),
        searchAllCountManagerProvider.get(),
        searchCountContentManagerProvider.get(),
        searchCollectionManagerProvider.get(),
        searchCountAllNotesManagerProvider.get());
  }

  private RelatedAudioAvailableUtil getRelatedAudioAvailableUtil() {
    return new RelatedAudioAvailableUtil(
        prefsProvider.get(), relatedAudioItemManagerProvider.get());
  }

  private DeviceUtil getDeviceUtil() {
    return new DeviceUtil(provideApplicationProvider.get());
  }

  private InputMethodManager getInputMethodManager() {
    return Preconditions.checkNotNull(
        appModule.provideInputMethodManager(provideApplicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  private ContentJsInterface getContentJsInterface() {
    return new ContentJsInterface(ldsUiUtilProvider.get(), provideGsonProvider.get());
  }

  private NotificationManager getNotificationManager() {
    return Preconditions.checkNotNull(
        appModule.provideNotificationManager(provideApplicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  private TextToSpeechEngine getTextToSpeechEngine() {
    return new TextToSpeechEngine(
        provideApplicationProvider.get(),
        textToSpeechManagerProvider.get(),
        contentRendererProvider.get(),
        itemManagerProvider.get(),
        languageUtilProvider.get(),
        new TextToSpeechGenerator(),
        getScreenUtil(),
        internalIntentsProvider.get(),
        mediaPlaybackPositionManagerProvider.get(),
        provideAnalyticsProvider.get(),
        getAnalyticsUtil(),
        provideCoroutineContextProvider.get(),
        getNotificationManager());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideApplicationProvider =
        DoubleCheck.provider(AppModule_ProvideApplicationFactory.create(builder.appModule));
    this.provideAnalyticsProvider =
        DoubleCheck.provider(
            AppModule_ProvideAnalyticsFactory.create(
                builder.appModule, provideApplicationProvider));
    this.provideDefaultSharedPreferencesProvider =
        PrefsModule_ProvideDefaultSharedPreferencesFactory.create(
            builder.prefsModule, provideApplicationProvider);
    this.provideBackedUpSharedPreferencesProvider =
        PrefsModule_ProvideBackedUpSharedPreferencesFactory.create(
            builder.prefsModule, provideApplicationProvider);
    this.prefsProvider =
        DoubleCheck.provider(
            Prefs_Factory.create(
                provideApplicationProvider,
                provideDefaultSharedPreferencesProvider,
                provideBackedUpSharedPreferencesProvider));
    this.provideCoroutineContextProvider =
        DoubleCheck.provider(
            AppModule_ProvideCoroutineContextProviderFactory.create(builder.appModule));
    this.provideSharedPreferencesProvider =
        LDSAccountModule_ProvideSharedPreferencesFactory.create(
            builder.lDSAccountModule, provideApplicationProvider);
    this.encryptUtilProvider = DoubleCheck.provider(EncryptUtil_Factory.create());
    this.lDSAccountPrefsProvider =
        DoubleCheck.provider(
            LDSAccountPrefs_Factory.create(provideSharedPreferencesProvider, encryptUtilProvider));
    this.provideEventBusProvider =
        DoubleCheck.provider(AppModule_ProvideEventBusFactory.create(builder.appModule));
    this.provideLDSAccountLoggerProvider =
        LDSAccountModule_ProvideLDSAccountLoggerFactory.create(builder.lDSAccountModule);
    this.provideCredentialsManagerProvider =
        DoubleCheck.provider(
            AppModule_ProvideCredentialsManagerFactory.create(
                builder.appModule, lDSAccountPrefsProvider));
    this.provideConnectivityManagerProvider =
        AppModule_ProvideConnectivityManagerFactory.create(
            builder.appModule, provideApplicationProvider);
    this.provideNetworkConnectionManagerProvider =
        DoubleCheck.provider(
            AppModule_ProvideNetworkConnectionManagerFactory.create(
                builder.appModule, provideConnectivityManagerProvider));
    this.lDSAccountAuthProvider =
        DoubleCheck.provider(
            LDSAccountAuth_Factory.create(
                provideCredentialsManagerProvider,
                provideLDSAccountLoggerProvider,
                provideNetworkConnectionManagerProvider));
    this.lDSAccountUtilProvider =
        DoubleCheck.provider(
            LDSAccountUtil_Factory.create(
                lDSAccountAuthProvider,
                provideCredentialsManagerProvider,
                provideLDSAccountLoggerProvider));
    this.provideLDSAccountInterceptorProvider =
        AppModule_ProvideLDSAccountInterceptorFactory.create(
            builder.appModule,
            provideLDSAccountLoggerProvider,
            lDSAccountUtilProvider,
            lDSAccountAuthProvider);
    this.getAuthenticatedClientProvider =
        ServiceModule_GetAuthenticatedClientFactory.create(
            builder.serviceModule, provideLDSAccountInterceptorProvider, lDSAccountAuthProvider);
    this.getAnnotationServiceProvider =
        DoubleCheck.provider(
            ServiceModule_GetAnnotationServiceFactory.create(
                builder.serviceModule, getAuthenticatedClientProvider));
    this.provideAssetManagerProvider =
        AppModule_ProvideAssetManagerFactory.create(builder.appModule, provideApplicationProvider);
    this.ldsZipUtilProvider =
        DoubleCheck.provider(LdsZipUtil_Factory.create(provideAssetManagerProvider));
    this.toastUtilProvider =
        DoubleCheck.provider(
            ToastUtil_Factory.create(provideApplicationProvider, provideCoroutineContextProvider));
    this.gLFileUtilProvider =
        DoubleCheck.provider(
            GLFileUtil_Factory.create(
                provideApplicationProvider, ldsZipUtilProvider, toastUtilProvider));
    this.provideDatabaseConfigProvider =
        DoubleCheck.provider(
            AppModule_ProvideDatabaseConfigFactory.create(builder.appModule, gLFileUtilProvider));
    this.databaseManagerProvider =
        DoubleCheck.provider(DatabaseManager_Factory.create(provideDatabaseConfigProvider));
    this.provideGsonProvider =
        DoubleCheck.provider(AppModule_ProvideGsonFactory.create(builder.appModule));
    this.annotationSyncSchedulerProvider =
        DoubleCheck.provider(AnnotationSyncScheduler_Factory.create());
    this.userdataDbUtilProvider =
        DoubleCheck.provider(
            UserdataDbUtil_Factory.create(
                prefsProvider, gLFileUtilProvider, databaseManagerProvider));
    this.highlightManagerProvider =
        DoubleCheck.provider(
            HighlightManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.bookmarkManagerProvider =
        DoubleCheck.provider(
            BookmarkManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.noteManagerProvider =
        DoubleCheck.provider(
            NoteManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.linkManagerProvider =
        DoubleCheck.provider(
            LinkManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.tagManagerProvider =
        DoubleCheck.provider(
            TagManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.notebookAnnotationManagerProvider =
        DoubleCheck.provider(
            NotebookAnnotationManager_Factory.create(
                databaseManagerProvider, userdataDbUtilProvider));
    this.notebookManagerProvider =
        DoubleCheck.provider(
            NotebookManager_Factory.create(
                databaseManagerProvider,
                userdataDbUtilProvider,
                notebookAnnotationManagerProvider,
                annotationSyncSchedulerProvider));
    this.annotationManagerProvider =
        DoubleCheck.provider(
            AnnotationManager_Factory.create(
                databaseManagerProvider,
                provideEventBusProvider,
                provideGsonProvider,
                annotationSyncSchedulerProvider,
                highlightManagerProvider,
                bookmarkManagerProvider,
                noteManagerProvider,
                linkManagerProvider,
                tagManagerProvider,
                userdataDbUtilProvider,
                notebookAnnotationManagerProvider,
                notebookManagerProvider));
    this.ldsTimeUtilProvider = DoubleCheck.provider(LdsTimeUtil_Factory.create());
    this.itemManagerProvider =
        DoubleCheck.provider(ItemManager_Factory.create(databaseManagerProvider));
    this.downloadedItemManagerProvider =
        DoubleCheck.provider(
            DownloadedItemManager_Factory.create(databaseManagerProvider, itemManagerProvider));
    this.downloadQueueItemManagerProvider =
        DoubleCheck.provider(
            DownloadQueueItemManager_Factory.create(
                databaseManagerProvider, provideCoroutineContextProvider));
    this.downloadedMediaManagerProvider =
        DoubleCheck.provider(DownloadedMediaManager_Factory.create(databaseManagerProvider));
    this.provideAndroidDownloadManagerProvider =
        DoubleCheck.provider(
            AppModule_ProvideAndroidDownloadManagerFactory.create(
                builder.appModule, provideApplicationProvider));
    this.provideDownloadManagerHelperProvider =
        DoubleCheck.provider(
            AppModule_ProvideDownloadManagerHelperFactory.create(
                builder.appModule, provideAndroidDownloadManagerProvider));
    this.ldsNetworkUtilProvider =
        DoubleCheck.provider(LdsNetworkUtil_Factory.create(provideConnectivityManagerProvider));
    this.gLDownloadManagerProvider = new DelegateFactory<>();
    this.libraryItemManagerProvider =
        DoubleCheck.provider(LibraryItemManager_Factory.create(databaseManagerProvider));
    this.syncContentItemAnnotationsQueueItemManagerProvider =
        DoubleCheck.provider(
            SyncContentItemAnnotationsQueueItemManager_Factory.create(
                databaseManagerProvider, userdataDbUtilProvider));
    this.contentItemUtilProvider = new DelegateFactory<>();
    this.contentItemUpdateUtilProvider =
        ContentItemUpdateUtil_Factory.create(
            provideApplicationProvider,
            prefsProvider,
            provideEventBusProvider,
            provideAnalyticsProvider,
            gLFileUtilProvider,
            ldsZipUtilProvider,
            itemManagerProvider,
            downloadedItemManagerProvider,
            downloadQueueItemManagerProvider,
            provideDownloadManagerHelperProvider,
            gLDownloadManagerProvider,
            libraryItemManagerProvider,
            annotationSyncSchedulerProvider,
            syncContentItemAnnotationsQueueItemManagerProvider,
            contentItemUtilProvider);
    this.downloadedContentProcessorProvider =
        DownloadedContentProcessor_Factory.create(contentItemUpdateUtilProvider);
    this.downloadedMediaProcessorProvider =
        DownloadedMediaProcessor_Factory.create(
            provideEventBusProvider,
            provideAnalyticsProvider,
            downloadQueueItemManagerProvider,
            provideDownloadManagerHelperProvider,
            downloadedMediaManagerProvider,
            gLFileUtilProvider);
    this.screenHistoryItemManagerProvider =
        DoubleCheck.provider(
            ScreenHistoryItemManager_Factory.create(
                databaseManagerProvider, userdataDbUtilProvider));
    this.screenManagerProvider =
        DoubleCheck.provider(
            ScreenManager_Factory.create(
                databaseManagerProvider,
                gLFileUtilProvider,
                screenHistoryItemManagerProvider,
                userdataDbUtilProvider));
    this.provideCastManagerProvider =
        DoubleCheck.provider(
            AppModule_ProvideCastManagerFactory.create(
                builder.appModule, provideApplicationProvider));
    this.playlistManagerProvider =
        DoubleCheck.provider(
            PlaylistManager_Factory.create(provideApplicationProvider, provideCastManagerProvider));
    this.imageUtilProvider =
        DoubleCheck.provider(ImageUtil_Factory.create(prefsProvider, gLFileUtilProvider));
    this.provideActivityManagerProvider =
        AppModule_ProvideActivityManagerFactory.create(
            builder.appModule, provideApplicationProvider);
    this.languageManagerProvider =
        DoubleCheck.provider(LanguageManager_Factory.create(databaseManagerProvider));
    this.languageUtilProvider =
        DoubleCheck.provider(LanguageUtil_Factory.create(languageManagerProvider));
    this.recentLanguageManagerProvider =
        DoubleCheck.provider(RecentLanguageManager_Factory.create(databaseManagerProvider));
    this.searchCollectionManagerProvider =
        DoubleCheck.provider(SearchCollectionManager_Factory.create(databaseManagerProvider));
    this.searchCountAllNotesManagerProvider =
        DoubleCheck.provider(SearchCountAllNotesManager_Factory.create(databaseManagerProvider));
    this.searchCountContentManagerProvider =
        DoubleCheck.provider(SearchCountContentManager_Factory.create(databaseManagerProvider));
    this.searchContentCollectionMapManagerProvider =
        DoubleCheck.provider(
            SearchContentCollectionMapManager_Factory.create(databaseManagerProvider));
    this.searchPreviewNoteManagerProvider =
        DoubleCheck.provider(SearchPreviewNoteManager_Factory.create(databaseManagerProvider));
    this.searchPreviewSubitemManagerProvider =
        DoubleCheck.provider(SearchPreviewSubitemManager_Factory.create(databaseManagerProvider));
    this.stopWordManagerProvider =
        DoubleCheck.provider(StopWordManager_Factory.create(databaseManagerProvider));
    this.searchUtilProvider =
        DoubleCheck.provider(
            SearchUtil_Factory.create(
                prefsProvider,
                languageUtilProvider,
                searchCollectionManagerProvider,
                searchCountAllNotesManagerProvider,
                searchCountContentManagerProvider,
                searchContentCollectionMapManagerProvider,
                searchPreviewNoteManagerProvider,
                searchPreviewSubitemManagerProvider,
                stopWordManagerProvider));
    this.screenUtilProvider =
        ScreenUtil_Factory.create(
            screenManagerProvider,
            prefsProvider,
            screenHistoryItemManagerProvider,
            playlistManagerProvider,
            imageUtilProvider,
            provideActivityManagerProvider,
            languageUtilProvider,
            recentLanguageManagerProvider,
            searchUtilProvider,
            provideGsonProvider);
    this.subItemMetadataManagerProvider =
        DoubleCheck.provider(SubItemMetadataManager_Factory.create(databaseManagerProvider));
    this.navCollectionManagerProvider =
        DoubleCheck.provider(
            NavCollectionManager_Factory.create(databaseManagerProvider, contentItemUtilProvider));
    this.subItemManagerProvider = new DelegateFactory<>();
    this.paragraphMetadataManagerProvider =
        DoubleCheck.provider(
            ParagraphMetadataManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.citationUtilProvider = new DelegateFactory<>();
    this.uriUtilProvider =
        DoubleCheck.provider(
            UriUtil_Factory.create(
                itemManagerProvider,
                subItemManagerProvider,
                paragraphMetadataManagerProvider,
                subItemMetadataManagerProvider,
                languageManagerProvider,
                citationUtilProvider,
                contentItemUtilProvider));
    this.contentMetaDataManagerProvider =
        DoubleCheck.provider(
            ContentMetaDataManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.checkContentVersionsTaskProvider =
        CheckContentVersionsTask_Factory.create(
            gLDownloadManagerProvider,
            downloadedItemManagerProvider,
            itemManagerProvider,
            ldsTimeUtilProvider);
    this.libraryCollectionManagerProvider =
        DoubleCheck.provider(
            LibraryCollectionManager_Factory.create(
                databaseManagerProvider, languageManagerProvider));
    this.internalIntentsProvider =
        DoubleCheck.provider(
            InternalIntents_Factory.create(
                provideApplicationProvider,
                screenUtilProvider,
                prefsProvider,
                languageManagerProvider,
                languageUtilProvider,
                annotationManagerProvider,
                bookmarkManagerProvider,
                subItemMetadataManagerProvider,
                navCollectionManagerProvider,
                downloadQueueItemManagerProvider,
                contentItemUtilProvider,
                gLDownloadManagerProvider,
                itemManagerProvider,
                subItemManagerProvider,
                uriUtilProvider,
                contentMetaDataManagerProvider,
                checkContentVersionsTaskProvider,
                libraryCollectionManagerProvider,
                toastUtilProvider));
    this.getStandardClientProvider =
        ServiceModule_GetStandardClientFactory.create(builder.serviceModule);
    this.provideCatalogServiceProvider =
        DoubleCheck.provider(
            ServiceModule_ProvideCatalogServiceFactory.create(
                builder.serviceModule, getStandardClientProvider));
    this.provideRoleCatalogServiceProvider =
        DoubleCheck.provider(
            ServiceModule_ProvideRoleCatalogServiceFactory.create(
                builder.serviceModule, getAuthenticatedClientProvider));
    this.provideRoleBasedContentServiceProvider =
        DoubleCheck.provider(
            ServiceModule_ProvideRoleBasedContentServiceFactory.create(
                builder.serviceModule, getAuthenticatedClientProvider));
    this.catalogUtilProvider =
        DoubleCheck.provider(
            CatalogUtil_Factory.create(
                prefsProvider,
                provideCatalogServiceProvider,
                provideRoleCatalogServiceProvider,
                provideRoleBasedContentServiceProvider));
    this.catalogMetaDataManagerProvider =
        DoubleCheck.provider(CatalogMetaDataManager_Factory.create(databaseManagerProvider));
    this.roleCatalogManagerProvider =
        DoubleCheck.provider(RoleCatalogManager_Factory.create(databaseManagerProvider));
    this.catalogDownloaderProvider =
        CatalogDownloader_Factory.create(
            provideApplicationProvider,
            catalogUtilProvider,
            gLFileUtilProvider,
            roleCatalogManagerProvider,
            prefsProvider,
            lDSAccountUtilProvider,
            downloadQueueItemManagerProvider,
            gLDownloadManagerProvider,
            catalogMetaDataManagerProvider);
    this.catalogSourceManagerProvider =
        DoubleCheck.provider(CatalogSourceManager_Factory.create(databaseManagerProvider));
    this.databaseUtilProvider =
        DatabaseUtil_Factory.create(
            databaseManagerProvider,
            prefsProvider,
            catalogMetaDataManagerProvider,
            ldsTimeUtilProvider);
    this.catalogUpdateUtilProvider =
        DoubleCheck.provider(
            CatalogUpdateUtil_Factory.create(
                prefsProvider,
                internalIntentsProvider,
                provideEventBusProvider,
                gLFileUtilProvider,
                ldsNetworkUtilProvider,
                catalogUtilProvider,
                catalogMetaDataManagerProvider,
                roleCatalogManagerProvider,
                gLDownloadManagerProvider,
                languageManagerProvider,
                downloadedItemManagerProvider,
                databaseManagerProvider,
                catalogDownloaderProvider,
                ldsZipUtilProvider,
                contentItemUtilProvider,
                lDSAccountUtilProvider,
                itemManagerProvider,
                catalogSourceManagerProvider,
                databaseUtilProvider));
    this.downloadedCatalogProcessorProvider =
        DownloadedCatalogProcessor_Factory.create(
            internalIntentsProvider,
            catalogUpdateUtilProvider,
            downloadQueueItemManagerProvider,
            prefsProvider);
    this.downloadedTipsProcessorProvider =
        DownloadedTipsProcessor_Factory.create(downloadQueueItemManagerProvider);
    this.mediaDownloaderProvider = MediaDownloader_Factory.create(subItemManagerProvider);
    this.navItemManagerProvider =
        DoubleCheck.provider(
            NavItemManager_Factory.create(databaseManagerProvider, contentItemUtilProvider));
    this.allAudioDownloaderProvider =
        AllAudioDownloader_Factory.create(provideEventBusProvider, navItemManagerProvider);
    this.contentDownloaderProvider =
        ContentDownloader_Factory.create(
            provideEventBusProvider,
            prefsProvider,
            itemManagerProvider,
            gLFileUtilProvider,
            catalogSourceManagerProvider,
            roleCatalogManagerProvider);
    this.cancelDownloadsTaskProvider =
        CancelDownloadsTask_Factory.create(
            downloadQueueItemManagerProvider, provideDownloadManagerHelperProvider);
    this.repairMissingDownloadedItemsProvider =
        DoubleCheck.provider(
            RepairMissingDownloadedItems_Factory.create(
                catalogSourceManagerProvider,
                contentItemUpdateUtilProvider,
                contentItemUtilProvider,
                contentMetaDataManagerProvider,
                provideCoroutineContextProvider,
                downloadedItemManagerProvider,
                gLDownloadManagerProvider,
                gLFileUtilProvider,
                itemManagerProvider,
                ldsTimeUtilProvider));
    DelegateFactory gLDownloadManagerProviderDelegate = (DelegateFactory) gLDownloadManagerProvider;
    this.gLDownloadManagerProvider =
        DoubleCheck.provider(
            GLDownloadManager_Factory.create(
                provideApplicationProvider,
                provideEventBusProvider,
                downloadedItemManagerProvider,
                downloadedMediaManagerProvider,
                downloadQueueItemManagerProvider,
                lDSAccountAuthProvider,
                provideDownloadManagerHelperProvider,
                ldsNetworkUtilProvider,
                gLFileUtilProvider,
                prefsProvider,
                toastUtilProvider,
                downloadedContentProcessorProvider,
                downloadedMediaProcessorProvider,
                downloadedCatalogProcessorProvider,
                downloadedTipsProcessorProvider,
                mediaDownloaderProvider,
                allAudioDownloaderProvider,
                contentDownloaderProvider,
                cancelDownloadsTaskProvider,
                DirectDownloadTask_Factory.create(),
                repairMissingDownloadedItemsProvider));
    gLDownloadManagerProviderDelegate.setDelegatedProvider(gLDownloadManagerProvider);
    DelegateFactory contentItemUtilProviderDelegate = (DelegateFactory) contentItemUtilProvider;
    this.contentItemUtilProvider =
        DoubleCheck.provider(
            ContentItemUtil_Factory.create(
                provideApplicationProvider,
                gLFileUtilProvider,
                downloadedItemManagerProvider,
                databaseManagerProvider,
                provideEventBusProvider,
                provideAnalyticsProvider,
                downloadQueueItemManagerProvider,
                gLDownloadManagerProvider,
                screenUtilProvider,
                itemManagerProvider,
                provideCoroutineContextProvider,
                provideDownloadManagerHelperProvider));
    contentItemUtilProviderDelegate.setDelegatedProvider(contentItemUtilProvider);
  }

  @SuppressWarnings("unchecked")
  private void initialize2(final Builder builder) {
    DelegateFactory subItemManagerProviderDelegate = (DelegateFactory) subItemManagerProvider;
    this.subItemManagerProvider =
        DoubleCheck.provider(
            SubItemManager_Factory.create(databaseManagerProvider, contentItemUtilProvider));
    subItemManagerProviderDelegate.setDelegatedProvider(subItemManagerProvider);
    DelegateFactory citationUtilProviderDelegate = (DelegateFactory) citationUtilProvider;
    this.citationUtilProvider =
        DoubleCheck.provider(
            CitationUtil_Factory.create(
                provideApplicationProvider,
                subItemManagerProvider,
                paragraphMetadataManagerProvider,
                subItemMetadataManagerProvider,
                contentItemUtilProvider,
                annotationManagerProvider,
                highlightManagerProvider));
    citationUtilProviderDelegate.setDelegatedProvider(citationUtilProvider);
    this.provideNotificationManagerProvider =
        AppModule_ProvideNotificationManagerFactory.create(
            builder.appModule, provideApplicationProvider);
    this.annotationSyncNotificationProvider =
        DoubleCheck.provider(
            AnnotationSyncNotification_Factory.create(
                provideApplicationProvider,
                provideNotificationManagerProvider,
                provideEventBusProvider));
    this.annotationSyncProcessorProvider =
        AnnotationSyncProcessor_Factory.create(
            annotationManagerProvider,
            bookmarkManagerProvider,
            highlightManagerProvider,
            tagManagerProvider,
            notebookManagerProvider,
            notebookAnnotationManagerProvider,
            linkManagerProvider,
            noteManagerProvider,
            ldsTimeUtilProvider,
            citationUtilProvider,
            annotationSyncNotificationProvider,
            provideGsonProvider);
    this.annotationChangeProcessorProvider =
        AnnotationChangeProcessor_Factory.create(
            gLFileUtilProvider,
            ldsTimeUtilProvider,
            subItemMetadataManagerProvider,
            annotationManagerProvider,
            itemManagerProvider,
            syncContentItemAnnotationsQueueItemManagerProvider,
            provideGsonProvider);
    this.folderSyncProcessorProvider =
        FolderSyncProcessor_Factory.create(
            ldsTimeUtilProvider,
            notebookManagerProvider,
            notebookAnnotationManagerProvider,
            provideGsonProvider);
    this.authenticationFailureNotificationProvider =
        DoubleCheck.provider(
            AuthenticationFailureNotification_Factory.create(
                provideApplicationProvider, prefsProvider, provideNotificationManagerProvider));
    this.webServiceUtilProvider = DoubleCheck.provider(WebServiceUtil_Factory.create());
    this.annotationFixTaskProvider =
        AnnotationFixTask_Factory.create(
            annotationManagerProvider,
            bookmarkManagerProvider,
            tagManagerProvider,
            annotationSyncSchedulerProvider);
    this.ldsThreadUtilProvider = DoubleCheck.provider(LdsThreadUtil_Factory.create());
    this.annotationSyncProvider =
        DoubleCheck.provider(
            AnnotationSync_Factory.create(
                provideApplicationProvider,
                prefsProvider,
                provideCoroutineContextProvider,
                lDSAccountPrefsProvider,
                provideEventBusProvider,
                getAnnotationServiceProvider,
                annotationSyncProcessorProvider,
                annotationChangeProcessorProvider,
                folderSyncProcessorProvider,
                ldsNetworkUtilProvider,
                gLFileUtilProvider,
                lDSAccountUtilProvider,
                annotationManagerProvider,
                notebookManagerProvider,
                notebookAnnotationManagerProvider,
                databaseManagerProvider,
                userdataDbUtilProvider,
                annotationSyncNotificationProvider,
                ldsTimeUtilProvider,
                authenticationFailureNotificationProvider,
                webServiceUtilProvider,
                catalogUpdateUtilProvider,
                annotationFixTaskProvider,
                provideGsonProvider,
                ldsThreadUtilProvider));
    this.annotationSyncJobProvider = AnnotationSyncJob_Factory.create(annotationSyncProvider);
    this.appJobCreatorProvider =
        DoubleCheck.provider(AppJobCreator_Factory.create(annotationSyncJobProvider));
    this.accountUtilProvider =
        DoubleCheck.provider(
            AccountUtil_Factory.create(
                provideApplicationProvider,
                provideEventBusProvider,
                internalIntentsProvider,
                userdataDbUtilProvider,
                prefsProvider,
                lDSAccountPrefsProvider,
                lDSAccountUtilProvider,
                annotationManagerProvider,
                catalogUpdateUtilProvider,
                provideCoroutineContextProvider,
                annotationSyncProvider));
    this.tabHistoryItemManagerProvider =
        DoubleCheck.provider(TabHistoryItemManager_Factory.create(databaseManagerProvider));
    this.tabManagerProvider =
        DoubleCheck.provider(
            TabManager_Factory.create(
                databaseManagerProvider, gLFileUtilProvider, tabHistoryItemManagerProvider));
    this.provideAppConfigServiceProvider =
        DoubleCheck.provider(
            ServiceModule_ProvideAppConfigServiceFactory.create(
                builder.serviceModule, getStandardClientProvider));
    this.appUpdateNotificationProvider =
        DoubleCheck.provider(
            AppUpdateNotification_Factory.create(
                provideApplicationProvider, provideNotificationManagerProvider));
    this.appUpdateUtilProvider =
        DoubleCheck.provider(
            AppUpdateUtil_Factory.create(
                prefsProvider,
                ldsNetworkUtilProvider,
                provideAppConfigServiceProvider,
                appUpdateNotificationProvider));
    this.provideTipsServiceProvider =
        DoubleCheck.provider(
            ServiceModule_ProvideTipsServiceFactory.create(
                builder.serviceModule, getStandardClientProvider));
    this.tipManagerProvider =
        DoubleCheck.provider(
            TipManager_Factory.create(databaseManagerProvider, languageManagerProvider));
    this.tipsUtilProvider =
        DoubleCheck.provider(
            TipsUtil_Factory.create(prefsProvider, provideTipsServiceProvider, tipManagerProvider));
    this.tipsMetaDataManagerProvider =
        DoubleCheck.provider(TipsMetaDataManager_Factory.create(databaseManagerProvider));
    this.tipsDownloaderProvider =
        TipsDownloader_Factory.create(
            provideApplicationProvider,
            tipsUtilProvider,
            gLFileUtilProvider,
            downloadQueueItemManagerProvider,
            gLDownloadManagerProvider);
    this.tipsUpdateUtilProvider =
        DoubleCheck.provider(
            TipsUpdateUtil_Factory.create(
                prefsProvider,
                provideEventBusProvider,
                gLFileUtilProvider,
                ldsNetworkUtilProvider,
                tipsUtilProvider,
                tipsMetaDataManagerProvider,
                gLDownloadManagerProvider,
                tipManagerProvider,
                databaseManagerProvider,
                tipsDownloaderProvider,
                ldsZipUtilProvider));
    this.tipsUpdateTaskProvider = TipsUpdateTask_Factory.create(tipsUpdateUtilProvider);
    this.appModule = builder.appModule;
    this.downloadReceivedTaskProvider =
        DownloadReceivedTask_Factory.create(
            gLDownloadManagerProvider,
            provideDownloadManagerHelperProvider,
            downloadQueueItemManagerProvider);
    this.itemCategoryManagerProvider =
        DoubleCheck.provider(ItemCategoryManager_Factory.create(databaseManagerProvider));
    this.languageNameManagerProvider =
        DoubleCheck.provider(LanguageNameManager_Factory.create(databaseManagerProvider));
    this.mediaPlaybackPositionManagerProvider =
        DoubleCheck.provider(MediaPlaybackPositionManager_Factory.create(databaseManagerProvider));
    this.videoUtilProvider =
        DoubleCheck.provider(VideoUtil_Factory.create(provideApplicationProvider));
    this.commonMenuProvider =
        DoubleCheck.provider(
            CommonMenu_Factory.create(
                internalIntentsProvider,
                languageNameManagerProvider,
                recentLanguageManagerProvider,
                languageUtilProvider));
    this.themeUtilProvider = DoubleCheck.provider(ThemeUtil_Factory.create(prefsProvider));
    this.analyticsUtilProvider =
        AnalyticsUtil_Factory.create(
            provideAnalyticsProvider,
            contentMetaDataManagerProvider,
            contentItemUtilProvider,
            itemManagerProvider,
            itemCategoryManagerProvider,
            languageNameManagerProvider,
            subItemManagerProvider,
            subItemMetadataManagerProvider,
            annotationManagerProvider,
            screenUtilProvider,
            bookmarkManagerProvider,
            noteManagerProvider,
            tipManagerProvider);
    this.subItemContentManagerProvider =
        DoubleCheck.provider(
            SubItemContentManager_Factory.create(databaseManagerProvider, contentItemUtilProvider));
    this.contentParagraphUtilProvider =
        DoubleCheck.provider(
            ContentParagraphUtil_Factory.create(
                paragraphMetadataManagerProvider, subItemContentManagerProvider));
    this.highlightUtilProvider =
        DoubleCheck.provider(
            HighlightUtil_Factory.create(prefsProvider, contentParagraphUtilProvider));
    this.markdownUtilProvider = MarkdownUtil_Factory.create(provideApplicationProvider);
    this.annotationUiUtilProvider =
        DoubleCheck.provider(
            AnnotationUiUtil_Factory.create(
                provideCoroutineContextProvider,
                highlightUtilProvider,
                itemManagerProvider,
                citationUtilProvider,
                internalIntentsProvider,
                subItemManagerProvider,
                subItemMetadataManagerProvider,
                markdownUtilProvider,
                provideApplicationProvider,
                contentItemUtilProvider));
    this.shareUtilProvider =
        DoubleCheck.provider(
            ShareUtil_Factory.create(
                subItemManagerProvider,
                subItemMetadataManagerProvider,
                provideAnalyticsProvider,
                analyticsUtilProvider,
                uriUtilProvider,
                annotationUiUtilProvider,
                contentItemUtilProvider));
    this.navigationTrailQueryManagerProvider =
        DoubleCheck.provider(
            NavigationTrailQueryManager_Factory.create(
                databaseManagerProvider,
                provideApplicationProvider,
                contentItemUtilProvider,
                libraryCollectionManagerProvider,
                navItemManagerProvider));
    this.catalogItemQueryManagerProvider =
        DoubleCheck.provider(
            CatalogItemQueryManager_Factory.create(
                databaseManagerProvider, itemManagerProvider, userdataDbUtilProvider));
    this.customCollectionItemManagerProvider =
        DoubleCheck.provider(
            CustomCollectionItemManager_Factory.create(
                databaseManagerProvider, userdataDbUtilProvider, itemManagerProvider));
    this.customCollectionManagerProvider =
        DoubleCheck.provider(
            CustomCollectionManager_Factory.create(
                databaseManagerProvider,
                userdataDbUtilProvider,
                customCollectionItemManagerProvider));
    this.catalogDirectoryViewModelProvider =
        CatalogDirectoryViewModel_Factory.create(
            provideApplicationProvider,
            tipManagerProvider,
            languageUtilProvider,
            catalogItemQueryManagerProvider,
            languageManagerProvider,
            prefsProvider,
            screenUtilProvider,
            downloadedItemManagerProvider,
            customCollectionManagerProvider,
            provideCoroutineContextProvider);
    this.customCollectionsViewModelProvider =
        CustomCollectionsViewModel_Factory.create(
            catalogItemQueryManagerProvider,
            customCollectionManagerProvider,
            provideCoroutineContextProvider);
    this.customCollectionDirectoryViewModelProvider =
        CustomCollectionDirectoryViewModel_Factory.create(
            catalogItemQueryManagerProvider,
            customCollectionItemManagerProvider,
            downloadedItemManagerProvider,
            provideCoroutineContextProvider);
    this.contentDirectoryItemQueryManagerProvider =
        DoubleCheck.provider(
            ContentDirectoryItemQueryManager_Factory.create(
                databaseManagerProvider, navCollectionManagerProvider, contentItemUtilProvider));
    this.navCollectionIndexEntryManagerProvider =
        DoubleCheck.provider(
            NavCollectionIndexEntryManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.contentDirectoryViewModelProvider =
        ContentDirectoryViewModel_Factory.create(
            provideGsonProvider,
            contentDirectoryItemQueryManagerProvider,
            navCollectionManagerProvider,
            navCollectionIndexEntryManagerProvider,
            itemManagerProvider,
            screenUtilProvider,
            navigationTrailQueryManagerProvider,
            provideCoroutineContextProvider);
    this.allAnnotationsViewModelProvider =
        AllAnnotationsViewModel_Factory.create(annotationManagerProvider);
    this.annotationsViewModelProvider =
        AnnotationsViewModel_Factory.create(
            annotationManagerProvider, provideCoroutineContextProvider);
    this.tagViewManagerProvider =
        DoubleCheck.provider(
            TagViewManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.tagUtilProvider =
        DoubleCheck.provider(
            TagUtil_Factory.create(
                provideEventBusProvider,
                tagManagerProvider,
                annotationManagerProvider,
                annotationSyncSchedulerProvider,
                analyticsUtilProvider));
    this.tagSelectionViewModelProvider =
        TagSelectionViewModel_Factory.create(
            tagManagerProvider,
            tagViewManagerProvider,
            tagUtilProvider,
            prefsProvider,
            provideCoroutineContextProvider);
    this.tagsViewModelProvider =
        TagsViewModel_Factory.create(
            tagManagerProvider,
            tagViewManagerProvider,
            annotationManagerProvider,
            prefsProvider,
            provideCoroutineContextProvider);
    this.notebookViewManagerProvider =
        DoubleCheck.provider(
            NotebookViewManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.notebookSelectionViewModelProvider =
        NotebookSelectionViewModel_Factory.create(
            notebookViewManagerProvider,
            annotationManagerProvider,
            notebookManagerProvider,
            notebookAnnotationManagerProvider,
            prefsProvider,
            provideCoroutineContextProvider);
    this.notebooksViewModelProvider =
        NotebooksViewModel_Factory.create(
            notebookManagerProvider,
            notebookViewManagerProvider,
            annotationManagerProvider,
            prefsProvider,
            provideCoroutineContextProvider);
    this.bookmarkQueryManagerProvider =
        DoubleCheck.provider(
            BookmarkQueryManager_Factory.create(databaseManagerProvider, userdataDbUtilProvider));
    this.bookmarkUtilProvider =
        DoubleCheck.provider(
            BookmarkUtil_Factory.create(
                annotationManagerProvider,
                subItemManagerProvider,
                itemManagerProvider,
                bookmarkManagerProvider,
                citationUtilProvider,
                analyticsUtilProvider,
                bookmarkQueryManagerProvider,
                annotationSyncSchedulerProvider));
    this.bookmarksViewModelProvider =
        BookmarksViewModel_Factory.create(
            provideCoroutineContextProvider, bookmarkManagerProvider, bookmarkUtilProvider);
    this.linkUtilProvider =
        DoubleCheck.provider(
            LinkUtil_Factory.create(
                linkManagerProvider,
                annotationManagerProvider,
                itemManagerProvider,
                subItemMetadataManagerProvider,
                analyticsUtilProvider,
                citationUtilProvider,
                contentItemUtilProvider));
    this.navSectionManagerProvider =
        DoubleCheck.provider(
            NavSectionManager_Factory.create(databaseManagerProvider, contentItemUtilProvider));
    this.searchGotoQueryManagerProvider =
        DoubleCheck.provider(SearchGotoQueryManager_Factory.create(databaseManagerProvider));
    this.searchSuggestionManagerProvider =
        DoubleCheck.provider(
            SearchSuggestionManager_Factory.create(
                databaseManagerProvider,
                provideApplicationProvider,
                searchUtilProvider,
                ldsTimeUtilProvider,
                notebookManagerProvider,
                searchGotoQueryManagerProvider,
                noteManagerProvider,
                itemManagerProvider,
                libraryCollectionManagerProvider,
                languageManagerProvider,
                navCollectionManagerProvider,
                contentItemUtilProvider));
    this.linksViewModelProvider =
        LinksViewModel_Factory.create(
            linkManagerProvider,
            linkUtilProvider,
            navSectionManagerProvider,
            searchSuggestionManagerProvider,
            subItemManagerProvider,
            subItemMetadataManagerProvider,
            provideCoroutineContextProvider);
    this.downloadedMediaCollectionManagerProvider =
        DoubleCheck.provider(
            DownloadedMediaCollectionManager_Factory.create(databaseManagerProvider));
    this.downloadedMediaViewModelProvider =
        DownloadedMediaViewModel_Factory.create(
            downloadedMediaCollectionManagerProvider,
            downloadedMediaManagerProvider,
            provideCoroutineContextProvider);
    this.studyPlansViewModelProvider =
        StudyPlansViewModel_Factory.create(provideApplicationProvider);
    this.studyItemsViewModelProvider =
        StudyItemsViewModel_Factory.create(
            provideApplicationProvider, provideCoroutineContextProvider);
    this.tipViewedManagerProvider =
        DoubleCheck.provider(TipViewedManager_Factory.create(databaseManagerProvider));
    this.tipViewModelProvider =
        TipViewModel_Factory.create(
            tipManagerProvider, tipViewedManagerProvider, provideCoroutineContextProvider);
    this.tipsPagerViewModelProvider = TipsPagerViewModel_Factory.create(analyticsUtilProvider);
    this.welcomeViewModelProvider = WelcomeViewModel_Factory.create(analyticsUtilProvider);
    this.tipQueryManagerProvider =
        DoubleCheck.provider(
            TipQueryManager_Factory.create(
                databaseManagerProvider, languageManagerProvider, tipViewedManagerProvider));
    this.tipListViewModelProvider =
        TipListViewModel_Factory.create(
            provideCoroutineContextProvider, languageUtilProvider, tipQueryManagerProvider);
    this.mapOfClassOfAndProviderOfViewModelProvider =
        MapProviderFactory.<Class<? extends ViewModel>, ViewModel>builder(20)
            .put(CatalogDirectoryViewModel.class, (Provider) catalogDirectoryViewModelProvider)
            .put(CustomCollectionsViewModel.class, (Provider) customCollectionsViewModelProvider)
            .put(
                CustomCollectionDirectoryViewModel.class,
                (Provider) customCollectionDirectoryViewModelProvider)
            .put(ContentDirectoryViewModel.class, (Provider) contentDirectoryViewModelProvider)
            .put(AllAnnotationsViewModel.class, (Provider) allAnnotationsViewModelProvider)
            .put(AnnotationsViewModel.class, (Provider) annotationsViewModelProvider)
            .put(TagSelectionViewModel.class, (Provider) tagSelectionViewModelProvider)
            .put(TagsViewModel.class, (Provider) tagsViewModelProvider)
            .put(NotebookSelectionViewModel.class, (Provider) notebookSelectionViewModelProvider)
            .put(NotebooksViewModel.class, (Provider) notebooksViewModelProvider)
            .put(BookmarksViewModel.class, (Provider) bookmarksViewModelProvider)
            .put(LinksViewModel.class, (Provider) linksViewModelProvider)
            .put(DownloadedMediaViewModel.class, (Provider) downloadedMediaViewModelProvider)
            .put(StudyPlansViewModel.class, (Provider) studyPlansViewModelProvider)
            .put(StudyItemsViewModel.class, (Provider) studyItemsViewModelProvider)
            .put(TipViewModel.class, (Provider) tipViewModelProvider)
            .put(TipsPagerViewModel.class, (Provider) tipsPagerViewModelProvider)
            .put(WelcomeViewModel.class, (Provider) welcomeViewModelProvider)
            .put(TipListViewModel.class, (Provider) tipListViewModelProvider)
            .put(TipListPagerViewModel.class, (Provider) TipListPagerViewModel_Factory.create())
            .build();
    this.viewModelFactoryProvider =
        DoubleCheck.provider(
            ViewModelFactory_Factory.create(mapOfClassOfAndProviderOfViewModelProvider));
    this.screenLauncherUtilProvider =
        ScreenLauncherUtil_Factory.create(
            provideApplicationProvider,
            screenHistoryItemManagerProvider,
            internalIntentsProvider,
            contentItemUtilProvider,
            screenUtilProvider,
            gLDownloadManagerProvider,
            provideGsonProvider);
    this.historyManagerProvider =
        DoubleCheck.provider(HistoryManager_Factory.create(databaseManagerProvider));
    this.checkDownloadsTaskProvider =
        CheckDownloadsTask_Factory.create(
            downloadQueueItemManagerProvider,
            provideDownloadManagerHelperProvider,
            gLDownloadManagerProvider,
            catalogUpdateUtilProvider);
    this.initialContentDownloadTaskProvider =
        InitialContentDownloadTask_Factory.create(
            provideApplicationProvider,
            itemManagerProvider,
            gLDownloadManagerProvider,
            ldsNetworkUtilProvider);
    this.startupTaskProvider =
        StartupTask_Factory.create(
            provideEventBusProvider,
            internalIntentsProvider,
            prefsProvider,
            databaseManagerProvider,
            catalogUpdateUtilProvider,
            screenLauncherUtilProvider,
            ldsTimeUtilProvider,
            userdataDbUtilProvider,
            historyManagerProvider,
            tipsUtilProvider,
            tipsUpdateUtilProvider,
            languageUtilProvider,
            checkDownloadsTaskProvider,
            initialContentDownloadTaskProvider);
    this.provideInputMethodManagerProvider =
        AppModule_ProvideInputMethodManagerFactory.create(
            builder.appModule, provideApplicationProvider);
    this.ldsKeyboardUtilProvider =
        DoubleCheck.provider(LdsKeyboardUtil_Factory.create(provideInputMethodManagerProvider));
    this.searchHistoryManagerProvider =
        DoubleCheck.provider(SearchHistoryManager_Factory.create(databaseManagerProvider));
    this.allSubItemsInNavCollectionQueryManagerProvider =
        DoubleCheck.provider(
            AllSubItemsInNavCollectionQueryManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.subItemSearchQueryManagerProvider =
        DoubleCheck.provider(
            SubItemSearchQueryManager_Factory.create(
                databaseManagerProvider,
                contentItemUtilProvider,
                searchUtilProvider,
                allSubItemsInNavCollectionQueryManagerProvider));
    this.searchAllCountManagerProvider =
        DoubleCheck.provider(SearchAllCountManager_Factory.create(databaseManagerProvider));
    this.customCollectionUtilProvider =
        DoubleCheck.provider(
            CustomCollectionUtil_Factory.create(
                provideApplicationProvider,
                customCollectionManagerProvider,
                customCollectionItemManagerProvider));
    this.catalogDirectoryMenuProvider =
        DoubleCheck.provider(
            CatalogDirectoryMenu_Factory.create(
                commonMenuProvider, internalIntentsProvider, languageManagerProvider));
    this.allItemsInCollectionQueryManagerProvider =
        DoubleCheck.provider(
            AllItemsInCollectionQueryManager_Factory.create(databaseManagerProvider));
    this.relatedAudioItemManagerProvider =
        DoubleCheck.provider(
            RelatedAudioItemManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.providePackageManagerProvider =
        AppModule_ProvidePackageManagerFactory.create(
            builder.appModule, provideApplicationProvider);
    this.ldsMusicUtilProvider =
        LdsMusicUtil_Factory.create(
            provideApplicationProvider, providePackageManagerProvider, subItemManagerProvider);
    this.externalIntentsProvider =
        DoubleCheck.provider(
            ExternalIntents_Factory.create(providePackageManagerProvider, ldsMusicUtilProvider));
    this.relatedVideoItemManagerProvider =
        DoubleCheck.provider(
            RelatedVideoItemManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
  }

  @SuppressWarnings("unchecked")
  private void initialize3(final Builder builder) {
    this.relatedVideoItemSourceManagerProvider =
        DoubleCheck.provider(
            RelatedVideoItemSourceManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.relatedVideoUtilProvider =
        RelatedVideoUtil_Factory.create(
            relatedVideoItemManagerProvider, relatedVideoItemSourceManagerProvider);
    this.contentRendererProvider =
        DoubleCheck.provider(
            ContentRenderer_Factory.create(
                provideApplicationProvider,
                provideAssetManagerProvider,
                prefsProvider,
                imageUtilProvider,
                subItemManagerProvider,
                subItemContentManagerProvider,
                gLFileUtilProvider,
                contentMetaDataManagerProvider,
                uriUtilProvider,
                contentItemUtilProvider,
                itemManagerProvider,
                ldsMusicUtilProvider,
                relatedVideoUtilProvider,
                languageManagerProvider));
    this.authorManagerProvider =
        DoubleCheck.provider(
            AuthorManager_Factory.create(databaseManagerProvider, contentItemUtilProvider));
    this.playlistItemQueryManagerProvider =
        DoubleCheck.provider(
            PlaylistItemQueryManager_Factory.create(
                databaseManagerProvider,
                contentItemUtilProvider,
                itemManagerProvider,
                authorManagerProvider,
                prefsProvider));
    this.textToSpeechManagerProvider =
        DoubleCheck.provider(
            TextToSpeechManager_Factory.create(provideApplicationProvider, prefsProvider));
    this.availableRelatedTypeQueryManagerProvider =
        DoubleCheck.provider(
            AvailableRelatedTypeQueryManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.noteUtilProvider =
        DoubleCheck.provider(
            NoteUtil_Factory.create(
                noteManagerProvider,
                annotationManagerProvider,
                notebookManagerProvider,
                notebookAnnotationManagerProvider));
    this.ldsStorageUtilProvider =
        DoubleCheck.provider(LdsStorageUtil_Factory.create(provideApplicationProvider));
    this.ldsDeviceUtilProvider =
        DoubleCheck.provider(LdsDeviceUtil_Factory.create(provideApplicationProvider));
    this.provideWindowManagerProvider =
        AppModule_ProvideWindowManagerFactory.create(builder.appModule, provideApplicationProvider);
    this.ldsUiUtilProvider =
        DoubleCheck.provider(
            LdsUiUtil_Factory.create(
                provideWindowManagerProvider, provideInputMethodManagerProvider));
    this.ldsFeedbackUtilProvider =
        DoubleCheck.provider(
            LdsFeedbackUtil_Factory.create(
                provideApplicationProvider,
                ldsStorageUtilProvider,
                ldsDeviceUtilProvider,
                ldsUiUtilProvider));
    this.deviceUtilProvider = DeviceUtil_Factory.create(provideApplicationProvider);
    this.feedbackUtilProvider =
        DoubleCheck.provider(
            FeedbackUtil_Factory.create(
                prefsProvider,
                lDSAccountPrefsProvider,
                provideApplicationProvider,
                ldsFeedbackUtilProvider,
                languageNameManagerProvider,
                gLFileUtilProvider,
                catalogMetaDataManagerProvider,
                downloadedItemManagerProvider,
                annotationManagerProvider,
                notebookManagerProvider,
                notebookAnnotationManagerProvider,
                screenUtilProvider,
                deviceUtilProvider,
                highlightManagerProvider,
                noteManagerProvider,
                linkManagerProvider,
                bookmarkManagerProvider,
                tagManagerProvider));
    this.notebookUtilProvider =
        DoubleCheck.provider(
            NotebookUtil_Factory.create(annotationManagerProvider, notebookManagerProvider));
    this.restoreJournalUtilProvider =
        DoubleCheck.provider(
            RestoreJournalUtil_Factory.create(
                provideApplicationProvider, annotationManagerProvider, notebookManagerProvider));
    this.customCollectionDirectoryMenuProvider =
        DoubleCheck.provider(
            CustomCollectionDirectoryMenu_Factory.create(
                commonMenuProvider, internalIntentsProvider, customCollectionUtilProvider));
    this.textHandleUtilProvider = DoubleCheck.provider(TextHandleUtil_Factory.create());
    this.contentJsInvokerProvider =
        DoubleCheck.provider(
            ContentJsInvoker_Factory.create(
                AnnotationListUtil_Factory.create(), provideGsonProvider));
    this.sidebarHistoryItemManagerProvider =
        DoubleCheck.provider(SidebarHistoryItemManager_Factory.create(databaseManagerProvider));
    this.relatedContentItemManagerProvider =
        DoubleCheck.provider(
            RelatedContentItemManager_Factory.create(
                databaseManagerProvider, contentItemUtilProvider));
    this.itemCollectionViewManagerProvider =
        DoubleCheck.provider(ItemCollectionViewManager_Factory.create(databaseManagerProvider));
    this.noteSearchQueryManagerProvider =
        DoubleCheck.provider(
            NoteSearchQueryManager_Factory.create(
                databaseManagerProvider, userdataDbUtilProvider, searchUtilProvider));
    this.searchEngineLocalProvider =
        DoubleCheck.provider(
            SearchEngineLocal_Factory.create(
                provideEventBusProvider,
                prefsProvider,
                ldsTimeUtilProvider,
                downloadedItemManagerProvider,
                itemCollectionViewManagerProvider,
                noteSearchQueryManagerProvider,
                subItemSearchQueryManagerProvider,
                searchCountContentManagerProvider,
                searchCollectionManagerProvider,
                libraryCollectionManagerProvider,
                searchContentCollectionMapManagerProvider,
                searchPreviewSubitemManagerProvider,
                searchCountAllNotesManagerProvider,
                searchPreviewNoteManagerProvider,
                allItemsInCollectionQueryManagerProvider,
                itemManagerProvider,
                searchUtilProvider));
  }

  @Override
  public Application application() {
    return provideApplicationProvider.get();
  }

  @Override
  public void inject(App arg0) {
    injectApp(arg0);
  }

  @Override
  public void inject(NotesActionModeCallback arg0) {}

  @Override
  public void inject(BookmarkWidgetProvider arg0) {
    injectBookmarkWidgetProvider(arg0);
  }

  @Override
  public void inject(DownloadManagerReceiver arg0) {
    injectDownloadManagerReceiver(arg0);
  }

  @Override
  public void inject(BookmarkWidgetFactory arg0) {
    injectBookmarkWidgetFactory(arg0);
  }

  @Override
  public void inject(DefaultAnalytics arg0) {
    injectDefaultAnalytics(arg0);
  }

  @Override
  public void inject(MediaService arg0) {
    injectMediaService(arg0);
  }

  @Override
  public void inject(GLAudioApi arg0) {
    injectGLAudioApi(arg0);
  }

  @Override
  public void inject(AudioItem arg0) {
    injectAudioItem(arg0);
  }

  @Override
  public void inject(VideoItem arg0) {
    injectVideoItem(arg0);
  }

  @Override
  public void inject(AnnotationsActivity arg0) {
    injectAnnotationsActivity(arg0);
  }

  @Override
  public void inject(AnnotationsFragment arg0) {
    injectAnnotationsFragment(arg0);
  }

  @Override
  public void inject(StartupActivity arg0) {
    injectStartupActivity(arg0);
  }

  @Override
  public void inject(SearchActivity arg0) {
    injectSearchActivity(arg0);
  }

  @Override
  public void inject(BaseFragment arg0) {
    injectBaseFragment(arg0);
  }

  @Override
  public void inject(CatalogDirectoryActivity arg0) {
    injectCatalogDirectoryActivity(arg0);
  }

  @Override
  public void inject(ContentViewPager arg0) {}

  @Override
  public void inject(ContentDirectoryActivity arg0) {
    injectContentDirectoryActivity(arg0);
  }

  @Override
  public void inject(ContentItemFragment arg0) {
    injectContentItemFragment(arg0);
  }

  @Override
  public void inject(ContentActivity arg0) {
    injectContentActivity(arg0);
  }

  @Override
  public void inject(DownloadedMediaActivity arg0) {
    injectDownloadedMediaActivity(arg0);
  }

  @Override
  public void inject(BaseActivity arg0) {
    injectBaseActivity(arg0);
  }

  @Override
  public void inject(HighlightPaletteActivity arg0) {
    injectHighlightPaletteActivity(arg0);
  }

  @Override
  public void inject(NoteActivity arg0) {
    injectNoteActivity(arg0);
  }

  @Override
  public void inject(ScreensFragment arg0) {
    injectScreensFragment(arg0);
  }

  @Override
  public void inject(SettingsActivity arg0) {
    injectSettingsActivity(arg0);
  }

  @Override
  public void inject(SettingsFragment arg0) {
    injectSettingsFragment(arg0);
  }

  @Override
  public void inject(ShareIntentActivity arg0) {
    injectShareIntentActivity(arg0);
  }

  @Override
  public void inject(TagSelectionActivity arg0) {
    injectTagSelectionActivity(arg0);
  }

  @Override
  public void inject(VideoPlayerActivity arg0) {
    injectVideoPlayerActivity(arg0);
  }

  @Override
  public void inject(CurrentDownloadsActivity arg0) {
    injectCurrentDownloadsActivity(arg0);
  }

  @Override
  public void inject(CustomCollectionsActivity arg0) {
    injectCustomCollectionsActivity(arg0);
  }

  @Override
  public void inject(NotebookSelectionActivity arg0) {
    injectNotebookSelectionActivity(arg0);
  }

  @Override
  public void inject(NotesActivity arg0) {
    injectNotesActivity(arg0);
  }

  @Override
  public void inject(AllAnnotationsFragment arg0) {
    injectAllAnnotationsFragment(arg0);
  }

  @Override
  public void inject(NotebooksFragment arg0) {
    injectNotebooksFragment(arg0);
  }

  @Override
  public void inject(TagsFragment arg0) {
    injectTagsFragment(arg0);
  }

  @Override
  public void inject(HighlightSelectionActivity arg0) {
    injectHighlightSelectionActivity(arg0);
  }

  @Override
  public void inject(AboutActivity arg0) {
    injectAboutActivity(arg0);
  }

  @Override
  public void inject(AppInfoActivity arg0) {
    injectAppInfoActivity(arg0);
  }

  @Override
  public void inject(BookmarksFragment arg0) {
    injectBookmarksFragment(arg0);
  }

  @Override
  public void inject(HistoryFragment arg0) {
    injectHistoryFragment(arg0);
  }

  @Override
  public void inject(CustomCollectionDirectoryActivity arg0) {
    injectCustomCollectionDirectoryActivity(arg0);
  }

  @Override
  public void inject(UriRouterActivity arg0) {
    injectUriRouterActivity(arg0);
  }

  @Override
  public void inject(LocationsActivity arg0) {
    injectLocationsActivity(arg0);
  }

  @Override
  public void inject(SignInActivity arg0) {
    injectSignInActivity(arg0);
  }

  @Override
  public void inject(WelcomeActivity arg0) {
    injectWelcomeActivity(arg0);
  }

  @Override
  public void inject(TipFragment arg0) {
    injectTipFragment(arg0);
  }

  @Override
  public void inject(TipListPagerActivity arg0) {
    injectTipListPagerActivity(arg0);
  }

  @Override
  public void inject(TipListFragment arg0) {
    injectTipListFragment(arg0);
  }

  @Override
  public void inject(LinkContentActivity arg0) {
    injectLinkContentActivity(arg0);
  }

  @Override
  public void inject(LinksActivity arg0) {
    injectLinksActivity(arg0);
  }

  @Override
  public void inject(SingleAnnotationActivity arg0) {
    injectSingleAnnotationActivity(arg0);
  }

  @Override
  public void inject(StudyPlansActivity arg0) {
    injectStudyPlansActivity(arg0);
  }

  @Override
  public void inject(StudyPlansPagerAdapter arg0) {
    injectStudyPlansPagerAdapter(arg0);
  }

  @Override
  public void inject(StudyPlanListFragment arg0) {
    injectStudyPlanListFragment(arg0);
  }

  @Override
  public void inject(StudyItemsActivity arg0) {
    injectStudyItemsActivity(arg0);
  }

  @Override
  public void inject(DeleteAllMediaDialogFragment arg0) {
    injectDeleteAllMediaDialogFragment(arg0);
  }

  @Override
  public void inject(DownloadMediaDialogFragment arg0) {
    injectDownloadMediaDialogFragment(arg0);
  }

  @Override
  public void inject(TextSizeDialogFragment arg0) {
    injectTextSizeDialogFragment(arg0);
  }

  @Override
  public void inject(ProgressDialogFragment arg0) {}

  @Override
  public void inject(ContentTouchListener arg0) {
    injectContentTouchListener(arg0);
  }

  @Override
  public void inject(ContentWebView arg0) {
    injectContentWebView(arg0);
  }

  @Override
  public void inject(MarkdownControls arg0) {}

  @Override
  public void inject(ContentJsInterface arg0) {}

  @Override
  public void inject(MediaFab arg0) {
    injectMediaFab(arg0);
  }

  @Override
  public void inject(DownloadedMediaAdapter arg0) {
    injectDownloadedMediaAdapter(arg0);
  }

  @Override
  public void inject(DownloadedMediaCollectionsAdapter arg0) {
    injectDownloadedMediaCollectionsAdapter(arg0);
  }

  @Override
  public void inject(CatalogDirectoryAdapter arg0) {
    injectCatalogDirectoryAdapter(arg0);
  }

  @Override
  public void inject(ContentDirectoryAdapter arg0) {
    injectContentDirectoryAdapter(arg0);
  }

  @Override
  public void inject(HistoryAdapter arg0) {
    injectHistoryAdapter(arg0);
  }

  @Override
  public void inject(NavigationTrailAdapter arg0) {}

  @Override
  public void inject(ScreensAdapter arg0) {
    injectScreensAdapter(arg0);
  }

  @Override
  public void inject(CustomCollectionsAdapter arg0) {}

  @Override
  public void inject(TagSelectionAdapter arg0) {}

  @Override
  public void inject(NotebooksAdapter arg0) {}

  @Override
  public void inject(TagsAdapter arg0) {}

  @Override
  public void inject(DownloadMediaDialogAdapter arg0) {}

  @Override
  public void inject(AnnotationsAdapter arg0) {}

  @Override
  public void inject(LocationsPagerAdapter arg0) {
    injectLocationsPagerAdapter(arg0);
  }

  @Override
  public void inject(BookmarksAdapter arg0) {
    injectBookmarksAdapter(arg0);
  }

  @Override
  public void inject(StudyPlansAdapter arg0) {
    injectStudyPlansAdapter(arg0);
  }

  @Override
  public void inject(StudyItemsAdapter arg0) {
    injectStudyItemsAdapter(arg0);
  }

  @Override
  public void inject(ContentDirectoryViewHolder arg0) {}

  @Override
  public void inject(DownloadableMediaListLoader arg0) {
    injectDownloadableMediaListLoader(arg0);
  }

  @Override
  public void inject(ScreensLoader arg0) {
    injectScreensLoader(arg0);
  }

  @Override
  public void inject(DownloadedMediaLoader arg0) {
    injectDownloadedMediaLoader(arg0);
  }

  @Override
  public void inject(AnnotationTagsLoader arg0) {
    injectAnnotationTagsLoader(arg0);
  }

  @Override
  public void inject(HistoryLoader arg0) {
    injectHistoryLoader(arg0);
  }

  @Override
  public void inject(InitialContentDownloadTask arg0) {}

  @Override
  public void inject(SideBar arg0) {
    injectSideBar(arg0);
  }

  @Override
  public void inject(SideBarRelatedContentItem arg0) {
    injectSideBarRelatedContentItem(arg0);
  }

  @Override
  public void inject(SideBarAnnotation arg0) {
    injectSideBarAnnotation(arg0);
  }

  @Override
  public void inject(SideBarUri arg0) {
    injectSideBarUri(arg0);
  }

  @Override
  public void inject(LDSCastButton.ThemeCompliantDialogFactory arg0) {
    injectThemeCompliantDialogFactory(arg0);
  }

  @Override
  public void inject(LanguageChangeActivity arg0) {
    injectLanguageChangeActivity(arg0);
  }

  @Override
  public void inject(TipsPagerActivity arg0) {
    injectTipsPagerActivity(arg0);
  }

  @Override
  public void inject(LDSCastButton arg0) {
    injectLDSCastButton(arg0);
  }

  @Override
  public void inject(ScreenSettingsFragment arg0) {
    injectScreenSettingsFragment(arg0);
  }

  @Override
  public void inject(ScreenSettingsActivity arg0) {
    injectScreenSettingsActivity(arg0);
  }

  @Override
  public void inject(AudioSettingsActivity arg0) {
    injectAudioSettingsActivity(arg0);
  }

  @Override
  public void inject(AudioSettingsFragment arg0) {
    injectAudioSettingsFragment(arg0);
  }

  @Override
  public void inject(SideBarRelatedContent arg0) {
    injectSideBarRelatedContent(arg0);
  }

  @Override
  public void inject(RelatedContentAdapter arg0) {}

  @Override
  public void inject(ContentSourceActivity arg0) {
    injectContentSourceActivity(arg0);
  }

  @Override
  public void inject(AnnotationView arg0) {
    injectAnnotationView(arg0);
  }

  @Override
  public void inject(BookmarkRouterActivity arg0) {
    injectBookmarkRouterActivity(arg0);
  }

  @Override
  public void inject(AnnotationInfoActivity arg0) {
    injectAnnotationInfoActivity(arg0);
  }

  @Override
  public void inject(SearchService arg0) {
    injectSearchService(arg0);
  }

  @Override
  public void inject(TextToSpeechControlsManager arg0) {
    injectTextToSpeechControlsManager(arg0);
  }

  @Override
  public void inject(AudioPlaybackControlsManager arg0) {
    injectAudioPlaybackControlsManager(arg0);
  }

  @Override
  public void inject(MiniPlaybackControls arg0) {
    injectMiniPlaybackControls(arg0);
  }

  @Override
  public void inject(TextToSpeechManager arg0) {}

  @Override
  public void inject(TextToSpeechService arg0) {
    injectTextToSpeechService(arg0);
  }

  @Override
  public void inject(TextToSpeechEngine arg0) {}

  @Override
  public void inject(CurrentDownloadsAdapter arg0) {
    injectCurrentDownloadsAdapter(arg0);
  }

  private App injectApp(App instance) {
    App_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    App_MembersInjector.injectAppJobCreator(instance, appJobCreatorProvider.get());
    App_MembersInjector.injectPrefs(instance, prefsProvider.get());
    App_MembersInjector.injectAppUpgradeUtil(instance, getAppUpgradeUtil());
    App_MembersInjector.injectGlUpdateLifecycle(instance, getGLUpdateLifecycle());
    return instance;
  }

  private BookmarkWidgetProvider injectBookmarkWidgetProvider(BookmarkWidgetProvider instance) {
    BookmarkWidgetProvider_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    BookmarkWidgetProvider_MembersInjector.injectDatabaseManager(
        instance, databaseManagerProvider.get());
    BookmarkWidgetProvider_MembersInjector.injectUserdataDbUtil(
        instance, userdataDbUtilProvider.get());
    BookmarkWidgetProvider_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BookmarkWidgetProvider_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private DownloadManagerReceiver injectDownloadManagerReceiver(DownloadManagerReceiver instance) {
    DownloadManagerReceiver_MembersInjector.injectApplication(
        instance, provideApplicationProvider.get());
    DownloadManagerReceiver_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    DownloadManagerReceiver_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    DownloadManagerReceiver_MembersInjector.injectDownloadReceivedTaskProvider(
        instance, downloadReceivedTaskProvider);
    DownloadManagerReceiver_MembersInjector.injectDownloadManagerHelper(
        instance, provideDownloadManagerHelperProvider.get());
    DownloadManagerReceiver_MembersInjector.injectDownloadQueueItemManager(
        instance, downloadQueueItemManagerProvider.get());
    DownloadManagerReceiver_MembersInjector.injectCatalogUpdateUtil(
        instance, catalogUpdateUtilProvider.get());
    DownloadManagerReceiver_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    return instance;
  }

  private BookmarkWidgetFactory injectBookmarkWidgetFactory(BookmarkWidgetFactory instance) {
    BookmarkWidgetFactory_MembersInjector.injectBookmarkManager(
        instance, bookmarkManagerProvider.get());
    BookmarkWidgetFactory_MembersInjector.injectApplication(
        instance, provideApplicationProvider.get());
    return instance;
  }

  private DefaultAnalytics injectDefaultAnalytics(DefaultAnalytics instance) {
    DefaultAnalytics_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private MediaService injectMediaService(MediaService instance) {
    MediaService_MembersInjector.injectPlaylistManager(instance, playlistManagerProvider.get());
    MediaService_MembersInjector.injectCastManager(instance, provideCastManagerProvider.get());
    MediaService_MembersInjector.injectPrefs(instance, prefsProvider.get());
    MediaService_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    MediaService_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    MediaService_MembersInjector.injectMediaPlaybackPositionManager(
        instance, mediaPlaybackPositionManagerProvider.get());
    MediaService_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    MediaService_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    return instance;
  }

  private GLAudioApi injectGLAudioApi(GLAudioApi instance) {
    GLAudioApi_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private AudioItem injectAudioItem(AudioItem instance) {
    AudioItem_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    AudioItem_MembersInjector.injectDownloadedMediaUtil(instance, getDownloadedMediaUtil());
    AudioItem_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    return instance;
  }

  private VideoItem injectVideoItem(VideoItem instance) {
    VideoItem_MembersInjector.injectVideoUtil(instance, videoUtilProvider.get());
    VideoItem_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    VideoItem_MembersInjector.injectDownloadedMediaUtil(instance, getDownloadedMediaUtil());
    VideoItem_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    return instance;
  }

  private AnnotationsActivity injectAnnotationsActivity(AnnotationsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    AnnotationsActivity_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    AnnotationsActivity_MembersInjector.injectNotebookManager(
        instance, notebookManagerProvider.get());
    AnnotationsActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    AnnotationsActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    return instance;
  }

  private AnnotationsFragment injectAnnotationsFragment(AnnotationsFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    AnnotationsFragment_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    AnnotationsFragment_MembersInjector.injectNotebookManager(
        instance, notebookManagerProvider.get());
    AnnotationsFragment_MembersInjector.injectSubItemMetadataManager(
        instance, subItemMetadataManagerProvider.get());
    AnnotationsFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private StartupActivity injectStartupActivity(StartupActivity instance) {
    StartupActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    StartupActivity_MembersInjector.injectStartupTaskProvider(instance, startupTaskProvider);
    return instance;
  }

  private SearchActivity injectSearchActivity(SearchActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    SearchActivity_MembersInjector.injectKeyboardUtil(instance, ldsKeyboardUtilProvider.get());
    SearchActivity_MembersInjector.injectPresenter(instance, getSearchPresenter());
    return instance;
  }

  private BaseFragment injectBaseFragment(BaseFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    return instance;
  }

  private CatalogDirectoryActivity injectCatalogDirectoryActivity(
      CatalogDirectoryActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectLibraryCollectionManager(
        instance, libraryCollectionManagerProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectCustomCollectionUtil(
        instance, customCollectionUtilProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectCatalogDirectoryMenu(
        instance, catalogDirectoryMenuProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectCustomCollectionItemManager(
        instance, customCollectionItemManagerProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectItemsInCollectionManager(
        instance, allItemsInCollectionQueryManagerProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectDownloadedItemManager(
        instance, downloadedItemManagerProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectDownloadManager(
        instance, gLDownloadManagerProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectContentItemUtil(
        instance, contentItemUtilProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectCatalogUpdateUtil(
        instance, catalogUpdateUtilProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectTipsUpdateTaskProvider(
        instance, tipsUpdateTaskProvider);
    CatalogDirectoryActivity_MembersInjector.injectAnnotationSync(
        instance, annotationSyncProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectLanguageUtil(
        instance, languageUtilProvider.get());
    CatalogDirectoryActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private ContentDirectoryActivity injectContentDirectoryActivity(
      ContentDirectoryActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    ContentDirectoryActivity_MembersInjector.injectNavItemManager(
        instance, navItemManagerProvider.get());
    ContentDirectoryActivity_MembersInjector.injectAnnotationSync(
        instance, annotationSyncProvider.get());
    ContentDirectoryActivity_MembersInjector.injectRelatedAudioAvailableUtil(
        instance, getRelatedAudioAvailableUtil());
    ContentDirectoryActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private ContentItemFragment injectContentItemFragment(ContentItemFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    ContentItemFragment_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    ContentItemFragment_MembersInjector.injectExternalIntents(
        instance, externalIntentsProvider.get());
    ContentItemFragment_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    ContentItemFragment_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    ContentItemFragment_MembersInjector.injectAnnotationSync(
        instance, annotationSyncProvider.get());
    ContentItemFragment_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    ContentItemFragment_MembersInjector.injectSubItemManager(
        instance, subItemManagerProvider.get());
    ContentItemFragment_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    ContentItemFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    ContentItemFragment_MembersInjector.injectContentRenderer(
        instance, contentRendererProvider.get());
    ContentItemFragment_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    ContentItemFragment_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    ContentItemFragment_MembersInjector.injectLinkUtil(instance, linkUtilProvider.get());
    ContentItemFragment_MembersInjector.injectLinkManager(instance, linkManagerProvider.get());
    return instance;
  }

  private ContentActivity injectContentActivity(ContentActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    ContentActivity_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    ContentActivity_MembersInjector.injectTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    ContentActivity_MembersInjector.injectSubItemManager(instance, subItemManagerProvider.get());
    ContentActivity_MembersInjector.injectHistoryManager(instance, historyManagerProvider.get());
    ContentActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    ContentActivity_MembersInjector.injectPlaylistManager(instance, playlistManagerProvider.get());
    ContentActivity_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    ContentActivity_MembersInjector.injectMediaPlaybackPositionManager(
        instance, mediaPlaybackPositionManagerProvider.get());
    ContentActivity_MembersInjector.injectPlaylistItemQueryManager(
        instance, playlistItemQueryManagerProvider.get());
    ContentActivity_MembersInjector.injectGlDownloadManager(
        instance, gLDownloadManagerProvider.get());
    ContentActivity_MembersInjector.injectTextToSpeechManager(
        instance, textToSpeechManagerProvider.get());
    ContentActivity_MembersInjector.injectAvailableRelatedTypeManager(
        instance, availableRelatedTypeQueryManagerProvider.get());
    ContentActivity_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    ContentActivity_MembersInjector.injectNetworkUtil(instance, ldsNetworkUtilProvider.get());
    ContentActivity_MembersInjector.injectRelatedAudioItemManager(
        instance, relatedAudioItemManagerProvider.get());
    return instance;
  }

  private DownloadedMediaActivity injectDownloadedMediaActivity(DownloadedMediaActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    DownloadedMediaActivity_MembersInjector.injectFileUtil(instance, gLFileUtilProvider.get());
    DownloadedMediaActivity_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    DownloadedMediaActivity_MembersInjector.injectPlaylistManager(
        instance, playlistManagerProvider.get());
    DownloadedMediaActivity_MembersInjector.injectPlaylistItemQueryManager(
        instance, playlistItemQueryManagerProvider.get());
    DownloadedMediaActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    DownloadedMediaActivity_MembersInjector.injectCoroutineContextProvider(
        instance, provideCoroutineContextProvider.get());
    return instance;
  }

  private BaseActivity injectBaseActivity(BaseActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private HighlightPaletteActivity injectHighlightPaletteActivity(
      HighlightPaletteActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    HighlightPaletteActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    HighlightPaletteActivity_MembersInjector.injectHighlightUtil(
        instance, highlightUtilProvider.get());
    return instance;
  }

  private NoteActivity injectNoteActivity(NoteActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    NoteActivity_MembersInjector.injectNoteManager(instance, noteManagerProvider.get());
    NoteActivity_MembersInjector.injectAnnotationListUtil(instance, new AnnotationListUtil());
    NoteActivity_MembersInjector.injectNoteUtil(instance, noteUtilProvider.get());
    NoteActivity_MembersInjector.injectAnnotationManager(instance, annotationManagerProvider.get());
    NoteActivity_MembersInjector.injectKeyboardUtil(instance, ldsKeyboardUtilProvider.get());
    NoteActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    NoteActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    NoteActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private ScreensFragment injectScreensFragment(ScreensFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    ScreensFragment_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    ScreensFragment_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    ScreensFragment_MembersInjector.injectScreenManager(instance, screenManagerProvider.get());
    ScreensFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    ScreensFragment_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    return instance;
  }

  private SettingsActivity injectSettingsActivity(SettingsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    SettingsActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    SettingsActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    return instance;
  }

  private SettingsFragment injectSettingsFragment(SettingsFragment instance) {
    SettingsFragment_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    SettingsFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    SettingsFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    SettingsFragment_MembersInjector.injectFeedbackUtil(instance, feedbackUtilProvider.get());
    SettingsFragment_MembersInjector.injectLdsAccountPrefs(instance, lDSAccountPrefsProvider.get());
    SettingsFragment_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    SettingsFragment_MembersInjector.injectExternalIntents(instance, externalIntentsProvider.get());
    SettingsFragment_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    SettingsFragment_MembersInjector.injectDownloadManager(
        instance, gLDownloadManagerProvider.get());
    SettingsFragment_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    SettingsFragment_MembersInjector.injectAnnotationSync(instance, annotationSyncProvider.get());
    return instance;
  }

  private ShareIntentActivity injectShareIntentActivity(ShareIntentActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private TagSelectionActivity injectTagSelectionActivity(TagSelectionActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    TagSelectionActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private VideoPlayerActivity injectVideoPlayerActivity(VideoPlayerActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    VideoPlayerActivity_MembersInjector.injectCastManager(
        instance, provideCastManagerProvider.get());
    VideoPlayerActivity_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    VideoPlayerActivity_MembersInjector.injectVideoUtil(instance, videoUtilProvider.get());
    VideoPlayerActivity_MembersInjector.injectDownloadManager(
        instance, gLDownloadManagerProvider.get());
    VideoPlayerActivity_MembersInjector.injectNavItemManager(
        instance, navItemManagerProvider.get());
    VideoPlayerActivity_MembersInjector.injectFileUtil(instance, gLFileUtilProvider.get());
    VideoPlayerActivity_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    VideoPlayerActivity_MembersInjector.injectMediaPlaybackPositionManager(
        instance, mediaPlaybackPositionManagerProvider.get());
    VideoPlayerActivity_MembersInjector.injectPlayListManager(
        instance, playlistManagerProvider.get());
    return instance;
  }

  private CurrentDownloadsActivity injectCurrentDownloadsActivity(
      CurrentDownloadsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    CurrentDownloadsActivity_MembersInjector.injectGlDownloadManager(
        instance, gLDownloadManagerProvider.get());
    CurrentDownloadsActivity_MembersInjector.injectDownloadQueueItemManager(
        instance, downloadQueueItemManagerProvider.get());
    return instance;
  }

  private CustomCollectionsActivity injectCustomCollectionsActivity(
      CustomCollectionsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    CustomCollectionsActivity_MembersInjector.injectCustomCollectionUtil(
        instance, customCollectionUtilProvider.get());
    CustomCollectionsActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    CustomCollectionsActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private NotebookSelectionActivity injectNotebookSelectionActivity(
      NotebookSelectionActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    NotebookSelectionActivity_MembersInjector.injectNotebookUtil(
        instance, notebookUtilProvider.get());
    NotebookSelectionActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private NotesActivity injectNotesActivity(NotesActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    NotesActivity_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    NotesActivity_MembersInjector.injectNotebookUtil(instance, notebookUtilProvider.get());
    NotesActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    NotesActivity_MembersInjector.injectRestoreJournalUtil(
        instance, restoreJournalUtilProvider.get());
    NotesActivity_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    return instance;
  }

  private AllAnnotationsFragment injectAllAnnotationsFragment(AllAnnotationsFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    AllAnnotationsFragment_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    AllAnnotationsFragment_MembersInjector.injectNotebookManager(
        instance, notebookManagerProvider.get());
    AllAnnotationsFragment_MembersInjector.injectSubItemMetadataManager(
        instance, subItemMetadataManagerProvider.get());
    AllAnnotationsFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private NotebooksFragment injectNotebooksFragment(NotebooksFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    NotebooksFragment_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    NotebooksFragment_MembersInjector.injectNotebookUtil(instance, notebookUtilProvider.get());
    NotebooksFragment_MembersInjector.injectAnnotationSync(instance, annotationSyncProvider.get());
    NotebooksFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    NotebooksFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private TagsFragment injectTagsFragment(TagsFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    TagsFragment_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    TagsFragment_MembersInjector.injectTagUtil(instance, tagUtilProvider.get());
    TagsFragment_MembersInjector.injectAnnotationSync(instance, annotationSyncProvider.get());
    TagsFragment_MembersInjector.injectAnnotationManager(instance, annotationManagerProvider.get());
    TagsFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    TagsFragment_MembersInjector.injectViewModelFactory(instance, viewModelFactoryProvider.get());
    return instance;
  }

  private HighlightSelectionActivity injectHighlightSelectionActivity(
      HighlightSelectionActivity instance) {
    HighlightSelectionActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    HighlightSelectionActivity_MembersInjector.injectHighlightUtil(
        instance, highlightUtilProvider.get());
    HighlightSelectionActivity_MembersInjector.injectAnalytics(
        instance, provideAnalyticsProvider.get());
    HighlightSelectionActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    HighlightSelectionActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private AboutActivity injectAboutActivity(AboutActivity instance) {
    AboutActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    AboutActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    AboutActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private AppInfoActivity injectAppInfoActivity(AppInfoActivity instance) {
    AppInfoActivity_MembersInjector.injectCatalogMetaDataManager(
        instance, catalogMetaDataManagerProvider.get());
    AppInfoActivity_MembersInjector.injectDownloadedItemManager(
        instance, downloadedItemManagerProvider.get());
    AppInfoActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    AppInfoActivity_MembersInjector.injectNotebookManager(instance, notebookManagerProvider.get());
    AppInfoActivity_MembersInjector.injectNotebookAnnotationManager(
        instance, notebookAnnotationManagerProvider.get());
    AppInfoActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    AppInfoActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    AppInfoActivity_MembersInjector.injectDeviceUtil(instance, getDeviceUtil());
    AppInfoActivity_MembersInjector.injectFeedbackUtil(instance, feedbackUtilProvider.get());
    AppInfoActivity_MembersInjector.injectFileUtil(instance, gLFileUtilProvider.get());
    AppInfoActivity_MembersInjector.injectUiUtil(instance, ldsUiUtilProvider.get());
    AppInfoActivity_MembersInjector.injectLdsDeviceUtil(instance, ldsDeviceUtilProvider.get());
    AppInfoActivity_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    AppInfoActivity_MembersInjector.injectContentMetaDataManager(
        instance, contentMetaDataManagerProvider.get());
    AppInfoActivity_MembersInjector.injectHighlightManager(
        instance, highlightManagerProvider.get());
    AppInfoActivity_MembersInjector.injectNoteManager(instance, noteManagerProvider.get());
    AppInfoActivity_MembersInjector.injectLinkManager(instance, linkManagerProvider.get());
    AppInfoActivity_MembersInjector.injectBookmarkManager(instance, bookmarkManagerProvider.get());
    AppInfoActivity_MembersInjector.injectTagManager(instance, tagManagerProvider.get());
    return instance;
  }

  private BookmarksFragment injectBookmarksFragment(BookmarksFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BookmarksFragment_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    BookmarksFragment_MembersInjector.injectBookmarkManager(
        instance, bookmarkManagerProvider.get());
    BookmarksFragment_MembersInjector.injectBookmarkQueryManager(
        instance, bookmarkQueryManagerProvider.get());
    BookmarksFragment_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    BookmarksFragment_MembersInjector.injectBookmarkUtil(instance, bookmarkUtilProvider.get());
    BookmarksFragment_MembersInjector.injectExternalIntents(
        instance, externalIntentsProvider.get());
    BookmarksFragment_MembersInjector.injectCitationUtil(instance, citationUtilProvider.get());
    BookmarksFragment_MembersInjector.injectAnnotationSync(instance, annotationSyncProvider.get());
    BookmarksFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    BookmarksFragment_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    return instance;
  }

  private HistoryFragment injectHistoryFragment(HistoryFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    HistoryFragment_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    HistoryFragment_MembersInjector.injectHistoryManager(instance, historyManagerProvider.get());
    return instance;
  }

  private CustomCollectionDirectoryActivity injectCustomCollectionDirectoryActivity(
      CustomCollectionDirectoryActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectCustomCollectionManager(
        instance, customCollectionManagerProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectCustomCollectionItemManager(
        instance, customCollectionItemManagerProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectDownloadManager(
        instance, gLDownloadManagerProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectCustomCollectionDirectoryMenu(
        instance, customCollectionDirectoryMenuProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectContentItemUtil(
        instance, contentItemUtilProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectDownloadedItemManager(
        instance, downloadedItemManagerProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectItemsInCollectionManager(
        instance, allItemsInCollectionQueryManagerProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectCustomCollectionUtil(
        instance, customCollectionUtilProvider.get());
    CustomCollectionDirectoryActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private UriRouterActivity injectUriRouterActivity(UriRouterActivity instance) {
    UriRouterActivity_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    UriRouterActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    UriRouterActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    UriRouterActivity_MembersInjector.injectUriUtil(instance, uriUtilProvider.get());
    UriRouterActivity_MembersInjector.injectLanguageManager(
        instance, languageManagerProvider.get());
    UriRouterActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    UriRouterActivity_MembersInjector.injectLanguageUtil(instance, languageUtilProvider.get());
    UriRouterActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private LocationsActivity injectLocationsActivity(LocationsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private SignInActivity injectSignInActivity(SignInActivity instance) {
    SignInActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    SignInActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    return instance;
  }

  private WelcomeActivity injectWelcomeActivity(WelcomeActivity instance) {
    WelcomeActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    WelcomeActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    WelcomeActivity_MembersInjector.injectLdsAccountUtil(instance, lDSAccountUtilProvider.get());
    WelcomeActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    WelcomeActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    WelcomeActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private TipFragment injectTipFragment(TipFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    TipFragment_MembersInjector.injectFileUtil(instance, gLFileUtilProvider.get());
    TipFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    TipFragment_MembersInjector.injectViewModelFactory(instance, viewModelFactoryProvider.get());
    return instance;
  }

  private TipListPagerActivity injectTipListPagerActivity(TipListPagerActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    TipListPagerActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    TipListPagerActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private TipListFragment injectTipListFragment(TipListFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    TipListFragment_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    TipListFragment_MembersInjector.injectLanguageUtil(instance, languageUtilProvider.get());
    TipListFragment_MembersInjector.injectTipsUtil(instance, tipsUtilProvider.get());
    TipListFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private LinkContentActivity injectLinkContentActivity(LinkContentActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    LinkContentActivity_MembersInjector.injectLinkUtil(instance, linkUtilProvider.get());
    LinkContentActivity_MembersInjector.injectLinkManager(instance, linkManagerProvider.get());
    LinkContentActivity_MembersInjector.injectCitationUtil(instance, citationUtilProvider.get());
    LinkContentActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    LinkContentActivity_MembersInjector.injectContentRenderer(
        instance, contentRendererProvider.get());
    return instance;
  }

  private LinksActivity injectLinksActivity(LinksActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    LinksActivity_MembersInjector.injectParagraphMetadataManager(
        instance, paragraphMetadataManagerProvider.get());
    LinksActivity_MembersInjector.injectInputMethodManager(instance, getInputMethodManager());
    LinksActivity_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    LinksActivity_MembersInjector.injectNavItemManager(instance, navItemManagerProvider.get());
    LinksActivity_MembersInjector.injectNavSectionManager(
        instance, navSectionManagerProvider.get());
    LinksActivity_MembersInjector.injectViewModelFactory(instance, viewModelFactoryProvider.get());
    return instance;
  }

  private SingleAnnotationActivity injectSingleAnnotationActivity(
      SingleAnnotationActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    SingleAnnotationActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    SingleAnnotationActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    return instance;
  }

  private StudyPlansActivity injectStudyPlansActivity(StudyPlansActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    StudyPlansActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    StudyPlansActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private StudyPlansPagerAdapter injectStudyPlansPagerAdapter(StudyPlansPagerAdapter instance) {
    StudyPlansPagerAdapter_MembersInjector.injectApplication(
        instance, provideApplicationProvider.get());
    return instance;
  }

  private StudyPlanListFragment injectStudyPlanListFragment(StudyPlanListFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    StudyPlanListFragment_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    StudyPlanListFragment_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    StudyPlanListFragment_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    return instance;
  }

  private StudyItemsActivity injectStudyItemsActivity(StudyItemsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(instance, getScreenLauncherUtil());
    BaseActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    StudyItemsActivity_MembersInjector.injectLanguageUtil(instance, languageUtilProvider.get());
    StudyItemsActivity_MembersInjector.injectNavigationTrailQueryManager(
        instance, navigationTrailQueryManagerProvider.get());
    StudyItemsActivity_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    StudyItemsActivity_MembersInjector.injectUriUtil(instance, uriUtilProvider.get());
    StudyItemsActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private DeleteAllMediaDialogFragment injectDeleteAllMediaDialogFragment(
      DeleteAllMediaDialogFragment instance) {
    DeleteAllMediaDialogFragment_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    DeleteAllMediaDialogFragment_MembersInjector.injectItemManager(
        instance, itemManagerProvider.get());
    DeleteAllMediaDialogFragment_MembersInjector.injectFileUtil(instance, gLFileUtilProvider.get());
    DeleteAllMediaDialogFragment_MembersInjector.injectAnalytics(
        instance, provideAnalyticsProvider.get());
    DeleteAllMediaDialogFragment_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    return instance;
  }

  private DownloadMediaDialogFragment injectDownloadMediaDialogFragment(
      DownloadMediaDialogFragment instance) {
    DownloadMediaDialogFragment_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    DownloadMediaDialogFragment_MembersInjector.injectGlDownloadManager(
        instance, gLDownloadManagerProvider.get());
    DownloadMediaDialogFragment_MembersInjector.injectRelatedAudioItemManager(
        instance, relatedAudioItemManagerProvider.get());
    DownloadMediaDialogFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private TextSizeDialogFragment injectTextSizeDialogFragment(TextSizeDialogFragment instance) {
    TextSizeDialogFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private ContentTouchListener injectContentTouchListener(ContentTouchListener instance) {
    ContentTouchListener_MembersInjector.injectUiUtil(instance, ldsUiUtilProvider.get());
    ContentTouchListener_MembersInjector.injectTextHandleUtil(
        instance, textHandleUtilProvider.get());
    ContentTouchListener_MembersInjector.injectContentJsInvoker(
        instance, contentJsInvokerProvider.get());
    return instance;
  }

  private ContentWebView injectContentWebView(ContentWebView instance) {
    ContentWebView_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    ContentWebView_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    ContentWebView_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    ContentWebView_MembersInjector.injectExternalIntents(instance, externalIntentsProvider.get());
    ContentWebView_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    ContentWebView_MembersInjector.injectAnnotationListUtil(instance, new AnnotationListUtil());
    ContentWebView_MembersInjector.injectPrefs(instance, prefsProvider.get());
    ContentWebView_MembersInjector.injectUiUtil(instance, ldsUiUtilProvider.get());
    ContentWebView_MembersInjector.injectTextHandleUtil(instance, textHandleUtilProvider.get());
    ContentWebView_MembersInjector.injectContentJsInterface(instance, getContentJsInterface());
    ContentWebView_MembersInjector.injectContentJsInvoker(instance, contentJsInvokerProvider.get());
    ContentWebView_MembersInjector.injectTimeUtil(instance, ldsTimeUtilProvider.get());
    ContentWebView_MembersInjector.injectSubItemManager(instance, subItemManagerProvider.get());
    ContentWebView_MembersInjector.injectParagraphMetadataManager(
        instance, paragraphMetadataManagerProvider.get());
    ContentWebView_MembersInjector.injectHighlightUtil(instance, highlightUtilProvider.get());
    ContentWebView_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    ContentWebView_MembersInjector.injectUriUtil(instance, uriUtilProvider.get());
    ContentWebView_MembersInjector.injectImageUtil(instance, imageUtilProvider.get());
    ContentWebView_MembersInjector.injectCitationUtil(instance, citationUtilProvider.get());
    ContentWebView_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    ContentWebView_MembersInjector.injectPlaylistManager(instance, playlistManagerProvider.get());
    ContentWebView_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    ContentWebView_MembersInjector.injectToastUtil(instance, toastUtilProvider.get());
    return instance;
  }

  private MediaFab injectMediaFab(MediaFab instance) {
    MediaFab_MembersInjector.injectPlaylistManager(instance, playlistManagerProvider.get());
    MediaFab_MembersInjector.injectTextToSpeechManager(instance, textToSpeechManagerProvider.get());
    return instance;
  }

  private DownloadedMediaAdapter injectDownloadedMediaAdapter(DownloadedMediaAdapter instance) {
    DownloadedMediaAdapter_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    return instance;
  }

  private DownloadedMediaCollectionsAdapter injectDownloadedMediaCollectionsAdapter(
      DownloadedMediaCollectionsAdapter instance) {
    DownloadedMediaCollectionsAdapter_MembersInjector.injectItemManager(
        instance, itemManagerProvider.get());
    return instance;
  }

  private CatalogDirectoryAdapter injectCatalogDirectoryAdapter(CatalogDirectoryAdapter instance) {
    CatalogDirectoryAdapter_MembersInjector.injectDownloadQueueItemManager(
        instance, downloadQueueItemManagerProvider.get());
    CatalogDirectoryAdapter_MembersInjector.injectContentItemUtil(
        instance, contentItemUtilProvider.get());
    return instance;
  }

  private ContentDirectoryAdapter injectContentDirectoryAdapter(ContentDirectoryAdapter instance) {
    ContentDirectoryAdapter_MembersInjector.injectUriUtil(instance, uriUtilProvider.get());
    return instance;
  }

  private HistoryAdapter injectHistoryAdapter(HistoryAdapter instance) {
    HistoryAdapter_MembersInjector.injectApplication(instance, provideApplicationProvider.get());
    return instance;
  }

  private ScreensAdapter injectScreensAdapter(ScreensAdapter instance) {
    ScreensAdapter_MembersInjector.injectFileUtil(instance, gLFileUtilProvider.get());
    ScreensAdapter_MembersInjector.injectScreenHistoryItemManager(
        instance, screenHistoryItemManagerProvider.get());
    return instance;
  }

  private LocationsPagerAdapter injectLocationsPagerAdapter(LocationsPagerAdapter instance) {
    LocationsPagerAdapter_MembersInjector.injectApplication(
        instance, provideApplicationProvider.get());
    return instance;
  }

  private BookmarksAdapter injectBookmarksAdapter(BookmarksAdapter instance) {
    BookmarksAdapter_MembersInjector.injectBookmarkUtil(instance, bookmarkUtilProvider.get());
    return instance;
  }

  private StudyPlansAdapter injectStudyPlansAdapter(StudyPlansAdapter instance) {
    StudyPlansAdapter_MembersInjector.injectApplication(instance, provideApplicationProvider.get());
    return instance;
  }

  private StudyItemsAdapter injectStudyItemsAdapter(StudyItemsAdapter instance) {
    StudyItemsAdapter_MembersInjector.injectApplication(instance, provideApplicationProvider.get());
    return instance;
  }

  private DownloadableMediaListLoader injectDownloadableMediaListLoader(
      DownloadableMediaListLoader instance) {
    DownloadableMediaListLoader_MembersInjector.injectVideoUtil(instance, videoUtilProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectItemManager(
        instance, itemManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectNavItemManager(
        instance, navItemManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectNavCollectionManager(
        instance, navCollectionManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectLibraryItemManager(
        instance, libraryItemManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectDownloadQueueItemManager(
        instance, downloadQueueItemManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectRelatedAudioItemManager(
        instance, relatedAudioItemManagerProvider.get());
    DownloadableMediaListLoader_MembersInjector.injectPrefs(instance, prefsProvider.get());
    return instance;
  }

  private ScreensLoader injectScreensLoader(ScreensLoader instance) {
    ScreensLoader_MembersInjector.injectScreenManager(instance, screenManagerProvider.get());
    return instance;
  }

  private DownloadedMediaLoader injectDownloadedMediaLoader(DownloadedMediaLoader instance) {
    DownloadedMediaLoader_MembersInjector.injectDownloadedMediaManager(
        instance, downloadedMediaManagerProvider.get());
    DownloadedMediaLoader_MembersInjector.injectDownloadedMediaCollectionManager(
        instance, downloadedMediaCollectionManagerProvider.get());
    return instance;
  }

  private AnnotationTagsLoader injectAnnotationTagsLoader(AnnotationTagsLoader instance) {
    AnnotationTagsLoader_MembersInjector.injectTagManager(instance, tagManagerProvider.get());
    return instance;
  }

  private HistoryLoader injectHistoryLoader(HistoryLoader instance) {
    HistoryLoader_MembersInjector.injectHistoryManager(instance, historyManagerProvider.get());
    return instance;
  }

  private SideBar injectSideBar(SideBar instance) {
    SideBar_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    SideBar_MembersInjector.injectSidebarHistoryItemManager(
        instance, sidebarHistoryItemManagerProvider.get());
    SideBar_MembersInjector.injectAnnotationManager(instance, annotationManagerProvider.get());
    SideBar_MembersInjector.injectPrefs(instance, prefsProvider.get());
    SideBar_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    SideBar_MembersInjector.injectContentItemUtil(instance, contentItemUtilProvider.get());
    SideBar_MembersInjector.injectGson(instance, provideGsonProvider.get());
    return instance;
  }

  private SideBarRelatedContentItem injectSideBarRelatedContentItem(
      SideBarRelatedContentItem instance) {
    SideBarRelatedContentItem_MembersInjector.injectCc(
        instance, provideCoroutineContextProvider.get());
    SideBarRelatedContentItem_MembersInjector.injectRelatedContentItemManager(
        instance, relatedContentItemManagerProvider.get());
    SideBarRelatedContentItem_MembersInjector.injectContentRenderer(
        instance, contentRendererProvider.get());
    SideBarRelatedContentItem_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    SideBarRelatedContentItem_MembersInjector.injectAnnotationUiUtil(
        instance, annotationUiUtilProvider.get());
    return instance;
  }

  private SideBarAnnotation injectSideBarAnnotation(SideBarAnnotation instance) {
    SideBarAnnotation_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    SideBarAnnotation_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    SideBarAnnotation_MembersInjector.injectLinkUtil(instance, linkUtilProvider.get());
    return instance;
  }

  private SideBarUri injectSideBarUri(SideBarUri instance) {
    SideBarUri_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    SideBarUri_MembersInjector.injectContentRenderer(instance, contentRendererProvider.get());
    SideBarUri_MembersInjector.injectAnnotationUiUtil(instance, annotationUiUtilProvider.get());
    SideBarUri_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    return instance;
  }

  private LDSCastButton.ThemeCompliantDialogFactory injectThemeCompliantDialogFactory(
      LDSCastButton.ThemeCompliantDialogFactory instance) {
    LDSCastButton_ThemeCompliantDialogFactory_MembersInjector.injectPrefs(
        instance, prefsProvider.get());
    return instance;
  }

  private LanguageChangeActivity injectLanguageChangeActivity(LanguageChangeActivity instance) {
    LanguageChangeActivity_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    LanguageChangeActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    LanguageChangeActivity_MembersInjector.injectScreenLauncherUtil(
        instance, getScreenLauncherUtil());
    LanguageChangeActivity_MembersInjector.injectLanguageManager(
        instance, languageManagerProvider.get());
    LanguageChangeActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    LanguageChangeActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    return instance;
  }

  private TipsPagerActivity injectTipsPagerActivity(TipsPagerActivity instance) {
    TipsPagerActivity_MembersInjector.injectViewModelFactory(
        instance, viewModelFactoryProvider.get());
    return instance;
  }

  private LDSCastButton injectLDSCastButton(LDSCastButton instance) {
    LDSCastButton_MembersInjector.injectCastManager(instance, provideCastManagerProvider.get());
    return instance;
  }

  private ScreenSettingsFragment injectScreenSettingsFragment(ScreenSettingsFragment instance) {
    ScreenSettingsFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    ScreenSettingsFragment_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    return instance;
  }

  private ScreenSettingsActivity injectScreenSettingsActivity(ScreenSettingsActivity instance) {
    ScreenSettingsActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    ScreenSettingsActivity_MembersInjector.injectAnalytics(
        instance, provideAnalyticsProvider.get());
    return instance;
  }

  private AudioSettingsActivity injectAudioSettingsActivity(AudioSettingsActivity instance) {
    AudioSettingsActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    AudioSettingsActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    return instance;
  }

  private AudioSettingsFragment injectAudioSettingsFragment(AudioSettingsFragment instance) {
    AudioSettingsFragment_MembersInjector.injectPrefs(instance, prefsProvider.get());
    AudioSettingsFragment_MembersInjector.injectPlaylistManager(
        instance, playlistManagerProvider.get());
    AudioSettingsFragment_MembersInjector.injectPlaylistItemQueryManager(
        instance, playlistItemQueryManagerProvider.get());
    AudioSettingsFragment_MembersInjector.injectRelatedAudioItemManager(
        instance, relatedAudioItemManagerProvider.get());
    AudioSettingsFragment_MembersInjector.injectTextToSpeechManager(
        instance, textToSpeechManagerProvider.get());
    AudioSettingsFragment_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    AudioSettingsFragment_MembersInjector.injectSubItemManager(
        instance, subItemManagerProvider.get());
    AudioSettingsFragment_MembersInjector.injectExternalIntents(
        instance, externalIntentsProvider.get());
    return instance;
  }

  private SideBarRelatedContent injectSideBarRelatedContent(SideBarRelatedContent instance) {
    SideBarRelatedContent_MembersInjector.injectRelatedContentItemManager(
        instance, relatedContentItemManagerProvider.get());
    SideBarRelatedContent_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    SideBarRelatedContent_MembersInjector.injectSubItemManager(
        instance, subItemManagerProvider.get());
    SideBarRelatedContent_MembersInjector.injectParagraphMetadataManager(
        instance, paragraphMetadataManagerProvider.get());
    SideBarRelatedContent_MembersInjector.injectHighlightUtil(
        instance, highlightUtilProvider.get());
    return instance;
  }

  private ContentSourceActivity injectContentSourceActivity(ContentSourceActivity instance) {
    ContentSourceActivity_MembersInjector.injectAnalytics(instance, provideAnalyticsProvider.get());
    ContentSourceActivity_MembersInjector.injectSubItemContentManager(
        instance, subItemContentManagerProvider.get());
    return instance;
  }

  private AnnotationView injectAnnotationView(AnnotationView instance) {
    AnnotationView_MembersInjector.injectBus(instance, provideEventBusProvider.get());
    AnnotationView_MembersInjector.injectPrefs(instance, prefsProvider.get());
    AnnotationView_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    AnnotationView_MembersInjector.injectSubItemMetadataManager(
        instance, subItemMetadataManagerProvider.get());
    AnnotationView_MembersInjector.injectAnnotationUiUtil(instance, annotationUiUtilProvider.get());
    AnnotationView_MembersInjector.injectContentRenderer(instance, contentRendererProvider.get());
    AnnotationView_MembersInjector.injectCitationUtil(instance, citationUtilProvider.get());
    AnnotationView_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    AnnotationView_MembersInjector.injectNoteUtil(instance, noteUtilProvider.get());
    AnnotationView_MembersInjector.injectContentItemUtil(instance, contentItemUtilProvider.get());
    AnnotationView_MembersInjector.injectItemManager(instance, itemManagerProvider.get());
    AnnotationView_MembersInjector.injectHighlightUtil(instance, highlightUtilProvider.get());
    AnnotationView_MembersInjector.injectShareUtil(instance, shareUtilProvider.get());
    AnnotationView_MembersInjector.injectUriUtil(instance, uriUtilProvider.get());
    AnnotationView_MembersInjector.injectAnalyticsUtil(instance, getAnalyticsUtil());
    AnnotationView_MembersInjector.injectCc(instance, provideCoroutineContextProvider.get());
    return instance;
  }

  private BookmarkRouterActivity injectBookmarkRouterActivity(BookmarkRouterActivity instance) {
    BookmarkRouterActivity_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    BookmarkRouterActivity_MembersInjector.injectScreenUtil(instance, getScreenUtil());
    BookmarkRouterActivity_MembersInjector.injectScreenLauncherUtil(
        instance, getScreenLauncherUtil());
    return instance;
  }

  private AnnotationInfoActivity injectAnnotationInfoActivity(AnnotationInfoActivity instance) {
    AnnotationInfoActivity_MembersInjector.injectAnnotationManager(
        instance, annotationManagerProvider.get());
    AnnotationInfoActivity_MembersInjector.injectLinkManager(instance, linkManagerProvider.get());
    AnnotationInfoActivity_MembersInjector.injectNotebookAnnotationManager(
        instance, notebookAnnotationManagerProvider.get());
    AnnotationInfoActivity_MembersInjector.injectNotebookManager(
        instance, notebookManagerProvider.get());
    AnnotationInfoActivity_MembersInjector.injectGson(instance, provideGsonProvider.get());
    return instance;
  }

  private SearchService injectSearchService(SearchService instance) {
    SearchService_MembersInjector.injectSearchUtil(instance, searchUtilProvider.get());
    SearchService_MembersInjector.injectSearchEngineLocal(
        instance, searchEngineLocalProvider.get());
    return instance;
  }

  private TextToSpeechControlsManager injectTextToSpeechControlsManager(
      TextToSpeechControlsManager instance) {
    TextToSpeechControlsManager_MembersInjector.injectItemManager(
        instance, itemManagerProvider.get());
    TextToSpeechControlsManager_MembersInjector.injectSubItemManager(
        instance, subItemManagerProvider.get());
    TextToSpeechControlsManager_MembersInjector.injectLanguageUtil(
        instance, languageUtilProvider.get());
    TextToSpeechControlsManager_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    TextToSpeechControlsManager_MembersInjector.injectTextToSpeechManager(
        instance, textToSpeechManagerProvider.get());
    TextToSpeechControlsManager_MembersInjector.injectCc(
        instance, provideCoroutineContextProvider.get());
    return instance;
  }

  private AudioPlaybackControlsManager injectAudioPlaybackControlsManager(
      AudioPlaybackControlsManager instance) {
    AudioPlaybackControlsManager_MembersInjector.injectPlaylistManager(
        instance, playlistManagerProvider.get());
    AudioPlaybackControlsManager_MembersInjector.injectPrefs(instance, prefsProvider.get());
    AudioPlaybackControlsManager_MembersInjector.injectDownloadManager(
        instance, gLDownloadManagerProvider.get());
    AudioPlaybackControlsManager_MembersInjector.injectInternalIntents(
        instance, internalIntentsProvider.get());
    AudioPlaybackControlsManager_MembersInjector.injectItemManager(
        instance, itemManagerProvider.get());
    AudioPlaybackControlsManager_MembersInjector.injectSubItemManager(
        instance, subItemManagerProvider.get());
    return instance;
  }

  private MiniPlaybackControls injectMiniPlaybackControls(MiniPlaybackControls instance) {
    MiniPlaybackControls_MembersInjector.injectCastManager(
        instance, provideCastManagerProvider.get());
    return instance;
  }

  private TextToSpeechService injectTextToSpeechService(TextToSpeechService instance) {
    TextToSpeechService_MembersInjector.injectTextToSpeechEngine(instance, getTextToSpeechEngine());
    return instance;
  }

  private CurrentDownloadsAdapter injectCurrentDownloadsAdapter(CurrentDownloadsAdapter instance) {
    CurrentDownloadsAdapter_MembersInjector.injectContentItemUtil(
        instance, contentItemUtilProvider.get());
    return instance;
  }

  public static final class Builder {
    private AppModule appModule;

    private PrefsModule prefsModule;

    private LDSAccountModule lDSAccountModule;

    private ServiceModule serviceModule;

    private Builder() {}

    public AppComponent build() {
      if (appModule == null) {
        throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
      }
      if (prefsModule == null) {
        this.prefsModule = new PrefsModule();
      }
      if (lDSAccountModule == null) {
        this.lDSAccountModule = new LDSAccountModule();
      }
      if (serviceModule == null) {
        this.serviceModule = new ServiceModule();
      }
      return new DaggerAppComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder lDSMobileCommonsModule(LDSMobileCommonsModule lDSMobileCommonsModule) {
      Preconditions.checkNotNull(lDSMobileCommonsModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder lDSMobileMediaModule(LDSMobileMediaModule lDSMobileMediaModule) {
      Preconditions.checkNotNull(lDSMobileMediaModule);
      return this;
    }

    public Builder lDSAccountModule(LDSAccountModule lDSAccountModule) {
      this.lDSAccountModule = Preconditions.checkNotNull(lDSAccountModule);
      return this;
    }

    public Builder prefsModule(PrefsModule prefsModule) {
      this.prefsModule = Preconditions.checkNotNull(prefsModule);
      return this;
    }

    public Builder serviceModule(ServiceModule serviceModule) {
      this.serviceModule = Preconditions.checkNotNull(serviceModule);
      return this;
    }
  }
}
