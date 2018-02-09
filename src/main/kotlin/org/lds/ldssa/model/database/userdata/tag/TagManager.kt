/*
 * TagManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.tag

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class TagManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : TagBaseManager(databaseManager) {
    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findAllByAnnotationId(id: Long): List<Tag> {
        return findAllBySelection("${TagConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(id))
    }

    fun deleteAllByAnnotationId(annotationId: Long): Int {
        return delete("${TagConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findAllAnnotationIdsByTagName(name: String): List<Long> {
        return findAllValuesBySelection(Long::class.java, TagConst.C_ANNOTATION_ID, TagConst.FULL_C_NAME + " = ?", SQLQueryBuilder.toSelectionArgs(name))
    }

    fun updateName(originalName: String, newName: String) {
        val formattedName = Tag.fixName(newName)

        val values = createNewDBToolsContentValues()
        values.put(TagConst.C_NAME, formattedName)
        update(values, "${TagConst.C_NAME} = ?", SQLQueryBuilder.toSelectionArgs(originalName))
    }

    fun deleteAllByName(name: String): Int {
        return delete("${TagConst.C_NAME} = ?", SQLQueryBuilder.toSelectionArgs(name))
    }

    fun findCountByAnnotationId(annotationId: Long): Long {
        return findCountBySelection("${TagConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findDistinctNameCount(): Long {
        return findCountByRawQuery("""SELECT count(1)
            | FROM (
            |   SELECT DISTINCT ${TagConst.C_NAME}
            |   FROM ${TagConst.TABLE}
            | )""".trimMargin())
    }

    fun findAllIdsByAnnotationId(id: Long): List<Long> {
        return findAllValuesBySelection(Long::class.java, TagConst.C_ID, "${TagConst.C_ANNOTATION_ID} = $id", null)
    }

    fun findAllWithInvalidCharacter(): List<Tag> {
        return findAllByRawQuery("SELECT * FROM ${TagConst.TABLE} WHERE ${TagConst.C_NAME} LIKE '%${Tag.ILLEGAL_CHAR}%'", null)
    }

    fun deleteByAnnotationIdAndName(annotationId: Long, name: String): Int {
        return delete("${TagConst.C_ANNOTATION_ID} = ? AND ${TagConst.C_NAME} = ?", SQLQueryBuilder.toSelectionArgs(annotationId, name))
    }
}