package org.lds.ldssa.task;

import android.app.Application;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.GlTestSampleCatalogDatabaseConfig;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.mobile.util.LdsZipUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@Module
public class AnnotationFixTaskTestModule {

    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig() {
        String dbName = "userdata-corrupt";
        String sourceGlPath = "src/test/resources/db/" + dbName;
        TestFilesystem.copyUserDatabaseToTestFilesystem(sourceGlPath);

        return GlTestSampleCatalogDatabaseConfig.getInstance();
    }

    @Provides
    @Singleton
    ContentItemUtil provideContentItemUtil() {
        ContentItemUtil contentItemUtil =  mock(ContentItemUtil.class);
        doReturn(true).when(contentItemUtil).isItemDownloadedAndOpen(anyLong());
        return contentItemUtil;
    }

    @Provides
    @Singleton
    GLFileUtil provideGLFileUtil(LdsZipUtil ldsZipUtil) {
        ToastUtil toastUtil = mock(ToastUtil.class);
        return new GLFileUtil(mock(Application.class), ldsZipUtil, toastUtil);
    }
}
