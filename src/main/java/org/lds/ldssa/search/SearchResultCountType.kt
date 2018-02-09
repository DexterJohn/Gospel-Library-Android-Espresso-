package org.lds.ldssa.search

import android.support.annotation.StringRes
import org.lds.ldssa.R

enum class SearchResultCountType(@StringRes val listHeaderStringRes: Int) {
    EXACT_PHRASE(R.string.exact_phrase), KEYWORD(R.string.keywords);
}
