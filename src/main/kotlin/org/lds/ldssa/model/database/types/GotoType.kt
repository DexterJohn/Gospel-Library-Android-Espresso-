package org.lds.ldssa.model.database.types

enum class GotoType {
    UNKNOWN,
    NAV_COLLECTION, // Items with sub-chapters(1 Nephi, Alma, etc)
    NAV_ITEM // Items that don't have sub-chapters(Enos, 4 Nephi, etc)
}
