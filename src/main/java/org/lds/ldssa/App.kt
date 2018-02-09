package org.lds.ldssa

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.evernote.android.job.JobManager
import com.facebook.FacebookSdk
import com.jakewharton.threetenabp.AndroidThreeTen
import com.localytics.android.Localytics
import io.fabric.sdk.android.Fabric
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.job.AppJobCreator
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.notification.NotificationChannels
import org.lds.ldssa.util.AppUpgradeUtil
import org.lds.mobile.devtools.initStetho
import org.lds.mobile.devtools.installLeakCanary
import org.lds.mobile.devtools.isLeakCanaryInAnalyzerProcess
import org.lds.mobile.log.CrashlyticsTree
import org.lds.mobile.log.DebugTree
import org.lds.mobile.log.ReleaseTree
import pocketbus.Registry
import timber.log.Timber
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import javax.inject.Inject

@Registry // PocketBus Registry
class App : Application() {
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var appJobCreator: AppJobCreator
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var appUpgradeUtil: AppUpgradeUtil
    @Inject
    lateinit var glUpdateLifecycle: GLUpdateLifecycle

    init {
        Injector.init(this)
    }

    override fun onCreate() {
        super.onCreate()

        // Leak Canary
        if (this.isLeakCanaryInAnalyzerProcess()) {
            // This process is dedicated to LeakCanary for heap analysis.
            // Do not init the app in this process!
            return
        }
        this.installLeakCanary()

        // Needs to be done prior to injection
        AndroidThreeTen.init(this)

        // Initialize dependency injection
        Injector.get().inject(this)

        // logging should be done before upgradeApp()
        setupLogging()

        FacebookSdk.sdkInitialize(applicationContext)
        JobManager.create(this).addJobCreator(appJobCreator)
        Localytics.autoIntegrate(this)

        // before anything gets initialized... allow for any app upgrades
        appUpgradeUtil.upgradeApp()

        analytics.upload()

        registerLifecycleCallbacks()
        registerExceptionLogging()

        NotificationChannels.registerAllChannels(this)

        // tools
        this.initStetho()
    }

    private fun setupLogging() {
        // Always register Crashltyics (even if CrashlyticsTree is not planted)
        Fabric.with(this, Crashlytics())

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        @Suppress("ConstantConditionIf") // value is constant from BuildConfig
        if (BuildConfig.BUILD_TYPE != "debug") {
            // Log.e(...) will log a non-fatal crash in Crashlytics
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        if (filesDir != null) {
            MultiDex.install(this)
        } else {
            // During app install it might have experienced "INSTALL_FAILED_DEXOPT" (reinstall is the only known work-around)
            // https://code.google.com/p/android/issues/detail?id=8886
            val message = getString(R.string.app_name) + " is in a bad state, please uninstall/reinstall"
            Timber.e(message)
        }
    }

    private fun registerLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(glUpdateLifecycle)
    }

    private fun registerExceptionLogging() {
        val platformUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, ex ->
            prefs.updateLastErrorTime()
            prefs.lastErrorMessage = ex.message
            prefs.lastErrorDetails = getStackTrace(ex)
            platformUncaughtExceptionHandler.uncaughtException(thread, ex)
        }
    }

    private fun getStackTrace(throwable: Throwable?): String {
        if (throwable == null || throwable.stackTrace == null || throwable.stackTrace.isEmpty()) {
            return ""
        }

        val out = StringOutputStream()
        val stream = PrintStream(out) // NOSONAR
        throwable.printStackTrace(stream) // NOSONAR

        val stackTrace = out.toString()
        stream.close()

        return stackTrace
    }

    private class StringOutputStream : OutputStream() {
        private val builder = StringBuilder()

        @Throws(IOException::class)
        override fun write(oneByte: Int) {
            builder.append(oneByte.toChar())
        }

        override fun toString(): String {
            return builder.toString()
        }
    }
}
