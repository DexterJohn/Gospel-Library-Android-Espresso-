package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.download.CatalogDownloader;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.util.LdsNetworkUtil;
import org.lds.mobile.util.LdsZipUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogUpdateUtil_Factory implements Factory<CatalogUpdateUtil> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<Bus> busProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<LdsNetworkUtil> networkUtilProvider;

  private final Provider<CatalogUtil> catalogUtilProvider;

  private final Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider;

  private final Provider<RoleCatalogManager> roleCatalogManagerProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private final Provider<DatabaseManager> databaseManagerProvider;

  private final Provider<CatalogDownloader> catalogDownloaderProvider;

  private final Provider<LdsZipUtil> ldsZipUtilProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<LDSAccountUtil> ldsAccountUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<CatalogSourceManager> catalogSourceManagerProvider;

  private final Provider<DatabaseUtil> databaseUtilProvider;

  public CatalogUpdateUtil_Factory(
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<Bus> busProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<LdsNetworkUtil> networkUtilProvider,
      Provider<CatalogUtil> catalogUtilProvider,
      Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider,
      Provider<RoleCatalogManager> roleCatalogManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<DatabaseManager> databaseManagerProvider,
      Provider<CatalogDownloader> catalogDownloaderProvider,
      Provider<LdsZipUtil> ldsZipUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<LDSAccountUtil> ldsAccountUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<CatalogSourceManager> catalogSourceManagerProvider,
      Provider<DatabaseUtil> databaseUtilProvider) {
    this.prefsProvider = prefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.busProvider = busProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.networkUtilProvider = networkUtilProvider;
    this.catalogUtilProvider = catalogUtilProvider;
    this.catalogMetaDataManagerProvider = catalogMetaDataManagerProvider;
    this.roleCatalogManagerProvider = roleCatalogManagerProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.downloadedItemManagerProvider = downloadedItemManagerProvider;
    this.databaseManagerProvider = databaseManagerProvider;
    this.catalogDownloaderProvider = catalogDownloaderProvider;
    this.ldsZipUtilProvider = ldsZipUtilProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.ldsAccountUtilProvider = ldsAccountUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.catalogSourceManagerProvider = catalogSourceManagerProvider;
    this.databaseUtilProvider = databaseUtilProvider;
  }

  @Override
  public CatalogUpdateUtil get() {
    return new CatalogUpdateUtil(
        prefsProvider.get(),
        internalIntentsProvider.get(),
        busProvider.get(),
        fileUtilProvider.get(),
        networkUtilProvider.get(),
        catalogUtilProvider.get(),
        catalogMetaDataManagerProvider.get(),
        roleCatalogManagerProvider.get(),
        downloadManagerProvider.get(),
        languageManagerProvider.get(),
        downloadedItemManagerProvider.get(),
        databaseManagerProvider.get(),
        catalogDownloaderProvider,
        ldsZipUtilProvider.get(),
        contentItemUtilProvider.get(),
        ldsAccountUtilProvider.get(),
        itemManagerProvider.get(),
        catalogSourceManagerProvider.get(),
        databaseUtilProvider.get());
  }

  public static Factory<CatalogUpdateUtil> create(
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<Bus> busProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<LdsNetworkUtil> networkUtilProvider,
      Provider<CatalogUtil> catalogUtilProvider,
      Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider,
      Provider<RoleCatalogManager> roleCatalogManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<DatabaseManager> databaseManagerProvider,
      Provider<CatalogDownloader> catalogDownloaderProvider,
      Provider<LdsZipUtil> ldsZipUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<LDSAccountUtil> ldsAccountUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<CatalogSourceManager> catalogSourceManagerProvider,
      Provider<DatabaseUtil> databaseUtilProvider) {
    return new CatalogUpdateUtil_Factory(
        prefsProvider,
        internalIntentsProvider,
        busProvider,
        fileUtilProvider,
        networkUtilProvider,
        catalogUtilProvider,
        catalogMetaDataManagerProvider,
        roleCatalogManagerProvider,
        downloadManagerProvider,
        languageManagerProvider,
        downloadedItemManagerProvider,
        databaseManagerProvider,
        catalogDownloaderProvider,
        ldsZipUtilProvider,
        contentItemUtilProvider,
        ldsAccountUtilProvider,
        itemManagerProvider,
        catalogSourceManagerProvider,
        databaseUtilProvider);
  }
}
