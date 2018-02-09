/*
 * Tag.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.tag


class Tag : TagBaseRecord() {

    override var name: String

        get() = super.name
        set(value) {super.name = fixName(value).take(NAME_MAX_LENGTH)}

    companion object {
        const val NAME_MAX_LENGTH = 256
        const val ILLEGAL_CHAR = ','  // tag titles cannot have a comma in it

        /**
         * Make sure there are no invalid characters
         */
        fun fixName(name: String): String {
            return name.replace(ILLEGAL_CHAR.toString(), "")
        }
    }
}