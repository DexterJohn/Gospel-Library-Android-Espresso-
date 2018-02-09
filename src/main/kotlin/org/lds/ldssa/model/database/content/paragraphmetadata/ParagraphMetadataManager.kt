/*
 * ParagraphMetadataManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.paragraphmetadata

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import java.util.*
import javax.inject.Inject


@javax.inject.Singleton
class ParagraphMetadataManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : ParagraphMetadataBaseManager(databaseManager) {

    private val ALL_BY_SUBITEM_ID_AND_IS_PARAGRAPH = SQLQueryBuilder()
            .table(ParagraphMetadataConst.TABLE)
            .filter(ParagraphMetadataConst.C_SUBITEM_ID, "?")
            .filter(ParagraphMetadataConst.C_PARAGRAPH_ID, CompareType.LIKE, "'p%'")
            .buildQuery()


    fun findAllAidsBySubItemAndParagraphIds(contentItemId: Long, subItemId: Long, paragraphIds: Set<String>): List<String> {
        val sqlFormattedParagraphIds = ArrayList<String>(paragraphIds.size)
        paragraphIds.mapTo(sqlFormattedParagraphIds) { "'$it'" }

        val sql = SQLQueryBuilder.build()
                .table(ParagraphMetadataConst.TABLE)
                .field(ParagraphMetadataConst.C_PARAGRAPH_AID)
                .filter(ParagraphMetadataConst.C_SUBITEM_ID, subItemId)
                .filter(InFilter.create(ParagraphMetadataConst.C_PARAGRAPH_ID, true, sqlFormattedParagraphIds))
                .buildQuery()
        return findAllValuesByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                rawQuery = sql)
    }

    fun findAllBySubItemIdAndIsParagraph(contentItemId: Long, subItemId: Long): List<ParagraphMetadata> {
        return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = ALL_BY_SUBITEM_ID_AND_IS_PARAGRAPH,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId))
    }

    fun findAllAidsBySubItemIdAndIsParagraph(contentItemId: Long, subItemId: Long): List<String> {
        return findAllValuesByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                rawQuery = ALL_BY_SUBITEM_ID_AND_IS_PARAGRAPH,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId))
    }

    fun findByContentItemAndSubItemAndParagraphAid(contentItemId: Long, subItemId: Long, paragraphAid: String): ParagraphMetadata? {
        return findBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = ParagraphMetadataConst.C_SUBITEM_ID + " = ? AND " + ParagraphMetadataConst.C_PARAGRAPH_AID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, paragraphAid))
    }

    fun findAid(contentItemId: Long, subItemId: Long, paragraphId: String?): String {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = ParagraphMetadataConst.C_PARAGRAPH_AID,
                selection = ParagraphMetadataConst.C_SUBITEM_ID + " = ? AND " + ParagraphMetadataConst.C_PARAGRAPH_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, paragraphId),
                defaultValue = "")
    }

    fun findParagraphIdByAid(contentItemId: Long, subItemId: Long, paragraphAid: String): String {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = ParagraphMetadataConst.C_PARAGRAPH_ID,
                selection = ParagraphMetadataConst.C_SUBITEM_ID + " = ? AND " + ParagraphMetadataConst.C_PARAGRAPH_AID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, paragraphAid),
                defaultValue = "")
    }

    fun findAidByVerseNumber(contentItemId: Long, subItemId: Long, verseNumber: String): String {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = ParagraphMetadataConst.C_PARAGRAPH_AID,
                selection = ParagraphMetadataConst.C_SUBITEM_ID + " = ? AND " + ParagraphMetadataConst.C_VERSE_NUMBER + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, verseNumber),
                defaultValue = "")
    }

    fun findByParagraphAid(contentItemId: Long, subItemId: Long, paragraphAid: String): ParagraphMetadata? {
        return findBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = ParagraphMetadataConst.C_SUBITEM_ID + " = ? AND " + ParagraphMetadataConst.C_PARAGRAPH_AID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, paragraphAid))
    }

    fun findStartIndexByParagraphId(contentItemId: Long, subItemId: Long, paragraphId: String): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = ParagraphMetadataConst.C_START_INDEX,
                selection = ParagraphMetadataConst.C_SUBITEM_ID + " = ? AND " + ParagraphMetadataConst.C_PARAGRAPH_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, paragraphId),
                defaultValue = 0L)
    }

    fun findVerseNumbers(contentItemId: Long, subItemId: Long, paragraphAids: List<String>): List<String> {
        val query = SQLQueryBuilder()
                .table(ParagraphMetadataConst.TABLE)
                .field(ParagraphMetadataConst.C_VERSE_NUMBER)
                .filter(ParagraphMetadataConst.C_SUBITEM_ID, subItemId)
                .filter(InFilter.create(ParagraphMetadataConst.C_PARAGRAPH_AID, paragraphAids))
                .orderBy(ParagraphMetadataConst.C_START_INDEX)

        return findAllValuesByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                rawQuery = query.toString())
    }

    fun findTopMostAid(contentItemId: Long, subItemId: Long): String {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = ParagraphMetadataConst.C_PARAGRAPH_AID,
                selection = ParagraphMetadataConst.C_SUBITEM_ID + "=?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId),
                defaultValue = "")
    }
}