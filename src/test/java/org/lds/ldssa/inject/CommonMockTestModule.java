package org.lds.ldssa.inject;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.res.AssetManager;

import org.lds.ldsaccount.CredentialsManager;
import org.lds.ldsaccount.NetworkConnectionManager;
import org.lds.ldssa.TestFilesystem;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.model.ContentServerType;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.mobile.download.DownloadManagerHelper;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pocketbus.Bus;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Set of common classes that are mocked
 */
@Module
public class CommonMockTestModule {
    // ========== LDS ==========
    @Provides
    @Singleton
    Analytics provideAnalytics() {
        return mock(Analytics.class);
    }

    @Provides
    @Singleton
    PlaylistManager providePlaylistManager() {
        return mock(PlaylistManager.class);
    }

    @Provides
    @Singleton
    CredentialsManager provideCredentialsManager() {
        return mock(CredentialsManager.class);
    }

    @Provides
    @Singleton
    AnnotationSyncScheduler provideAnnotationSyncScheduler() {
        return mock(AnnotationSyncScheduler.class);
    }

    @Provides
    @Singleton
    DownloadManagerHelper provideDownloadManagerHelper() {
        return mock(DownloadManagerHelper.class);
    }

    // ========== ANDROID ==========

    @Provides
    @Singleton
    Application provideApplication() {
        Application application = mock(Application.class);

        when(application.getFilesDir()).thenReturn(TestFilesystem.INTERNAL_FILES_DIR);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String type = invocation.getArgument(0);
                if (type != null) {
                    return new File(TestFilesystem.EXTERNAL_FILES_DIR, type);
                } else {
                    return TestFilesystem.EXTERNAL_FILES_DIR;
                }
            }
        }).when(application).getExternalFilesDir(or(isNull(String.class), anyString()));

        return application;
    }



    @Provides
    @Singleton
    Bus provideBus() {
        return mock(Bus.class);
    }

    @Provides
    @Singleton
    Prefs providePrefs() {
        Prefs prefs = mock(Prefs.class);

        when(prefs.getContentServerType()).thenReturn(ContentServerType.PROD);

        return prefs;
    }

    @Provides
    @Singleton
    InternalIntents provideInternalIntents() {
        InternalIntents internalIntents = mock(InternalIntents.class);

        return internalIntents;
    }

    @Provides
    @Singleton
    AssetManager provideAssetManager() {
        AssetManager mockAssetManager = mock(AssetManager.class);

        // mock: AssetManager.open(filename)
        try {
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    String filename = invocation.getArgument(0).toString();
                    return new FileInputStream(TestFilesystem.ASSETS_DIR_PATH + filename);
                }
            }).when(mockAssetManager).open(any(String.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mockAssetManager;
    }

    @Provides
    @Singleton
    NetworkConnectionManager provideNetworkConnectionManager() {
        return mock(NetworkConnectionManager.class);
    }

    @Provides
    @Singleton
    ActivityManager provideActivityManager() {
        return mock(ActivityManager.class);
    }

    @Provides
    @Singleton
    NotificationManager provideNotificationManager() {
        return mock(NotificationManager.class);
    }
}
