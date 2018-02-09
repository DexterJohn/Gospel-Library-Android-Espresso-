/*
 * ItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.item

import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceConst
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataConst
import org.lds.ldssa.model.database.types.CatalogItemSourceType
import java.util.ArrayList
import javax.inject.Inject


@javax.inject.Singleton
class ItemManager @Inject constructor(databaseManager: DatabaseManager) : ItemBaseManager(databaseManager) {

    private val SOURCE_TYPE_QUERY = SQLQueryBuilder()
            .field(ItemConst.FULL_C_ID)
            .table(ItemConst.TABLE)
            .join(CatalogSourceConst.TABLE, ItemConst.FULL_C_SOURCE_ID, CatalogSourceConst.FULL_C_ID)
            .filter(CatalogSourceConst.FULL_C_SOURCE_TYPE, "?")
            .buildQuery()

    private val TITLE_BY_DOC_ID_QUERY = SQLQueryBuilder()
            .field(ItemConst.FULL_C_TITLE)
            .table(ItemConst.TABLE)
            .join(SubItemMetadataConst.TABLE, ItemConst.FULL_C_ID, SubItemMetadataConst.C_ITEM_ID)
            .filter(SubItemMetadataConst.FULL_C_DOC_ID, "?")
            .buildQuery()

    fun findAllByRowIds(itemIds: List<Long>): List<Item> {
        val qb = SQLQueryBuilder()
                .table(ItemConst.TABLE)
                .filter(InFilter.create(ItemConst.C_ID, true, itemIds))

        return findAllByRawQuery(qb.buildQuery(), null)
    }

    fun findIdByUri(languageId: Long, uri: String): Long {
        return findValueBySelection(
                valueType = Long::class.java,
                column = ItemConst.C_ID,
                selection = "${ItemConst.C_LANGUAGE_ID} = ? AND ${ItemConst.C_URI} = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(languageId, uri),
                defaultValue = -1L)
    }

    fun findAllIdsByUris(languageId: Long, uris: List<String>): List<Long> {
        val qb = SQLQueryBuilder()
                .table(ItemConst.TABLE)
                .filter(InFilter.create(ItemConst.C_URI, true, quoteStrings(uris)))
                .filter(ItemConst.C_LANGUAGE_ID, "?")
                .field(ItemConst.C_ID)

        return findAllValuesByRawQuery(Long::class.java, qb.buildQuery(), SQLQueryBuilder.toSelectionArgs(languageId))
    }

    fun findAllIdsByExternalIds(externalIds: List<String>): List<Long> {
        val qb = SQLQueryBuilder()
                .table(ItemConst.TABLE)
                .filter(InFilter.create(ItemConst.C_EXTERNAL_ID, true, quoteStrings(externalIds)))
                .field(ItemConst.C_ID)

        return findAllValuesByRawQuery(Long::class.java, qb.buildQuery(), null)
    }

    fun findAllByExternalIds(externalIds: Set<String>): List<Item> {
        if (externalIds.isEmpty()) {
            return ArrayList()
        }

        val query = SQLQueryBuilder()
                .table(ItemConst.TABLE)
                .filter(InFilter.create(ItemConst.C_EXTERNAL_ID, true, quoteStrings(externalIds)))
                .buildQuery()
        return findAllByRawQuery(query, null)
    }

    fun findTitleById(id: Long): String {
        return findValueByRowId(String::class.java, ItemConst.C_TITLE, id, defaultValue = "")
    }

    fun findLanguageIdById(id: Long): Long {
        return findValueByRowId(Long::class.java, ItemConst.C_LANGUAGE_ID, id, 0L)
    }

    fun findTitleByDocId(docId: String): String {
        return findValueByRawQuery(String::class.java, rawQuery = TITLE_BY_DOC_ID_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(docId), defaultValue = "")
    }

    fun findVersionById(id: Long): Long {
        return findValueByRowId(Long::class.java, ItemConst.C_VERSION, id, 0L)
    }

    fun findImageCoverRenditionsById(id: Long): String {
        return findValueByRowId(String::class.java, ItemConst.C_ITEM_COVER_RENDITIONS, id, defaultValue = "")
    }

    private fun quoteStrings(values: Collection<String>): List<String> {
        // place single quotes around all items in the list
        val valuesWithQuotesList = ArrayList<String>(values.size)
        values.mapTo(valuesWithQuotesList) { "'$it'" }

        return valuesWithQuotesList
    }

    fun findAllBySourceType(secure: CatalogItemSourceType): List<Long> {
        return findAllValuesByRawQuery(Long::class.java, SOURCE_TYPE_QUERY, SQLQueryBuilder.toSelectionArgs(secure.ordinal))
    }

    fun findUriById(itemId: Long): String {
        return findValueBySelection(String::class.java, ItemConst.C_URI,
                selection = ItemConst.C_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(itemId),
                defaultValue = "")
    }
}