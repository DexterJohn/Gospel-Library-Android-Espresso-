package org.lds.ldssa.model.database.types

import android.support.annotation.DrawableRes

import org.lds.ldssa.R

enum class SearchSuggestionType constructor(// search for raw text
        @DrawableRes val preIconRes: Int,
        val isDirectLinkable: Boolean // this type of item can have link created for it (1 Nephi 3, 1 Nephi 3:7, etc)
) {
    UNKNOWN(R.drawable.ic_lds_book_24dp, false),
    FIND_ON_PAGE(R.drawable.ic_lds_action_search_24dp, false),
    FIND_IN_CONTENT_ITEM(R.drawable.ic_lds_action_search_24dp, false),
    FIND_IN_LIBRARY_COLLECTION(R.drawable.ic_lds_action_search_24dp, false),
    FIND_IN_NAV_COLLECTION(R.drawable.ic_lds_action_search_24dp, false),
    HISTORY(R.drawable.ic_lds_history_24dp, false),
    CATALOG_COLLECTION(R.drawable.ic_lds_book_24dp, false), // Scriptures, Leaders
    CONTENT_ITEM(R.drawable.ic_lds_book_24dp, false), // Book of Mormon, October Ensign, Primary 2

    GOTO_NAV_COLLECTION(R.drawable.ic_lds_action_forward_arrow_24dp, false), // 1 Nephi, Alma, etc
    GOTO_SUB_ITEM(R.drawable.ic_lds_action_forward_arrow_24dp, true), // Omni, 4 Nephi

    NOTEBOOK(R.drawable.ic_lds_notebook_24dp, false),
    NOTE(R.drawable.ic_lds_note_24dp, false),

    SEARCH_FOR(R.drawable.ic_lds_action_search_24dp, false)
}
