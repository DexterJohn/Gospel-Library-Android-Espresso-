package org.lds.ldssa.util;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.DatabaseManagerConst;
import org.lds.ldssa.model.database.tips.tipsmetadata.TipsMetaDataManager;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("deprecation")
public class TipsUpdateUtilTest {

    @Inject
    TipsUpdateUtil tipsUpdateUtil;
    @Inject
    GLFileUtil glFileUtil;
    @Inject
    LdsNetworkUtil networkUtil;
    @Inject
    Prefs prefs;
    @Inject
    TipsMetaDataManager tipsMetaDataManager;
    @Inject
    TipsUtil tipsUtil;
    @Inject
    GLFileUtil fileUtil;
    @Inject
    DatabaseManager databaseManager;

    @Before
    public void setUp() throws Exception {
        TipsUpdateUtilTestComponent component = DaggerTipsUpdateUtilTestComponent.builder().tipsUpdateUtilTestModule(new TipsUpdateUtilTest.TipsUpdateUtilTestModule()).build();
        component.inject(this);

        Timber.plant(new JavaTree());

        TestFilesystem.deleteFilesystem();

        // don't use DownloadManager to download content
        when(prefs.isDirectDownload()).thenReturn(true);

        tipsUpdateUtil = spy(tipsUpdateUtil);
        doNothing().when(tipsUpdateUtil).downloadTips(anyInt());
    }


    @Test
    public void prepareTipsDatabase() throws Exception {
        assertTrue(tipsUpdateUtil.prepareTipsDatabase());
        assertTrue(glFileUtil.getTipsFile().exists());
    }

    @Test
    public void updateTipsNoTips() throws Exception {
        doReturn(5).when(tipsUtil).fetchTipsVersion();

        tipsUpdateUtil.updateTips();

        verify(tipsUpdateUtil, times(1)).downloadTips(anyInt());
    }

    @Test
    public void updateTipsExistingTipsNoUpdate() throws Exception {
        // because we are moving the tips database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME);

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_PATH), fileUtil.getTipsFile());
        assertTrue(fileUtil.getTipsFile().exists());

        int newVersion = tipsMetaDataManager.findVersion();

        doReturn(newVersion).when(tipsUtil).fetchTipsVersion();

        tipsUpdateUtil.updateTips();

        verify(tipsUpdateUtil, times(0)).downloadTips(anyInt());
    }

    @Test
    public void updateTipsExistingTipsUpdateAvailable() throws Exception {
        // because we are moving the tips database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME);

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_PATH), fileUtil.getTipsFile());
        assertTrue(fileUtil.getTipsFile().exists());

        // identify that there is a new version by taking the existing tips and incrementing the number
        int newVersion = tipsMetaDataManager.findVersion() + 1;

        doReturn(newVersion).when(tipsUtil).fetchTipsVersion();

        tipsUpdateUtil.updateTips();

        verify(tipsUpdateUtil, times(1)).downloadTips(anyInt());
    }

    @Test
    public void updateTipsExistingTipsUpdateAlreadyDownloaded() throws Exception {
        // because we are moving the tips database... close the database first
        databaseManager.closeDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME);

        // copy in sample downloaded tips zip
        File downloadedTipsZipFile = fileUtil.getTipsZipDownloadFile();
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_ZIP_PATH), downloadedTipsZipFile);
        assertTrue(downloadedTipsZipFile.exists());

        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_PATH), fileUtil.getTipsFile());
        assertTrue(fileUtil.getTipsFile().exists());

        int newVersion = tipsMetaDataManager.findVersion();

        // fake an older tips by changing the version to an older version number
        int oldVersion = newVersion - 1;
        tipsMetaDataManager.updateVersion(oldVersion);
        assertEquals(oldVersion, tipsMetaDataManager.findVersion());

        // mock/override
        doReturn(newVersion).when(tipsUtil).fetchTipsVersion();

        tipsUpdateUtil.updateTips();

        // check to make sure the new version got updated
        assertEquals(newVersion, tipsMetaDataManager.findVersion());

        verify(tipsUpdateUtil, times(1)).installDownloadedTips();
        verify(tipsUpdateUtil, times(0)).downloadTips(anyInt());
        assertFalse(downloadedTipsZipFile.exists());
    }

    @Test
    public void updateTipsNoNetwork() throws Exception {
        // make sure no check is performed if there is no network
        when(networkUtil.isConnected()).thenReturn(false);
        when(networkUtil.isConnected(anyBoolean())).thenReturn(false);

        tipsUpdateUtil.updateTips();

        verify(prefs, times(0)).updateLastTipsUpdateTime();
        verify(tipsUpdateUtil, times(0)).downloadTips(anyInt());
    }

    @Test
    public void swapTipsUpdateAvailable() throws Exception {
        // copy in sample downloaded tips zip
        File downloadedTipsZipFile = fileUtil.getTipsZipDownloadFile();
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_ZIP_PATH), downloadedTipsZipFile);
        assertTrue(downloadedTipsZipFile.exists());

        tipsUpdateUtil.installDownloadedTips();

        assertFalse(downloadedTipsZipFile.exists());
        assertFalse(fileUtil.getTempCatalogFile().exists());
    }

    @Test
    public void installDownloadedTipsNothingToInstall() throws Exception {
        assertFalse(tipsUpdateUtil.installDownloadedTips());

        // if there is nothing to install... don't remove the existing tips
        verify(tipsUpdateUtil, times(0)).removeExistingTips(any(File.class));
    }

    @Test
    public void installDownloadedTipsNothingToInstallExistingTips() throws Exception {
        // copy in sample database
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_PATH), fileUtil.getTipsFile());
        assertTrue(fileUtil.getTipsFile().exists());

        assertFalse(tipsUpdateUtil.installDownloadedTips());

        // if there is nothing to install... don't remove the existing tips
        assertTrue(fileUtil.getTipsFile().exists());
    }

    @Test
    public void installDownloadedTipsUpdateAvailable() throws Exception {
        // copy in sample downloaded tips zip
        File downloadedTipsZipFile = fileUtil.getTipsZipDownloadFile();
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_ZIP_PATH), downloadedTipsZipFile);
        assertTrue(downloadedTipsZipFile.exists());

        // make sure there is NO existing tips
        assertFalse(fileUtil.getTipsFile().exists());

        assertTrue(tipsUpdateUtil.installDownloadedTips());

        assertTrue(fileUtil.getTipsFile().exists());
        assertFalse(downloadedTipsZipFile.exists());
        assertFalse(fileUtil.getTempTipsFile().exists());
    }

    @Test
    public void installDownloadedTipsCorruptUpdateAvailable() throws Exception {
        // copy in sample tips
        FileUtils.copyFile(new File(TestFilesystem.LATEST_SAMPLE_TIPS_PATH), fileUtil.getTipsFile());
        assertTrue(fileUtil.getTipsFile().exists());

        // copy a file that is NOT a zip file
        File downloadedTipsZipFile = fileUtil.getTipsZipDownloadFile();
        FileUtils.copyFile(new File(TestFilesystem.ASSETS_DIR_PATH, "index.json"), downloadedTipsZipFile);
        assertTrue(downloadedTipsZipFile.exists());

        assertFalse(tipsUpdateUtil.installDownloadedTips());

        // the existing tips should not have been damaged
        assertTrue(fileUtil.getTipsFile().exists());
        assertTrue(tipsUpdateUtil.verifyTips());
        assertFalse(downloadedTipsZipFile.exists());
        assertFalse(fileUtil.getTempTipsFile().exists());
    }

    @Module
    public class TipsUpdateUtilTestModule {

        @Provides
        @Singleton
        TipsService provideTipsService() {
            return mock(TipsService.class);
        }

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
    }

}