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
 * - Catalog
 * - Tips
 */
public class GlDefaultDatabaseConfig extends TestDatabaseConfig {

    public static GlDefaultDatabaseConfig getInstance() {
        List<AndroidDatabase> databases = new ArrayList<>();

        // user database
        databases.add(new AndroidDatabase(TestFilesystem.DEFAULT_USERDATABASE_NAME, TestFilesystem.getUserDatabasePath(), DatabaseManager.GL_VERSION, DatabaseManager.GL_VIEWS_VERSION));

        // gl database
        databases.add(new AndroidDatabase(DatabaseManagerConst.GL_DATABASE_NAME, TestFilesystem.GL_DATABASE_PATH, DatabaseManager.GL_VERSION, DatabaseManager.GL_VIEWS_VERSION));

        // catalog
        databases.add(new AndroidDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME, TestFilesystem.CATALOG_DATABASE_PATH, DatabaseManager.CATALOG_VERSION, DatabaseManager.CATALOG_VIEWS_VERSION));

        // tips
        databases.add(new AndroidDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME, TestFilesystem.TIPS_DATABASE_PATH, DatabaseManager.TIPS_VERSION, DatabaseManager.TIPS_VIEWS_VERSION));

        return new GlDefaultDatabaseConfig(databases);
    }

    public GlDefaultDatabaseConfig(@Nonnull List<AndroidDatabase> androidDatabaseList) {
        super(androidDatabaseList);
    }
}
