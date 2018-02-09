package org.lds.ldssa.util;

import android.os.Build;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.BuildConfig;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.app.AppConfigService;
import org.lds.ldssa.model.webservice.app.DtoAppConfig;
import org.lds.ldssa.ui.notification.AppUpdateNotification;
import org.lds.mobile.util.LdsNetworkUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class AppUpdateUtil {
    private Prefs prefs;
    private LdsNetworkUtil networkUtil;
    private AppConfigService appConfigService;
    private AppUpdateNotification appUpdateNotification;

    @Inject
    public AppUpdateUtil(Prefs prefs, LdsNetworkUtil networkUtil, AppConfigService appConfigService, AppUpdateNotification appUpdateNotification) {
        this.prefs = prefs;
        this.networkUtil = networkUtil;
        this.appConfigService = appConfigService;
        this.appUpdateNotification = appUpdateNotification;
    }

    /**
     * Determines if the current application version
     * is the most recent available.  If it is not then
     * we will post a notification informing the user
     * that an update is available.
     */
    public void verifyAppVersion() {
        Timber.i("Checking for newer version of App...");

        //If we aren't connected to the internet don't verify the version
        if (!networkUtil.isConnected()) {
            return;
        }

        Call<DtoAppConfig> call = appConfigService.getAppConfig();
        DtoAppConfig config;

        try {
            Response<DtoAppConfig> response = call.execute();
            config = response.body();
        } catch (Exception e) {
            Timber.w(e, "Unable to retrieve the current app config");
            return;
        }

        prefs.updateLastAppUpdateCheck();
        if (config != null && isMoreRecent(config.getLatestVersion())) {
            appUpdateNotification.show();
        }
    }

    /**
     * Determines if the version represented by <code>latestVersion</code> is
     * more recent than the current app version.
     *
     * @param latestVersion The version to compare to the current app version.
     *                      This is expected to be in the format [minSDKVersion-appVersionCode] (e.g. "15-35000")
     * @return True if the <code>latestVersion</code> represents a more recent app version than the current one
     */
    private boolean isMoreRecent(String latestVersion) {
        if (StringUtils.isBlank(latestVersion)) {
            return false;
        }

        //Split the latestVersion in to the sdkVersion and versionCode
        String[] segments = latestVersion.split("-");
        if (segments.length != 2) {
            return false;
        }

        int minSdk = tryParseInt(segments[0], -1);
        int version = tryParseInt(segments[1], -1);

        //Perform the version comparison
        return minSdk > 0 && version > 0 && Build.VERSION.SDK_INT >= minSdk && version > BuildConfig.VERSION_CODE;
    }

    private int tryParseInt(String stringVal, int defaultVal) {
        if (stringVal == null) {
            return defaultVal;
        }

        try {
            return Integer.valueOf(stringVal);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
