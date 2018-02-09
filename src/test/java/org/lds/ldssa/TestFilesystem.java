package org.lds.ldssa;

import org.apache.commons.io.FileUtils;
import org.lds.ldssa.model.database.DatabaseManagerConst;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.UserdataDbUtil;

import java.io.File;

public class TestFilesystem {

    public static final String FILESYSTEM_DIR_PATH = "build/test-filesystem";
    public static final File FILESYSTEM_DIR = new File(FILESYSTEM_DIR_PATH);
    public static final String INTERNAL_DIR_PATH = FILESYSTEM_DIR_PATH + "/internal";
    public static final File INTERNAL_DIR = new File(INTERNAL_DIR_PATH);
    public static final String EXTERNAL_DIR_PATH = FILESYSTEM_DIR_PATH + "/external";
    public static final File EXTERNAL_DIR = new File(EXTERNAL_DIR_PATH);

    public static final String INTERNAL_FILES_DIR_PATH = INTERNAL_DIR_PATH + "/files";
    public static final File INTERNAL_FILES_DIR = new File(INTERNAL_FILES_DIR_PATH);
    public static final String INTERNAL_DATABASES_DIR_PATH = INTERNAL_DIR_PATH + "/databases";

    public static final String EXTERNAL_FILES_DIR_PATH = EXTERNAL_DIR_PATH + "/files";
    public static final File EXTERNAL_FILES_DIR = new File(EXTERNAL_FILES_DIR_PATH);

    public static final String ASSETS_DIR_PATH = "src/main/assets/";

    public static final String DEFAULT_USERDATABASE_NAME = UserdataDbUtil.createDatabaseName(0, 0);

    public static final String GL_DATABASE_PATH = INTERNAL_DATABASES_DIR_PATH + "/" + DatabaseManagerConst.GL_DATABASE_NAME;

    public static final String SEARCH_DATABASE_PATH = INTERNAL_DATABASES_DIR_PATH + "/" + DatabaseManagerConst.SEARCH_DATABASE_NAME;

    public static final String TEST_RESOURCES_PATH = "src/test/resources";

    public static final String LATEST_SAMPLE_CATALOG_PATH = TEST_RESOURCES_PATH + "/db/Catalog.sqlite";
    public static final String LATEST_SAMPLE_CATALOG_ZIP_PATH = ASSETS_DIR_PATH + "catalog.zip";
    public static final String SAMPLE_CATALOG_DIFF_ZIP_PATH = TEST_RESOURCES_PATH + "/catalog-update/catalog-diff.zip";
    public static final String SAMPLE_CATALOG_DIFF_FAIL_ZIP_PATH = TEST_RESOURCES_PATH + "/catalog-update/catalog-diff-fail.zip";
    public static final String CATALOG_DATABASE_PATH = INTERNAL_FILES_DIR_PATH + "/" + GLFileUtil.CATALOG_DIRECTORY_NAME + "/" + DatabaseManagerConst.CATALOG_DATABASE_NAME;

    public static final String LATEST_SAMPLE_TIPS_PATH = TEST_RESOURCES_PATH + "/db/tips.sqlite";
    public static final String LATEST_SAMPLE_TIPS_ZIP_PATH = ASSETS_DIR_PATH + "tips.zip";
    public static final String TIPS_DATABASE_PATH = INTERNAL_FILES_DIR_PATH + "/" + GLFileUtil.TIPS_DIRECTORY_NAME + "/" + DatabaseManagerConst.TIPS_DATABASE_NAME;

    public static final String DOWNLOADS_PATH = EXTERNAL_FILES_DIR_PATH + "/Downloads";
    public static final File DOWNLOADS_DIR = new File(DOWNLOADS_PATH);

    public static void deleteFilesystem() {
        FileUtils.deleteQuietly(FILESYSTEM_DIR);
    }

    public static void copyGlBlankDatabase() {
        String dbName = "userdata-blank";
        String sourceGlPath = "src/test/resources/db/" + dbName;
        TestFilesystem.copyGlDatabaseToTestFilesystem(sourceGlPath);
    }

    public static void copyGlDatabaseToTestFilesystem(String sourcePath) {
        copyDatabase(sourcePath, GL_DATABASE_PATH);
    }

    public static void copySearchDatabaseToTestFilesystem(String sourcePath) {
        copyDatabase(sourcePath, SEARCH_DATABASE_PATH);
    }

    public static String getUserDatabasePath() {
        return getUserDatabasePath(DEFAULT_USERDATABASE_NAME);
    }

    public static String getUserDatabasePath(String databaseName) {
        return INTERNAL_FILES_DIR_PATH + "/userdata/" + databaseName;
    }

    public static void copyUserDatabaseToTestFilesystem(String sourcePath) {
        copyUserDatabaseToTestFilesystem(sourcePath, DEFAULT_USERDATABASE_NAME);
    }

    public static void copyUserDatabaseToTestFilesystem(String sourcePath, String databaseName) {
        copyDatabase(sourcePath, getUserDatabasePath(databaseName));
    }


    public static void copyDatabase(String sourcePath, String targetPath) {
        File targetDBFile = new File(targetPath);
        File dbDirectory = targetDBFile.getParentFile();

        try {
            FileUtils.forceMkdir(dbDirectory);

            if (targetDBFile.exists()) {
                FileUtils.deleteQuietly(targetDBFile);
            }

            FileUtils.copyFile(new File(sourcePath), targetDBFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getContentArchive(String filename) {
        return new File(TEST_RESOURCES_PATH + "/content-archives/" + filename);
    }
}
