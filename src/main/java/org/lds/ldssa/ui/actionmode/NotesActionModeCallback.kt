package org.lds.ldssa.ui.actionmode

import android.content.Context
import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import org.lds.ldssa.R
import org.lds.mobile.ui.util.LdsDrawableUtil

class NotesActionModeCallback(val context: Context?) : ActionMode.Callback {

    var actionModeVisible = false
        private set

    private var actionMenu: Menu? = null
    private var actionMode: ActionMode? = null

    var actionModeStarted: () -> Unit = { }
    var actionModeEnded: () -> Unit = { }
    var actionModeMenuItemSelected: (menuItem: MenuItem) -> Boolean = { false }
    var actionModeSelectionCount: () -> Int = { 0 }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        actionModeVisible = true

        actionModeStarted()
        mode.setTitle(R.string.select_items)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        actionMenu = menu
        actionMode = mode
        updateMenu()

        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return actionModeMenuItemSelected(item)
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        actionModeVisible = false

        actionModeEnded()
    }

    fun updateMenu() {
        actionMenu?.clear()
        actionMode?.menuInflater?.inflate(R.menu.action_mode_notebook, actionMenu)

        actionMenu?.let { menu ->
            val selectedCount = actionModeSelectionCount()
            menu.findItem(R.id.menu_item_delete).isVisible = selectedCount > 0
            menu.findItem(R.id.menu_item_merge).isVisible = selectedCount > 1
            context?.let {
                LdsDrawableUtil.tintAllMenuIconsForTheme(it, menu, R.attr.themeStyleColorToolbarActionModeIcon)
            }
        }
    }
}
