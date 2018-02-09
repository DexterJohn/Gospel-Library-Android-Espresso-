package org.lds.ldssa.model.navigation


class DtoNavigationHistoryItemExtra {
    var key = ""
    var value = ""

    constructor()

    constructor(key: String, value: String) {
        this.key = key
        this.value = value
    }

    constructor(key: String, value: Int) {
        this.key = key
        this.value = value.toString()
    }

    constructor(key: String, value: Long) {
        this.key = key
        this.value = value.toString()
    }

    constructor(key: String, value: Float) {
        this.key = key
        this.value = value.toString()
    }

    constructor(key: String, value: Double) {
        this.key = key
        this.value = value.toString()
    }

    constructor(key: String, value: Boolean) {
        this.key = key
        this.value = value.toString()
    }

    val valueAsInt: Int
        get() = Integer.parseInt(value)

    val valueAsLong: Long
        get() = java.lang.Long.parseLong(value)

    val valueAsFloat: Float
        get() = java.lang.Float.parseFloat(value)

    val valueAsDouble: Double
        get() = java.lang.Double.parseDouble(value)

    val valueAsBoolean: Boolean
        get() = java.lang.Boolean.parseBoolean(value)

}
