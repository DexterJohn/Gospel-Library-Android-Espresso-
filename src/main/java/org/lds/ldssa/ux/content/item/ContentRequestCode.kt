package org.lds.ldssa.ux.content.item

// startActivityForResult in Activity or Fragment
enum class ContentRequestCode {
    REQUEST_UNKNOWN,
    REQUEST_BOOKMARK,
    REQUEST_NOTE,
    REQUEST_NOTEBOOK_SELECTION,
    REQUEST_TAG,
    REQUEST_LINK,
    REQUEST_SELECT_HIGHLIGHT,
    REQUEST_SELECT_STICKY,
    REQUEST_APPLICATION_SHARE,
    REQUEST_HIGHLIGHT_PALETTE;

    companion object {
        @JvmStatic
        fun getCode(requestCode: Int): ContentRequestCode {
            if (requestCode <= ContentRequestCode.values().size) {
                return ContentRequestCode.values()[requestCode]
            }

            return REQUEST_UNKNOWN
        }
    }
}