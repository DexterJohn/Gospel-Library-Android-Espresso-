package org.lds.ldssa.util;

import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.model.ContentServerType;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.catalog.dto.DtoCatalogVersion;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.ldssa.model.webservice.rolecontent.dto.DtoCustomCatalogs;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * The catalogs are stored as follows
 * file/catalog/catalog (location of the core catalog (role based catalogs merge into this file))
 * file/catalog-archive/xxx.zip (location of core and role based catalogs that have already been unzipped and merged in)
 * sdcard/.../files/Download/xxx.zip (location of downloaded catalogs)
 *
 * NOTE: CatalogUpdateUtil is separate from CatalogUtil to prevent a circular dependency
 */
@Singleton
public class CatalogUtil {
    public static final String CORE_CATALOG_NAME = "core";

    public enum RoleCatalogUpdateStatus {
        ERROR, // Error checking for catalog update
        ALREADY_UP_TO_DATE, // already updated (no change/merge needed)
        REBUILD_CATALOG, // catalog needs to be completely rebuilt
        MERGE_ONLY // core catalog has no role content, and can allow for a merge only
    }

    private final Prefs prefs;
    private final CatalogService catalogService;
    private final RoleCatalogService roleCatalogService;
    private final RoleBasedContentService roleBasedContentService;

    @Inject
    public CatalogUtil(Prefs prefs,
                       CatalogService catalogService,
                       RoleCatalogService roleCatalogService,
                       RoleBasedContentService roleBasedContentService) {
        this.prefs = prefs;
        this.catalogService = catalogService;
        this.roleCatalogService = roleCatalogService;
        this.roleBasedContentService = roleBasedContentService;
    }

    @Nonnull
    public String getCatalogDownloadUri(int version) {
        String baseUri = prefs.getContentServerType().getContentUrl();
        return getCatalogDownloadUri(baseUri, version);
    }

    @Nonnull
    public String getCatalogDownloadUri(@Nonnull String baseUri, int version) {
        return baseUri + "/catalogs/" + version + ".zip";
    }

    @Nonnull
    public String getCatalogDiffDownloadUri(int version) {
        String baseUri = prefs.getContentServerType().getContentUrl();
        return baseUri + "/catalog-diffs/" + version + ".zip";
    }

    /**
     * Call webservice to determine the latest version of a catalog
     * @return latest version of the catalog or -1 on failure
     */
    public int fetchCatalogVersion(@Nonnull String catalogName, @Nonnull String url) {
        try {
            Call<DtoCatalogVersion> call;
            if (catalogName.equals(CORE_CATALOG_NAME)) {
                call = catalogService.getCatalogConfig(url);
            } else {
                call = roleCatalogService.getCatalogConfig(url);
            }

            Response<DtoCatalogVersion> response = call.execute();

            if (response.isSuccessful()) {
                DtoCatalogVersion availableVersion = response.body();

                if (availableVersion == null) {
                    Timber.e("Catalog Version missing for [%s]  url: [%s]", catalogName, url);
                    return -1;
                }
                return availableVersion.getCatalogVersion();
            } else {
                Timber.e("Catalog Version missing for [%s]   url: [%s]   responseCode: [%d]", catalogName, url, response.code());
                return -1;
            }
        } catch (Exception error) {
            Timber.e(error);
        }

        return -1;
    }

    @Nonnull
    public DtoCustomCatalogs fetchRoleBasedCatalogs() throws IOException {
        if (prefs.getDeveloperForceNoRoles()) {
            return new DtoCustomCatalogs();
        }

        // fetch list of possible catalogs
        Call<DtoCustomCatalogs> call;
        if (prefs.getContentServerType() == ContentServerType.PROD) {
            call = roleBasedContentService.getCatalogsProd();
        } else {
            // "Staging" for role based content is the same as "Beta" for regular content
            call = roleBasedContentService.getCatalogsStaging();
        }
        Response<DtoCustomCatalogs> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return new DtoCustomCatalogs();
        }
    }
}