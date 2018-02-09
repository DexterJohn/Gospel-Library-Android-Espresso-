/*
 * BookmarkManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.bookmark

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class BookmarkManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : BookmarkBaseManager(databaseManager) {
    private val INCREMENT_DISPLAY_ORDER = "UPDATE ${BookmarkConst.TABLE} SET ${BookmarkConst.C_DISPLAY_ORDER} = ${BookmarkConst.C_DISPLAY_ORDER} + 1"

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findByAnnotationId(id: Long): Bookmark? {
        return findBySelection("${BookmarkConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(id))
    }

    fun findCountByAnnotationId(annotationId: Long): Long {
        return findCountBySelection("${BookmarkConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun deleteAllByAnnotationId(annotationId: Long): Int {
        return delete("${BookmarkConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findAnnotationIdById(bookmarkId: Long): Long {
        return findValueByRowId(Long::class.java, BookmarkConst.C_ANNOTATION_ID, bookmarkId, -1L)
    }

    fun updateDisplayOrder(id: Long, displayOrder: Int) {
        val values = createNewDBToolsContentValues()
        values.put(BookmarkConst.C_DISPLAY_ORDER, displayOrder)

        update(values, id)
    }

    fun updateName(id: Long, name: String) {
        val values = createNewDBToolsContentValues()
        values.put(BookmarkConst.C_NAME, name)

        update(values, id)
    }

    /**
     * Increment all display_order values so that a new record can be added
     */
    fun incrementAllDisplayOrders() {
        executeSql(INCREMENT_DISPLAY_ORDER)
    }

    /**
     * Update citation for bookmark... no sync needed (not a sync'd field)
     */
    fun updateCitation(id: Long, citation: String) {
        val values = createNewDBToolsContentValues()
        values.put(BookmarkConst.C_CITATION, citation)
        update(values, id)
    }

    fun findAllWithInvalidName(): List<Bookmark> {
        return findAllBySelection("LENGTH(${BookmarkConst.C_NAME}) > ${Bookmark.NAME_MAX_LENGTH}")
    }

}