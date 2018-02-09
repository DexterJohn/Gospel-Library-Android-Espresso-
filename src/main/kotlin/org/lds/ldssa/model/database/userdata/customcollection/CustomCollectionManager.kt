/*
 * CustomCollectionManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.customcollection

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager
import org.lds.ldssa.util.UserdataDbUtil
import org.threeten.bp.LocalDateTime
import java.util.HashMap
import javax.inject.Inject


@javax.inject.Singleton
class CustomCollectionManager @Inject constructor(databaseManager: DatabaseManager,
                                                  val userdataDbUtil: UserdataDbUtil,
                                                  val customCollectionItemManager: CustomCollectionItemManager) : CustomCollectionBaseManager(databaseManager) {
    private var MAX_POSITION: String

    init {
        val max = SQLQueryBuilder()
                .table(CustomCollectionConst.TABLE)
                .field("MAX(${CustomCollectionConst.C_ORDER_POSITION})")

        MAX_POSITION = max.buildQuery()
    }

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    override fun save(record: CustomCollection?, databaseName: String): Boolean {
        if (record == null) {
            return false
        }

        if (record.isNewRecord) {
            record.created = LocalDateTime.now()
        }

        if (record.orderPosition <= 0) {
            val position = findValueByRawQuery(Int::class.java, rawQuery = MAX_POSITION, defaultValue = 0)
            record.orderPosition = position + 1
        }

        record.lastModified = LocalDateTime.now()
        record.initUniqueId() // make sure this record has a unique id

        return super.save(record, databaseName)
    }

    fun updateCollection(id: Long, name: String): Boolean {
        val values = createNewDBToolsContentValues()
        values.put(CustomCollectionConst.C_TITLE, name)
        values.put(CustomCollectionConst.C_LAST_MODIFIED, System.currentTimeMillis())
        return update(values, id) > 0
    }

    fun updateCollectionLastModified(id: Long): Boolean {
        val values = createNewDBToolsContentValues()
        values.put(CustomCollectionConst.C_LAST_MODIFIED, System.currentTimeMillis())
        return update(values, id) > 0
    }

    fun updateOrderPositions(positionMap: Map<Long, Int>) {
        val values = createNewDBToolsContentValues()
        beginTransaction()

        for ((key, value) in positionMap) {
            values.put(CustomCollectionConst.C_ORDER_POSITION, value)
            values.put(CustomCollectionConst.C_LAST_MODIFIED, System.currentTimeMillis())
            update(values, key)
        }

        endTransaction(true)
    }

    fun updateOrderPositions() {
        val ids = findAllValuesBySelection(Long::class.java, CustomCollectionConst.C_ID, null, null, CustomCollectionConst.C_ORDER_POSITION)
        val positions = HashMap<Long, Int>()
        for (index in ids.indices) {
            positions.put(ids[index], index)
        }
        updateOrderPositions(positions)
    }

    fun findTitleById(id: Long): String {
        return findValueByRowId(
                valueType = String::class.java,
                column = CustomCollectionConst.C_TITLE,
                rowId = id,
                defaultValue = "")
    }

    override fun delete(record: CustomCollection?, databaseName: String): Int {
        if (record == null) {
            return 0
        }

        customCollectionItemManager.deleteAllByCollectionId(record.id)
        val deleteCount = super.delete(record, databaseName)

        if (deleteCount > 0) {
            updateOrderPositions()
        }

        return deleteCount
    }
}