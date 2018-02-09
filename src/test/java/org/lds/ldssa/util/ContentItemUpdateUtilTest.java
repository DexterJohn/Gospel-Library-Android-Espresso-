package org.lds.ldssa.util;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.types.CatalogItemSourceType;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.mobile.log.JavaTree;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@SuppressWarnings("deprecation")
public class ContentItemUpdateUtilTest {

    @Inject
    ContentItemUpdateUtil contentItemUpdateUtil;
    @Inject
    GLFileUtil glFileUtil;
    @Inject
    ContentMetaDataManager contentMetaDataManager;

    @Before
    public void setUp() throws Exception {
        ContentItemUpdateUtilTestComponent component = DaggerContentItemUpdateUtilTestComponent.builder()
                .contentItemUpdateUtilTestModule(new ContentItemUpdateUtilTest.ContentItemUpdateUtilTestModule())
                .build();
        component.inject(this);

        Timber.plant(new JavaTree());

        TestFilesystem.deleteFilesystem();

        contentItemUpdateUtil = spy(contentItemUpdateUtil);
    }

    @Test
    public void installContentItem() throws Exception {
        long androidDownloadId = 0; // some reference to the download manager
        long contentItemId = 201392133; // book of mormon
        int version = 25;
        File contentArchiveFile = TestFilesystem.getContentArchive("bom." + version + ".zip");
        File downloadedContentZipFile = new File(TestFilesystem.DOWNLOADS_DIR, "bom.zip");

        // "Download" the content file
        assertTrue(contentArchiveFile.exists());
        FileUtils.copyFile(contentArchiveFile, downloadedContentZipFile);

        // Install
        assertTrue(contentItemUpdateUtil.installContentItem(androidDownloadId, contentItemId, downloadedContentZipFile, CatalogItemSourceType.DEFAULT));

        // make sure content item exists in the right place
        File contentItemDir = glFileUtil.getContentItemDir(contentItemId);
        File contentItemDb = glFileUtil.getContentItemDatabase(contentItemId);
        assertTrue(contentItemDir.exists());
        assertTrue(contentItemDb.exists());

        // open the file and check the version
        assertEquals(String.valueOf(version), contentMetaDataManager.findItemPackageVersion(contentItemId));

        // Make sure everything got cleanup
        // assertFalse(downloadedContentZipFile.exists()); // NOTE: Android DownloadManager now deletes the downloaded zip file
        assertFalse(new File(glFileUtil.getContentItemTempDirname(contentItemId)).exists());
    }

    @Test
    public void installContentItemMissingDownload() throws Exception {
        long androidDownloadId = 0; // some reference to the download manager
        long contentItemId = 201392133; // book of mormon
        int version = 25;
        File contentArchiveFile = new File("missing-file.zip");
        File downloadedContentZipFile = new File(TestFilesystem.DOWNLOADS_DIR, "bom.zip");

        // The file should NOT exist
        assertFalse(contentArchiveFile.exists());

        // Install
        assertFalse(contentItemUpdateUtil.installContentItem(androidDownloadId, contentItemId, downloadedContentZipFile, CatalogItemSourceType.DEFAULT));

        // make sure content item DOES NOT exists in the right place
        File contentItemDir = glFileUtil.getContentItemDir(contentItemId);
        File contentItemDb = glFileUtil.getContentItemDatabase(contentItemId);
        assertFalse(contentItemDir.exists());
        assertFalse(contentItemDb.exists());

        // Make sure everything got cleanup
        assertFalse(downloadedContentZipFile.exists());
        assertFalse(new File(glFileUtil.getContentItemTempDirname(contentItemId)).exists());
    }

    @Test
    public void installContentItemBadContentItemId() throws Exception {
        long androidDownloadId = 0; // some reference to the download manager
        long contentItemId = 9999999; // book of mormon
        int version = 25;
        File contentArchiveFile = TestFilesystem.getContentArchive("some-file.zip");
        File downloadedContentZipFile = new File(TestFilesystem.DOWNLOADS_DIR, "bom.zip");

        // The file should NOT exist
        assertFalse(contentArchiveFile.exists());

        // Install
        assertFalse(contentItemUpdateUtil.installContentItem(androidDownloadId, contentItemId, downloadedContentZipFile, CatalogItemSourceType.DEFAULT));

        // make sure content item DOES NOT exists in the right place
        File contentItemDir = glFileUtil.getContentItemDir(contentItemId);
        File contentItemDb = glFileUtil.getContentItemDatabase(contentItemId);
        assertFalse(contentItemDir.exists());
        assertFalse(contentItemDb.exists());

        // Make sure everything got cleanup
        assertFalse(downloadedContentZipFile.exists());
        assertFalse(new File(glFileUtil.getContentItemTempDirname(contentItemId)).exists());
    }

    @Module
    public class ContentItemUpdateUtilTestModule {

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