package org.lds.ldssa.inject;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldsaccount.AuthStatus;
import org.lds.ldsaccount.CredentialsManager;
import org.lds.ldsaccount.LDSAccountAuth;
import org.lds.ldsaccount.LDSAccountLogger;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.database.tips.tipsappversion.TipsAppVersionManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.converter.LocalDateTimeTypeConverter;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.ldssa.model.webservice.tips.TipsService;
import org.lds.ldssa.util.CatalogUtil;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.TipsUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.util.LdsNetworkUtil;
import org.lds.mobile.util.LdsTimeUtil;
import org.threeten.bp.LocalDateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pocketbus.Bus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Set of common classes are NOT mocked, but provide real functionality in the standard JRE
 */
@Module
public class CommonTestModule {
    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(DatabaseConfig databaseConfig) {
        DatabaseManager databaseManager = spy(new DatabaseManager(databaseConfig));

        // don't allow the database to be upgraded
        doNothing().when(databaseManager).onUpgrade(any(AndroidDatabase.class), anyInt(), anyInt());

//        JdbcSqliteDatabaseWrapper.setEnableLogging(true);

        return databaseManager;
    }

    @Provides
    @Singleton
    ContentItemUtil provideContentItemUtil(GLFileUtil glFileUtil, DownloadedItemManager downloadedItemManager, DatabaseManager databaseManager) {
        Bus bus = mock(Bus.class);
        Application application = mock(Application.class);
        Analytics analytics = mock(Analytics.class);
        DownloadQueueItemManager downloadItemQueueManager = mock(DownloadQueueItemManager.class);
        GLDownloadManager downloadManager = mock(GLDownloadManager.class);
        ScreenUtil screenUtil = mock(ScreenUtil.class);
        ItemManager itemManager = mock(ItemManager.class);
        CoroutineContextProvider cc = mock(CoroutineContextProvider.class);
        DownloadManagerHelper downloadManagerHelper = mock(DownloadManagerHelper.class);
        return spy(new ContentItemUtil(application, glFileUtil, downloadedItemManager, databaseManager, bus, analytics, downloadItemQueueManager, downloadManager, screenUtil, itemManager, cc, downloadManagerHelper));
    }

    @Provides
    @Singleton
    LdsTimeUtil provideTimeUtil() {
        return spy(new LdsTimeUtil());
    }

    @Provides
    @Singleton
    ToastUtil provideToastUtil(CoroutineContextProvider cc) {
        return spy(new ToastUtil(null, cc));
    }

    @Provides
    @Singleton
    CatalogUtil provideCatalogUtil(Prefs prefs) {
        return spy(new CatalogUtil(
                prefs,
                mock(CatalogService.class),
                mock(RoleCatalogService.class),
                mock(RoleBasedContentService.class)
                ));
    }

    @Provides
    @Singleton
    TipsUtil provideTipsUtil(Prefs prefs, TipsService tipsService, TipManager tipManager, TipsAppVersionManager tipsAppVersionManager) {
        return spy(new TipsUtil(
                prefs,
                tipsService,
                tipManager
                ));
    }

    @Provides
    @Singleton
    LanguageManager provideLanguageManager(DatabaseManager databaseManager) {
        LanguageManager languageManager = spy(new LanguageManager(databaseManager));

        doReturn(1L).when(languageManager).findIdByLocale("eng");
        doReturn("eng").when(languageManager).findLocaleById(1L);

        return languageManager;
    }

    @Provides
    @Singleton
    LanguageUtil provideLanguageUtil(LanguageManager languageManager) {
        LanguageUtil languageUtil = spy(new LanguageUtil(languageManager));

        when(languageUtil.getDeviceLanguageId()).thenReturn(1L);

        return languageUtil;
    }

    @Provides
    @Singleton
    LDSAccountUtil provideLDSAccountUtil(LDSAccountAuth ldsAccountAuth, CredentialsManager credentialsManager, LDSAccountLogger ldsAccountLogger) {
        LDSAccountUtil ldsAccountUtil = spy(new LDSAccountUtil(ldsAccountAuth, credentialsManager, ldsAccountLogger));

        when(ldsAccountUtil.hasCredentials()).thenReturn(true);
//        when(ldsAccountUtil.checkAuthenticatedConnection(any(org.lds.ldsaccount.LDSAccountEnvironment.class))).thenReturn(AuthStatus.SUCCESS);
        doReturn(AuthStatus.SUCCESS).when(ldsAccountUtil).checkAuthenticatedConnection(any(org.lds.ldsaccount.LDSAccountEnvironment.class));

        return ldsAccountUtil;
    }

    @Provides
    @Singleton
    LdsNetworkUtil provideLdsNetworkUtil() {
        LdsNetworkUtil networkUtil = mock(LdsNetworkUtil.class);
        when(networkUtil.isConnected()).thenReturn(true);
        when(networkUtil.isConnected(anyBoolean())).thenReturn(true);

        return networkUtil;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder()
//                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeConverter());
        return builder.create();
    }

    @Provides
    @Singleton
    CoroutineContextProvider provideCoroutineContextProvider() {
        return CoroutineContextProvider.TestCoroutineContextProvider.INSTANCE;
    }
}