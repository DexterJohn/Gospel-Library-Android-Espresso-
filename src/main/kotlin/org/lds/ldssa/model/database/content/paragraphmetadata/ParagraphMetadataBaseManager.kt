/*
 * ParagraphMetadataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.paragraphmetadata

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class ParagraphMetadataBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<ParagraphMetadata>(databaseManager) {

    override val allColumns: Array<String> = ParagraphMetadataConst.ALL_COLUMNS
    override val primaryKey = ParagraphMetadataConst.PRIMARY_KEY_COLUMN
    override val dropSql = ParagraphMetadataConst.DROP_TABLE
    override val createSql = ParagraphMetadataConst.CREATE_TABLE
    override val insertSql = ParagraphMetadataConst.INSERT_STATEMENT
    override val updateSql = ParagraphMetadataConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return ParagraphMetadataConst.DATABASE
    }

    override fun newRecord() : ParagraphMetadata {
        return ParagraphMetadata()
    }

    override fun getTableName() : String {
        return ParagraphMetadataConst.TABLE
    }


}