package org.lds.ldssa.task;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import org.lds.ldssa.download.CheckDownloadsTask;
import org.lds.ldssa.download.InitialContentDownloadTask;
import org.lds.ldssa.event.StartupProgressEvent;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.gl.history.HistoryManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.TipsUpdateUtil;
import org.lds.ldssa.util.TipsUtil;
import org.lds.ldssa.util.UserdataDbUtil;
import org.lds.mobile.task.RxTask;
import org.lds.mobile.util.LdsTimeUtil;

import javax.inject.Inject;
import javax.inject.Provider;

import pocketbus.Bus;
import timber.log.Timber;

public class StartupTask extends RxTask<Boolean> {
    public static final int TOTAL_STARTUP_TASKS = 7;

    private final Bus bus;
    private final InternalIntents internalIntents;
    private final Prefs prefs;
    private final DatabaseManager databaseManager;
    private final CatalogUpdateUtil catalogUpdateUtil;
    private final ScreenLauncherUtil screenLauncherUtil;
    private final LdsTimeUtil timeUtil;
    private final UserdataDbUtil userdataDbUtil;
    private final HistoryManager historyManager;
    private final TipsUtil tipsUtil;
    private final TipsUpdateUtil tipsUpdateUtil;
    private final LanguageUtil languageUtil;
    private final Provider<CheckDownloadsTask> checkDownloadsTaskProvider;
    private final Provider<InitialContentDownloadTask> initialContentDownloadTaskProvider;

    private FragmentActivity contextActivity; // to start intent
    private int startupTaskCount = 0; // total tasks count is defined above in TOTAL_STARTUP_TASKS

    @Inject
    public StartupTask(Bus bus, InternalIntents internalIntents, Prefs prefs, DatabaseManager databaseManager, CatalogUpdateUtil catalogUpdateUtil,
                       ScreenLauncherUtil screenLauncherUtil, LdsTimeUtil timeUtil, UserdataDbUtil userdataDbUtil,
                       HistoryManager historyManager, TipsUtil tipsUtil, TipsUpdateUtil tipsUpdateUtil, LanguageUtil languageUtil,
                       Provider<CheckDownloadsTask> checkDownloadsTaskProvider, Provider<InitialContentDownloadTask> initialContentDownloadTaskProvider) { // NOSONAR
        this.bus = bus;
        this.internalIntents = internalIntents;
        this.prefs = prefs;
        this.databaseManager = databaseManager;
        this.catalogUpdateUtil = catalogUpdateUtil;
        this.screenLauncherUtil = screenLauncherUtil;
        this.timeUtil = timeUtil;
        this.userdataDbUtil = userdataDbUtil;
        this.historyManager = historyManager;
        this.tipsUtil = tipsUtil;
        this.tipsUpdateUtil = tipsUpdateUtil;
        this.languageUtil = languageUtil;
        this.checkDownloadsTaskProvider = checkDownloadsTaskProvider;
        this.initialContentDownloadTaskProvider = initialContentDownloadTaskProvider;
    }

    public StartupTask init(FragmentActivity contextActivity) {
        this.contextActivity = contextActivity;
        return this;
    }

    @NonNull
    @Override
    protected Boolean run() {
        long startMs = System.currentTimeMillis();

        // make initial connection to database(s)
        showProgress("Init databases");
        databaseManager.initDatabaseConnection();

        // make sure that a catalog file exists prior to database initialization (must be first access to database... creates the catalog database)
        showProgress("Prepare catalog");
        catalogUpdateUtil.prepareCatalogDatabase();

        // make sure that a tips file exists prior to database initialization
        showProgress("Prepare Tips");
        tipsUpdateUtil.prepareTipsDatabase();

        // init a default userdata database
        showProgress("Prepare userdata database");
        userdataDbUtil.openCurrentDatabase();

        //Removes history older than 3 months or greater than 1_000 items
        showProgress("History cleanup");
        historyManager.cleanupHistory();

        //Download the initial catalog items
        showProgress("Content item download");
        if (!prefs.getInitialContentDownloaded()) {
            initialContentDownloadTaskProvider.get().init(languageUtil.getDeviceLanguageId()).execute(); // this should always use the device language
            prefs.setInitialContentDownloaded(true);
        } else {
            checkDownloadsTaskProvider.get().execute();
        }

        showProgress("FINISHED");
        timeUtil.logTimeElapsedFromNow("Startup", "Startup Elapsed Time:", startMs);

        return true;
    }

    @Override
    protected void onResult(Boolean b) {
        if (tipsUtil.shouldShowWelcomeScreen()) {
            internalIntents.showWelcomeScreen(contextActivity, tipsUtil.getWelcomeTipIds());
        } else if (contextActivity != null) {
            screenLauncherUtil.showStartupScreen(contextActivity);
        }
    }

    private void showProgress(String logText) {
        showProgress(logText, false);
    }

    private void showProgress(String logText, boolean indeterminate) {
        startupTaskCount++;
        Timber.i("Startup progress: [%d] [%s]", startupTaskCount, logText);

        if (indeterminate) {
            bus.post(new StartupProgressEvent(logText, true));
        } else {
            bus.post(new StartupProgressEvent(logText, startupTaskCount));
        }
    }
}
