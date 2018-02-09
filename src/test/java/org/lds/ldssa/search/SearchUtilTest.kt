package org.lds.ldssa.search

import org.dbtools.android.domain.database.JdbcSqliteDatabaseWrapper
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.lds.ldssa.TestFilesystem
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.GlTestSampleCatalogDatabaseConfig
import org.lds.ldssa.model.database.catalog.stopword.StopWordManager
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.LanguageUtil
import org.lds.mobile.log.JavaTree
import org.mockito.Mockito.mock
import timber.log.Timber
import java.util.Arrays

class SearchUtilTest {

    private lateinit var searchUtil: SearchUtil
    private lateinit var stopWordManager: StopWordManager
    private lateinit var databaseManager: DatabaseManager

    @Before
    @Throws(Exception::class)
    fun setUp() {
        TestFilesystem.deleteFilesystem()
        val databaseConfig = GlTestSampleCatalogDatabaseConfig.getInstance()
        databaseManager = DatabaseManager(databaseConfig)
        JdbcSqliteDatabaseWrapper.setEnableLogging(true)

        Timber.plant(JavaTree())

        stopWordManager = StopWordManager(databaseManager)

        searchUtil = SearchUtil(mock(Prefs::class.java), mock(LanguageUtil::class.java),
                mock(SearchCollectionManager::class.java),
                mock(SearchCountAllNotesManager::class.java),
                mock(SearchCountContentManager::class.java),
                mock(SearchContentCollectionMapManager::class.java),
                mock(SearchPreviewNoteManager::class.java),
                mock(SearchPreviewSubitemManager::class.java),
                stopWordManager)
    }

    @Test
    @Throws(Exception::class)
    fun testPrepareSearchText() {
        val searchTextRequest = searchUtil.createSearchRequest("True to the Faith", 1, false)
    }

    @Test
    @Throws(Exception::class)
    fun testCreateSearchKeywords() {
        assertEquals(0, searchUtil.createSearchKeywords(1, "").size.toLong())
        assertEquals(0, searchUtil.createSearchKeywords(1, " ").size.toLong())
        assertEquals(0, searchUtil.createSearchKeywords(1, "the").size.toLong())
        assertEquals(0, searchUtil.createSearchKeywords(1, "The").size.toLong())

        assertThat(searchUtil.createSearchKeywords(1, "True to the Faith"), `is`(Arrays.asList("true", "faith")))
        assertThat(searchUtil.createSearchKeywords(1, "True To The Faith"), `is`(Arrays.asList("true", "faith")))

        assertThat(searchUtil.createSearchKeywords(1, "Hello World"), `is`(Arrays.asList("hello", "world")))
    }

    @Test
    @Throws(Exception::class)
    fun testRemoveParenthesis() {
        assertEquals("Hello World", searchUtil.removeParenthesis("Hello (World)"))
        assertEquals("Hello World", searchUtil.removeParenthesis("(Hello World)"))
    }

    @Test
    @Throws(Exception::class)
    fun testRemovePunctuation() {
        assertEquals("", searchUtil.removePunctuation(":"))

        assertEquals("Hello World", searchUtil.removePunctuation("Hello World."))
        assertEquals("Hello World", searchUtil.removePunctuation("Hello; World"))
        assertEquals("Hello World", searchUtil.removePunctuation("Hello-World"))
        assertEquals("Hello World", searchUtil.removePunctuation("Hello World?"))
        assertEquals("Hello World", searchUtil.removePunctuation("Hello: World"))
        assertEquals("Hello World", searchUtil.removePunctuation(":Hello World"))
    }

    @Test
    @Throws(Exception::class)
    fun testIsExactPhraseSearchText() {
        assertTrue(searchUtil.isExactPhraseSearchText("\"Hello World\""))
        assertTrue(searchUtil.isExactPhraseSearchText("\"Hello World"))
        assertFalse(searchUtil.isExactPhraseSearchText("Hello World\""))
        assertFalse(searchUtil.isExactPhraseSearchText("Hello World"))
    }

    @Test
    @Throws(Exception::class)
    fun testRemoveSearchQuotes() {
        assertEquals("Hello World", searchUtil.removeSearchQuotes("\"Hello World\""))
        assertEquals("Hello World", searchUtil.removeSearchQuotes("\"Hello World"))
        assertEquals("Hello World", searchUtil.removeSearchQuotes("Hello World\""))
        assertEquals("Hello World", searchUtil.removeSearchQuotes("Hello World"))

        assertEquals("", searchUtil.removeSearchQuotes("\""))
    }
}