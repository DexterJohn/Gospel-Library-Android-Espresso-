package org.lds.ldssa.model.navigation

import com.google.gson.Gson
import java.util.ArrayList
import java.util.Arrays

abstract class NavigationHistoryItemExtras {

    abstract fun getExtras(): List<DtoNavigationHistoryItemExtra>
    abstract fun setExtras(extrasList: List<DtoNavigationHistoryItemExtra>)

    fun serialize(gson: Gson): String {
        try {
            return gson.toJson(getExtras())
        } catch (e: Exception) {
            throw IllegalStateException("Failed to create json", e)
        }
    }

    fun deserialize(gson: Gson, json: String) {
        try {
            setExtras(ArrayList(Arrays.asList(*gson.fromJson(json, Array<DtoNavigationHistoryItemExtra>::class.java))))
        } catch (e: Exception) {
            throw IllegalStateException("Failed to parse json", e)
        }
    }
}
