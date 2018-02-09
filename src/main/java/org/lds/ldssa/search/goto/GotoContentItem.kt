package org.lds.ldssa.search.goto

enum class GotoContentItem constructor(val uri: String, val contentItemPosition: Int, val downloadExternalIdPart: String) {
    BOM("/scriptures/bofm", 1, "_scriptures_bofm"),
    DC("/scriptures/dc-testament", 2, "_scriptures_dc"),
    PGP("/scriptures/pgp", 3, "_scriptures_pgp"),
    NT("/scriptures/nt", 4, "_scriptures_nt"),
    OT("/scriptures/ot", 5, "_scriptures_ot"),
    HYMNS("/manual/hymns", 6, "_manual_hymns"),
    TG("/scriptures/tg", 7, "_scriptures_tg"),
    BD("/scriptures/bd", 8, "_scriptures_bd"),
    GS("/scriptures/gs", 9, "_scriptures_gs")
}
