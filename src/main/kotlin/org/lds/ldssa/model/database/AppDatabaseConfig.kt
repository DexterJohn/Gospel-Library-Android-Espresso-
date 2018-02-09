
package org.lds.ldssa.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.AndroidDatabaseBaseManager
import org.dbtools.android.domain.config.DatabaseConfig
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.database.contentvalues.AndroidDBToolsContentValues
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.dbtools.android.domain.log.DBToolsAndroidLogger
import org.dbtools.android.domain.log.DBToolsLogger
import org.lds.ldssa.util.GLFileUtil


class AppDatabaseConfig(val fileUtil: GLFileUtil) : DatabaseConfig {

    override fun identifyDatabases(databaseManager: AndroidDatabaseBaseManager) {
        val glDbFile = fileUtil.getDbFile(DatabaseManagerConst.GL_DATABASE_NAME)
        val catalogDbFile = fileUtil.catalogFile
        val tipsDbFile = fileUtil.tipsFile
        val searchDbFile = fileUtil.getDbFile(DatabaseManagerConst.SEARCH_DATABASE_NAME)

        databaseManager.addDatabase(DatabaseManagerConst.GL_DATABASE_NAME, glDbFile.path, DatabaseManager.GL_VERSION, DatabaseManager.GL_VIEWS_VERSION)
        databaseManager.addDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME, catalogDbFile.path, DatabaseManager.CATALOG_VERSION, DatabaseManager.CATALOG_VIEWS_VERSION)
        databaseManager.addDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME, tipsDbFile.path, DatabaseManager.TIPS_VERSION, DatabaseManager.TIPS_VIEWS_VERSION)
        databaseManager.addDatabase(DatabaseManagerConst.SEARCH_DATABASE_NAME, searchDbFile.path, DatabaseManager.SEARCH_VERSION, DatabaseManager.SEARCH_VIEWS_VERSION)
    }

    override fun createNewDatabaseWrapper(androidDatabase: AndroidDatabase) : DatabaseWrapper<*, *> {
        return SqliteHtmlTokenizerDatabaseWrapper(androidDatabase)
    }

    override fun createNewDBToolsLogger(): DBToolsLogger {
        return DBToolsAndroidLogger()
    }

    override fun createNewDBToolsContentValues() : DBToolsContentValues<*> {
        return AndroidDBToolsContentValues()
    }
}