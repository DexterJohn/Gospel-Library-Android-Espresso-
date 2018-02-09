/*
 * Link.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.link

import org.apache.commons.lang3.StringUtils
import java.util.ArrayList
import java.util.Arrays


class Link : LinkBaseRecord() {

    val NAME_MAX_LENGTH = 500

    /**
     * WARNING.... should ONLY be called from DTO (because paragraphId could be comma separated values).  Use getParagraphAids() instead
     * @return comma separated list of paragraphAids
     */
    override var paragraphAid: String?
        get() = super.paragraphAid
        set(value) {super.paragraphAid = value}

    fun getParagraphAids(): List<String> {
        val aid = paragraphAid
        if (aid != null) {
            return Arrays.asList(*aid.trim { it <= ' ' }.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) // remove all spaces that might exist
        } else {
            return ArrayList()
        }
    }

    fun setParagraphAids(paragraphAids: Collection<String>) {
        paragraphAid = StringUtils.join(paragraphAids, ",")
    }

    override var name: String
        get() = super.name
        set(value) {super.name = value.take(NAME_MAX_LENGTH)}
}