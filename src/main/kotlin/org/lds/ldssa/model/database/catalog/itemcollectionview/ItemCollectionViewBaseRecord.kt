/*
 * ItemCollectionViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.itemcollectionview

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ItemCollectionViewBaseRecord  : AndroidBaseRecord {

    open var itemId: Long = 0
    open var itemTitle: String = ""
    open var itemPosition: Int = 0
    open var collectionId: Long = 0
    open var collectionTitle: String = ""
    open var collectionPosition: Int = 0
    open var sectionId: Long = 0
    open var sectionPosition: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return ItemCollectionViewConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ItemCollectionViewConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ItemCollectionViewConst.C_ITEM_ID, itemId)
        values.put(ItemCollectionViewConst.C_ITEM_TITLE, itemTitle)
        values.put(ItemCollectionViewConst.C_ITEM_POSITION, itemPosition.toLong())
        values.put(ItemCollectionViewConst.C_COLLECTION_ID, collectionId)
        values.put(ItemCollectionViewConst.C_COLLECTION_TITLE, collectionTitle)
        values.put(ItemCollectionViewConst.C_COLLECTION_POSITION, collectionPosition.toLong())
        values.put(ItemCollectionViewConst.C_SECTION_ID, sectionId)
        values.put(ItemCollectionViewConst.C_SECTION_POSITION, sectionPosition.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            itemId,
            itemTitle,
            itemPosition.toLong(),
            collectionId,
            collectionTitle,
            collectionPosition.toLong(),
            sectionId,
            sectionPosition.toLong())
    }

    open fun copy() : ItemCollectionView {
        val copy = ItemCollectionView()
        copy.itemId = itemId
        copy.itemTitle = itemTitle
        copy.itemPosition = itemPosition
        copy.collectionId = collectionId
        copy.collectionTitle = collectionTitle
        copy.collectionPosition = collectionPosition
        copy.sectionId = sectionId
        copy.sectionPosition = sectionPosition
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, itemId)
        statement.bindString(2, itemTitle)
        statement.bindLong(3, itemPosition.toLong())
        statement.bindLong(4, collectionId)
        statement.bindString(5, collectionTitle)
        statement.bindLong(6, collectionPosition.toLong())
        statement.bindLong(7, sectionId)
        statement.bindLong(8, sectionPosition.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, itemId)
        statement.bindString(2, itemTitle)
        statement.bindLong(3, itemPosition.toLong())
        statement.bindLong(4, collectionId)
        statement.bindString(5, collectionTitle)
        statement.bindLong(6, collectionPosition.toLong())
        statement.bindLong(7, sectionId)
        statement.bindLong(8, sectionPosition.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        itemId = values.getAsLong(ItemCollectionViewConst.C_ITEM_ID)
        itemTitle = values.getAsString(ItemCollectionViewConst.C_ITEM_TITLE)
        itemPosition = values.getAsInteger(ItemCollectionViewConst.C_ITEM_POSITION)
        collectionId = values.getAsLong(ItemCollectionViewConst.C_COLLECTION_ID)
        collectionTitle = values.getAsString(ItemCollectionViewConst.C_COLLECTION_TITLE)
        collectionPosition = values.getAsInteger(ItemCollectionViewConst.C_COLLECTION_POSITION)
        sectionId = values.getAsLong(ItemCollectionViewConst.C_SECTION_ID)
        sectionPosition = values.getAsInteger(ItemCollectionViewConst.C_SECTION_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_ITEM_ID))
        itemTitle = cursor.getString(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_ITEM_TITLE))
        itemPosition = cursor.getInt(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_ITEM_POSITION))
        collectionId = cursor.getLong(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_COLLECTION_ID))
        collectionTitle = cursor.getString(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_COLLECTION_TITLE))
        collectionPosition = cursor.getInt(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_COLLECTION_POSITION))
        sectionId = cursor.getLong(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_SECTION_ID))
        sectionPosition = cursor.getInt(cursor.getColumnIndexOrThrow(ItemCollectionViewConst.C_SECTION_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}