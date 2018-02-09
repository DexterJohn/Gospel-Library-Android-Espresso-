@file:Suppress("DEPRECATION")

package org.lds.ldssa.model.webservice.annotation

import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import org.apache.commons.io.FileUtils
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.lds.ldssa.TestFilesystem
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.types.AnnotationChangeType
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.highlight.Highlight
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.database.userdata.note.Note
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.model.database.userdata.notebook.Notebook
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotationChange
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotationFolder
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotationSync
import org.lds.ldssa.model.webservice.annotation.dto.folder.DtoFolderSync
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.sync.AnnotationSyncScheduler
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.ldssa.util.annotations.BookmarkUtil
import org.lds.ldssa.util.annotations.LinkUtil
import org.lds.ldssa.util.annotations.NoteUtil
import org.lds.ldssa.util.annotations.TagUtil
import org.lds.mobile.log.JavaTree
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.threeten.bp.LocalDateTime
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import javax.inject.Inject

class AnnotationSyncTest {

    @Inject
    lateinit var mockPrefs: Prefs
    @Inject
    lateinit var mockSyncScheduler: AnnotationSyncScheduler
    @Inject
    lateinit var mockFileUtil: GLFileUtil
    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var bookmarkManager: BookmarkManager
    @Inject
    lateinit var highlightManager: HighlightManager
    @Inject
    lateinit var tagManager: TagManager
    @Inject
    lateinit var notebookManager: NotebookManager
    @Inject
    lateinit var notebookAnnotationManager: NotebookAnnotationManager
    @Inject
    lateinit var linkManager: LinkManager
    @Inject
    lateinit var noteManager: NoteManager
    @Inject
    lateinit var noteUtil: NoteUtil
    @Inject
    lateinit var tagUtil: TagUtil
    @Inject
    lateinit var bookmarkUtil: BookmarkUtil
    @Inject
    lateinit var linkUtil: LinkUtil
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var mockSubItemMetadataManager: SubItemMetadataManager
    @Inject
    lateinit var gson: Gson

    // Prefs state
    private var prefsFoldersServerSince = Prefs.DAWN_OF_TIME_TEXT
    private var prefsFoldersLastSync = ThreeTenUtil.getDawnOfTime()
    private var prefsAnnotationsServerSince = Prefs.DAWN_OF_TIME_TEXT
    private var prefsAnnotationsLastSync = ThreeTenUtil.getDawnOfTime()

    @Before
    @Throws(IOException::class)
    fun setup() {
        timber.log.Timber.plant(JavaTree())

        val component = DaggerAnnotationSyncTestComponent.builder().annotationSyncTestModule(AnnotationSyncTestModule()).build()
        component.inject(this)

        // prepare a clean working space
        TestFilesystem.deleteFilesystem()

        // default "dawn of time" for last sync ts
        setupPrefs()

        // annotationSync
        annotationSync = spy(annotationSync)
        doNothing().`when`(annotationSync).writeFeedbackLog(ArgumentMatchers.anyString()) // ignore writing feedback log
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        timber.log.Timber.uprootAll()
        databaseManager.closeAllAndReset()
    }

    @Test
    @Throws(Exception::class)
    fun testSmallSync() {
        resetPrefs()

        // ********** TEST 1 - FULL SYNC **********
        syncSmallData()

        // TEST 1 - VERIFY
        verifySmallSync()
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSync() {
        resetPrefs()

        // ********** TEST 1 - FULL SYNC **********
        syncLargeData()

        // TEST 1 - VERIFY
        verifyLargeSync()
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalUpdateAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        // ********** update annotation color, sync **********
        val annotationUniqueId = "b5566965-9424-4b27-b6fe-3c1f4f3b2adf"
        val annotation = annotationManager.findByUniqueId(annotationUniqueId)
        assertNotNull(annotation)

        annotationManager.findFullAnnotationData(annotation)

        // change highlight color
        assertEquals(if (annotation!!.getFirstHighlight() != null) annotation.getFirstHighlight()!!.color else null, HighlightColor.PINK.htmlName)
        annotation.setAllHighlightColors(HighlightColor.BLUE, HighlightAnnotationStyle.FILL)

        // save annotation and verify
        annotationManager.save(annotation)
        assertEquals(if (annotation.getFirstHighlight() != null) annotation.getFirstHighlight()!!.color else null, HighlightColor.BLUE.htmlName)

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        validateResults(results, buildActualResults())

        // read the generated json file and make sure that only 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong())
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals(annotationUniqueId, annotationChanges[0].annotationId)
        assertEquals(HighlightColor.BLUE.htmlName, annotationChanges[0].annotation!!.highlights!!.items[0].color)
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalDeleteAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        // ********** TEST 3 - delete an annotation **********
        // delete mark to be deleted (trashed) (1. delete all records, 2. remove annotation from folders, 3. mark annotation deleted)

        // a2 contains 1 note, 1 highlight, 1 notebook/folder, 1 tag
        val annotationToDelete = annotationManager.findByUniqueId("07dab172-3853-4f98-9a85-c8fc6f074338")
        assertNotNull(annotationToDelete)
        val annotationToDeleteId = annotationToDelete!!.id

        annotationManager.trashById(annotationToDeleteId)

        assertEquals(noteManager.findCount(), (INITIAL_NOTE_COUNT - 1).toLong())
        assertEquals(noteManager.findCountByAnnotationId(annotationToDeleteId), 0)

        assertEquals(highlightManager.findCount(), (INITIAL_HIGHLIGHT_COUNT - 1).toLong())
        assertEquals(highlightManager.findCountByAnnotationId(annotationToDeleteId), 0)

        assertEquals(tagManager.findCount(), (INITIAL_TAG_COUNT - 1).toLong())
        assertEquals(tagManager.findCountByAnnotationId(annotationToDeleteId), 0)

        assertEquals(notebookAnnotationManager.findCount(), (INITIAL_NOTEBOOK_ANNOTATION_COUNT - 1).toLong())

        // PRE-SYNC VERIFY
        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount()) // make sure that the annotation record has yet been removed from the database... (the deleted annotation is marked trashed)
        assertEquals((INITIAL_HIGHLIGHT_COUNT - 1).toLong(), highlightManager.findCount()) // highlight should already be removed
        assertEquals((INITIAL_NOTE_COUNT - 1).toLong(), noteManager.findCount()) // note should already be removed
        assertEquals((INITIAL_TAG_COUNT - 1).toLong(), tagManager.findCount()) // highlight should already be removed

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // SYNC CHANGES
        performNoIncomingChangeAnnotationSync()

        // TEST 3 - VERIFY (Part2)
        // make sure no new annotations are received
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT - 1).toLong()) // record should now be removed
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.highlightCount = (INITIAL_HIGHLIGHT_COUNT - 1).toLong()
        results.noteCount = (INITIAL_NOTE_COUNT - 1).toLong()
        results.tagCount = (INITIAL_TAG_COUNT - 1).toLong()
        results.setNotebookAnnotationCount((INITIAL_NOTEBOOK_ANNOTATION_COUNT - 1).toLong())
        validateResults(results, buildActualResults())

        // make sure that only 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val changes = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, changes!!.size.toLong())
        assertEquals(annotationToDelete.uniqueId, changes[0].annotationId)

    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalAddAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        val newAnnotation = Annotation()

        // Highlight
        val newHighlight1 = Highlight()
        newHighlight1.color = HighlightColor.YELLOW.htmlName
        newHighlight1.paragraphAid = "123456"
        newHighlight1.offsetStart = 20
        newHighlight1.offsetEnd = 40

        // Note
        val newNote = Note()
        newNote.title = "Some Title"
        newNote.content = "Some Content"

        // Tag
        val newTag = Tag()
        newTag.name = "Faith"

        // Save Annotation
        newAnnotation.highlights = Arrays.asList(newHighlight1)
        newAnnotation.note = newNote
        newAnnotation.tags = Arrays.asList(newTag)
        annotationManager.save(newAnnotation)

        // PRE-SYNC VERIFY
        assertEquals((INITIAL_ANNOTATION_COUNT + 1).toLong(), annotationManager.findCount())
        assertEquals((INITIAL_HIGHLIGHT_COUNT + 1).toLong(), highlightManager.findCount())
        assertEquals((INITIAL_NOTE_COUNT + 1).toLong(), noteManager.findCount())
        assertEquals((INITIAL_TAG_COUNT + 1).toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // SYNC CHANGES
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT + 1).toLong()) // record should now be removed
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.highlightCount = (INITIAL_HIGHLIGHT_COUNT + 1).toLong()
        results.noteCount = (INITIAL_NOTE_COUNT + 1).toLong()
        results.tagCount = (INITIAL_TAG_COUNT + 1).toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        // make sure that only 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong())
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals(newAnnotation.uniqueId, annotationChanges[0].annotationId)
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalUpdateNotebook() {
        val results = setupCleanLargeSyncAndVerify()

        // ********** update notebook name and remove 1 annotation **********
        val notebookUniqueId = "e2b42eb3-fe9b-4fc6-a6a7-47668824bbde" // notebook with 2 annotations associated
        val notebook = notebookManager.findByUniqueId(notebookUniqueId)
        assertNotNull(notebook)

        val notebookAnnotations = notebookAnnotationManager.findAllByNotebookId(notebook!!.id)

        assertEquals("NB3", notebook.name)
        assertEquals(2, notebookAnnotations.size.toLong())

        // change notebook name
        val newFolderName = "Notebook2"
        notebook.name = newFolderName

        // delete annotation from notebook
        val firstNotebookAnnotation = notebookAnnotations[0]
        notebookAnnotationManager.deleteByNotebookIdAndAnnotationId(notebook.id, firstNotebookAnnotation.annotationId)

        notebookManager.save(notebook)
        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        annotationManager.updateLastModified(firstNotebookAnnotation.annotationId, true)
        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(2)).scheduleSync()

        // verify changes
        assertEquals(INITIAL_NOTEBOOK_COUNT.toLong(), notebookManager.findCount())
        assertEquals((INITIAL_NOTEBOOK_ANNOTATION_COUNT - 1).toLong(), notebookAnnotationManager.findCount())

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setNotebookAnnotationCount((INITIAL_NOTEBOOK_ANNOTATION_COUNT - 1).toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that only 1 annotation was sent (for the deleted annotation folder)
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong())
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals(annotationManager.findUniqueIdById(firstNotebookAnnotation.annotationId), annotationChanges[0].annotationId)
        assertNull(annotationChanges[0].annotation!!.folders)

        // read the generated FOLDER-OUT json file and make sure that only 1 folder was sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertEquals(1, folderChanges!!.size.toLong())
        assertEquals(notebookUniqueId, folderChanges[0].folderId)
        assertEquals(newFolderName, folderChanges[0].folder!!.label)
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalDeleteNotebook() {
        val results = setupCleanLargeSyncAndVerify()

        val notebookUniqueId = "e2b42eb3-fe9b-4fc6-a6a7-47668824bbde" // notebook with 2 annotations associated
        notebookManager.trashById(annotationManager, notebookManager.findIdByUniqueId(notebookUniqueId))

        // verify changes
        assertEquals(INITIAL_NOTEBOOK_COUNT.toLong(), notebookManager.findCount()) // notebook is marked as trashed... but not deleted
        assertEquals((INITIAL_NOTEBOOK_ANNOTATION_COUNT - 2).toLong(), notebookAnnotationManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.notebooksSaved = (INITIAL_NOTEBOOK_COUNT - 1).toLong()
        results.setNotebookAnnotationCount((INITIAL_NOTEBOOK_ANNOTATION_COUNT - 2).toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 2 annotations were sent (for the deleted annotation folder)
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(2, annotationChanges!!.size.toLong()) // 2 annotations were affected
        assertNull(annotationChanges[0].annotation!!.folders) // there should be no more associations to the notebook
        assertNull(annotationChanges[1].annotation!!.folders) // there should be no more associations to the notebook

        // read the generated FOLDER-OUT json file and make sure that only 1 folder was sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertEquals(1, folderChanges!!.size.toLong())
        assertEquals(notebookUniqueId, folderChanges[0].folderId)
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalAddNotebook() {
        val results = setupCleanLargeSyncAndVerify()

        val newNotebook = Notebook()
        newNotebook.name = "Notebook4"
        notebookManager.save(newNotebook)

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // verify changes
        assertEquals((INITIAL_NOTEBOOK_COUNT + 1).toLong(), notebookManager.findCount())

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.notebooksSaved = (INITIAL_NOTEBOOK_COUNT + 1).toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        // read the generated FOLDER-OUT json file and make sure that only 1 folder was sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertEquals(1, folderChanges!!.size.toLong())

        val dtoFolderChange = folderChanges[0]
        assertEquals(newNotebook.uniqueId, dtoFolderChange.folderId)
        assertEquals(newNotebook.uniqueId, dtoFolderChange.folder!!.id)
        assertEquals("Notebook4", dtoFolderChange.folder!!.label)
    }

    @Test
    @Throws(Exception::class)
    fun testLargeSyncLocalAddNotebookItem() {
        val results = setupCleanLargeSyncAndVerify()

        val notebook = notebookManager.findByUniqueId("e2b42eb3-fe9b-4fc6-a6a7-47668824bbde")
        assertNotNull(notebook)
        val annotation = annotationManager.findByUniqueId("00cf4318-119e-4219-be95-eb761f252bde")
        assertNotNull(annotation)
        annotationManager.moveToNotebook(annotation!!, notebook!!.id)

        // verify changes
        assertEquals(INITIAL_NOTEBOOK_COUNT.toLong(), notebookManager.findCount())
        assertEquals((INITIAL_NOTEBOOK_ANNOTATION_COUNT + 1).toLong(), notebookAnnotationManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.notebooksSaved = INITIAL_NOTEBOOK_COUNT.toLong()
        results.setNotebookAnnotationCount((INITIAL_NOTEBOOK_ANNOTATION_COUNT + 1).toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        val folderUri = String.format(DtoAnnotationFolder.FOLDER_URI_TEMPLATE, "0", notebook.uniqueId)
        assertEquals(folderUri, annotationChanges[0].annotation!!.folders!!.getItems()!![0].uri) // there should be no more associations to the notebook


        // read the generated FOLDER-OUT json file and make sure that only 1 folder was sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertEquals(1, folderChanges!!.size.toLong())
        val dtoFolderChange = folderChanges[0]
        assertEquals(notebook.uniqueId, dtoFolderChange.folderId)
        assertEquals(notebook.uniqueId, dtoFolderChange.folder!!.id)
        assertEquals(annotation.uniqueId, dtoFolderChange.folder!!.order!!.id[0])
    }

    @Test
    @Throws(Exception::class)
    fun testBookmarkUtilAdd() = runBlocking {
        val results = setupCleanLargeSyncAndVerify()

        val mockBookmarkUtil = spy(bookmarkUtil)

        doReturn("998877").`when`(mockBookmarkUtil).findDocIdForBookmark(456, 789)
        mockBookmarkUtil.add("Testing123", 456, 789, "112233")

        assertEquals((INITIAL_BOOKMARK_COUNT + 1).toLong(), bookmarkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT + 1).toLong())
        results.bookmarkCount = (INITIAL_BOOKMARK_COUNT + 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals("998877", annotationChanges[0].annotation!!.docId)

        val dtoBookmark = annotationChanges[0].annotation!!.bookmark
        assertEquals("Testing123", dtoBookmark!!.name)
        assertEquals("112233", dtoBookmark.aid)
    }

    @Test
    @Throws(Exception::class)
    fun testBookmarkUtilUpdate() = runBlocking {
        val results = setupCleanLargeSyncAndVerify()

        val annotation = annotationManager.findByUniqueId("1d2c1860-e5c1-47b5-bbe3-2ce03983ef49")
        assertNotNull(annotation)
        val existingBookmark = bookmarkManager.findByAnnotationId(annotation!!.id)

        assertNotNull("Existing Bookmark", existingBookmark)

        val mockBookmarkUtil = spy(bookmarkUtil)

        doReturn("998877").`when`(mockBookmarkUtil).findDocIdForBookmark(456, 789)
        mockBookmarkUtil.update(existingBookmark!!.id, 456, 789, "112234")

        assertEquals(INITIAL_BOOKMARK_COUNT.toLong(), bookmarkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.bookmarkCount = INITIAL_BOOKMARK_COUNT.toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals("998877", annotationChanges[0].annotation!!.docId)

        val dtoBookmark = annotationChanges[0].annotation!!.bookmark
        assertEquals("EQ Lesson", dtoBookmark!!.name)
        assertEquals("112234", dtoBookmark.aid)
    }

    @Test
    @Throws(Exception::class)
    fun testBookmarkUtilRename() = runBlocking {
        val results = setupCleanLargeSyncAndVerify()

        //        Annotation annotation = annotationManager.findByUniqueId("1d2c1860-e5c1-47b5-bbe3-2ce03983ef49");
        val annotation = annotationManager.findByUniqueId("4F35BAFC-43F4-426E-B3B1-94764B356A01")
        assertNotNull(annotation)

        val existingBookmark = bookmarkManager.findByAnnotationId(annotation!!.id)
        assertNotNull("Existing Bookmark", existingBookmark)

        bookmarkUtil.rename(existingBookmark!!.id, "Other Name")

        assertEquals(INITIAL_BOOKMARK_COUNT.toLong(), bookmarkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.bookmarkCount = INITIAL_BOOKMARK_COUNT.toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals("19066009", annotationChanges[0].annotation!!.docId)

        val dtoBookmark = annotationChanges[0].annotation!!.bookmark
        assertEquals("Other Name", dtoBookmark!!.name)
        assertEquals("19066010", dtoBookmark.aid)
    }

    @Test
    @Throws(Exception::class)
    fun testBookmarkUtilDelete() = runBlocking {
        val results = setupCleanLargeSyncAndVerify()

        val annotation = annotationManager.findByUniqueId("1d2c1860-e5c1-47b5-bbe3-2ce03983ef49")
        assertNotNull(annotation)
        val existingBookmark = bookmarkManager.findByAnnotationId(annotation!!.id)

        assertNotNull("Existing Bookmark", existingBookmark)

        bookmarkUtil.delete(existingBookmark!!.id)

        assertEquals((INITIAL_BOOKMARK_COUNT - 1).toLong(), bookmarkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT - 1).toLong())
        results.bookmarkCount = (INITIAL_BOOKMARK_COUNT - 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected

        assertEquals("1d2c1860-e5c1-47b5-bbe3-2ce03983ef49", annotationChanges[0].annotationId)
        assertEquals(AnnotationChangeType.TRASH, annotationChanges[0].changeType)
    }

    @Test
    @Throws(Exception::class)
    fun testNoteUtilAddToAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("0dda50b9-eb71-497c-91e6-e6e12190c338")
        assertNotNull(existingAnnotation)
        annotationManager.findFullAnnotationData(existingAnnotation)

        // make sure that there is not already any note
        assertNull(existingAnnotation!!.note)

        noteUtil.add(existingAnnotation.id, "New Title", "New Content")

        assertEquals((INITIAL_NOTE_COUNT + 1).toLong(), noteManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.noteCount = (INITIAL_NOTE_COUNT + 1).toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoNote = annotationChanges[0].annotation!!.note
        assertEquals("New Title", dtoNote!!.title)
        assertEquals("New Content", dtoNote.content)
    }

    @Test
    @Throws(Exception::class)
    fun testNoteUtilAddJournal() {
        val results = setupCleanLargeSyncAndVerify()

        val notebookUniqueId = "e2b42eb3-fe9b-4fc6-a6a7-47668824bbde"
        val existingNotebook = notebookManager.findByUniqueId(notebookUniqueId)
        assertNotNull(existingNotebook)

        val annotation = noteUtil.createJournalAnnotation(existingNotebook!!.id)
        noteUtil.add(annotation.id, "Journal Title", "Journal Content")

        assertEquals((INITIAL_NOTE_COUNT + 1).toLong(), noteManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(3)).scheduleSync() // 1 for new Annotation + 1 for save of Note + 1 for updating the display order on the Notebook

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT + 1).toLong())
        results.noteCount = (INITIAL_NOTE_COUNT + 1).toLong()
        results.setNotebookAnnotationCount((INITIAL_NOTEBOOK_ANNOTATION_COUNT + 1).toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val annotation1 = annotationChanges[0].annotation
        assertNull(annotation1!!.docId) // make sure we don't pass "" docId
        assertNull(annotation1.citation) // make sure we don't pass ""
        assertNull(annotation1.scope) // make sure we don't pass ""

        val dtoNote = annotationChanges[0].annotation!!.note
        assertEquals("Journal Title", dtoNote!!.title)
        assertEquals("Journal Content", dtoNote.content)

        val folderUri = String.format(DtoAnnotationFolder.FOLDER_URI_TEMPLATE, "0", notebookUniqueId)
        assertEquals(folderUri, annotationChanges[0].annotation!!.folders!!.getItems()!![0].uri)

        // read the generated FOLDER-OUT json file and make sure that only 1 folder was sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertEquals(1, folderChanges!!.size.toLong())
        val dtoFolderChange = folderChanges[0]
        assertEquals(notebookUniqueId, dtoFolderChange.folderId)
        assertEquals(notebookUniqueId, dtoFolderChange.folder!!.id)
        //        assertEquals(annotation.getUniqueId(), dtoFolderChange.getFolder().getOrder().getId().get(0));
    }

    @Test
    @Throws(Exception::class)
    fun testNoteUtilUpdate() {
        val results = setupCleanLargeSyncAndVerify()

        val annotationId = annotationManager.findIdByUniqueId("0d203893-7b17-4635-9470-d83158749cfc")
        val existingNote = noteManager.findByAnnotationId(annotationId)
        assertNotNull(existingNote)

        noteUtil.update(existingNote!!, "New Title", "New Content")

        assertEquals(INITIAL_NOTE_COUNT.toLong(), noteManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.noteCount = INITIAL_NOTE_COUNT.toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoNote = annotationChanges[0].annotation!!.note
        assertEquals("New Title", dtoNote!!.title)
        assertEquals("New Content", dtoNote.content)

        // Make sure the URI stays in tact
        val notebookAnnotation = notebookAnnotationManager.findAllByAnnotationId(existingNote.annotationId)[0]
        val notebookUniqueId = notebookManager.findNotebookUniqueIdById(notebookAnnotation.notebookId)
        val folderUri = String.format(DtoAnnotationFolder.FOLDER_URI_TEMPLATE, "0", notebookUniqueId)
        assertEquals(folderUri, annotationChanges[0].annotation!!.folders!!.getItems()!![0].uri)

        // read the generated FOLDER-OUT json file and make sure that NO folders are sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertNull(folderChanges)
    }

    /**
     * Deleted the note, but keep the annotation around
     */
    @Test
    @Throws(Exception::class)
    fun testNoteUtilDelete() {
        val results = setupCleanLargeSyncAndVerify()

        val annotationId = annotationManager.findIdByUniqueId("0e9178cd-ddc5-4479-9658-70bb5cf29c8e")

        noteUtil.deleteByAnnotationId(annotationId)

        assertEquals((INITIAL_NOTE_COUNT - 1).toLong(), noteManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.noteCount = (INITIAL_NOTE_COUNT - 1).toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        assertNull(annotationChanges[0].annotation!!.note)
    }

    /**
     * If the annotation ONLY contains a note... if the note is deleted, then the annotation needs to be removed/trashed
     * If the annotation was associated to a notebook/folder, then it needs to be unassociated
     *
     * @throws Exception
     */
    @Test
    @Throws(Exception::class)
    fun testNoteUtilDeleteWithAnnotation() {

        val results = setupCleanLargeSyncAndVerify()

        val annotationUniqueId = "0d203893-7b17-4635-9470-d83158749cfc"
        val annotationId = annotationManager.findIdByUniqueId(annotationUniqueId)
        val notebookAnnotation = notebookAnnotationManager.findAllByAnnotationId(annotationId)[0]
        val notebookUniqueId = notebookManager.findNotebookUniqueIdById(notebookAnnotation.notebookId)

        noteUtil.deleteByAnnotationId(annotationId)

        assertEquals((INITIAL_NOTE_COUNT - 1).toLong(), noteManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT - 1).toLong())
        results.noteCount = (INITIAL_NOTE_COUNT - 1).toLong()
        results.setNotebookAnnotationCount((INITIAL_NOTEBOOK_ANNOTATION_COUNT - 1).toLong())
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        assertEquals(annotationUniqueId, annotationChanges[0].annotationId)
        assertEquals(AnnotationChangeType.TRASH, annotationChanges[0].changeType)

        // read the generated FOLDER-OUT json file and make sure that only 1 folder was sent
        val dtoFolderSync = gson.fromJson(BufferedReader(FileReader(FOLDER_1_OUT_FILE)), DtoFolderSync::class.java)
        val folderChanges = dtoFolderSync.syncFolders!!.changes
        assertEquals(1, folderChanges!!.size.toLong())


        val dtoFolderChange = folderChanges[0]
        assertEquals(notebookUniqueId, dtoFolderChange.folderId)

        // make sure note was removed from folder
        var existsInFolder = false
        for (folderAnnotationUniqueId in dtoFolderChange.folder!!.order!!.id) {
            if (folderAnnotationUniqueId == annotationUniqueId) {
                existsInFolder = true
            }
        }
        assertEquals(false, existsInFolder)
    }

    @Test
    @Throws(Exception::class)
    fun testHighlightAdd() {
        val results = setupCleanLargeSyncAndVerify()

        val newAnnotation = Annotation()
        newAnnotation.docId = "abc123"

        // Highlight
        val newHighlight1 = Highlight()
        newHighlight1.color = HighlightColor.YELLOW.htmlName
        newHighlight1.paragraphAid = "123456"
        newHighlight1.offsetStart = 5
        newHighlight1.offsetEnd = -1

        val newHighlight2 = Highlight()
        newHighlight2.color = HighlightColor.BLUE.htmlName
        newHighlight2.paragraphAid = "123457"
        newHighlight2.offsetStart = -1
        newHighlight2.offsetEnd = 10
        newHighlight2.style = HighlightAnnotationStyle.UNDERLINE.stringValue

        // Save Annotation
        newAnnotation.highlights = Arrays.asList(newHighlight1, newHighlight2)
        annotationManager.save(newAnnotation)

        assertEquals((INITIAL_ANNOTATION_COUNT + 1).toLong(), annotationManager.findCount())
        assertEquals((INITIAL_HIGHLIGHT_COUNT + 2).toLong(), highlightManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT + 1).toLong())
        results.highlightCount = (INITIAL_HIGHLIGHT_COUNT + 2).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoHighlights = annotationChanges[0].annotation!!.highlights!!.items
        assertEquals("123456", dtoHighlights[0].aid)
        assertEquals(HighlightColor.YELLOW.htmlName, dtoHighlights[0].color)
        assertEquals("5", dtoHighlights[0].offsetStart)
        assertEquals("-1", dtoHighlights[0].offsetEnd)
        assertNull(dtoHighlights[0].style) // HighlightAnnotationStyle.FILL.getStringValue()

        assertEquals("123457", dtoHighlights[1].aid)
        assertEquals(HighlightColor.BLUE.htmlName, dtoHighlights[1].color)
        assertEquals("-1", dtoHighlights[1].offsetStart)
        assertEquals("10", dtoHighlights[1].offsetEnd)
        assertEquals(HighlightAnnotationStyle.UNDERLINE.stringValue, dtoHighlights[1].style)
    }

    /**
     * reduce the highlight from 3 paragraphs to 2 paragraphs, change color to RED and UNDERLINE
     */
    @Test
    @Throws(Exception::class)
    fun testHighlightUpdate() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("272e9c0d-8567-42f2-b49f-3179b65d5840")
        if (existingAnnotation == null) {
            fail()
        }

        annotationManager.findFullAnnotationData(existingAnnotation)

        val highlights = existingAnnotation!!.highlights
        highlights[0].paragraphAid = "19075382"
        highlights[0].offsetStart = 5
        highlights[0].offsetEnd = -1
        highlights[0].color = HighlightColor.RED.htmlName
        highlights[0].style = HighlightAnnotationStyle.UNDERLINE.stringValue

        highlights[1].paragraphAid = "19075383"
        highlights[1].offsetStart = -1
        highlights[1].offsetEnd = 10
        highlights[1].color = HighlightColor.RED.htmlName
        highlights[1].style = HighlightAnnotationStyle.UNDERLINE.stringValue

        // remove 3rd paragraph
        highlights.removeAt(2)

        existingAnnotation.highlights = highlights

        // Save Annotation
        annotationManager.save(existingAnnotation)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_HIGHLIGHT_COUNT - 1).toLong(), highlightManager.findCount()) // 3rd highlight removed

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.highlightCount = (INITIAL_HIGHLIGHT_COUNT - 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoHighlights = annotationChanges[0].annotation!!.highlights!!.items
        assertEquals(2, dtoHighlights.size.toLong())

        assertEquals("19075382", dtoHighlights[0].aid)
        assertEquals(HighlightColor.RED.htmlName, dtoHighlights[0].color)
        assertEquals("5", dtoHighlights[0].offsetStart)
        assertEquals("-1", dtoHighlights[0].offsetEnd)
        assertEquals(HighlightAnnotationStyle.UNDERLINE.stringValue, dtoHighlights[0].style)

        assertEquals("19075383", dtoHighlights[1].aid)
        assertEquals(HighlightColor.RED.htmlName, dtoHighlights[1].color)
        assertEquals("-1", dtoHighlights[1].offsetStart)
        assertEquals("10", dtoHighlights[1].offsetEnd)
        assertEquals(HighlightAnnotationStyle.UNDERLINE.stringValue, dtoHighlights[1].style)
    }

    /**
     * increase the highlight from 1 paragraphs to 2 paragraphs, change color to RED and UNDERLINE
     */
    @Test
    @Throws(Exception::class)
    fun testHighlightUpdate2() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("271ecd92-f633-4585-a043-49fe2b318c0a")
        assertNotNull(existingAnnotation)
        annotationManager.findFullAnnotationData(existingAnnotation)

        val highlights = existingAnnotation!!.highlights
        highlights[0].paragraphAid = "26838446"
        highlights[0].offsetStart = 5
        highlights[0].offsetEnd = -1
        highlights[0].color = HighlightColor.RED.htmlName
        highlights[0].style = HighlightAnnotationStyle.UNDERLINE.stringValue

        val newHighlight = Highlight()
        newHighlight.paragraphAid = "26838447"
        newHighlight.offsetStart = -1
        newHighlight.offsetEnd = 10
        newHighlight.color = HighlightColor.RED.htmlName
        newHighlight.style = HighlightAnnotationStyle.UNDERLINE.stringValue

        highlights.add(newHighlight)

        existingAnnotation.highlights = highlights

        // Save Annotation
        annotationManager.save(existingAnnotation)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_HIGHLIGHT_COUNT + 1).toLong(), highlightManager.findCount()) // 3rd highlight removed

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.highlightCount = (INITIAL_HIGHLIGHT_COUNT + 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoHighlights = annotationChanges[0].annotation!!.highlights!!.items
        assertEquals(2, dtoHighlights.size.toLong())

        assertEquals("26838446", dtoHighlights[0].aid)
        assertEquals(HighlightColor.RED.htmlName, dtoHighlights[0].color)
        assertEquals("5", dtoHighlights[0].offsetStart)
        assertEquals("-1", dtoHighlights[0].offsetEnd)
        assertEquals(HighlightAnnotationStyle.UNDERLINE.stringValue, dtoHighlights[0].style)

        assertEquals("26838447", dtoHighlights[1].aid)
        assertEquals(HighlightColor.RED.htmlName, dtoHighlights[1].color)
        assertEquals("-1", dtoHighlights[1].offsetStart)
        assertEquals("10", dtoHighlights[1].offsetEnd)
        assertEquals(HighlightAnnotationStyle.UNDERLINE.stringValue, dtoHighlights[1].style)
    }

    /**
     * Add a tag to an existing annotation (with nothing but a highlight)
     * using TagUtil
     */
    @Test
    @Throws(Exception::class)
    fun testTagAdd() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("29b28df2-a18d-41d9-8820-296d44e5a3da")
        assertNotNull(existingAnnotation)

        tagUtil.add(existingAnnotation!!.id, "Good Stuff")

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_TAG_COUNT + 1).toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = (INITIAL_TAG_COUNT + 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val tags = annotationChanges[0].annotation!!.tags!!.items
        assertEquals(1, tags.size.toLong())
        assertEquals("Good Stuff", tags[0])
    }

    /**
     * Add multiple tags to an existing annotation (with nothing but a highlight)
     * using TagUtil
     */
    @Test
    @Throws(Exception::class)
    fun testTagAddMultiple() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("29b28df2-a18d-41d9-8820-296d44e5a3da")
        assertNotNull(existingAnnotation)

        tagUtil.add(existingAnnotation!!.id, "Good Stuff")
        tagUtil.add(existingAnnotation.id, "Really Good Stuff")

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_TAG_COUNT + 2).toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(2)).scheduleSync() // once for each tag

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = (INITIAL_TAG_COUNT + 2).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val tags = annotationChanges[0].annotation!!.tags!!.items
        assertEquals(2, tags.size.toLong())
        assertEquals("Good Stuff", tags[0])
        assertEquals("Really Good Stuff", tags[1])
    }

    /**
     * Add multiple tags to an existing annotation (with nothing but a highlight)
     * using Annotation
     */
    @Test
    @Throws(Exception::class)
    fun testTagAddToAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("29b28df2-a18d-41d9-8820-296d44e5a3da")
        assertNotNull(existingAnnotation)
        annotationManager.findFullAnnotationData(existingAnnotation)

        val tag1 = Tag()
        tag1.name = "Good Stuff"

        val tag2 = Tag()
        tag2.name = "Really Good Stuff"

        existingAnnotation!!.tags = Arrays.asList(tag1, tag2)

        annotationManager.save(existingAnnotation)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_TAG_COUNT + 2).toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = (INITIAL_TAG_COUNT + 2).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val tags = annotationChanges[0].annotation!!.tags!!.items
        assertEquals(2, tags.size.toLong())
        assertEquals("Good Stuff", tags[0])
        assertEquals("Really Good Stuff", tags[1])
    }

    /**
     * Delete 1 tag from an annotation with 2 tags
     * using Annotation
     */
    @Test
    @Throws(Exception::class)
    fun testTagDeleteFromAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("4b0ad03e-ec97-4ae6-9281-9f51f0037e3f")
        assertNotNull(existingAnnotation)
        annotationManager.findFullAnnotationData(existingAnnotation)

        // Remove atonement from annotation
        val iterator = existingAnnotation!!.tags.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().name == "Atonement") {
                iterator.remove()
                break
            }
        }

        annotationManager.save(existingAnnotation)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_TAG_COUNT - 1).toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = (INITIAL_TAG_COUNT - 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected

        val tags = annotationChanges[0].annotation!!.tags!!.items
        assertEquals(1, tags.size.toLong())
        assertEquals("Charity", tags[0])
    }

    /**
     * Delete 1 tag from an annotation with 2 tags
     * using TagUtil
     */
    @Test
    @Throws(Exception::class)
    fun testTagDelete() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("4b0ad03e-ec97-4ae6-9281-9f51f0037e3f")
        assertNotNull(existingAnnotation)
        existingAnnotation ?: return
        annotationManager.findFullAnnotationData(existingAnnotation)

        // Remove atonement from annotation
        tagUtil.delete(existingAnnotation.id, "Atonement")

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_TAG_COUNT - 1).toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = (INITIAL_TAG_COUNT - 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected

        val tags = annotationChanges[0].annotation!!.tags!!.items
        assertEquals(1, tags.size.toLong())
        assertEquals("Charity", tags[0])
    }

    /**
     * Delete All tags by name
     * using TagUtil
     */
    @Test
    @Throws(Exception::class)
    fun testTagDeleteByName() {
        val results = setupCleanLargeSyncAndVerify()

        tagUtil.deleteTags(Arrays.asList("plan", "Scripture Mastery"))

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_TAG_COUNT - 30).toLong(), tagManager.findCount()) // 5 for plan and 25 for Scripture Mastery

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = (INITIAL_TAG_COUNT - 30).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(30, annotationChanges!!.size.toLong()) // 1 annotations were affected
    }

    /**
     * Rename All tags
     * using TagUtil
     */
    @Test
    @Throws(Exception::class)
    fun testTagRename() {
        val results = setupCleanLargeSyncAndVerify()

        tagUtil.renameTags("plan", "The Plan")

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals(INITIAL_TAG_COUNT.toLong(), tagManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.tagCount = INITIAL_TAG_COUNT.toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(5, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val tags = annotationChanges[0].annotation!!.tags!!.items
        assertEquals(1, tags.size.toLong())
        assertEquals("The Plan", tags[0])
    }

    /**
     * Add a link to an existing annotation (with nothing but a highlight)
     * using LinkUtil
     */
    @Test
    @Throws(Exception::class)
    fun testLinkAdd() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("29b28df2-a18d-41d9-8820-296d44e5a3da")
        assertNotNull(existingAnnotation)

        `when`(mockSubItemMetadataManager.findDocVersionByDocId("112233")).thenReturn(998877)
        linkUtil.add(existingAnnotation!!.id, "112233", Arrays.asList("1", "2"), "My Link")

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_LINK_COUNT + 1).toLong(), linkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.linkCount = (INITIAL_LINK_COUNT + 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoRef = annotationChanges[0].annotation!!.refs!!.items[0]
        assertEquals("My Link", dtoRef.name)
        assertEquals("112233", dtoRef.docId)
        assertEquals("1,2", dtoRef.aid)
        assertEquals(998877, dtoRef.contentVersion!!.toInt().toLong())
    }

    /**
     * Add a link to an existing annotation (with nothing but a highlight)
     * using Annotation
     */
    @Test
    @Throws(Exception::class)
    fun testLinkAddToAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("29b28df2-a18d-41d9-8820-296d44e5a3da")
        assertNotNull(existingAnnotation)

        annotationManager.findFullAnnotationData(existingAnnotation)

        val newLink = Link()
        newLink.name = "My Link"
        newLink.setParagraphAids(Arrays.asList("1", "2"))
        newLink.docId = "112233"
        newLink.contentVersion = 998877

        existingAnnotation!!.links = Arrays.asList(newLink)

        annotationManager.save(existingAnnotation)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_LINK_COUNT + 1).toLong(), linkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.linkCount = (INITIAL_LINK_COUNT + 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected
        validateJsonOutgoingAnnotation(annotationChanges[0])

        val dtoRef = annotationChanges[0].annotation!!.refs!!.items[0]
        assertEquals("My Link", dtoRef.name)
        assertEquals("112233", dtoRef.docId)
        assertEquals("1,2", dtoRef.aid)
        assertEquals(998877, dtoRef.contentVersion!!.toInt().toLong())
    }

    /**
     * Delete a link from an existing annotation
     * using Annotation
     */
    @Test
    @Throws(Exception::class)
    fun testLinkDeleteFromAnnotation() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("026830fa-1e6c-4c7f-b127-e4289cf27917")
        assertNotNull(existingAnnotation)
        annotationManager.findFullAnnotationData(existingAnnotation)

        existingAnnotation!!.links = ArrayList()

        annotationManager.save(existingAnnotation)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_LINK_COUNT - 1).toLong(), linkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.linkCount = (INITIAL_LINK_COUNT - 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected

        assertNull(annotationChanges[0].annotation!!.refs)
    }

    /**
     * Delete a link from an existing annotation
     * using LinkUtil
     */
    @Test
    @Throws(Exception::class)
    fun testLinkDelete() {
        val results = setupCleanLargeSyncAndVerify()

        val existingAnnotation = annotationManager.findByUniqueId("026830fa-1e6c-4c7f-b127-e4289cf27917")
        assertNotNull(existingAnnotation)
        annotationManager.findFullAnnotationData(existingAnnotation)

        linkUtil.delete(existingAnnotation!!.id, existingAnnotation.links[0].id)

        assertEquals(INITIAL_ANNOTATION_COUNT.toLong(), annotationManager.findCount())
        assertEquals((INITIAL_LINK_COUNT - 1).toLong(), linkManager.findCount())

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // sync
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.linkCount = (INITIAL_LINK_COUNT - 1).toLong()
        validateResults(results, buildActualResults())

        // read the generated ANNOTATION-OUT json file and make sure that 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong()) // 1 annotations were affected

        assertNull(annotationChanges[0].annotation!!.refs)
    }

    /**
     * Test to make sure annotations stay associated with the proper notebook/folder, after an annotation has been update on another device
     */
    @Test
    @Throws(Exception::class)
    fun testNotebookAnnotationAssociationAfterAnnotationUpdate() {
        setupCleanLargeSyncAndVerify()

        // make sure annotation/note already exists
        val annotationIdBefore = annotationManager.findIdByUniqueId("0d203893-7b17-4635-9470-d83158749cfc")
        val noteBefore = noteManager.findByAnnotationId(annotationIdBefore)
        assertNotNull(noteBefore)
        assertEquals("testing 456", noteBefore!!.content)

        val notebookAnnotationBefore = notebookAnnotationManager.findAllByAnnotationId(annotationIdBefore)[0]

        // sync a single annotation with an update to a note
        setResponseFiles("src/test/resources/annotations/update-note/folder-2-in.json", "src/test/resources/annotations/update-note/annotation-2-changes-in.json", "src/test/resources/annotations/update-note/annotation-4-in.json")
        annotationSync.sync()

        // the note annotation should have been deleted and re-added... giving it a new id
        val annotationIdAfter = annotationManager.findIdByUniqueId("0d203893-7b17-4635-9470-d83158749cfc")
        assertNotEquals(annotationIdBefore, annotationIdAfter)

        // make sure the new updated note sync'd
        val noteAfter = noteManager.findByAnnotationId(annotationIdAfter)
        assertNotNull(noteAfter)
        assertEquals("testing 456 ABC", noteAfter!!.content)


        // reload the same notebookAnnotation from before (based on the rowId from before)
        val notebookAnnotationAfter = notebookAnnotationManager.findByRowId(notebookAnnotationBefore.id)
        assertNotNull(notebookAnnotationAfter)

        // after the sync, the id for the updated annotation/note should have been updated in the notebookAnnotation table
        assertEquals(annotationIdAfter, notebookAnnotationAfter!!.annotationId)
    }


    /**
     * Test to make sure ALL notes are saved (not cut off)
     */
    @Test
    @Throws(Exception::class)
    fun testLongNotes() {
        val results = setupCleanLargeSyncAndVerify()

        val newAnnotation = Annotation()

        val longNoteLength: Long = 19949
        // read in a long note
        val readInLongNote = FileUtils.readFileToString(File("src/test/resources/text/jacob5.txt"), "UTF-8")
        assertEquals(longNoteLength, readInLongNote.length.toLong())

        // Note
        val newNote = Note()
        newNote.title = "Long Note Title"
        newNote.content = readInLongNote

        // verify that the long note in the Note object
        val newNoteContent = newNote.content
        assertNotNull(newNoteContent)

        assertEquals(longNoteLength, newNoteContent!!.length.toLong())
        assertEquals(readInLongNote, newNoteContent)

        // Save Annotation
        newAnnotation.note = newNote
        annotationManager.save(newAnnotation)

        // PRE-SYNC VERIFY
        assertEquals((INITIAL_ANNOTATION_COUNT + 1).toLong(), annotationManager.findCount())
        assertEquals((INITIAL_NOTE_COUNT + 1).toLong(), noteManager.findCount())
        assertEquals(INITIAL_HIGHLIGHT_COUNT.toLong(), highlightManager.findCount()) // nothing changes
        assertEquals(INITIAL_TAG_COUNT.toLong(), tagManager.findCount()) // nothing changes

        // Verify sync was requested
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(1)).scheduleSync()

        // SYNC CHANGES
        performNoIncomingChangeAnnotationSync()

        // VERIFY
        // make sure no new annotations are received
        results.setAnnotationCount((INITIAL_ANNOTATION_COUNT + 1).toLong()) // record should now be removed
        results.annotationsReceived = 0
        results.annotationsAdded = 0
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.highlightCount = INITIAL_HIGHLIGHT_COUNT.toLong()
        results.noteCount = (INITIAL_NOTE_COUNT + 1).toLong()
        results.tagCount = INITIAL_TAG_COUNT.toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        // make sure that only 1 annotation was sent
        val dtoAnnotationSync = gson.fromJson(BufferedReader(FileReader(ANNOTATION_1_OUT_FILE)), DtoAnnotationSync::class.java)
        val annotationChanges = dtoAnnotationSync.syncAnnotations!!.changes
        assertEquals(1, annotationChanges!!.size.toLong())
        validateJsonOutgoingAnnotation(annotationChanges[0])

        assertEquals(newAnnotation.uniqueId, annotationChanges[0].annotationId)


        // re-read in from the database
        val dbNewAnnotation = annotationManager.findByUniqueId(newAnnotation.uniqueId)
        annotationManager.findFullAnnotationData(dbNewAnnotation)
        assertNotNull(dbNewAnnotation)

        // verify that the long note in the Note object
        val dbNote = dbNewAnnotation!!.note
        assertNotNull(dbNote)

        val dbNoteContent = dbNote!!.content
        assertNotNull(dbNoteContent)

        // check length
        assertEquals(longNoteLength, dbNoteContent!!.length.toLong())
        assertEquals(readInLongNote, dbNoteContent)
    }


    // ============= Helper functions =============

    private fun buildActualResults(): AnnotationResults {
        val results = AnnotationResults()
        results.setAnnotationCount(annotationManager.findCount())
        results.annotationsReceived = annotationSync.annotationsReceived
        results.annotationsAdded = annotationSync.annotationsAdded
        results.annotationsUpdated = annotationSync.annotationsUpdated
        results.annotationsRemoved = annotationSync.annotationsRemoved
        results.bookmarkCount = bookmarkManager.findCount()
        results.highlightCount = highlightManager.findCount()
        results.linkCount = linkManager.findCount()
        results.tagCount = tagManager.findCount()
        results.noteCount = noteManager.findCount()
        results.notebooksSaved = notebookManager.findCount()
        results.setNotebookAnnotationCount(notebookAnnotationManager.findCount())

        return results
    }

    private fun validateResults(expected: AnnotationResults, actual: AnnotationResults) {
        // Annotations
        assertEquals("Annotations saved", expected.annotationsSaved, actual.annotationsSaved)
        assertEquals("Received: ", expected.annotationsReceived, actual.annotationsReceived)
        assertEquals("Added: ", expected.annotationsAdded, actual.annotationsAdded)
        assertEquals("Updated: ", expected.annotationsUpdated, actual.annotationsUpdated)
        assertEquals("Removed: ", expected.annotationsRemoved, actual.annotationsRemoved)

        // Bookmarks
        assertEquals("Bookmarks saved", expected.bookmarkCount, actual.bookmarkCount)

        // Highlights
        assertEquals("Highlights saved", expected.highlightCount, actual.highlightCount)

        // Links
        assertEquals("Links saved", expected.linkCount, actual.linkCount)

        // Tags
        assertEquals("Tags saved", expected.tagCount, actual.tagCount)

        // Notes
        assertEquals("Notes saved", expected.noteCount, actual.noteCount)

        // Folders
        //        showFolderData(notebookManager, mockNotebookAnnotationManager);
        assertEquals("Notebooks saved", expected.notebooksSaved, actual.notebooksSaved)
        assertEquals("Annotation Folders", expected.annotationNotebookCount, actual.annotationNotebookCount)
    }

    private fun validateJsonOutgoingAnnotation(dtoAnnotationChange: DtoAnnotationChange) {
        assertEquals(AnnotationManager.ANNOTATION_DEVICE, dtoAnnotationChange.annotation!!.device)
        assertEquals(AnnotationManager.getAnnotationSource(), dtoAnnotationChange.annotation!!.source)
    }

    @Suppress("unused")
    private fun showFolderData(notebookManager: NotebookManager, notebookAnnotationManager: NotebookAnnotationManager) {
        for (folder in notebookManager.findAll()) {
            println("=== FOLDER === \n" + folder.toString())
        }

        for (folderAnnotation in notebookAnnotationManager.findAll()) {
            println("=== FOLDER ANNOTATION === \n" + folderAnnotation.toString())
        }
    }

    private fun resetPrefs() {
        prefsFoldersServerSince = Prefs.DAWN_OF_TIME_TEXT
        prefsFoldersLastSync = ThreeTenUtil.getDawnOfTime()
        prefsAnnotationsServerSince = Prefs.DAWN_OF_TIME_TEXT
        prefsAnnotationsLastSync = ThreeTenUtil.getDawnOfTime()
    }

    private fun setupPrefs() {
        // ** prefsFoldersServerSince **
        `when`(mockPrefs.foldersServerSinceTs).thenAnswer { prefsFoldersServerSince }
        doAnswer ({ invocation ->
                prefsFoldersServerSince = invocation.getArgument(0)
                return@doAnswer null
        }).`when`<Prefs>(mockPrefs).setFoldersServerSinceTs(ArgumentMatchers.any(String::class.java))

        // ** prefsFoldersLastSync **
        `when`(mockPrefs.foldersLastSyncTs).thenAnswer { prefsFoldersLastSync }
        doAnswer({ invocation ->
                prefsFoldersLastSync = invocation.getArgument(0)
                return@doAnswer null
        }).`when`<Prefs>(mockPrefs).setFoldersLastSyncTs(ArgumentMatchers.any(LocalDateTime::class.java))

        // ** prefsAnnotationsServerSince **
        `when`(mockPrefs.annotationsServerSinceTs).thenAnswer { prefsAnnotationsServerSince }
        doAnswer({ invocation ->
                prefsAnnotationsServerSince = invocation.getArgument(0)
                return@doAnswer null
        }).`when`<Prefs>(mockPrefs).setAnnotationsServerSinceTs(ArgumentMatchers.any(String::class.java))

        // ** prefsAnnotationssLastSync **
        `when`(mockPrefs.annotationsLastSyncTs).thenAnswer { prefsAnnotationsLastSync }
        doAnswer({ invocation ->
                prefsAnnotationsLastSync = invocation.getArgument(0)
                return@doAnswer null
        }).`when`<Prefs>(mockPrefs).setAnnotationsLastSyncTs(ArgumentMatchers.any(LocalDateTime::class.java))
    }

    @Throws(IOException::class)
    private fun setResponseFilesNothingNew() {
        setResponseFiles("src/test/resources/annotations/nothing-new/folder-2-in.json", "src/test/resources/annotations/nothing-new/annotation-2-changes-in.json", "src/test/resources/annotations/nothing-new/annotation-4-in.json")
    }

    @Throws(IOException::class)
    private fun setResponseFiles(folder2In: String, annotation2In: String, annotation4In: String) {
        // ignore actual server calls
        doAnswer {
            FileUtils.copyFileToDirectory(File(folder2In), SYNC_DIR)
            true
        }.`when`(annotationSync).syncFoldersToServer()
        doAnswer {
            FileUtils.copyFileToDirectory(File(annotation2In), SYNC_DIR)
            true
        }.`when`(annotationSync).sendAnnotationsToServer()
        doAnswer {
            FileUtils.copyFileToDirectory(File(annotation4In), SYNC_DIR)
            true
        }.`when`(annotationSync).requestAnnotationsFromServer()
    }

    @Throws(IOException::class)
    private fun syncSmallData() {
        setResponseFiles("src/test/resources/annotations/small-new/folder-2-in.json", "src/test/resources/annotations/small-new/annotation-2-changes-in.json", "src/test/resources/annotations/small-new/annotation-4-in.json")
        annotationSync.sync()
    }

    @Throws(IOException::class)
    private fun syncLargeData() {
        setResponseFiles("src/test/resources/annotations/large-new/folder-2-in.json", "src/test/resources/annotations/large-new/annotation-2-changes-in.json", "src/test/resources/annotations/large-new/annotation-4-in.json")
        annotationSync.sync()
    }

    private fun verifySmallSync(): AnnotationResults {
        val results = AnnotationResults()
        results.setAnnotationCount(2)
        results.annotationsReceived = 2 // same as setAnnotationCount because we requested "normal" annotations (this is a new sync... don't send deleted)
        results.annotationsAdded = 2
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.bookmarkCount = 1
        results.highlightCount = 1
        results.linkCount = 0
        results.tagCount = 1
        results.noteCount = 0
        results.notebooksSaved = 1
        results.setNotebookAnnotationCount(0)
        validateResults(results, buildActualResults())

        return results
    }

    private fun verifyLargeSync(): AnnotationResults {
        val results = AnnotationResults()
        results.setAnnotationCount(INITIAL_ANNOTATION_COUNT.toLong())
        results.annotationsReceived = INITIAL_ANNOTATION_COUNT.toLong() // same as setAnnotationCount because we requested "normal" annotations (this is a new sync... don't send deleted)
        results.annotationsAdded = INITIAL_ANNOTATION_COUNT.toLong()
        results.annotationsUpdated = 0
        results.annotationsRemoved = 0
        results.bookmarkCount = INITIAL_BOOKMARK_COUNT.toLong()
        results.highlightCount = INITIAL_HIGHLIGHT_COUNT.toLong()
        results.linkCount = INITIAL_LINK_COUNT.toLong()
        results.tagCount = INITIAL_TAG_COUNT.toLong()
        results.noteCount = INITIAL_NOTE_COUNT.toLong()
        results.notebooksSaved = INITIAL_NOTEBOOK_COUNT.toLong()
        results.setNotebookAnnotationCount(INITIAL_NOTEBOOK_ANNOTATION_COUNT.toLong())
        validateResults(results, buildActualResults())

        return results
    }

    @Throws(IOException::class)
    private fun setupCleanLargeSyncAndVerify(): AnnotationResults {
        resetPrefs()
        annotationSync.setCleanupAfterSync(false) // allow the unit test to test created .json files created during sync

        // perform fresh sync of large data
        syncLargeData()

        // make sure schedule sync mock count is 0
        verify<AnnotationSyncScheduler>(mockSyncScheduler, times(0)).scheduleSync()

        return verifyLargeSync()
    }

    @Throws(IOException::class)
    private fun performNoIncomingChangeAnnotationSync() {
        // sync
        setResponseFilesNothingNew()
        annotationSync.sync()

        // because there is nothing new.... we should NOT make second request to annotation service
        assertFalse(mockFileUtil.syncAnnotations3OutFile.exists())
    }

    companion object {
        private val SYNC_DIR = File(TestFilesystem.INTERNAL_FILES_DIR_PATH, "sync")
        private val ANNOTATION_1_OUT_FILE = File(SYNC_DIR, GLFileUtil.SYNC_ANNOTATION_1_OUT_FILENAME)
        private val FOLDER_1_OUT_FILE = File(SYNC_DIR, GLFileUtil.SYNC_FOLDER_1_OUT_FILENAME)

        private val INITIAL_ANNOTATION_COUNT = 383
        private val INITIAL_BOOKMARK_COUNT = 19
        private val INITIAL_HIGHLIGHT_COUNT = 395
        private val INITIAL_LINK_COUNT = 7
        private val INITIAL_TAG_COUNT = 69
        private val INITIAL_NOTE_COUNT = 81
        private val INITIAL_NOTEBOOK_COUNT = 7
        private val INITIAL_NOTEBOOK_ANNOTATION_COUNT = 8
    }


}
