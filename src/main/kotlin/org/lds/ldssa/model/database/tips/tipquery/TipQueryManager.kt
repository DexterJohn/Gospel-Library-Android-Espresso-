/*
 * TipQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.tips.tipquery

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.gl.tipviewed.TipViewedManager
import org.lds.ldssa.model.database.tips.tip.TipConst
import org.lds.ldssa.model.database.tips.tipsappversion.TipsAppVersionConst
import org.lds.ldssa.model.database.types.TipType
import javax.inject.Inject


@javax.inject.Singleton
class TipQueryManager @Inject constructor(databaseManager: DatabaseManager,
                                          val languageManager: LanguageManager,
                                          val tipViewedManager: TipViewedManager) : TipQueryBaseManager(databaseManager) {
    companion object {
        private val QUERY_BY_NAME: SQLQueryBuilder
        private val QUERY_BY_POSITION: SQLQueryBuilder

        init {
            val query = SQLQueryBuilder()
                    .table(TipConst.TABLE)
                    .field(TipConst.FULL_C_ID, TipQueryConst.C_ID)
                    .field(TipConst.FULL_C_ISO6393, TipQueryConst.C_ISO6393)
                    .field(TipConst.FULL_C_TITLE, TipQueryConst.C_TITLE)
                    .field(TipsAppVersionConst.FULL_C_ID, TipQueryConst.C_VERSION_ID)
                    .field(TipsAppVersionConst.FULL_C_TITLE, TipQueryConst.C_VERSION_NAME)
                    .filter(TipQueryConst.C_ISO6393, "?")
                    .join(TipsAppVersionConst.TABLE, TipConst.FULL_C_VERSION_ID, TipsAppVersionConst.FULL_C_ID)

            QUERY_BY_NAME = query.clone()
                    .orderBy(TipConst.FULL_C_TITLE)

            QUERY_BY_POSITION = query.clone()
                    .orderBy(TipsAppVersionConst.FULL_C_POSITION + " DESC", TipConst.FULL_C_POSITION)
        }
    }

    override fun getQuery(): String {
        return QUERY_BY_NAME.toString()
    }

    fun findTipsByType(orderByName: Boolean, languageId: Long, tipType: TipType): List<TipQuery> {
        val viewedIds = tipViewedManager.findAllTipIds()
        val query: SQLQueryBuilder
        if (orderByName) {
            query = QUERY_BY_NAME.clone()
        } else {
            query = QUERY_BY_POSITION.clone()
        }
        query.apply(buildCaseStatement(viewedIds))
        query.filter(TipConst.FULL_C_SHOW_IN_WELCOME, "?")
        val selectionArgs = SQLQueryBuilder.toSelectionArgs(languageManager.findLocaleById(languageId), tipType.value)

        return findAllByRawQuery(query.buildQuery(), selectionArgs)
    }

    private fun buildCaseStatement(viewedIds: List<Long>?): SQLQueryBuilder {
        val viewed: String
        if (viewedIds == null || viewedIds.isEmpty()) {
            viewed = "0"
        } else {
            val caseStatement = StringBuilder(" CASE WHEN " + TipConst.FULL_C_ID + " IN (")
            for (index in viewedIds.indices) {
                caseStatement.append(viewedIds[index])
                if (index < viewedIds.size - 1) {
                    caseStatement.append(",")
                }
            }
            viewed = caseStatement.append(") THEN 1 ELSE 0 END").toString()
        }
        return SQLQueryBuilder().field(viewed, TipQueryConst.C_VIEWED)
    }
}