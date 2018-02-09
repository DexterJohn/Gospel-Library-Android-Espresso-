package org.lds.ldssa.model.prefs.type

import org.lds.ldssa.R

enum class TagSortType {
    ALPHABETICAL,
    MOST_RECENT,
    COUNT;


    companion object {
        @JvmStatic
        fun getByOrdinal(ordinal: Int): TagSortType {
            for (type in values()) {
                if (type.ordinal == ordinal) {
                    return type
                }
            }

            return MOST_RECENT
        }

        fun getSortTypeFromMenuId(menuId: Int): TagSortType {
            return when (menuId) {
                R.id.menu_item_tag_sort_alphabetical -> TagSortType.ALPHABETICAL
                R.id.menu_item_tag_sort_count -> TagSortType.COUNT
                R.id.menu_item_tag_sort_recent -> TagSortType.MOST_RECENT
                else -> TagSortType.MOST_RECENT
            }
        }

        fun getSortMenuItemId(sortType: TagSortType): Int {
            return when (sortType) {
                TagSortType.ALPHABETICAL -> R.id.menu_item_tag_sort_alphabetical
                TagSortType.COUNT -> R.id.menu_item_tag_sort_count
                TagSortType.MOST_RECENT -> R.id.menu_item_tag_sort_recent
            }
        }
    }
}
