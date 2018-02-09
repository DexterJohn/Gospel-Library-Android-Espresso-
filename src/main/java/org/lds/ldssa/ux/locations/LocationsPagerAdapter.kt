package org.lds.ldssa.ux.locations


import android.app.Application
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.ui.adapter.LocationTabType
import org.lds.ldssa.ux.locations.bookmarks.BookmarksFragment
import org.lds.ldssa.ux.locations.history.HistoryFragment
import org.lds.ldssa.ux.locations.screens.ScreensFragment
import javax.inject.Inject

/**
 * Adapter for LocationActivity when tabs are NOT shown in overview
 */
class LocationsPagerAdapter(
        fragmentManager: FragmentManager,
        private val screenId: Long,
        private val visibleTabs: List<LocationTabType>,
        private val referenceContentItemId: Long,
        private val referenceSubItemId: Long,
        private val referenceParagraphAid: String
) : FragmentPagerAdapter(fragmentManager) {

    @Inject
    lateinit var application: Application

    private val fragments: Array<Fragment?>
    private val titles: List<String>

    init {
        Injector.get().inject(this)

        fragments = arrayOfNulls(visibleTabs.size)
        titles = visibleTabs.map { application.getString(it.titleResId) }
    }

    override fun getCount() = visibleTabs.size

    override fun getPageTitle(position: Int) = titles[position]

    override fun getItem(position: Int): Fragment {
        return fragments[position] ?: buildFragment(position)
    }

    private fun buildFragment(position: Int): Fragment {
        val fragment: Fragment = when (visibleTabs[position]) {
            LocationTabType.BOOKMARKS -> BookmarksFragment.newInstance(screenId, referenceContentItemId, referenceSubItemId, referenceParagraphAid)
            LocationTabType.TABS -> ScreensFragment.newInstance(screenId)
            LocationTabType.HISTORY -> HistoryFragment.newInstance(screenId)
        }

        fragments[position] = fragment

        return fragment
    }

    fun showFab(position: Int): Boolean {
        return when (visibleTabs[position]) {
            LocationTabType.BOOKMARKS -> referenceParagraphAid.isNotBlank()
            LocationTabType.TABS -> true
            LocationTabType.HISTORY -> false
        }
    }

    fun onNewClick(position: Int) {
        when (visibleTabs[position]) {
            LocationTabType.BOOKMARKS -> {
                val bookmarksFragment = getItem(position) as BookmarksFragment
                bookmarksFragment.onNewClick()
            }
            LocationTabType.TABS -> {
                val screensFragment = getItem(position) as ScreensFragment
                screensFragment.onNewClick()
            }
            else -> {
            }  // do nothing
        }
    }
}