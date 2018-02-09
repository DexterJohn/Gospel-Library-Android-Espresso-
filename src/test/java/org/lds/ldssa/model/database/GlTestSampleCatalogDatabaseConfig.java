package org.lds.ldssa.model.database;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.config.TestDatabaseConfig;
import org.lds.ldssa.TestFilesystem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * This database config has:
 * BLANK:
 * - User database
 * - GL Database
 *
 * POPULATED
 * Last downloaded (during build)
 * - Catalog
 */
public class GlTestSampleCatalogDatabaseConfig extends TestDatabaseConfig {

    public static GlTestSampleCatalogDatabaseConfig getInstance() {
        List<AndroidDatabase> databases = new ArrayList<>();

        // user database
        databases.add(new AndroidDatabase(TestFilesystem.DEFAULT_USERDATABASE_NAME, TestFilesystem.getUserDatabasePath(), DatabaseManager.GL_VERSION, DatabaseManager.GL_VIEWS_VERSION));

        // gl database
        databases.add(new AndroidDatabase(DatabaseManagerConst.GL_DATABASE_NAME, TestFilesystem.GL_DATABASE_PATH, DatabaseManager.GL_VERSION, DatabaseManager.GL_VIEWS_VERSION));

        // catalog
        databases.add(new AndroidDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME, TestFilesystem.LATEST_SAMPLE_CATALOG_PATH, DatabaseManager.CATALOG_VERSION, DatabaseManager.CATALOG_VIEWS_VERSION));

        // search
        databases.add(new AndroidDatabase(DatabaseManagerConst.SEARCH_DATABASE_NAME, TestFilesystem.SEARCH_DATABASE_PATH, DatabaseManager.SEARCH_VERSION, DatabaseManager.SEARCH_VIEWS_VERSION));

        // tips
//        databases.add(new AndroidDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME, BuildEnv.GRADLE.getTestDbDir() + DatabaseManagerConst.TIPS_DATABASE_NAME, DatabaseManager.TIPS_VERSION, DatabaseManager.TIPS_VIEWS_VERSION));

        return new GlTestSampleCatalogDatabaseConfig(databases);
    }

    public GlTestSampleCatalogDatabaseConfig(@Nonnull List<AndroidDatabase> androidDatabaseList) {
        super(androidDatabaseList);
    }
}
