package org.lds.ldssa.ui.menu

import android.content.Context
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import org.lds.ldssa.R
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.languagename.LanguageName
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager
import org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageManager
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.ui.util.LdsDrawableUtil
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonMenu @Inject
constructor(
        private val internalIntents: InternalIntents,
        private val languageNameManager: LanguageNameManager,
        private val recentLanguageManager: RecentLanguageManager,
        private val languageUtil: LanguageUtil
) {

    fun onCreateOptionsMenu(context: Context, menu: Menu, inflater: MenuInflater) {
        inflateMenuPre(context, menu, inflater)
        inflateMenuPost(context, menu, inflater)
    }

    fun inflateMenuPre(context: Context, menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_common_pre, menu)
        LdsDrawableUtil.tintAllMenuIconsForTheme(context, menu, R.attr.themeStyleColorToolbarIcon)
    }

    fun inflateMenuPost(context: Context, menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_common_post, menu)
        LdsDrawableUtil.tintAllMenuIconsForTheme(context, menu, R.attr.themeStyleColorToolbarIcon)
    }

    fun onOptionsItemSelected(baseActivity: BaseActivity, item: MenuItem, optionsMenu: Menu, languageId: Long, uri: String?, glContentContext: GLContentContext): Boolean {
        return onGeneralOptionsItemSelected(baseActivity, item) || onActionOptionsItemSelected(baseActivity, item, optionsMenu, languageId, uri, glContentContext)
    }

    private fun onActionOptionsItemSelected(baseActivity: BaseActivity, item: MenuItem, optionsMenu: Menu, languageId: Long, uri: String?, glContentContext: GLContentContext): Boolean {
        when (item.itemId) {
            R.id.menu_item_search -> {
                internalIntents.showSearchActivity(baseActivity, baseActivity.getScreenId(), glContentContext = glContentContext)
                return true
            }
            R.id.menu_item_bookmark -> {
                internalIntents.showBookmarks(baseActivity, baseActivity.getScreenId())
                return true
            }
            R.id.menu_item_language -> {
                updateLanguagesSubMenu(baseActivity, optionsMenu, languageId, uri)
                return true
            }

        // language sub-menu items are handled by menu Intent (InternalIntents.createLanguageChangeCatalogRootIntent(...))

            else -> return false
        }
    }

    private fun onGeneralOptionsItemSelected(baseActivity: BaseActivity, item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home, R.id.menu_item_settings, R.id.menu_item_new_screen -> {
                onDelayedHandleMenuItem(baseActivity, item.itemId)
                return true
            }
            else -> return false
        }
    }


    /**
     * Allow overflow menu popup to close prior to call
     */
    private fun onDelayedHandleMenuItem(baseActivity: BaseActivity, menuItemId: Int) {
        val handlerClose = Handler()
        handlerClose.postDelayed({
            when (menuItemId) {
                android.R.id.home -> baseActivity.onBackPressed()
                R.id.menu_item_settings -> internalIntents.showSettings(baseActivity, baseActivity.getScreenId())
                R.id.menu_item_new_screen -> internalIntents.showCatalogRoot(baseActivity, ScreenUtil.NEW_SCREEN_ID)
            }
        }, MENU_CALL_DELAY.toLong())
    }

    private fun updateLanguagesSubMenu(baseActivity: BaseActivity, optionsMenu: Menu?, currentLanguageId: Long, uri: String?) {
        if (optionsMenu == null) {
            return
        }

        val languagesMenu = optionsMenu.findItem(R.id.menu_item_language)
        if (languagesMenu != null) {
            val languagesSubMenu = languagesMenu.subMenu
            if (languagesSubMenu != null) {
                languagesSubMenu.clear()
                val languageItems = createLanguageMenuItems(currentLanguageId)

                languageItems.forEachIndexed { order, languageItem ->
                    val languageMenuItem = languagesSubMenu.add(0, languageItem.itemId, order, languageItem.title)
                    languageMenuItem.intent = internalIntents.createLanguageChangeCatalogRootIntent(baseActivity, baseActivity.getScreenId(), languageItem.itemId.toLong(), uri)

                    if (currentLanguageId == languageMenuItem.itemId.toLong()) {
                        languageMenuItem.isCheckable = true
                        languageMenuItem.isChecked = true
                    } else {
                        languageMenuItem.isCheckable = false
                    }
                }
            }
        }
    }

    private fun createLanguageMenuItems(currentLanguageId: Long): List<LanguageItem> {
        val languageNameList = languageNameManager.findLocalizedLanguagesById(languageUtil.deviceLanguageId) // this should always use the device language
        val languageNameMap = HashMap<Long, LanguageName>(languageNameList.size)

        for (languageName in languageNameList) {
            languageNameMap.put(languageName.languageId, languageName)
        }

        val recentLanguageIds = recentLanguageManager.findAllRecent()
        val languageItems = ArrayList<LanguageItem>()

        val alreadyAddedIds = HashSet<Long>()


        // 1. Add the current language first
        val currentLanguageName = languageNameMap[currentLanguageId]
        if (currentLanguageName != null) {
            languageItems.add(LanguageItem(currentLanguageName.languageId.toInt(), currentLanguageName.name))
            alreadyAddedIds.add(currentLanguageName.languageId)
        }

        // 2. Add the recent languages
        for (recentLanguageId in recentLanguageIds) {
            val recentLanguageName = languageNameMap[recentLanguageId]
            if (recentLanguageName != null && recentLanguageId != currentLanguageId) {
                languageItems.add(LanguageItem(recentLanguageName.languageId.toInt(), recentLanguageName.name))
                alreadyAddedIds.add(recentLanguageName.languageId)
            }
        }

        // 3. Add all remaining languages (excluding the alreadyAddedIds)
        languageNameList
                .filterNot { alreadyAddedIds.contains(it.languageId) }
                .mapTo(languageItems) { LanguageItem(it.languageId.toInt(), it.name) }

        return languageItems
    }

    private inner class LanguageItem(val itemId: Int, val title: String)

    companion object {
        private val MENU_CALL_DELAY = 300
    }
}
