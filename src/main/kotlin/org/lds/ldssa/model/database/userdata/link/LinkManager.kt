/*
 * LinkManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.link

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class LinkManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : LinkBaseManager(databaseManager) {
    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findAllByAnnotationId(annotationId: Long): List<Link> {
        return findAllBySelection("${LinkConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    /**
     * Should only be called from AnnotationListUtil
     */
    fun deleteAllByAnnotationId(annotationId: Long): Int {
        return delete("${LinkConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findCountByAnnotationId(annotationId: Long): Long {
        return findCountBySelection("${LinkConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findAllIdsByAnnotationId(id: Long): List<Long> {
        return findAllValuesBySelection(Long::class.java, LinkConst.C_ID, "${LinkConst.C_ANNOTATION_ID} = $id")
    }

    fun findAllByDocId(docId: String): List<Link> {
        return findAllBySelection("${LinkConst.C_DOC_ID} = ?", SQLQueryBuilder.toSelectionArgs(docId))
    }


}