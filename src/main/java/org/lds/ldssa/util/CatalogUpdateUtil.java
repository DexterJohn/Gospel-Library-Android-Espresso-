package org.lds.ldssa.util;

import org.apache.commons.io.FileUtils;
import org.dbtools.android.domain.AndroidDatabase;
import org.lds.ldsaccount.AuthStatus;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.BuildConfig;
import org.lds.ldssa.R;
import org.lds.ldssa.download.CatalogDownloader;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.event.BackgroundSnackbarEvent;
import org.lds.ldssa.event.StartupProgressEvent;
import org.lds.ldssa.event.catalog.CatalogReloadEvent;
import org.lds.ldssa.event.catalog.CatalogUpdateCheckCompletedEvent;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.DatabaseManagerConst;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager;
import org.lds.ldssa.model.database.types.CatalogItemSourceType;
import org.lds.ldssa.model.database.types.SnackbarAction;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.ServiceModule;
import org.lds.ldssa.model.webservice.rolecontent.dto.DtoCustomCatalogMeta;
import org.lds.mobile.util.LdsNetworkUtil;
import org.lds.mobile.util.LdsZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import pocketbus.Bus;
import timber.log.Timber;

/**
 * NOTE: CatalogUpdateUtil is separate from CatalogUtil to prevent a circular dependency
 */
@Singleton
public class CatalogUpdateUtil {
    // Injected
    private final Prefs prefs;
    private final InternalIntents internalIntents;
    private final Bus bus;
    private final GLFileUtil fileUtil;
    private final LdsNetworkUtil networkUtil;
    private final CatalogUtil catalogUtil;
    private final CatalogMetaDataManager catalogMetaDataManager;
    private final RoleCatalogManager roleCatalogManager;
    private final GLDownloadManager downloadManager;
    private final LanguageManager languageManager;
    private final DownloadedItemManager downloadedItemManager;
    private final DatabaseManager databaseManager;
    private final Provider<CatalogDownloader> catalogDownloaderProvider;
    private final LdsZipUtil ldsZipUtil;
    private final ContentItemUtil contentItemUtil;
    private final LDSAccountUtil ldsAccountUtil;
    private final ItemManager itemManager;
    private final CatalogSourceManager catalogSourceManager;
    private final DatabaseUtil databaseUtil;

    // State Variables
    private static AtomicBoolean checkInProgress = new AtomicBoolean(false);

    @Inject
    public CatalogUpdateUtil(Prefs prefs, InternalIntents internalIntents, Bus bus, GLFileUtil fileUtil, LdsNetworkUtil networkUtil, CatalogUtil catalogUtil,
                             CatalogMetaDataManager catalogMetaDataManager, RoleCatalogManager roleCatalogManager, GLDownloadManager downloadManager,
                             LanguageManager languageManager, DownloadedItemManager downloadedItemManager, DatabaseManager databaseManager,
                             Provider<CatalogDownloader> catalogDownloaderProvider, LdsZipUtil ldsZipUtil, ContentItemUtil contentItemUtil, LDSAccountUtil ldsAccountUtil, ItemManager itemManager, CatalogSourceManager catalogSourceManager, DatabaseUtil databaseUtil) {
        this.prefs = prefs;
        this.internalIntents = internalIntents;
        this.bus = bus;
        this.fileUtil = fileUtil;
        this.networkUtil = networkUtil;
        this.catalogUtil = catalogUtil;
        this.catalogMetaDataManager = catalogMetaDataManager;
        this.roleCatalogManager = roleCatalogManager;
        this.downloadManager = downloadManager;
        this.languageManager = languageManager;
        this.downloadedItemManager = downloadedItemManager;
        this.databaseManager = databaseManager;
        this.catalogDownloaderProvider = catalogDownloaderProvider;
        this.ldsZipUtil = ldsZipUtil;
        this.contentItemUtil = contentItemUtil;
        this.ldsAccountUtil = ldsAccountUtil;
        this.itemManager = itemManager;
        this.catalogSourceManager = catalogSourceManager;
        this.databaseUtil = databaseUtil;
    }

    /**
     * If a catalog file does not exist, prepare a catalog from:
     * 1. already existing previous download zip file
     * 2. zip file bundled with app in assets
     *
     * @return true if the catalog is ready
     */
    public boolean prepareCatalogDatabase() {
        Timber.i("Preparing Catalog Database...");

        File catalogFile = fileUtil.getCatalogFile();

        // if there is already a catalog file, verify that the file is OK
        if (catalogFile.exists() && !verifyCatalog()) {
            Timber.e("Current catalog could not be verified... deleting....");
            databaseManager.deleteDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);
        }

        // make the catalog directory
        File catalogDir = new File(catalogFile.getParent());
        if (!catalogDir.exists() && !catalogDir.mkdirs()) {
            return false;
        }

        // check to see if there is a pending/downloaded catalog diff awaiting upgrade
        File downloadedCatalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        if (downloadedCatalogDiffZipFile != null && downloadedCatalogDiffZipFile.exists()) {
            int onlineVersion = catalogUtil.fetchCatalogVersion(CatalogUtil.CORE_CATALOG_NAME, prefs.getContentServerType().getContentUrl() + "/index.json");
            boolean diffUpgradeSuccessful = diffUpgradeCatalog(onlineVersion);
            if (diffUpgradeSuccessful) {
                redownloadRoleBasedCatalogs();
                updateContentDatabases();
            } else {
                // If the diff upgrade fails, then download the full catalog database
                Timber.e("Catalog diff update failed. Downloading full catalog. Version = %d", onlineVersion);
                downloadCoreCatalog(onlineVersion, true);
            }
        }

        // check to see if there is a pending/downloaded catalog awaiting swap
        File downloadedCatalogZipFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        if (downloadedCatalogZipFile != null && downloadedCatalogZipFile.exists()) {
            swapCatalog();
        }

        // if there is still NO catalogs.... use that last downloaded archived core catalog OR the bundled catalog
        if (!catalogFile.exists()) {
            // use the last downloaded catalog (if it exists)
            installArchivedCatalog();
        }

        // Reset the catalog update check preferences (A catalog update has been handled (successfully or not) by this point and does not need to be handled again)
        prefs.resetCatalogUpdateDownloadPrefs();

        // if catalog file exists... record the version and installed
        return catalogFile.exists();
    }

    public void updateCatalog(boolean applyUpdateNow) {
        updateCatalog(applyUpdateNow, false);
    }

    public void updateCatalog(boolean applyUpdateNow, boolean checkForContentItemUpdates) {
        if (!checkInProgress.compareAndSet(false, true)) {
            Timber.w("Catalog Update skipped (already in process)");
            return;
        }

        try {
            Timber.i("Catalog Update...");
            performUpdateCatalog(applyUpdateNow);
        } finally {
            checkInProgress.set(false);
            bus.post(new CatalogUpdateCheckCompletedEvent());
        }

        if (checkForContentItemUpdates) {
            // force check of content versions even if catalog was not swapped
            downloadManager.updateContent();
        }
    }

    private void performUpdateCatalog(boolean applyUpdateNow) {
        if (!networkUtil.isConnected()) {
            return;
        }

        prefs.updateLastCatalogUpdateTime();

        int onlineVersion = fetchLatestCatalogVersion();

        // Support change in catalog environment... if the environment changes, force download a new catalog
        if (prefs.isForceCatalogUpdate()) {
            downloadCoreCatalog(onlineVersion, false);
            prefs.setForceCatalogUpdate(false);
            return;
        }

        // DEVELOPER DEBUG: allow override of online version
        if (prefs.isDeveloperModeEnabled() && prefs.getDeveloperCatalogVersion() > 0) {
            onlineVersion = prefs.getDeveloperCatalogVersion();
        }

        long currentCatalogVersion = getCurrentCatalogVersion();

        // validate role base content and return true if role based content needs the catalog to be rebuilt
        CatalogUtil.RoleCatalogUpdateStatus roleCatalogUpdateStatus = fetchRoleCatalogStatus();

        boolean coreCatalogUpdateAvailable = onlineVersion != currentCatalogVersion || roleCatalogUpdateStatus == CatalogUtil.RoleCatalogUpdateStatus.ERROR;
        int newVersion = onlineVersion; // always take the online version, even if it is lower (rollback)

        if (coreCatalogUpdateAvailable) {
            downloadOrSwapCatalog(newVersion, applyUpdateNow);
        } else if (roleCatalogUpdateStatus == CatalogUtil.RoleCatalogUpdateStatus.REBUILD_CATALOG) {
            // This will copy in the archive (with no role data) and then merge any remaining roles
            installArchivedCatalog();
        } else if (roleCatalogUpdateStatus == CatalogUtil.RoleCatalogUpdateStatus.MERGE_ONLY) {
            downloadRoleCatalogs();
        }
    }

    private long getCurrentCatalogVersion() {
        if (fileUtil.getCatalogFile().exists()) {
            return catalogMetaDataManager.findVersion();
        }
        return 0;
    }

    private int fetchLatestCatalogVersion() {
        return catalogUtil.fetchCatalogVersion(CatalogUtil.CORE_CATALOG_NAME, prefs.getContentServerType().getContentUrl() + "/index.json");
    }

    private void downloadOrSwapCatalog(int newVersion, boolean applyUpdateNow) {
        File catalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        if (catalogDiffZipFile != null && catalogDiffZipFile.exists() && applyUpdateNow) {
            // restart and apply catalog update now (swapCatalog() is called at startup)
            internalIntents.restart();
            return;
        }

        File catalogZipDownloadFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        if (catalogZipDownloadFile != null && catalogZipDownloadFile.exists()) {
            if (applyUpdateNow) {
                // restart and apply catalog update now (swapCatalog() is called at startup)
                internalIntents.restart();
            }
        } else {
            prefs.setCatalogForceUpdateWhenAvailable(applyUpdateNow); // restart when download completes?
            downloadCoreCatalog(newVersion, false);
        }
    }

    public void swapCatalog() {
        if (installDownloadedCatalog()) {
            // update content for changed catalog
            updateContentDatabases();
        }
    }

    public void downloadCoreCatalog(int catalogVersion, boolean forceDownloadFullCoreCatalog) {
        if (downloadManager.networkUsable(false)) {
            catalogDownloaderProvider.get().initCoreCatalogDownload(catalogVersion, forceDownloadFullCoreCatalog).execute();
        } else {
            bus.post(new BackgroundSnackbarEvent(R.string.update_catalog_wifi_required, SnackbarAction.OK));
        }
    }

    public void downloadRoleCatalogs() {
        if (downloadManager.networkUsable(false)) {
            catalogDownloaderProvider.get().initRoleCatalogDownload().execute();
        } else {
            bus.post(new BackgroundSnackbarEvent(R.string.update_catalog_wifi_required, SnackbarAction.OK));
        }
    }

    public void updateContentDatabases() {
        downloadManager.updateContent();
    }

    /**
     * Verify that catalog is in good working order (tables exist, etc)
     * @return true if catalog is good
     */
    boolean verifyCatalog() {
        try {
            return databaseUtil.validateCatalogDatabase() && languageManager.findCount() > 0;
        } catch (Exception e) {
            Timber.e(e, "Failed to verify catalog");
            return false;
        }
    }

    boolean installDownloadedCatalog() {
        Timber.d("Installing catalog from download/update");
        bus.post(new StartupProgressEvent("Swapping catalog", true));

        File newCatalogZipFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);

        // make sure there is a Catalog file to install
        if (newCatalogZipFile != null && !newCatalogZipFile.exists()) {
            return false;
        }

        // Extract the db file from the zip to a temp zip file
        File tempCatalogFile = fileUtil.getTempCatalogFile();
        boolean unzipSuccessful = ldsZipUtil.extractZipEntryFromFile(newCatalogZipFile, GLFileUtil.ZIP_ENTRY_CATALOG_DB_NAME, tempCatalogFile);

        // verify that the temp catalog file got created
        if (!unzipSuccessful || !tempCatalogFile.exists()) {
            Timber.e("Failed to unzip catalog (catalog database does NOT exist)... deleting downloaded catalog zip file...");
            FileUtils.deleteQuietly(newCatalogZipFile);
            return false;
        }

        // cleanup any existing catalog
        File catalogFile = fileUtil.getCatalogFile();
        removeExistingCatalog(catalogFile);

        // move the temp catalog to the final location
        try {
            FileUtils.copyFile(tempCatalogFile, catalogFile);
        } catch (IOException e) {
            Timber.e(e, "Failed to move temp catalog");
            FileUtils.deleteQuietly(newCatalogZipFile);
            return false;
        }

        // add the new catalog to dbtools
        prepareNewCatalog(catalogFile);

        // don't archive if the database is bad
        if (verifyCatalog()) {
            // Archive latest catalog file
            fileUtil.archiveCatalog(tempCatalogFile, fileUtil.getCatalogArchiveFile());
            FileUtils.deleteQuietly(newCatalogZipFile);
            return true;
        } else {
            Timber.e("Failed to verify new catalog... deleting downloaded catalog zip file and extracted catalog database...");
            removeExistingCatalog(catalogFile);
            FileUtils.deleteQuietly(newCatalogZipFile);
            FileUtils.deleteQuietly(tempCatalogFile);
            return false;
        }
    }

    void installArchivedCatalog() {
        File archiveCatalogFile = fileUtil.getCatalogArchiveFile();
        if (!archiveCatalogFile.exists()) {
            installBundledCatalog();
            return;
        }

        Timber.d("Installing catalog from archive");
        bus.post(new StartupProgressEvent("Swapping catalog", true));

        File catalogFile = fileUtil.getCatalogFile();

        // cleanup any existing catalog
        removeExistingCatalog(catalogFile);

        // copy the archive to become the catalog
        try {
            FileUtils.copyFile(archiveCatalogFile, catalogFile);
        } catch (IOException e) {
            Timber.e(e, "Failed to install from archive");
        }

        prepareNewCatalog(catalogFile);
    }

    void installBundledCatalog() {
        Timber.d("Installing catalog from bundle");

        File catalogFile = fileUtil.getCatalogFile();
        File catalogDir = catalogFile.getParentFile();

        // cleanup any existing catalog
        removeExistingCatalog(catalogFile);

        // unzip the catalog from bundled assets
        File assetCatalogFile = fileUtil.unZipAssetCatalog(catalogDir, GLFileUtil.BUNDLE_CATALOG_DB_NAME);
        if (assetCatalogFile == null || !assetCatalogFile.exists()) {
            return;
        }

        // the catalog.zip file contains "Catalog.sqlite" rename it to the proper name
        assetCatalogFile.renameTo(catalogFile);

        prepareNewCatalog(catalogFile);
    }

    boolean diffUpgradeCatalog(long expectedVersion) {
        Timber.d("Applying catalog diff");
        bus.post(new StartupProgressEvent("Swapping catalog", true));

        // 1. Check to see if a diff file exists, do nothing if file doesn't exist
        File catalogDiffZipFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME);
        if (catalogDiffZipFile == null || !catalogDiffZipFile.exists()) {
            return false;
        }

        // 2. Update the live catalog
        // Ensure that the catalog exists
        AndroidDatabase catalog = databaseManager.getDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);
        if (catalog == null) {
            FileUtils.deleteQuietly(catalogDiffZipFile);
            return false;
        }

        // Extract the diff file from the zip
        File catalogDiffFile = fileUtil.getTempCatalogDiffFile();
        long currentCatalogVersion = getCurrentCatalogVersion();
        boolean unzipSuccessful = ldsZipUtil.extractZipEntryFromFile(catalogDiffZipFile, fileUtil.getZipCatalogDiffFilename(currentCatalogVersion), catalogDiffFile);

        // Verify that the diff catalog file got created
        if (!unzipSuccessful || !catalogDiffFile.exists()) {
            Timber.e("Failed to unzip catalog diff (catalog diff file does NOT exist)... deleting downloaded catalog diff zip file...");
            FileUtils.deleteQuietly(catalogDiffZipFile);
            return false;
        }

        // Apply the diff to the live catalog
        boolean diffUpgradeSuccess = databaseManager.diffUpgradeDatabase(catalog, catalogDiffFile);
        if (!diffUpgradeSuccess) {
            FileUtils.deleteQuietly(catalogDiffZipFile);
            FileUtils.deleteQuietly(catalogDiffFile);
            Timber.e("Error while applying catalog diff from version [%d] to [%d]", currentCatalogVersion, expectedVersion);
            return false;
        }

        // 3. Ensure that the diff was applied
        long updatedVersion = catalogMetaDataManager.findVersion();
        if (updatedVersion != expectedVersion) {
            FileUtils.deleteQuietly(catalogDiffZipFile);
            FileUtils.deleteQuietly(catalogDiffFile);
            Timber.e("Failed to apply catalog diff: updated version [%d] != expected version [%d]", updatedVersion, expectedVersion);
            return false;
        }

        // 5. Update the archive database
        File archiveFile = fileUtil.getCatalogArchiveFile();
        if (archiveFile.exists()) {
            String dbName = GLFileUtil.DEFAULT_ARCHIVE_CATALOG_DB_NAME;
            AndroidDatabase archiveDatabase = new AndroidDatabase(dbName, archiveFile.getAbsolutePath(), DatabaseManager.CATALOG_VERSION, DatabaseManager.CATALOG_VIEWS_VERSION);
            databaseManager.addDatabase(archiveDatabase);
            databaseManager.connectDatabase(dbName);
            databaseManager.diffUpgradeDatabase(archiveDatabase, catalogDiffFile);
            databaseManager.removeDatabase(dbName);
        }

        // 6. Cleanup and delete the unneeded files
        FileUtils.deleteQuietly(catalogDiffZipFile);
        FileUtils.deleteQuietly(catalogDiffFile);

        return true;
    }

    void removeExistingCatalog(File catalogFile) {
        // close existing catalog database
        databaseManager.closeDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // delete existing catalog
        FileUtils.deleteQuietly(catalogFile);
    }

    private void prepareNewCatalog(File catalogFile) {
        // Add the new catalog database to the DatabaseManager
        // update database items for changes in the new catalog
        databaseManager.addDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME,
                catalogFile.getPath(),
                DatabaseManager.CATALOG_VERSION,
                DatabaseManager.CATALOG_VIEWS_VERSION);

        // make sure the catalog database is open
        // and also create the views
        AndroidDatabase database = databaseManager.getDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);
        if (database != null) {
            databaseManager.openDatabase(database);
            databaseManager.onCreateViews(database);
        }

        File catalogArchive = fileUtil.getCatalogArchiveFile();
        File catalogTemp = fileUtil.getTempCatalogFile();
        if (!catalogArchive.exists() && !catalogContainsSecureContent()) {
            try {
                FileUtils.copyFile(catalogFile, catalogTemp);
                fileUtil.archiveCatalog(catalogTemp, catalogArchive);
            } catch (IOException e) {
                Timber.e("Unable to archive catalog");
            }
        }

        redownloadRoleBasedCatalogs();

        bus.post(new CatalogReloadEvent());
    }

    private void redownloadRoleBasedCatalogs() {
        // re-download and install role based content... if needed
        // delete any existing role catalog entries
        roleCatalogManager.deleteAll();

        // if this user is an authenticated user... then try to download any Role Catalogs
        if (ldsAccountUtil.hasCredentials() && catalogSourceManager.findSecureContentCount() == 0) {
            downloadRoleCatalogs();
        }
    }

    public void mergeRoleCatalog(String catalogName, long catalogVersion) {
        File roleCatalogZipFile = fileUtil.getCatalogZipDownloadFile(catalogName);

        if (roleCatalogZipFile == null) {
            Timber.e("Failed to mergeRoleCatalog... zipFile == null for catalogName [%s]", catalogName);
            return;
        }

        // ONLY apply role catalogs if the catalog is the VERY LATEST version (downloaded role catalogs only work with the lastest available catalog)
        long onlineVersion = fetchLatestCatalogVersion();
        long currentCatalogVersion = getCurrentCatalogVersion();
        if (onlineVersion != currentCatalogVersion) {
            Timber.e("Failed to mergeRoleCatalog [%s]... online catalog version [%d] != current catalog version [%d]", catalogName, onlineVersion, currentCatalogVersion);
            return;
        }

        Timber.d("Unzipping: %s", roleCatalogZipFile.getAbsolutePath());

        if (!roleCatalogZipFile.exists()) {
            Timber.e("Cannot merge catalog... zip file [%s] does not exist", roleCatalogZipFile.getAbsolutePath());
            return;
        }

        File roleCatalogDbFile = fileUtil.getRoleCatalogDbName(catalogName);
        ldsZipUtil.extractZipEntryFromFile(roleCatalogZipFile, GLFileUtil.ZIP_ENTRY_CATALOG_DB_NAME, roleCatalogDbFile);

        if (!roleCatalogDbFile.exists()) {
            Timber.e("Failed to extract role catalog from zip file [%s]", roleCatalogZipFile.getAbsolutePath());
            return;
        }

        // merge
        mergeRoleCatalog(roleCatalogDbFile);

        // record role catalog is installed (and merged)
        roleCatalogManager.updateStatus(catalogName, catalogVersion, true);

        // cleanup
        FileUtils.deleteQuietly(roleCatalogDbFile);
        FileUtils.deleteQuietly(roleCatalogZipFile);

        // Update the ui if needed
        bus.post(new CatalogReloadEvent());
    }

    private synchronized void mergeRoleCatalog(@Nonnull File roleCatalogDbFile) {
        Timber.i("Merging role catalog [%s]...", roleCatalogDbFile);

        // Get a handle on the core catalog database
        AndroidDatabase coreDatabase = databaseManager.getDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // Merge into core database
        if (databaseManager.mergeDatabase(roleCatalogDbFile, coreDatabase)) {
            Timber.i("MERGED role catalog [%s]", roleCatalogDbFile);
        } else {
            Timber.e("FAILED to merge role catalog [%s] ", roleCatalogDbFile);
        }
    }

    /**
     * Verify the rights of the user and what role based catalogs are available to them.  If they no longer have rights,
     * or if the content no longer exists, cleanup and return that the catalog needs to be rebuilt
     * @return RoleCatalogUpdateStatus enum of the action that needs to happen to the catalog (rebuilt or merged) because of a change in the role based catalogs
     */
    protected CatalogUtil.RoleCatalogUpdateStatus fetchRoleCatalogStatus() {
        CatalogUtil.RoleCatalogUpdateStatus roleCatalogUpdateStatus = CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE;
        try {
            if (ldsAccountUtil.checkAuthenticatedConnection(ServiceModule.CURRENT_ENVIRONMENT) != AuthStatus.SUCCESS) {
                return CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE;
            }

            // retrieve the catalogs that are available for this user
            List<DtoCustomCatalogMeta> dtoCustomCatalogs = catalogUtil.fetchRoleBasedCatalogs().getCatalogs();

            // create a Set of the names (for quick lookup)
            Set<String> onlineCatalogNames = new HashSet<>(dtoCustomCatalogs.size());
            for (DtoCustomCatalogMeta dtoCatalog : dtoCustomCatalogs) {
                onlineCatalogNames.add(dtoCatalog.getName());
            }

            // check to see if any role based content has been removed for this user (Example: A user is no longer a Bishop... remove Handbook 1)
            List<String> installedCatalogNames = roleCatalogManager.findAllInstalledNames();
            for (String installedCatalogName : installedCatalogNames) {
                if (!onlineCatalogNames.contains(installedCatalogName)) {
                    // remove installed content items
                    removeRoleBasedContent(installedCatalogName);

                    // remove catalog entries
                    roleCatalogManager.deleteByCatalogName(installedCatalogName);

                    // indicate a catalog rebuild is required
                    roleCatalogUpdateStatus = CatalogUtil.RoleCatalogUpdateStatus.REBUILD_CATALOG;
                }
            }

            // check to see if we need to go on further....
            if (roleCatalogUpdateStatus != CatalogUtil.RoleCatalogUpdateStatus.ALREADY_UP_TO_DATE) {
                return roleCatalogUpdateStatus;
            }

            boolean missingRoleCatalog = false;
            boolean rebuildCatalog = false;
            // check installed role content and if there is a new version available
            for (DtoCustomCatalogMeta dtoRoleCatalog : dtoCustomCatalogs) {
                // stop checking if one of the role catalogs cause the need to rebuild the catalog
                if (rebuildCatalog) {
                    break;
                }

                String roleCatalogName = dtoRoleCatalog.getName();

                // fetch the name and version and check to see if installed
                boolean roleCatalogInstalled = roleCatalogManager.isInstalled(roleCatalogName);
                String roleCatalogWithVersionUrl = dtoRoleCatalog.getUrl() + BuildConfig.CONTENT_SCHEMA_VERSION;
                int roleCatalogVersion = catalogUtil.fetchCatalogVersion(dtoRoleCatalog.getName(), roleCatalogWithVersionUrl + "/index.json");

                // make sure that there is a valid version
                if (roleCatalogVersion >= 0) {
                    // 1. check for catalogs that have been added
                    if (!roleCatalogInstalled) {
                        missingRoleCatalog = true;
                    }

                    // 2. check for newer version of existing catalog
                    if (roleCatalogInstalled && roleCatalogManager.isUpdateNeeded(dtoRoleCatalog.getName(), roleCatalogVersion)) {
                        rebuildCatalog = true;
                    }
                }
            }

            if (rebuildCatalog) {
                roleCatalogUpdateStatus = CatalogUtil.RoleCatalogUpdateStatus.REBUILD_CATALOG;
            } else if (missingRoleCatalog) {
                roleCatalogUpdateStatus = CatalogUtil.RoleCatalogUpdateStatus.MERGE_ONLY;
            }
            return roleCatalogUpdateStatus;
        } catch (IOException e) {
            Timber.e(e, "Failed to check for update in role based catalogs");
            return CatalogUtil.RoleCatalogUpdateStatus.ERROR;
        }
    }

    protected void removeRoleBasedContent(String roleCatalogName) {
        // find and delete ALL secure items (todo: FUTURE (no implementation in the catalog database yet) add in the item table the catalog source id)
        List<Long> secureContentItemIds = downloadedItemManager.findAllInstalledSecureItemIds();

        // delete the content
        for (Long secureContentItemId : secureContentItemIds) {
            // find all the content for the given catalog name and delete
            contentItemUtil.deleteContentItem(secureContentItemId);
        }

        // delete the archived catalog
        FileUtils.deleteQuietly(fileUtil.getRoleCatalogZipArchiveFile(roleCatalogName));
    }

    /**
     * Removes role based content and catalog data
     * @return true if role based content existed
     */
    public boolean removeAllRoleBasedContent() {
        Timber.i("Removing role based content...");
        boolean containsSecureContent = catalogContainsSecureContent();
        if (!containsSecureContent) {
            return false;
        }

        // remove secure content items
        List<Long> secureContentItemIds = itemManager.findAllBySourceType(CatalogItemSourceType.SECURE);
        for (Long contentItemId : secureContentItemIds) {
            contentItemUtil.deleteContentItem(contentItemId);
        }

        // remove catalog with secure content... last downloaded default catalog will be restored in prepareCatalog(...)
        databaseManager.deleteDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME);

        // remove secure sources references from database
        roleCatalogManager.deleteAll();

        return true;
    }

    boolean catalogContainsSecureContent() {
        return catalogSourceManager.findSecureContentCount() > 0;
    }
}
