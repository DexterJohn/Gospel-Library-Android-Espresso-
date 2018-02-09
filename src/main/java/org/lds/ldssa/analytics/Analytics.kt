package org.lds.ldssa.analytics

import android.app.DownloadManager

import org.lds.mobile.util.LdsNetworkUtil

interface Analytics {
    fun upload()
    fun setDimensions(dimensions: List<String>)
    fun postEvent(eventId: String, attributes: Map<String, String>)
    fun postEvent(eventId: String)
    fun postScreen(screen: String)

    object Event {
        const val ANNOTATION_CHANGED = "Annotation Changed"
        const val AUDIO_FAILED = "Audio Failed"
        const val AUTHENTICATION_FAILED = "Authentication Failed"
        const val BOOKMARK_CHANGED = "Bookmark Changed"
        const val CONTENT_ANNOTATED = "Content Annotated"
        const val CONTENT_SHARED = "Content Shared"
        const val CONTENT_VIEWED = "Content Viewed"
        const val INVALID_LINK_SELECTED = "Invalid Link Selected"
        const val ITEM_INSTALLED = "Item Installed"
        const val ITEM_UNINSTALLED = "Item Uninstalled"
        const val ITEM_UPDATED = "Item Updated"
        const val LINK_CHANGED = "Link Changed"
        const val MARK_CHANGED = "Mark Changed"
        const val NOTE_CHANGED = "Note Changed"
        const val NOTEBOOK_CHANGED = "Notebook Changed"
        const val NOTES_TAB_SELECTED = "Notes Tab Selected"
        const val SEARCH_OPENED = "Search Opened"
        const val SEARCH_PERFORMED = "Search Performed"
        const val SIDEBAR_PIN_CHANGED = "Sidebar Pin Changed"
        const val SWITCH_TAB = "Switch Screens"
        const val SYNC_FAILED = "Sync Failed"
        const val TAG_CHANGED = "Tag Changed"
        const val TEXT_TO_SPEECH_LISTENED = "Text-to-speech listened"
        const val TIP_VIEWED = "Tip Viewed"
        const val VIDEO_FAILED = "Video Failed"
    }

    object Screen {
        const val ABOUT_VIEW = "About View"
        const val ANNOTATIONS = "Annotations"
        const val ANNOTATION_VIEW = "Annotation View"
        const val APP_INFO = "App Info"
        const val CATALOG_DIRECTORY_VIEW = "Catalog Directory"
        const val CONTENT_DIRECTORY_VIEW = "Content Directory"
        const val CONTENT_VIEW = "Content View"
        const val CONTENT_SOURCE = "Content Source"
        const val CURRENT_DOWNLOADS = "Current Downloads"
        const val CUSTOM_COLLECTION_DIRECTORY = "Custom Collection Directory"
        const val DOWNLOADED_MEDIA = "Downloaded Media"
        const val HIGHLIGHT_PALETTE = "Highlight Palette"
        const val HIGHLIGHT_SELECTION = "Highlight Selection"
        const val IMAGE_VIEW = "Image View"
        const val LINK_CONTENT = "Link Content"
        const val LINKS = "Links"
        const val LOCATIONS = "Locations"
        const val MANAGE_CUSTOM_COLLECTIONS = "Manage Custom Collections"
        const val NOTE_EDIT = "Note Edit"
        const val NOTE_SELECTION = "Note Selection"
        const val NOTES = "Notes"
        const val SEARCH = "Search"
        const val SEARCH_RESULTS = "Search Results"
        const val SETTINGS = "Settings"
        const val SHARE_CONTENT = "Share Content"
        const val SIGN_IN_VIEW = "Sign In View"
        const val TAG_SELECTION = "Tag Selection"
        const val TIPS_VIEW = "Tips View"
        const val STUDY_PLANS = "Study Plans"
        const val STUDY_PLAN_ITEMS = "Study Plan Items"
        const val VIDEO_VIEW = "Video View"
        const val WELCOME_VIEW = "Welcome View"
    }

    object Attribute {
        const val ANNOTATION_TYPE = "Annotation Type"
        const val CHANGE_TYPE = "Change Type"
        const val COLOR_SCHEME_VARIATION = "Color Scheme Variation"
        const val CONTENT_FONT = "Content Font"
        const val CONTENT_GROUP = "Content Group"
        const val CONTENT_LANGUAGE = "Content Language"
        const val CONTENT_LANGUAGE_CODE = "Content Language Code"
        const val CONTENT_SCOPE = "Content Scope"
        const val CONTENT_SIZE_CATEGORY = "Content Size Category"
        const val CONTENT_TYPE = "Content Type"
        const val CONTENT_URI = "Content URI"
        const val CONTENT_VERSION = "Content Version"
        const val DESTINATION_CONTENT_LANGUAGE = "Destination Content Language"
        const val DESTINATION_URI = "Destination URI"
        const val DEVICE_MODEL = "Device Model"
        const val DEVICE_OS = "Device OS"
        const val DURATION_VIEWED_GROUP = "Duration Viewed Group"
        const val ERROR = "Error"
        const val ERROR_CODE = "Error Code"
        const val ERROR_EXTRA = "Error Extra"
        const val INTERNET_CONNECTION = "Internet Connection"
        const val ITEM_URI = "Item URI"
        const val MARKING_COLOR = "Marking Color"
        const val MARKING_STYLE = "Marking Style"
        const val PERCENTAGE_VIEWED = "Percentage Viewed"
        const val PERCENTAGE_VIEWED_GROUP = "Percentage Viewed Group"
        const val REASON = "Reason"
        const val REFERRER = "Referrer"
        const val SEARCH_STRING = "Search String"
        const val SECONDS_VIEWED = "Seconds Viewed"
        const val SIDEBAR_PIN_STATUS = "Sidebar Pin Status"
        const val SHARE_TYPE = "Share Type"
        const val SOURCE_TYPE = "Source Type"
        const val SOURCE_URI = "Source URI"
        const val TAB = "Tab"
        const val TITLE = "Title"
        const val URI = "URI"
        const val URL = "URL"
    }

    object Value {
        const val ADD_TO_NOTEBOOK = "Add To Notebook"
        const val AUDIO = "Audio"
        const val AUDIO_AUTO_ADVANCE = "Audio Auto Advance"
        const val BACK = "Back"
        const val BOOKMARK = "Bookmark"
        const val CONTENT_EXTERNAL_LINK = "Content External Link"
        const val CONTENT_INLINE_LINK = "Content Inline Link"
        const val COPIED = "Copied"
        const val CREATE = "Create"
        const val DELETE = "Delete"
        const val DOWNLOADED_MEDIA = "Downloaded Media"
        const val EXIT_LINK_MODE = "Exit Link Mode"
        const val EXIT_NOTE_MODE = "Exit Note Mode"
        const val EXIT_NOTEBOOK_MODE = "Exit Notebook Mode"
        const val EXIT_TAG_MODE = "Exit Tag Mode"
        const val EXTERNAL_LINK = "External Link"
        const val FORWARD = "Forward"
        const val HIGHLIGHT = "Highlight"
        const val HISTORY = "History"
        const val IMAGE = "Image"
        const val INCORRECT_USERNAME_OR_PASSWORD = "Incorrect Username and/or Password"
        const val ITEM_GO_TO = "Item Go To"
        const val ITEM_NAVIGATION = "Item Navigation"
        const val LANGUAGE_CHANGED = "Language Changed"
        const val LINK = "Link"
        const val LINKED_TO = "Linked To"
        const val LOCAL = "Local"
        const val MARK = "Mark"
        const val MEDIA_PLAYBACK_NOTIFICATION = "Media Playback Notification"
        const val NETWORK_CELLULAR = "Cellular"
        const val NETWORK_NONE = "None"
        const val NETWORK_UNAVAILABLE = "Network Unavailable"
        const val NETWORK_WIFI = "Wifi"
        const val NFC = "NFC"
        const val NO_INTERNET = "No Internet"
        const val NOTE = "Note"
        const val NOTEBOOK_ANNOTATION_LINK = "Notebook Annotation Link"
        const val NOTEBOOK_BOOKMARK_LINK = "Notebook Bookmark Link"
        const val NOTEBOOK_MARK_LINK = "Notebook Mark Link"
        const val NOTEBOOK_PAGE_LINK = "Notebook Page Link"
        const val NOTEBOOK_TAG_LINK = "Notebook Tag Link"
        const val NOTEBOOKS = "Notebooks"
        const val OTHER_INTERNET = "Other Internet"
        const val PINNED = "Pinned"
        const val PLAY_BUTTON = "Play Button"
        const val ALL = "ALL"
        const val REFRESH_UI = "Refresh UI"
        const val RELATED_CONTENT_ANNOTATION_LINK = "Related Content Annotation Link"
        const val RELATED_CONTENT_LINK = "Related Content Link"
        const val RELATED_CONTENT_PAGE_LINK = "Related Content Page Link"
        const val RELATED_CONTENT_TAG_LINK = "Related Content Tag Link"
        const val REMOTE = "Remote"
        const val RESTORE_STATE = "Restore State"
        const val SANS_SERIF = "Sans-serif"
        const val SCRIPTURE_GO_TO = "Scripture Go To"
        const val SEARCH = "Search"
        const val SEARCH_RESULT = "Search Result"
        const val SEND_INTENT = "Send Intent"
        const val SERIF = "Serif"
        const val SIZE_1 = "Extra Small"
        const val SIZE_2 = "Small"
        const val SIZE_3 = "Medium"
        const val SIZE_4 = "Large"
        const val SIZE_5 = "Extra Large"
        const val SIZE_6 = "Extra Extra Large"
        const val SIZE_7 = "Extra Extra Extra Large"
        const val SNIPPET = "Snippet"
        const val STATUS_CANCELLED = "Cancelled"
        const val STATUS_FAILURE = "Failure"
        const val STATUS_SUCCESS = "Success"
        const val STATUS_BAR_NOTIFICATION = "Status Bar Notification"
        const val SWIPE = "Swipe"
        const val SWITCH_SCREENS = "Switch Screens"
        const val TAG = "Tag"
        const val TAGS = "Tags"
        const val TEXT = "Text"
        const val UNDERLINE = "Underline"
        const val UNKNOWN = "Unknown"
        const val UNPINNED = "Unpinned"
        const val UPDATE = "Update"
        const val VIDEO = "Video"

        private const val MILLIS_PER_SECOND = 1000.0

        @JvmStatic
        fun millisToSeconds(millis: Long): String {
            return (millis / MILLIS_PER_SECOND).toString()
        }

        fun getNetwork(networkUtil: LdsNetworkUtil): String {
            if (networkUtil.isConnected(false)) {
                return NETWORK_WIFI
            } else if (networkUtil.isConnected()) {
                return NETWORK_CELLULAR
            }
            return NETWORK_NONE
        }

        fun getDownloadStatus(status: Int): String {
            when (status) {
                DownloadManager.STATUS_SUCCESSFUL -> return STATUS_SUCCESS
                DownloadManager.STATUS_FAILED -> return STATUS_FAILURE
                else -> return STATUS_CANCELLED
            }
        }
    }

    object Dimension {
        const val WEEK_DAY_MON_SAT = "Monday through Saturday"
        const val WEEK_DAY_SUNDAY = "Sunday"
    }

    enum class Referrer constructor(val value: String) {
        UNKNOWN(Value.UNKNOWN),
        AUDIO_AUTO_ADVANCE(Value.AUDIO_AUTO_ADVANCE),
        BACK(Value.BACK),
        BOOKMARK(Value.BOOKMARK),
        CONTENT_INLINE_LINK(Value.CONTENT_INLINE_LINK),
        DOWNLOADED_MEDIA(Value.DOWNLOADED_MEDIA),
        EXIT_LINK_MODE(Value.EXIT_LINK_MODE),
        EXIT_NOTE_MODE(Value.EXIT_NOTE_MODE),
        EXIT_NOTEBOOK_MODE(Value.EXIT_NOTEBOOK_MODE),
        EXIT_TAG_MODE(Value.EXIT_TAG_MODE),
        EXTERNAL_LINK(Value.EXTERNAL_LINK),
        HISTORY(Value.HISTORY),
        ITEM_GO_TO(Value.ITEM_GO_TO),
        ITEM_NAVIGATION(Value.ITEM_NAVIGATION),
        LANGUAGE_CHANGED(Value.LANGUAGE_CHANGED),
        MEDIA_PLAYBACK_NOTIFICATION(Value.MEDIA_PLAYBACK_NOTIFICATION),
        NOTEBOOK_ANNOTATION_LINK(Value.NOTEBOOK_ANNOTATION_LINK),
        NOTEBOOK_BOOKMARK_LINK(Value.NOTEBOOK_BOOKMARK_LINK),
        NOTEBOOK_MARK_LINK(Value.NOTEBOOK_MARK_LINK),
        NOTEBOOK_PAGE_LINK(Value.NOTEBOOK_PAGE_LINK),
        NOTEBOOK_TAG_LINK(Value.NOTEBOOK_TAG_LINK),
        PLAY_BUTTON(Value.PLAY_BUTTON),
        RELATED_CONTENT_ANNOTATION_LINK(Value.RELATED_CONTENT_ANNOTATION_LINK),
        RELATED_CONTENT_LINK(Value.RELATED_CONTENT_LINK),
        RELATED_CONTENT_PAGE_LINK(Value.RELATED_CONTENT_PAGE_LINK),
        RELATED_CONTENT_TAG_LINK(Value.RELATED_CONTENT_TAG_LINK),
        RESTORE_STATE(Value.RESTORE_STATE),
        SCRIPTURE_GO_TO(Value.SCRIPTURE_GO_TO),
        SEARCH(Value.SEARCH_RESULT),
        SEARCH_RESULT(Value.SEARCH_RESULT),
        SWIPE(Value.SWIPE),
        SWITCH_SCREENS(Value.SWITCH_SCREENS)
    }

    enum class AnnotationType constructor(val value: String) {
        MARK(Value.MARK),
        BOOKMARK(Value.BOOKMARK),
        NOTE(Value.NOTE),
        TAG(Value.TAG),
        LINK(Value.LINK),
        LINKED_TO(Value.LINKED_TO)
    }

    enum class ChangeType constructor(val value: String) {
        CREATE(Value.CREATE),
        UPDATE(Value.UPDATE),
        DELETE(Value.DELETE)
    }
}
