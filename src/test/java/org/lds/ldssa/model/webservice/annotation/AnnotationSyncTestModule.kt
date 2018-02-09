package org.lds.ldssa.model.webservice.annotation

import dagger.Module
import dagger.Provides
import org.dbtools.android.domain.config.DatabaseConfig
import org.lds.ldsaccount.LDSAccountPrefs
import org.lds.ldssa.TestFilesystem
import org.lds.ldssa.model.database.GlTestSampleCatalogDatabaseConfig
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.ui.notification.AnnotationSyncNotification
import org.lds.mobile.util.LdsThreadUtil
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
class AnnotationSyncTestModule {
    @Provides
    @Singleton
    fun provideAnnotationSyncNotification(): AnnotationSyncNotification {
        return mock(AnnotationSyncNotification::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabaseConfig(): DatabaseConfig {
        TestFilesystem.copyGlBlankDatabase()
        return GlTestSampleCatalogDatabaseConfig.getInstance()
    }

    @Provides
    @Singleton
    fun provideLDSAccountPrefs(): LDSAccountPrefs {
        return mock(LDSAccountPrefs::class.java)
    }

    @Provides
    @Singleton
    fun provideLDSAnnotationService(): LDSAnnotationService {
        return mock(LDSAnnotationService::class.java)
    }

    @Provides
    @Singleton
    fun provideSubItemMetadataManager(): SubItemMetadataManager {
        return mock(SubItemMetadataManager::class.java)
    }

    @Provides
    @Singleton
    fun provideLdsThreadUtil(): LdsThreadUtil {
        val mock = mock(LdsThreadUtil::class.java)
        `when`(mock.isMainThread()).thenReturn(false)
        return mock
    }
}
