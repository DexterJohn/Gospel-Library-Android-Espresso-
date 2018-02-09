package org.lds.ldssa.util

import android.database.Cursor
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.mobile.util.LdsTimeUtil
import org.sqlite.database.sqlite.SQLiteDatabaseCorruptException
import timber.log.Timber
import javax.inject.Inject

class DatabaseUtil
@Inject constructor(val databaseManager: DatabaseManager,
                    val prefs: Prefs,
                    private val catalogMetaDataManager: CatalogMetaDataManager,
                    private val ldsTimeUtil: LdsTimeUtil){

    fun validateCatalogDatabase(): Boolean {
        val startMs = System.currentTimeMillis()

        try {
            // check to see if this version of the catalog has already been verified
            val currentCatalogVersion = catalogMetaDataManager.findVersion()
            if (currentCatalogVersion == prefs.lastCatalogValidatedVersion) {
                Timber.d("Catalog already validated (for version [%d])", currentCatalogVersion)
                return true
            }

            // verify catalog is OK and record the success in prefs
            Timber.d("Validating Catalog new catalog...")
            val success = validateDatabase(GLFileUtil.DEFAULT_CATALOG_DB_NAME)
            if (success) {
                prefs.lastCatalogValidatedVersion = catalogMetaDataManager.findVersion()
            }

            ldsTimeUtil.logTimeElapsedFromNow("DatabaseUtil", "Catalog Validation success:[$success]", startMs)

            return success
        } catch (e: Exception) {
            Timber.e(e, "Failed to validateCatalogDatabase")
            return false
        }
    }

    /**
     * 17 APR 2017 NOTE:
     *
     * In an effort to fix Crashlytics issues we were seeing, we supplied this function to test
     * if a database is "valid"/"corrupt".  Later, we realized that we had a timing issue with the unzipping
     * of content...
     *
     * Because we think the problem is resolved with fixing the timing issue AND because this function
     * dramatically slows down the app (especially with search), this function may no longer be necessary
     *
     * 02 OCT 2017 NOTE:
     *
     * We have come across some cases where users still have corrupt catalog files...
     */
    fun validateDatabase(databaseName: String): Boolean {
        var pragmaCheckCursor: Cursor? = null
        try {
            val database = databaseManager.getDatabase(databaseName)
            if (database == null) {
                Timber.e("validateDatabase - database [%s] failed to get database", databaseName)
                return false
            }

            // make sure the database is open
            if (!databaseManager.openDatabase(database)) {
                Timber.e("validateDatabase - failed to open database [%s]", databaseName)
                return false
            }

            pragmaCheckCursor = database.databaseWrapper.rawQuery("pragma quick_check", null)
            if (!pragmaCheckCursor!!.moveToFirst()) {
                Timber.e("validateDatabase - database [%s] pragma check returned no results", databaseName)
                return false
            }
            if (pragmaCheckCursor.getString(0) != ContentItemUtil.CORRUPTION_CHECK_PASSED) {
                Timber.e("validateDatabase - database [%s] pragma check failed", databaseName)
                return false
            }
        } catch (e: SQLiteDatabaseCorruptException) {
            Timber.e(e, "validateDatabase - database [%s] database was corrupt", databaseName)
            return false
        } catch (e: Exception) {
            Timber.e(e, "ContentItemUtil - validateDatabase")
        } finally {
            if (pragmaCheckCursor != null) {
                pragmaCheckCursor.close()
            }
        }

        return true
    }
}