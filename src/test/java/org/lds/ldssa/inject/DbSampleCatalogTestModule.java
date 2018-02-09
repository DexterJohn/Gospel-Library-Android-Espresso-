package org.lds.ldssa.inject;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldssa.model.database.GlTestSampleCatalogDatabaseConfig;
import org.lds.ldssa.TestFilesystem;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbSampleCatalogTestModule {
    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig() {
        TestFilesystem.copyGlBlankDatabase();
        return GlTestSampleCatalogDatabaseConfig.getInstance();
    }
}
