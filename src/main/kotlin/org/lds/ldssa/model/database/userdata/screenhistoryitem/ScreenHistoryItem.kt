/*
 * ScreenHistoryItem.kt
 *
 * Created: 10/16/2017 12:03:55
 */



package org.lds.ldssa.model.database.userdata.screenhistoryitem

import com.google.gson.Gson
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras


class ScreenHistoryItem  : ScreenHistoryItemBaseRecord() {
    fun setExtras(gson: Gson, extras: NavigationHistoryItemExtras) {
        extrasJson = extras.serialize(gson)
    }

    fun <T : NavigationHistoryItemExtras> getExtras(gson: Gson, type: Class<T>): T {
        try {
            val extras = type.newInstance()
            extras.deserialize(gson, extrasJson)
            return type.cast(extras)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to create extras", e)
        }

    }

    override fun toString(): String {
        return "ScreenHistoryItemBaseRecord{id=$id, screenId=$screenId, historyPosition=$historyPosition, sourceType=$sourceType, title='$title', description='$description', extras='$extrasJson'}"
    }
}