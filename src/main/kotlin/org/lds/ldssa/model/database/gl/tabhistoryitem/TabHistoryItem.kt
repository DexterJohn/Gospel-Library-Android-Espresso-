/*
 * TabHistoryItem.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.gl.tabhistoryitem

import com.google.gson.Gson
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras

@Deprecated("Use ScreenHistoryItem")
class TabHistoryItem : TabHistoryItemBaseRecord() {

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
        return "TabHistoryItemBaseRecord{id=$id, tabId=$tabId, historyPosition=$historyPosition, sourceType=$sourceType, title='$title', description='$description', extras='$extrasJson'}"
    }

}