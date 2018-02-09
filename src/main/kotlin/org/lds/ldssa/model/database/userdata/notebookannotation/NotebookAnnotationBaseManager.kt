/*
 * NotebookAnnotationBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.notebookannotation

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class NotebookAnnotationBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<NotebookAnnotation>(databaseManager) {

    override val allColumns: Array<String> = NotebookAnnotationConst.ALL_COLUMNS
    override val primaryKey = NotebookAnnotationConst.PRIMARY_KEY_COLUMN
    override val dropSql = NotebookAnnotationConst.DROP_TABLE
    override val createSql = NotebookAnnotationConst.CREATE_TABLE
    override val insertSql = NotebookAnnotationConst.INSERT_STATEMENT
    override val updateSql = NotebookAnnotationConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NotebookAnnotationConst.DATABASE
    }

    override fun newRecord() : NotebookAnnotation {
        return NotebookAnnotation()
    }

    override fun getTableName() : String {
        return NotebookAnnotationConst.TABLE
    }


}