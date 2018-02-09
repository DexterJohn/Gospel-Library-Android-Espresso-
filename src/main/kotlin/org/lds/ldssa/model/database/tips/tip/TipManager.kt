/*
 * TipManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.tips.tip

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.tips.tipsappversion.TipsAppVersionConst
import org.lds.ldssa.model.database.types.TipType
import java.util.Locale
import javax.inject.Inject


@javax.inject.Singleton
class TipManager @Inject constructor(databaseManager: DatabaseManager,
                                     val languageManager: LanguageManager) : TipBaseManager(databaseManager) {

    private val WELCOME_SELECTION_RAW_QUERY_BASE = SQLQueryBuilder()
            .table(TipConst.TABLE)
            .join(TipsAppVersionConst.TABLE, TipConst.FULL_C_VERSION_ID, TipsAppVersionConst.FULL_C_ID)
            .filter(TipsAppVersionConst.FULL_C_TITLE, '?')
            .filter(TipConst.FULL_C_SHOW_IN_WELCOME, '1')
            .filter(TipConst.FULL_C_ISO6393, '?')

    private val WELCOME_SELECTION_RAW_QUERY_COUNT = WELCOME_SELECTION_RAW_QUERY_BASE.clone()
            .field("count(1)")
            .buildQuery()

    private val WELCOME_SELECTION_RAW_QUERY_IDS = WELCOME_SELECTION_RAW_QUERY_BASE.clone()
            .field(TipConst.FULL_C_ID)
            .orderBy(TipConst.FULL_C_POSITION)
            .buildQuery()

    fun findWelcomeTipCount(): Long {
        return findCountByRawQuery(WELCOME_SELECTION_RAW_QUERY_COUNT, selectionArgs = SQLQueryBuilder.toSelectionArgs(BuildConfig.WELCOME_TIPS_VERSION, Locale.getDefault().isO3Language))
    }

    fun findAllWelcomeTipIds(): List<Long> {
        return findAllValuesByRawQuery(Long::class.java, WELCOME_SELECTION_RAW_QUERY_IDS, SQLQueryBuilder.toSelectionArgs(BuildConfig.WELCOME_TIPS_VERSION, Locale.getDefault().isO3Language))
    }

    fun findCountByLanguageId(languageId: Long): Long {
        return findCountBySelection("${TipConst.C_ISO6393} = ?", SQLQueryBuilder.toSelectionArgs(languageManager.findLocaleById(languageId)))
    }

    fun findAllIdsByLanguageIdAndType(languageId: Long, tipType: TipType): List<Long> {
        return findAllValuesBySelection(Long::class.java,
                column = TipConst.C_ID,
                selection = "${TipConst.C_ISO6393} = ? AND ${TipConst.C_SHOW_IN_WELCOME} = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(languageManager.findLocaleById(languageId), tipType.value),
                orderBy = if (tipType == TipType.REGULAR) TipConst.FULL_C_TITLE else "${TipConst.C_VERSION_ID}, ${TipConst.C_POSITION}")
    }

}