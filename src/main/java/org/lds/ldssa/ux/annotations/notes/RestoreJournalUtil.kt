package org.lds.ldssa.ux.annotations.notes

import android.app.Application
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebook.Notebook
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import javax.inject.Inject
import javax.inject.Singleton

@Deprecated("remove this when we feel that all legacy journal entries are converted")
@Singleton
class RestoreJournalUtil
@Inject constructor(private val application: Application,
                    private val annotationManager: AnnotationManager,
                    private val notebookManager: NotebookManager) {

    /**
     * @return true if migration occurred
     */
    fun migrateLegacyJournalItems(): Long {
        val legacyJournalAnnotationIds = annotationManager.findLegacyJournalAnnotationIds()

        if (legacyJournalAnnotationIds.isEmpty()) {
            return 0
        }

        annotationManager.beginTransaction()

        // determine a unique journal notebook name
        var journalNotebookName = application.getString(R.string.journal)
        val journalNotebook = Notebook()
        var nameCount = 1
        while (notebookManager.findNotebookNameCount(journalNotebookName) > 0) {
            journalNotebookName = application.getString(R.string.journal) + " " + nameCount
            nameCount++
        }

        // Create "Journal" notebook
        journalNotebook.name = journalNotebookName
        notebookManager.save(journalNotebook)


        // Move legacy journal annotations to "Journal" notebook
        var annotationMigrationCount: Long = 0
        for (annotationId in legacyJournalAnnotationIds) {
            val annotationUniqueId = annotationManager.findUniqueIdById(annotationId)
            annotationManager.moveToNotebook(annotationId, annotationUniqueId, journalNotebook.id)
            annotationMigrationCount++
        }

        annotationManager.endTransaction(true)

        return annotationMigrationCount
    }
}
