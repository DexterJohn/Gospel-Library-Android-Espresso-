package org.lds.ldssa.util;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.DatabaseManagerConst;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.item.Item;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.ldssa.model.webservice.tips.TipsService;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.log.JavaTree;
import org.lds.mobile.util.LdsNetworkUtil;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("deprecation")
public class CatalogUpdateUtilTest {

    public static final long TEST_DIFF_START_VERSION = 13;
    public static final long TEST_DIFF_END_VERSION = 1234;
    public static final long TEST_DIFF_ITEM_ID = 123456;
    public static final String TEST_DIFF_ITEM_TITLE = "Book of Testing";

    @Inject
    CatalogUpdateUtil catalogUpdateUtil;
    @Inject
    GLFileUtil glFileUtil;
    @Inject
    LdsNetworkUtil networkUtil;
    @Inject
    Prefs prefs;
    @Inject
    CatalogMetaDataManager catalogMetaDataManager;
    @Inject
    ItemManager itemManager;
    @Inject
    CatalogUtil catalogUtil;
    @Inject
    GLFileUtil fileUtil;
    @Inject
    DatabaseManager databaseManager;

    @Before
    public void setUp() throws Exception {
        CatalogUpdateUtilTestComponent component = DaggerCatalogUpdateUtilTestComponent.builder().catalogUpdateUtilTestModule(new CatalogUpdateUtilTest.CatalogUpdateUtilTestModule()).build();
        component.inject(this);

        Timber.plant(new JavaTree());

        TestFilesystem.deleteFilesystem();

        // don't use DownloadManager to download content
        when(prefs.isDirectDownload()).thenReturn(true);

        catalogUpdateUtil = spy(catalogUpdateUtil);
        doNothing().when(catalogUpdateUtil).downloadCoreCatalog(anyInt(), anyBoolean());
        doNothing().when(catalogUpdateUtil).downloadRoleCatalogs();
        doNothing().when(catalogUpdateUtil).updateContentDatabases();
    }


    @Test
    public void prepareCatalogDatabase() throws Exception {
        assertTrue(catalogUpdateUtil.prepareCatalogDatabase());
        assertTrue(glFileUtil.getCatalogFile().exists());
    }

    @Test
    public void updateCatalogNoCatalog() throws Exception {
        doReturn(5).when(catalogUtil).fetchCatalogVersion(anyString(), anyString());
        doReturn(CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE).when(catalogUpdateUtil).fetchRoleCatalogStatus();

        catalogUpdateUtil.updateCatalog(false);

        verify(catalogUpdateUtil, times(1)).downloadCoreCatalog(anyInt(), anyBoolean());
    }

    @Test
    public void updateCatalogExistingCatalogNoUpdate() throws Exception {
        // because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        int newVersion = catalogMetaDataManager.findVersion();

        doReturn(newVersion).when(catalogUtil).fetchCatalogVersion(anyString(), anyString());
        doReturn(CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE).when(catalogUpdateUtil).fetchRoleCatalogStatus();

        catalogUpdateUtil.updateCatalog(false);

        verify(catalogUpdateUtil, times(0)).downloadCoreCatalog(anyInt(), anyBoolean());
    }

    @Test
    public void updateCatalogExistingCatalogUpdateAvailable() throws Exception {
        // because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        // identify that there is a new version by taking the existing catalog and incrementing the number
        int newVersion = catalogMetaDataManager.findVersion() + 1;

        doReturn(newVersion).when(catalogUtil).fetchCatalogVersion(anyString(), anyString());
        doReturn(CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE).when(catalogUpdateUtil).fetchRoleCatalogStatus();

        catalogUpdateUtil.updateCatalog(false);

        verify(catalogUpdateUtil, times(1)).downloadCoreCatalog(anyInt(), anyBoolean());
    }

    @Test
    public void updateCatalogExistingCatalogUpdateAlreadyDownloaded() throws Exception {
        // because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // copy in sample downloaded catalog zip
        File downloadedCatalogZipFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_ZIP_PATH), downloadedCatalogZipFile);
        assertTrue(downloadedCatalogZipFile.exists());

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        int newVersion = catalogMetaDataManager.findVersion();

        // fake an older catalog by changing the version to an older version number
        int oldVersion = newVersion - 1;
        catalogMetaDataManager.updateVersion(oldVersion);
        assertEquals(oldVersion, catalogMetaDataManager.findVersion());

        // mock/override
        doReturn(newVersion).when(catalogUtil).fetchCatalogVersion(anyString(), anyString());
        doReturn(CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE).when(catalogUpdateUtil).fetchRoleCatalogStatus();

        catalogUpdateUtil.updateCatalog(false);

        // updateCatalog(...) applies updates by restarting the app... which then calls prepareCatalogDatabase()...
        // so... emulate a restart of the app...
        catalogUpdateUtil.prepareCatalogDatabase();

        // check to make sure the new version got updated
        assertEquals(newVersion, catalogMetaDataManager.findVersion());

        verify(catalogUpdateUtil, times(1)).swapCatalog();
        verify(catalogUpdateUtil, times(1)).updateContentDatabases();
        verify(catalogUpdateUtil, times(0)).downloadCoreCatalog(anyInt(), anyBoolean());
        assertFalse(downloadedCatalogZipFile.exists());
    }

    @Test
    public void updateCatalogNoNetwork() throws Exception {
        // make sure no check is performed if there is no network
        when(networkUtil.isConnected()).thenReturn(false);
        when(networkUtil.isConnected(anyBoolean())).thenReturn(false);

        catalogUpdateUtil.updateCatalog(false);

        verify(prefs, times(0)).updateLastCatalogUpdateTime();
        verify(catalogUpdateUtil, times(0)).downloadCoreCatalog(anyInt(), anyBoolean());
    }

    /**
     * User no longer has a position that allows them to have some rolebased content
     */
    @Test
    public void updateCatalogNoUpdateRoleRebuild() throws Exception {
        // because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        int newVersion = catalogMetaDataManager.findVersion();

        // there is NO new version
        doReturn(newVersion).when(catalogUtil).fetchCatalogVersion(anyString(), anyString());

        // identify that the catalog needs to be rebuilt
        doReturn(CatalogUtil.RoleCatalogUpdateStatus.REBUILD_CATALOG).when(catalogUpdateUtil).fetchRoleCatalogStatus();

        catalogUpdateUtil.updateCatalog(false);

        // check to make sure the new version got updated
        assertEquals(newVersion, catalogMetaDataManager.findVersion());

        // only installArchivedCatalog should be called
        verify(catalogUpdateUtil, times(0)).swapCatalog();
        verify(catalogUpdateUtil, times(0)).updateContentDatabases();
        verify(catalogUpdateUtil, times(1)).installArchivedCatalog();
    }

    @Test
    public void swapCatalogNothingToSwap() throws Exception {
        catalogUpdateUtil.swapCatalog();

        verify(catalogUpdateUtil, times(0)).updateContentDatabases();
    }

    @Test
    public void swapCatalogUpdateAvailable() throws Exception {
        // copy in sample downloaded catalog zip
        File downloadedCatalogZipFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_ZIP_PATH), downloadedCatalogZipFile);
        assertTrue(downloadedCatalogZipFile.exists());

        catalogUpdateUtil.swapCatalog();

        verify(catalogUpdateUtil, times(1)).updateContentDatabases();
        assertFalse(downloadedCatalogZipFile.exists());
        assertFalse(fileUtil.getTempCatalogFile().exists());
    }

    @Test
    public void installDownloadedCatalogNothingToInstall() throws Exception {
        assertFalse(catalogUpdateUtil.installDownloadedCatalog());

        // if there is nothing to install... don't remove the existing catalog
        verify(catalogUpdateUtil, times(0)).removeExistingCatalog(any(File.class));
    }

    @Test
    public void installDownloadedCatalogNothingToInstallExistingCatalog() throws Exception {
        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        assertFalse(catalogUpdateUtil.installDownloadedCatalog());

        // if there is nothing to install... don't remove the existing catalog
        assertTrue(fileUtil.getCatalogFile().exists());
    }

    @Test
    public void installDownloadedCatalogUpdateAvailable() throws Exception {
        // copy in sample downloaded catalog zip
        File downloadedCatalogZipFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_ZIP_PATH), downloadedCatalogZipFile);
        assertTrue(downloadedCatalogZipFile.exists());

        // make sure there is NO existing catalog
        assertFalse(fileUtil.getCatalogFile().exists());

        assertTrue(catalogUpdateUtil.installDownloadedCatalog());

        assertTrue(fileUtil.getCatalogFile().exists());
        assertFalse(downloadedCatalogZipFile.exists());
        assertFalse(fileUtil.getTempCatalogFile().exists());
    }

    @Test
    public void installDownloadedCatalogCorruptUpdateAvailable() throws Exception {
        // copy in sample catalog
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        // copy a file that is NOT a zip file
        File downloadedCatalogZipFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.ASSETS_DIR_PATH, "index.json"), downloadedCatalogZipFile);
        assertTrue(downloadedCatalogZipFile.exists());

        assertFalse(catalogUpdateUtil.installDownloadedCatalog());

        // the existing catalog should not have been damaged
        assertTrue(fileUtil.getCatalogFile().exists());
        assertTrue(catalogUpdateUtil.verifyCatalog());
        assertFalse(downloadedCatalogZipFile.exists());
        assertFalse(fileUtil.getTempCatalogFile().exists());
    }

    @Test
    public void diffUpgradeCatalogNoDiffFile() throws Exception {
        // Because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);
        long newVersion = catalogMetaDataManager.findVersion();
        assertFalse(catalogUpdateUtil.diffUpgradeCatalog(newVersion));
    }

    @Test
    public void diffUpgradeCatalogUpdate() throws Exception {
        // Because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        // Set the database version to the beginning version
        catalogMetaDataManager.updateVersion(TEST_DIFF_START_VERSION);

        // Copy in a sample downloaded diff zip
        File downloadedCatalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.SAMPLE_CATALOG_DIFF_ZIP_PATH), downloadedCatalogDiffZipFile);
        assertTrue(downloadedCatalogDiffZipFile.exists());

        // Make sure the diff upgrade returns success
        assertTrue(catalogUpdateUtil.diffUpgradeCatalog(TEST_DIFF_END_VERSION));

        // Make sure the diff was applied
        assertEquals(TEST_DIFF_END_VERSION, catalogMetaDataManager.findVersion());
        Item item = itemManager.findByRowId(TEST_DIFF_ITEM_ID);
        assertNotNull(item);
        assertTrue(item.getTitle().equals(TEST_DIFF_ITEM_TITLE));

        // Make sure the temporary/diff files were cleaned up
        assertFalse(downloadedCatalogDiffZipFile.exists());
        assertFalse(fileUtil.getTempCatalogDiffFile().exists());
    }

    @Test
    public void diffUpgradeCatalogMissingCatalog() throws Exception {
        // Because we are moving the catalog database... close the database first
        databaseManager.removeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Copy in a sample downloaded diff zip
        File downloadedCatalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.SAMPLE_CATALOG_DIFF_ZIP_PATH), downloadedCatalogDiffZipFile);
        assertTrue(downloadedCatalogDiffZipFile.exists());

        // Make sure the diff upgrade returns failure
        assertFalse(catalogUpdateUtil.diffUpgradeCatalog(TEST_DIFF_END_VERSION));

        // Make sure the temporary/diff files were cleaned up
        assertFalse(downloadedCatalogDiffZipFile.exists());
        assertFalse(fileUtil.getTempCatalogDiffFile().exists());
    }

    @Test
    public void diffUpgradeCatalogDiffApplicationFailed() throws Exception {
        // Because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        // Set the database version to the beginning version
        catalogMetaDataManager.updateVersion(TEST_DIFF_START_VERSION);

        // Copy in a sample downloaded diff zip
        File downloadedCatalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.SAMPLE_CATALOG_DIFF_ZIP_PATH), downloadedCatalogDiffZipFile);
        assertTrue(downloadedCatalogDiffZipFile.exists());

        // Make sure the diff upgrade returns failure because it compares the wrong version
        assertFalse(catalogUpdateUtil.diffUpgradeCatalog(TEST_DIFF_START_VERSION));

        // Make sure the temporary/diff files were cleaned up
        assertFalse(downloadedCatalogDiffZipFile.exists());
        assertFalse(fileUtil.getTempCatalogDiffFile().exists());
    }

    @Test
    public void diffUpgradeCatalogUpdateSecureContent() throws Exception {
        // Because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Copy in sample catalog database
        File catalogFile = fileUtil.getCatalogFile();
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), catalogFile);
        assertTrue(catalogFile.exists());

        // Set the database version to the beginning version
        catalogMetaDataManager.updateVersion(TEST_DIFF_START_VERSION);

        // Copy the catalog to act as the archive
        File archiveFile = fileUtil.getCatalogArchiveFile();
        FileUtils.copyFile(catalogFile, archiveFile);
        assertTrue(archiveFile.exists());
        assertTrue(catalogFile.exists());

        // Copy in a sample downloaded diff zip
        File downloadedCatalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.SAMPLE_CATALOG_DIFF_ZIP_PATH), downloadedCatalogDiffZipFile);
        assertTrue(downloadedCatalogDiffZipFile.exists());

        // Make sure the diff upgrade returns success
        doReturn(true).when(catalogUpdateUtil).catalogContainsSecureContent();
        assertTrue(catalogUpdateUtil.diffUpgradeCatalog(TEST_DIFF_END_VERSION));

        // Make sure the diff was applied
        assertEquals(TEST_DIFF_END_VERSION, catalogMetaDataManager.findVersion());
        Item item = itemManager.findByRowId(TEST_DIFF_ITEM_ID);
        assertNotNull(item);
        assertTrue(item.getTitle().equals(TEST_DIFF_ITEM_TITLE));

        // Load the archive in place of the catalog
        databaseManager.removeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);
        databaseManager.addDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME, archiveFile.getAbsolutePath(), DatabaseManager.CATALOG_VERSION, DatabaseManager.CATALOG_VIEWS_VERSION);
        databaseManager.connectDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Make sure the diff was applied to the archive
        assertEquals(TEST_DIFF_END_VERSION, catalogMetaDataManager.findVersion());
        item = itemManager.findByRowId(TEST_DIFF_ITEM_ID);
        assertNotNull(item);
        assertTrue(item.getTitle().equals(TEST_DIFF_ITEM_TITLE));

        // Make sure the temporary/diff files were cleaned up
        databaseManager.removeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);
        assertFalse(downloadedCatalogDiffZipFile.exists());
        assertFalse(fileUtil.getTempCatalogDiffFile().exists());
    }

    @Test
    public void diffUpgradeCatalogUpdateFailedOnDiff() throws Exception {
        // Because we are moving the catalog database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_CATALOG_PATH), fileUtil.getCatalogFile());
        assertTrue(fileUtil.getCatalogFile().exists());

        // Set the database version to the beginning version
        catalogMetaDataManager.updateVersion(TEST_DIFF_START_VERSION);

        // Copy in a sample downloaded diff zip
        File downloadedCatalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        FileUtils.copyFile(new File(TestFilesystem.SAMPLE_CATALOG_DIFF_FAIL_ZIP_PATH), downloadedCatalogDiffZipFile);
        assertTrue(downloadedCatalogDiffZipFile.exists());

        // Make sure the diff upgrade returns false becasue of the SQL Exception
        assertFalse(catalogUpdateUtil.diffUpgradeCatalog(TEST_DIFF_END_VERSION));

        // Make sure the temporary/diff files were cleaned up
        assertFalse(downloadedCatalogDiffZipFile.exists());
        assertFalse(fileUtil.getTempCatalogDiffFile().exists());
    }


    @Module
    public class CatalogUpdateUtilTestModule {
        @Provides
        @Singleton
        CatalogService provideCatalogService() {
            return mock(CatalogService.class);
        }

        @Provides
        @Singleton
        RoleCatalogService provideRoleCatalogService() {
            return mock(RoleCatalogService.class);
        }

        @Provides
        @Singleton
        RoleBasedContentService provideRoleContentService() {
            return mock(RoleBasedContentService.class);
        }

        @Provides
        @Singleton
        TipsService provideTipsService() {
            return mock(TipsService.class);
        }
    }

}