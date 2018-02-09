package org.lds.ldssa.intent

import org.lds.ldssa.analytics.Analytics


class ContentIntentParams (val screenId: Long,
                                val contentItemId: Long,
                                var subItemId: Long,
                                val referrer: Analytics.Referrer) {
    constructor(screenId: Long,
                contentItemId: Long,
                subItemUri: String? = null,
                referrer: Analytics.Referrer) : this(screenId, contentItemId, -1, referrer) {
        this.subItemUri = subItemUri
    }

    var subItemUri: String? = null

    var scrollPosition: Int = 0
    var scrollToParagraphAid: String? = null
    var markParagraphAids: Array<String?>? = null
    var findOnPageText: String? = null
    var markTextOffsetSqliteOffsets: String? = null
    var markTextOffsetSqliteExactPhrase: Boolean = false
    var fromClickOnScreen: Boolean = false
    var isFromWidget: Boolean = false
    var startNewTask: Boolean = false
    var showRootCatalogOnFail: Boolean = false

}