/*
 * DatabaseBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.AndroidBaseManager
import org.dbtools.android.domain.AndroidDatabaseManager
import org.dbtools.android.domain.config.DatabaseConfig


@Suppress("unused", "ConvertSecondaryConstructorToPrimary", "RemoveEmptySecondaryConstructorBody")
@SuppressWarnings("all")
abstract class DatabaseBaseManager  : AndroidDatabaseManager {


    constructor(databaseConfig: DatabaseConfig) : super(databaseConfig) {
    }

    open fun createCatalogTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.item.ItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.itemcategory.ItemCategoryConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.language.LanguageConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.languagename.LanguageNameConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.stopword.StopWordConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.searchgoto.SearchGotoConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createContentTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.author.AuthorConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.authorrole.AuthorRoleConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.navcollection.NavCollectionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntryConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.navitem.NavItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.navsection.NavSectionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.relatedaudiovoice.RelatedAudioVoiceConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.relatedvideoitem.RelatedVideoItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.relatedvideoitemsource.RelatedVideoItemSourceConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.role.RoleConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.subitem.SubItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.subitemauthor.SubitemAuthorConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.subitemtopic.SubitemTopicConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.content.subitemcontent.SubItemContentConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createTipsTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.tips.tipsmetadata.TipsMetaDataConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.tips.tip.TipConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.tips.tipsappversion.TipsAppVersionConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createGlTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.tab.TabConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.tabhistoryitem.TabHistoryItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.sidebarhistoryitem.SidebarHistoryItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.history.HistoryConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.tipviewed.TipViewedConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createUserdataTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.annotation.AnnotationConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.notebook.NotebookConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.tag.TagConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.bookmark.BookmarkConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.highlight.HighlightConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.link.LinkConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.note.NoteConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.screen.ScreenConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createSearchTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchcollection.SearchCollectionConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchhistory.SearchHistoryConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    override fun onCreate(androidDatabase: AndroidDatabase) {
        logger.i(TAG, "Creating database: ${androidDatabase.name}")
        if (androidDatabase.name == DatabaseManagerConst.CATALOG_DATABASE_NAME) {
            createCatalogTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.CONTENT_DATABASE_NAME) {
            createContentTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.TIPS_DATABASE_NAME) {
            createTipsTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.GL_DATABASE_NAME) {
            createGlTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.USERDATA_DATABASE_NAME) {
            createUserdataTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.SEARCH_DATABASE_NAME) {
            createSearchTables(androidDatabase)
        }
    }

    open fun createCatalogViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.catalog.itemcollectionview.ItemCollectionViewManager.CREATE_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun dropCatalogViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.dropTable(database, org.lds.ldssa.model.database.catalog.itemcollectionview.ItemCollectionViewManager.DROP_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createGlViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager.CREATE_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun dropGlViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.dropTable(database, org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager.DROP_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createUserdataViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager.CREATE_VIEW)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.userdata.tagview.TagViewManager.CREATE_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun dropUserdataViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.dropTable(database, org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager.DROP_VIEW)
        AndroidBaseManager.dropTable(database, org.lds.ldssa.model.database.userdata.tagview.TagViewManager.DROP_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createSearchViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchcountcollection.SearchCountCollectionManager.CREATE_VIEW)
        AndroidBaseManager.createTable(database, org.lds.ldssa.model.database.search.searchallcount.SearchAllCountManager.CREATE_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun dropSearchViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.dropTable(database, org.lds.ldssa.model.database.search.searchcountcollection.SearchCountCollectionManager.DROP_VIEW)
        AndroidBaseManager.dropTable(database, org.lds.ldssa.model.database.search.searchallcount.SearchAllCountManager.DROP_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    override fun onCreateViews(androidDatabase: AndroidDatabase) {
        logger.i(TAG, "Creating database views: ${androidDatabase.name}")
        if (androidDatabase.name == DatabaseManagerConst.CATALOG_DATABASE_NAME) {
            createCatalogViews(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.GL_DATABASE_NAME) {
            createGlViews(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.USERDATA_DATABASE_NAME) {
            createUserdataViews(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.SEARCH_DATABASE_NAME) {
            createSearchViews(androidDatabase)
        }
    }

    override fun onDropViews(androidDatabase: AndroidDatabase) {
        logger.i(TAG, "Dropping database views: ${androidDatabase.name}")
        if (androidDatabase.name == DatabaseManagerConst.CATALOG_DATABASE_NAME) {
            dropCatalogViews(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.GL_DATABASE_NAME) {
            dropGlViews(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.USERDATA_DATABASE_NAME) {
            dropUserdataViews(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.SEARCH_DATABASE_NAME) {
            dropSearchViews(androidDatabase)
        }
    }


}