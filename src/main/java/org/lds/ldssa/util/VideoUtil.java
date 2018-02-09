package org.lds.ldssa.util;

import android.app.Application;
import android.support.annotation.Nullable;

import com.devbrackets.android.exomedia.util.DeviceUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideo;
import org.lds.ldssa.model.webview.content.dto.DtoVideoSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VideoUtil {
    private static final String CONTAINER_HLS = "hls";

    private final Application application;

    private Boolean deviceSupportsHls;

    @Inject
    public VideoUtil(Application application) {
        this.application = application;
    }

    /**
     * Determines the url for the current video that can be used for download and sharing
     *
     * @return The video download url
     */
    @Nullable
    public String getVideoDownloadUrl(@Nullable DtoInlineVideo videoQualities) {
        if (videoQualities == null || videoQualities.getSources() == null || videoQualities.getSources().isEmpty()) {
            return null;
        }

        String downloadUrl = null;
        int targetResolution = 0;

        //Finds the best video quality
        for (DtoVideoSource source : videoQualities.getSources()) {
            //We can't download HLS videos
            if (isSourceHLS(source)) {
                continue;
            }

            //Compare the current resolution to the source and set the url if the sources resolution is greater
            int height = NumberUtils.toInt(source.getHeight(), 0);
            if (StringUtils.isNotBlank(source.getUrl()) && height > targetResolution) {
                targetResolution = height;
                downloadUrl = source.getUrl();
            }
        }

        return downloadUrl;
    }

    /**
     * Determines the download size for the current video
     *
     * @return The video download size
     */
    public long getVideoDownloadSize(@Nullable DtoInlineVideo videoQualities) {
        if (videoQualities == null || videoQualities.getSources() == null || videoQualities.getSources().isEmpty()) {
            return 0;
        }

        long downloadSize = 0;
        int targetResolution = 0;

        //Finds the best video quality
        for (DtoVideoSource source : videoQualities.getSources()) {
            //We can't download HLS videos
            if (isSourceHLS(source)) {
                continue;
            }

            //Compare the current resolution to the source and set the url if the sources resolution is greater
            int height = NumberUtils.toInt(source.getHeight(), 0);
            if (StringUtils.isNotBlank(source.getUrl()) && height > targetResolution) {
                targetResolution = height;
                try {
                    downloadSize = Long.valueOf(source.getSize());
                } catch (NumberFormatException e) {
                    downloadSize = 0L;
                }
            }
        }

        return downloadSize;
    }

    /**
     * Determines if the specified VideoSource is for an HLS video.
     *
     * @param source The source to determine if it is an HLS
     * @return True if the specified source is an HLS
     */
    public boolean isSourceHLS(DtoVideoSource source) {
        return CONTAINER_HLS.equalsIgnoreCase(source.getDataContainer());
    }

    /**
     * Determines if the current device supports HLS videos
     *
     * @return True if the device supports HLS
     */
    public boolean deviceSupportsHLS() {
        if (deviceSupportsHls == null) {
            DeviceUtil deviceUtil = new DeviceUtil();
            deviceSupportsHls = deviceUtil.supportsExoPlayer(application);
        }

        return deviceSupportsHls;
    }
}
