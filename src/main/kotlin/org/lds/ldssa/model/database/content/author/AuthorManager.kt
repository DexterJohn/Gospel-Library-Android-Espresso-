/*
 * AuthorManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.author

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.subitem.SubItemConst
import org.lds.ldssa.model.database.content.subitemauthor.SubitemAuthorConst
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class AuthorManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : AuthorBaseManager(databaseManager) {

    val IMAGE_RENDITIONS_QUERY = """SELECT ${AuthorConst.FULL_C_IMAGE_RENDITIONS}
        | FROM ${AuthorConst.TABLE}
        |   JOIN ${SubitemAuthorConst.TABLE} ON ${AuthorConst.FULL_C_ID} = ${SubitemAuthorConst.FULL_C_AUTHOR_ID}
        |   JOIN ${SubItemConst.TABLE} ON ${SubitemAuthorConst.FULL_C_SUBITEM_ID} = ${SubItemConst.FULL_C_ID}
        | WHERE ${SubItemConst.FULL_C_ID} = ? """.trimMargin()

    fun findImageRenditionsBySubItemId(contentItemId: Long, subItemId: Long): String {
        return findValueByRawQuery(String::class.java, "", IMAGE_RENDITIONS_QUERY, SQLQueryBuilder.toSelectionArgs(subItemId), contentItemUtil.getOpenedDatabaseName(contentItemId))
    }
}