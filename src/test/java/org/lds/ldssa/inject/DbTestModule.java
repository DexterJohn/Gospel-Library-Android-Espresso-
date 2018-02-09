package org.lds.ldssa.inject;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.GlDefaultDatabaseConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbTestModule {
    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig() {
        TestFilesystem.copyGlBlankDatabase();
        return GlDefaultDatabaseConfig.getInstance();
    }
}
