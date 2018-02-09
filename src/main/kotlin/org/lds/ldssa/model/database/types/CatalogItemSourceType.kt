package org.lds.ldssa.model.database.types

enum class CatalogItemSourceType {
    UNKNOWN, // should never be 0
    DEFAULT, // most content is 1
    SECURE, // Role based content
    FOREIGN // iOS supported foreign (third-party) content
}
