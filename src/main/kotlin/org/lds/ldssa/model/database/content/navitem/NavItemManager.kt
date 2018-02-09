/*
 * NavItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.navitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class NavItemManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : NavItemBaseManager(databaseManager) {

    fun findById(contentItemId: Long, navItemId: Long): NavItem? {
        return findByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId), rowId = navItemId)
    }

    fun findSubItemIdById(contentItemId: Long, id: Long): Long {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = NavItemConst.C_SUBITEM_ID,
                rowId = id,
                defaultValue = 0L)
    }

    fun findTitleById(contentItemId: Long, id: Long): String? {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = NavItemConst.C_TITLE_HTML,
                rowId = id,
                defaultValue = null)
    }

    fun findIdBySubItemId(contentItemId: Long, subItemId: Long): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = NavItemConst.C_ID,
                selection = NavItemConst.C_SUBITEM_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId),
                defaultValue = 0L)
    }

    fun findAllSearchSuggestionGotoScriptureItems(contentItemId: Long): List<NavItem> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = NavItemConst.C_NAV_SECTION_ID + " = 1")
    }

    fun findAllSearchSuggestionGotoScriptureHelpItems(contentItemId: Long): List<NavItem> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = NavItemConst.C_NAV_SECTION_ID + " != 1")
    }

    fun findSubItemIdByChapter(contentItemId: Long, sectionId: Long, chapterNumber: String): Long {
        var subItemId = findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = NavItemConst.C_SUBITEM_ID,
                selection = NavItemConst.C_NAV_SECTION_ID + " = ? AND " + NavItemConst.C_URI + " LIKE ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(sectionId, "%/" + chapterNumber),
                defaultValue = 0L)

        // D&C will not have a subItemId because it does not have a proper sectionId
        if (subItemId == 0L) {
            subItemId = findSubItemIdByChapter(contentItemId, chapterNumber)
        }

        return subItemId
    }

    fun findSubItemIdByChapter(contentItemId: Long, chapterNumber: String): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = NavItemConst.C_SUBITEM_ID,
                selection = NavItemConst.C_URI + " LIKE ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs("%/" + chapterNumber),
                defaultValue = 0L)
    }

}