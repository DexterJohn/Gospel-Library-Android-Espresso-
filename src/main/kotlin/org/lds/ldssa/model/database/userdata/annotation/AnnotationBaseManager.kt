/*
 * AnnotationBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.annotation

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class AnnotationBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Annotation>(databaseManager) {

    override val allColumns: Array<String> = AnnotationConst.ALL_COLUMNS
    override val primaryKey = AnnotationConst.PRIMARY_KEY_COLUMN
    override val dropSql = AnnotationConst.DROP_TABLE
    override val createSql = AnnotationConst.CREATE_TABLE
    override val insertSql = AnnotationConst.INSERT_STATEMENT
    override val updateSql = AnnotationConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return AnnotationConst.DATABASE
    }

    override fun newRecord() : Annotation {
        return Annotation()
    }

    override fun getTableName() : String {
        return AnnotationConst.TABLE
    }


}