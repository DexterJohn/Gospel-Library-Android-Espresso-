package org.lds.ldssa.model.database.gl

import org.lds.ldssa.model.database.types.ItemMediaType

/**
 * An interface used to reference the base methods used in
 * the download related items.  This is used when filtering
 * out already downloaded items, etc.
 */
// todo move to a different package
interface Downloadable {
    val type: ItemMediaType
    val tertiaryId: String?
    val contentItemId: Long
    val subItemId: Long
}
