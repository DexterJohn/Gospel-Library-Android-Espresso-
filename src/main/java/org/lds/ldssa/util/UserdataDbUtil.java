package org.lds.ldssa.util;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.DatabaseManagerConst;
import org.lds.ldssa.model.prefs.Prefs;

import java.io.File;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserdataDbUtil {
    private final Prefs prefs;
    private final GLFileUtil fileUtil;
    private final DatabaseManager databaseManager;

    private long currentPersonId;
    private int currentInstanceId;
    private String currentDatabaseName;

    @Inject
    public UserdataDbUtil(Prefs prefs, GLFileUtil fileUtil, DatabaseManager databaseManager) {
        this.prefs = prefs;
        this.fileUtil = fileUtil;
        this.databaseManager = databaseManager;
    }

    public boolean openCurrentDatabase() {
        return openDatabase(prefs.getCurrentPersonId(), prefs.getCurrentPersonAnnotationInstanceId());
    }

    public boolean openDatabase(long personId, int instanceId) {
        String databaseName = getDatabaseName(personId, instanceId);
        return openDatabase(databaseName);
    }

    public boolean openDatabase(String databaseName) {
        if (databaseManager.containsDatabase(databaseName)) {
            return true;
        } else {
            File userdataDatabaseFile = getDatabaseFile(databaseName);
            databaseManager.addDatabase(databaseName, userdataDatabaseFile.getAbsolutePath(), DatabaseManager.USERDATA_VERSION, DatabaseManager.USERDATA_VIEWS_VERSION);
            databaseManager.connectDatabase(databaseName); // open and create the database
            return true;
        }
    }

    @Nonnull
    private File getDatabaseFile(String databaseName) {
        File dbFile = new File(fileUtil.getUserdataDir(), databaseName);
        boolean dbExists = dbFile.exists();
        if (!dbExists) {
            dbFile.getParentFile().mkdirs();
        }

        return dbFile;
    }

    public String getCurrentDatabaseName() {
        return getDatabaseName(prefs.getCurrentPersonId(), prefs.getCurrentPersonAnnotationInstanceId());
    }

    public String getCurrentOpenedDatabaseName() {
        String databaseName = getCurrentDatabaseName();
        openDatabase(databaseName);
        return databaseName;
    }

    public String getDatabaseName(long personId, int instanceId) {
        if (currentPersonId != personId || currentInstanceId != instanceId || StringUtils.isEmpty(currentDatabaseName)) {
            currentPersonId = personId;
            currentInstanceId = instanceId;
            currentDatabaseName = createDatabaseName(personId, instanceId);
        }

        return currentDatabaseName;
    }

    public static String createDatabaseName(long personId, int instanceId) {
        return DatabaseManagerConst.USERDATA_DATABASE_NAME + "-" + personId + "-" + instanceId;
    }

    /**
     * Used for Sign Out or Clean sync
     */
    public void resetCurrentDatabase() {
        databaseManager.deleteDatabase(getCurrentOpenedDatabaseName());
    }
}
