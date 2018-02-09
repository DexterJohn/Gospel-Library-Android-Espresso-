package org.lds.ldssa.download;

import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItem;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.model.ContentServerType;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.ldssa.model.webservice.tips.TipsService;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.util.LdsNetworkUtil;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DownloadContentTest {

    @Inject
    LdsNetworkUtil networkUtil;
    @Inject
    Prefs prefs;

    @Inject
    DownloadedItemManager downloadedItemManager;
    @Inject
    DownloadQueueItemManager downloadQueueItemManager;
    @Inject
    GLDownloadManager glDownloadManager;
    @Inject
    AnnotationSyncScheduler annotationSyncScheduler;
    @Inject
    DownloadManagerHelper downloadManagerHelper;
    @Inject
    GLFileUtil fileUtil;

    @Before
    public void setUp() throws Exception {
        DownloadTestComponent component = DaggerDownloadTestComponent.builder().downloadTestModule(new DownloadTestModule()).build();
        component.inject(this);

        TestFilesystem.deleteFilesystem();

        // don't use DownloadManager to download content
        when(prefs.isDirectDownload()).thenReturn(true);
    }

    public static final long BOOK_OF_MORMON_CONTENT_ITEM_ID = 201392133;

    @Test
    public void testDownloadSingleBook() throws Exception {
        when(networkUtil.isConnected()).thenReturn(true);
        when(networkUtil.isConnected(anyBoolean())).thenReturn(true);
        when(prefs.getContentServerType()).thenReturn(ContentServerType.PROD);

        // since there is no access to the Android DownloadManager.... mock the request for getting the destinationUri from the AndroidDownloadManager
        File contentItemZipFile = fileUtil.getContentItemZipDownloadFile(BOOK_OF_MORMON_CONTENT_ITEM_ID);
        if (contentItemZipFile != null) {
            assertNotNull(contentItemZipFile);
            when(downloadManagerHelper.getDestinationUri(anyLong())).thenReturn(contentItemZipFile.toURI().toString());
        }
        glDownloadManager.downloadContentDirect(BOOK_OF_MORMON_CONTENT_ITEM_ID);

        // sync should happen if the version changed
        verify(annotationSyncScheduler, times(1)).scheduleSync();

        // make sure the queue is empty
        assertEquals(0, downloadQueueItemManager.findCount());

        // make sure there is only one downloaded item
        assertEquals(1, downloadedItemManager.findCount());

        // make sure the 1 item is the bom
        DownloadedItem downloadedItem = downloadedItemManager.findByContentItemId(BOOK_OF_MORMON_CONTENT_ITEM_ID);
        assertNotNull(downloadedItem);
        assertEquals(BOOK_OF_MORMON_CONTENT_ITEM_ID, downloadedItem.getContentItemId());

        // make sure the content database exists
        assertTrue(verifyContentDbExists(BOOK_OF_MORMON_CONTENT_ITEM_ID));
    }

    private boolean verifyContentDbExists(long contentItemId) {
        File contentDir = new File(TestFilesystem.INTERNAL_FILES_DIR_PATH + "/content/content-databases/" + contentItemId);
        File contentDatabase = new File(contentDir, "package.sqlite");

        return contentDatabase.exists();
    }

    @Module
    public class DownloadTestModule {
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