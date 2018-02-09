package org.lds.ldssa.util

import android.support.v4.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import org.apache.commons.lang3.StringUtils
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebook.Notebook
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Utility class to help simplify interaction with
 * the Notebook database so that we only have one
 * point of entry.
 */
@Singleton
class NotebookUtil @Inject
constructor(private val annotationManager: AnnotationManager, private val notebookManager: NotebookManager) {

    fun promptNotebookMerge(activity: FragmentActivity?, notebookIds: List<Long>) {
        activity ?: return
        MaterialDialog.Builder(activity)
                .title(R.string.merged_notebook_name)
                .content(R.string.merged_notebook_name_prompt)
                .positiveText(R.string.merge)
                .negativeText(R.string.cancel)
                .inputRange(0, Notebook.NAME_MAX_LENGTH)
                .input(null, null) { _, input ->
                    if (StringUtils.isNotBlank(input)) {
                        notebookManager.mergeNotebooks(annotationManager, input.toString(), notebookIds)
                    }
                }
                .show()
    }

    /**
     * Prompts the user for adding a new Notebook
     *
     * @param activity The activity to display the dialog with
     */
    fun promptAdd(activity: FragmentActivity) {
        MaterialDialog.Builder(activity)
                .title(R.string.create_notebook)
                .positiveText(R.string.add)
                .negativeText(R.string.cancel)
                .inputRange(0, Notebook.NAME_MAX_LENGTH)
                .input(null, null) { _, input ->
                    if (StringUtils.isNotBlank(input)) {
                        val newNotebook = Notebook()
                        newNotebook.name = input.toString()
                        notebookManager.save(newNotebook)
                    }
                }
                .show()
    }

    /**
     * Prompts the user for edit the specified Notebook
     *
     * @param activity The activity to display the dialog with
     */
    fun promptEdit(activity: FragmentActivity?, notebookId: Long) {
        activity ?: return
        val notebook = notebookManager.findByRowId(notebookId) ?: return

        MaterialDialog.Builder(activity)
                .title(R.string.edit_notebook)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .inputRange(0, Notebook.NAME_MAX_LENGTH)
                .input(null, notebook.name) { _, input ->
                    if (StringUtils.isNotBlank(input)) {
                        notebook.name = input.toString()
                        notebook.lastModified = LocalDateTime.now()
                        notebookManager.save(notebook)
                    }
                }
                .show()
    }

    /**
     * Prompts the user to delete a list of notebooks
     *
     * @param activity The activity to display the dialog with
     * @param notebookIds the id list of the notebooks to delete
     */
    fun promptDelete(activity: FragmentActivity?, notebookIds: List<Long>, postDelete: () -> Unit = {}) {
        if (activity == null || notebookIds.isEmpty()) {
            return
        }

        MaterialDialog.Builder(activity)
                .title(R.string.delete_notebooks_title)
                .content(R.string.delete_notebooks_message)
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive { _, _ ->
                    notebookManager.trashByIds(annotationManager, notebookIds)
                    postDelete()
                }
                .show()
    }
}