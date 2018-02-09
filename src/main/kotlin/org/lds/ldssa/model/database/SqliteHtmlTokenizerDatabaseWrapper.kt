package org.lds.ldssa.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.database.SQLiteDatabaseWrapper
import org.lds.ldssa.util.ContentItemUtil
import org.sqlite.database.enums.Tokenizer

/**
 * Special wrapper for a custom version of SQLite that has built in support of a html tokenizer
 */
class SqliteHtmlTokenizerDatabaseWrapper(androidDatabase: AndroidDatabase) : SQLiteDatabaseWrapper(androidDatabase.path) {
    private var libraryLoaded = false

    init {

        // Content databases only need the tokenizer extension loaded
        // User data databases only need the pcre extension loaded
        if (androidDatabase.name.startsWith(ContentItemUtil.CONTENT_DATABASE_PREFIX)) {
            loadLibrary()
        }
    }

    private fun loadLibrary() {
        if (!libraryLoaded) {
            // "sqliteX" already loaded in super class
            System.loadLibrary("tokenizers")

            database.loadExtension("libtokenizers")
            database.registerTokenizer(Tokenizer.HTML_TOKENIZER)

            libraryLoaded = true
        }
    }
}
