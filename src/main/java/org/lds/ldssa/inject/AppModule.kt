package org.lds.ldssa.inject

import android.accounts.AccountManager
import android.app.ActivityManager
import android.app.Application
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.net.ConnectivityManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import org.dbtools.android.domain.config.DatabaseConfig
import org.lds.ldsaccount.CredentialsManager
import org.lds.ldsaccount.LDSAccountAuth
import org.lds.ldsaccount.LDSAccountInterceptor
import org.lds.ldsaccount.LDSAccountLogger
import org.lds.ldsaccount.LDSAccountPrefs
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldsaccount.NetworkConnectionManager
import org.lds.ldsaccount.inject.LDSAccountModule
import org.lds.ldssa.BusRegistry
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.analytics.DefaultAnalytics
import org.lds.ldssa.model.database.AppDatabaseConfig
import org.lds.ldssa.model.prefs.PrefsModule
import org.lds.ldssa.model.webservice.ServiceModule
import org.lds.ldssa.model.webservice.converter.LocalDateTimeTypeConverter
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.inject.LDSMobileCommonsModule
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.media.dagger.LDSMobileMediaModule
import org.threeten.bp.LocalDateTime
import pocketbus.Bus
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(LDSMobileCommonsModule::class, LDSMobileMediaModule::class, LDSAccountModule::class, PrefsModule::class, ServiceModule::class))
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideAssetManager(application: Application): AssetManager {
        return application.assets
    }

    @Provides
    @Singleton
    fun provideCredentialsManager(ldsAccountPrefs: LDSAccountPrefs): CredentialsManager {
        return ldsAccountPrefs
    }

    @Provides
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionManager(connectivityManager: ConnectivityManager): NetworkConnectionManager {
        return object : NetworkConnectionManager {
            override fun isConnected(): Boolean {
                return connectivityManager.activeNetworkInfo?.isConnected == true
            }
        }
    }

    @Provides
    fun provideWindowManager(application: Application): WindowManager {
        return application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    @Provides
    fun provideInputMethodManager(application: Application): InputMethodManager {
        return application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    fun provideNotificationManager(application: Application): NotificationManager {
        return application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun provideActivityManager(application: Application): ActivityManager {
        return application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }

    @Provides
    fun provideAccountManager(application: Application): AccountManager {
        return application.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
    }

    @Provides
    fun provideLDSAccountInterceptor(log: LDSAccountLogger, ldsAccountUtil: LDSAccountUtil, ldsAccountAuth: LDSAccountAuth): LDSAccountInterceptor {
        return LDSAccountInterceptor(log, ldsAccountUtil, ldsAccountAuth)
    }

    @Provides
    fun providePackageManager(application: Application): PackageManager {
        return application.packageManager
    }

    @Provides
    @Singleton
    fun provideCastManager(application: Application): CastManager {
        return CastManager(application, initCast = true, castFromDeviceEnabled = true)
    }

    @Provides
    @Singleton
    fun provideAndroidDownloadManager(application: Application): DownloadManager {
        return application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    @Singleton
    fun provideDownloadManagerHelper(downloadManager: DownloadManager): DownloadManagerHelper {
        return DownloadManagerHelper(downloadManager)
    }

    @Provides
    @Singleton
    fun provideAnalytics(application: Application): Analytics {
        return DefaultAnalytics(application)
    }

    @Provides
    @Singleton
    fun provideEventBus(): Bus {
        val bus = Bus.Builder()
                .build()
        bus.setRegistry(BusRegistry())
        return bus
    }

    @Provides
    @Singleton
    fun provideDatabaseConfig(fileUtil: GLFileUtil): DatabaseConfig {
        return AppDatabaseConfig(fileUtil)
    }

    @Provides
    @Singleton
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProvider.MainCoroutineContextProvider
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
//                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeConverter())
        return builder.create()
    }

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}
