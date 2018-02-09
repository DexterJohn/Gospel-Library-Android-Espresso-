/*
 * NoteManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.note

import io.reactivex.Maybe
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.types.SearchSuggestionType
import org.lds.ldssa.util.UserdataDbUtil
import java.util.ArrayList
import javax.inject.Inject


@javax.inject.Singleton
class NoteManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : NoteBaseManager(databaseManager) {
    companion object {
        val NOTE_FTS_TABLE = NoteConst.TABLE + "_fts"
        // primary key for note fts table
        val NOTE_ROW_ID = "rowid"

        // used to create the note_fts table
        val CREATE_VIRTUAL_TABLE =
                "CREATE VIRTUAL TABLE IF NOT EXISTS " + NOTE_FTS_TABLE +
                        " USING fts4 (content=\"" + NoteConst.TABLE + "\"," +
                        NoteConst.C_TITLE + " TEXT NOT NULL," +
                        NoteConst.C_CONTENT + " TEXT NOT NULL," +
                        "tokenize=simple" +
                        ");"

        // database triggers to make sure data is consistent between note and note_fts
        private val TRIGGER_DELETE = "DELETE FROM $NOTE_FTS_TABLE WHERE docid=old.$NOTE_ROW_ID; END;"
        private val TRIGGER_INSERT = "INSERT INTO " + NOTE_FTS_TABLE +
                "(docid, " + NoteConst.C_TITLE + ", " + NoteConst.C_CONTENT + ") " +
                "VALUES(new." + NOTE_ROW_ID + ", new." + NoteConst.C_TITLE + ", new." + NoteConst.C_CONTENT + "); END;"

        val CREATE_TRIGGER_BEFORE_UPDATE =
                "CREATE TRIGGER IF NOT EXISTS " + NoteConst.TABLE + "_bu BEFORE UPDATE ON " + NoteConst.TABLE + " BEGIN " +
                        TRIGGER_DELETE

        val CREATE_TRIGGER_BEFORE_DELETE =
                "CREATE TRIGGER IF NOT EXISTS " + NoteConst.TABLE + "_bd BEFORE DELETE ON " + NoteConst.TABLE + " BEGIN " +
                        TRIGGER_DELETE

        val CREATE_TRIGGER_AFTER_UPDATE =
                "CREATE TRIGGER IF NOT EXISTS " + NoteConst.TABLE + "_au AFTER UPDATE ON " + NoteConst.TABLE + " BEGIN " +
                        TRIGGER_INSERT

        val CREATE_TRIGGER_AFTER_INSERT =
                "CREATE TRIGGER IF NOT EXISTS " + NoteConst.TABLE + "_ai AFTER INSERT ON " + NoteConst.TABLE + " BEGIN " +
                        TRIGGER_INSERT

        val FTS_REBUILD = "INSERT INTO $NOTE_FTS_TABLE($NOTE_FTS_TABLE) VALUES('rebuild')"
    }


    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findByAnnotationId(id: Long): Note? {
        return findBySelection("${NoteConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(id))
    }

    fun findByAnnotationIdRx(id: Long): Maybe<Note> {
        return findBySelectionRx("${NoteConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(id))
    }

    fun findCountByAnnotationId(annotationId: Long): Long {
        return findCountBySelection(NoteConst.C_ANNOTATION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun deleteAllByAnnotationId(annotationId: Long): Int {
        return delete(NoteConst.C_ANNOTATION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun isNoteEmpty(annotationId: Long): Boolean {
        if (findCountByAnnotationId(annotationId) == 0L) {
            return true
        }
        return findCountBySelection("ifnull(length(" + NoteConst.C_TITLE + "), 0) == 0 AND ifnull(length(" + NoteConst.C_CONTENT + "), 0) == 0 AND " +
                NoteConst.C_ANNOTATION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(annotationId)) > 0
    }

    fun findAllSearchSuggestions(searchText: String, limit: Int): List<SearchSuggestion> {
        val suggestions = ArrayList<SearchSuggestion>()

        val notes = findAllByRawQuery("SELECT * FROM " + NoteConst.TABLE + " WHERE " + NoteConst.C_TITLE + " LIKE ? LIMIT ?",
                SQLQueryBuilder.toSelectionArgs("%$searchText%", limit))

        for (note in notes) {
            val searchSuggestion = SearchSuggestion()
            searchSuggestion.type = SearchSuggestionType.NOTE
            searchSuggestion.id = note.annotationId

            val title = note.title
            if (title != null) {
                searchSuggestion.title = title
            }

            suggestions.add(searchSuggestion)
        }

        return suggestions
    }
}