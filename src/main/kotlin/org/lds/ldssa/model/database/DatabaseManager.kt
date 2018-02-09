
package org.lds.ldssa.model.database

import org.dbtools.android.domain.AndroidBaseManager
import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.DatabaseConfig
import org.dbtools.android.domain.database.DatabaseWrapper
import org.lds.ldssa.model.database.search.searchhistory.SearchHistoryConst
import org.lds.ldssa.model.database.userdata.note.NoteManager
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DatabaseManager @Inject constructor(databaseConfig: DatabaseConfig) : DatabaseBaseManager(databaseConfig) {

    companion object {
        const val CATALOG_VERSION = 1
        const val CATALOG_VIEWS_VERSION = 3
        const val GL_VERSION = 17
        const val GL_VIEWS_VERSION = 0
        const val TIPS_VERSION = 2
        const val TIPS_VIEWS_VERSION = 1
        const val USERDATA_VERSION = 8
        const val USERDATA_VIEWS_VERSION = 5
        const val SEARCH_VERSION = 3
        const val SEARCH_VIEWS_VERSION = 2
    }

    override fun onCreate(androidDatabase: AndroidDatabase) {
        if (androidDatabase.name.startsWith(DatabaseManagerConst.USERDATA_DATABASE_NAME)) {
            Timber.i("Creating userdata database: %s", androidDatabase.name)
            createUserdataTables(androidDatabase)
            createUserdataViews(androidDatabase)
        } else {
            super.onCreate(androidDatabase)
        }
    }

    override fun createUserdataTables(androidDatabase: AndroidDatabase) {
        super.createUserdataTables(androidDatabase)
        // create note_fts table for note and associated triggers
        createUserdataTriggers(androidDatabase)
    }

    override fun onUpgrade(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        val databaseName = androidDatabase.name
        Timber.i("Upgrading database [%s] from version %s to %s", databaseName, oldVersion, newVersion)
        when {
            databaseName == DatabaseManagerConst.GL_DATABASE_NAME -> onUpgradeGLDatabase(androidDatabase, oldVersion, newVersion)
            databaseName == DatabaseManagerConst.SEARCH_DATABASE_NAME -> onUpgradeSearchDatabase(androidDatabase, oldVersion, newVersion)
            databaseName.startsWith(DatabaseManagerConst.USERDATA_DATABASE_NAME) -> onUpgradeUserdataDatabase(androidDatabase, oldVersion, newVersion)
        }
    }

    override fun onUpgradeViews(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        // because user databases don't have exact database name match to USERDATA_DATABASE_NAME... override
        val databaseName = androidDatabase.name
        if (oldVersion != newVersion && databaseName.startsWith(DatabaseManagerConst.USERDATA_DATABASE_NAME)) {
            Timber.i("Upgrading database VIEWS [%s] from version %s to %s", androidDatabase.name, oldVersion, newVersion)
            dropUserdataViews(androidDatabase)
            createUserdataViews(androidDatabase)
        } else {
            // be sure to allow super to upgrade
            super.onUpgradeViews(androidDatabase, oldVersion, newVersion)
        }
    }

    private fun onUpgradeGLDatabase(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 16) {
            onCleanDatabase(androidDatabase)
        } else {
            when (oldVersion) {
                16 -> upgradeGLDatabaseVersion16(androidDatabase)
            }
        }
    }

    private fun upgradeGLDatabaseVersion16(androidDatabase: AndroidDatabase) {
        Timber.i("Upgrading version 16 database...")
        // create search suggestion goto table
        val database = androidDatabase.databaseWrapper
//        AndroidBaseManager.createTable(database, SearchSuggestionGotoConst.CREATE_TABLE)

        // remove old search history table, and replace with new one
        AndroidBaseManager.dropTable(database, SearchHistoryConst.DROP_TABLE)
        AndroidBaseManager.createTable(database, SearchHistoryConst.CREATE_TABLE)

        Timber.i("Upgrading version 16 database... DONE")
    }

    private fun onUpgradeUserdataDatabase(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        // handle userdata upgrades in AppUpgradeUtil
    }

    private fun onUpgradeSearchDatabase(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            Timber.i("Re-creating search database")
            onCleanDatabase(androidDatabase)
        }
    }

    fun initDatabaseConnection() {
        Timber.i("Initializing Database connections...")
        closeAllAndReset() // just in case this is a App.restart()... clear any existing database references
        try {
            getWritableDatabase(DatabaseManagerConst.GL_DATABASE_NAME)
        } catch (e: Exception) {
            Timber.e(e, "Failed to open GL database... attempting to recreate")
            cleanDatabase(DatabaseManagerConst.GL_DATABASE_NAME)
            getWritableDatabase(DatabaseManagerConst.GL_DATABASE_NAME)
        }
        try {
            getWritableDatabase(DatabaseManagerConst.SEARCH_DATABASE_NAME)
        } catch (e: Exception) {
            Timber.e(e, "Failed to open search database... attempting to recreate")
            cleanDatabase(DatabaseManagerConst.SEARCH_DATABASE_NAME)
            getWritableDatabase(DatabaseManagerConst.SEARCH_DATABASE_NAME)
        }

    }

    private fun createUserdataTriggers(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        AndroidBaseManager.createTable(database, NoteManager.CREATE_VIRTUAL_TABLE)
        createTrigger(database, NoteManager.CREATE_TRIGGER_BEFORE_UPDATE)
        createTrigger(database, NoteManager.CREATE_TRIGGER_BEFORE_DELETE)
        createTrigger(database, NoteManager.CREATE_TRIGGER_AFTER_UPDATE)
        createTrigger(database, NoteManager.CREATE_TRIGGER_AFTER_INSERT)
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    private fun createTrigger(db: DatabaseWrapper<*, *>?, sql: String) {
        if (db == null) {
            throw IllegalArgumentException("db cannot be null")
        }
        db.execSQL(sql)
    }

    fun diffUpgradeDatabase(androidDatabase: AndroidDatabase, diffFile: File): Boolean {
        if (!diffFile.exists()) {
            // Can't do the upgrade if there is no diff file
            return false
        }
        val database = androidDatabase.databaseWrapper

        try {
            database.beginTransaction()
            var statement = ""
            diffFile.forEachLine { line ->
                statement += line
                if (statement.endsWith(';')) {
                    AndroidBaseManager.executeSql(database, statement, false)
                    statement = ""
                } else {
                    // If the statement currently does not end with [;] then there must be multiple lines to the full statement.
                    // Make sure to keep the newline character (especially for things like image renditions)
                    statement += '\n'
                }
            }
            database.setTransactionSuccessful()
        } catch (e: Exception) {
            Timber.e(e, "Failed to apply catalog diff. File: [%s] Error: [%s]", diffFile.absolutePath, e.message)
            return false
        } finally {
            database.endTransaction()
        }
        return true
    }
}