package org.lds.ldssa.model.prefs.type

import org.lds.ldssa.R

enum class NotebookSortType {
    ALPHABETICAL,
    MOST_RECENT,
    COUNT;

    companion object {
        @JvmStatic
        fun getByOrdinal(ordinal: Int): NotebookSortType {
            for (type in values()) {
                if (type.ordinal == ordinal) {
                    return type
                }
            }

            return MOST_RECENT
        }

        fun getSortTypeFromMenuId(menuId: Int): NotebookSortType {
            return when (menuId) {
                R.id.notebook_selection_menu_sort_name -> NotebookSortType.ALPHABETICAL
                R.id.notebook_selection_menu_sort_recent -> NotebookSortType.MOST_RECENT
                R.id.notebook_selection_menu_sort_count -> NotebookSortType.COUNT
                else -> NotebookSortType.MOST_RECENT
            }
        }

        fun getSortMenuItemId(sortType: NotebookSortType): Int {
            return when (sortType) {
                NotebookSortType.ALPHABETICAL -> R.id.notebook_selection_menu_sort_name
                NotebookSortType.MOST_RECENT -> R.id.notebook_selection_menu_sort_recent
                NotebookSortType.COUNT -> R.id.notebook_selection_menu_sort_count
            }
        }
    }
}
