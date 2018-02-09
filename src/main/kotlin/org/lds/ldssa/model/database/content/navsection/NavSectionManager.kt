/*
 * NavSectionManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.navsection

import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class NavSectionManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : NavSectionBaseManager(databaseManager) {

    fun findIdByCollectionId(contentItemId: Long, navCollectionId: Long): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = NavSectionConst.C_ID,
                selection = NavSectionConst.C_NAV_COLLECTION_ID + " = " + navCollectionId,
                defaultValue = 0L)
    }


}