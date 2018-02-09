/*
 * NavParentCollectionQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.content.navparentcollectionquery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.navcollection.NavCollectionConst
import org.lds.ldssa.model.database.content.navsection.NavSectionConst
import javax.inject.Inject


@javax.inject.Singleton
class NavParentCollectionQueryManager @Inject constructor(databaseManager: DatabaseManager) : NavParentCollectionQueryBaseManager(databaseManager) {
    companion object {
        private val QUERY: String
        private val NAV_COLLECTION_ID: String
        private val NAV_COLLECTION_ROOT_COLLECTION_ID_QUERY: String = SQLQueryBuilder()
                .field(NavCollectionConst.C_ID)
                .table(NavCollectionConst.TABLE)
                .filter(NavCollectionConst.C_NAV_SECTION_ID, CompareType.IS_NULL)
                .toString()


        init {
            NAV_COLLECTION_ID = "CASE WHEN " + NavSectionConst.C_NAV_COLLECTION_ID + " = " + "(" +
                    NAV_COLLECTION_ROOT_COLLECTION_ID_QUERY + ") THEN 0 ELSE " +
                    NavSectionConst.C_NAV_COLLECTION_ID + " END"

            val parentCollectionId = SQLQueryBuilder()
                    .field(NAV_COLLECTION_ID, NavParentCollectionQueryConst.C_ID)
                    .field(NavSectionConst.FULL_C_ID, NavParentCollectionQueryConst.C_NAV_SECTION_ID)
                    .table(NavSectionConst.TABLE)
                    .join(JoinType.JOIN, NavCollectionConst.TABLE, NavCollectionConst.FULL_C_NAV_SECTION_ID, NavSectionConst.FULL_C_ID)
                    .filter(NavCollectionConst.FULL_C_ID, "?")

            QUERY = parentCollectionId.buildQuery()

        }
    }

    override fun getQuery() : String {
        return QUERY
    }


}