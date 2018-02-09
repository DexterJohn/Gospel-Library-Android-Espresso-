package org.lds.ldssa.model.database.search.searchsuggestion;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.model.database.GlTestSampleCatalogDatabaseConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchSuggestionManagerTestModule {

    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig() {
        TestFilesystem.copySearchDatabaseToTestFilesystem("src/test/resources/db/search/search");
        return GlTestSampleCatalogDatabaseConfig.getInstance();
    }
}
