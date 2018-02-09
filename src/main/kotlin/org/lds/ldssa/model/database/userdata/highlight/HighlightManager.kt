/*
 * HighlightManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.highlight

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class HighlightManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : HighlightBaseManager(databaseManager) {
    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findAllByAnnotationId(annotationId: Long): List<Highlight> {
        return findAllBySelection("${HighlightConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findCountByAnnotationId(annotationId: Long): Long {
        return findCountBySelection("${HighlightConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findDistinctHighlightCountByAnnotationId(): Long {
        return findCountByRawQuery("""SELECT count(1)
            | FROM (
            |   SELECT DISTINCT ${HighlightConst.C_ANNOTATION_ID}
            |   FROM highlight
            | )""".trimMargin())
    }

    fun deleteAllByAnnotationId(annotationId: Long): Int {
        return delete("${HighlightConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun saveAll(highlights: List<Highlight>?) {
        if (highlights == null || highlights.isEmpty()) {
            return
        }

        for (highlight in highlights) {
            save(highlight)
        }
    }

    fun findAllParagraphIdsByAnnotationId(annotationId: Long): List<String> {
        return findAllValuesBySelection(String::class.java, HighlightConst.C_PARAGRAPH_AID, "${HighlightConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId), HighlightConst.C_PARAGRAPH_AID)
    }

    fun findAllIdsByAnnotationId(id: Long): List<Long> {
        return findAllValuesBySelection(Long::class.java, HighlightConst.C_ID, "${HighlightConst.C_ANNOTATION_ID} = $id", null)
    }

    @Deprecated("""This is ONLY used for finding highlights with the color "selection" issues""", ReplaceWith(""))
    fun findAllSelectionHighlightAnnotationIds(): List<Long> {
        return findAllValuesBySelection(Long::class.java, HighlightConst.C_ANNOTATION_ID, "color = '${HighlightColor.SELECTION.htmlName}'")
    }
}