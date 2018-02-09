package org.lds.ldssa.util;

import android.net.Uri;
import android.support.annotation.NonNull;

import javax.annotation.Nullable;

public class MimeTypeUtil {
    public enum MediaType {
        SMOOTH_STREAM(".ism", "application/vnd.ms-sstr+xml"),
        DASH(".mpd", "application/dash+xml"),
        HLS(".m3u8", "application/x-mpegurl"),
        MP4(".mp4", "video/mp4"),
        FMP4(".fmp4", "video/fmp4"),
        M4A(".m4a", "video/m4a"),
        MP3(".mp3", "audio/mp3"),
        TS(".ts", "video/mp2t"),
        AAC(".aac", "audio/aac"),
        WEBM(".webm", "video/webm"),
        MKV(".mkv", "video/mkv"),
        UNKNOWN("", "");

        private String extension;
        private String mimeType;

        MediaType(String extension, String mimeType) {
            this.extension = extension;
            this.mimeType = mimeType;
        }

        public String getExtension() {
            return extension;
        }

        public String getMimeType() {
            return mimeType;
        }
    }

    /**
     * Determines the media type based on the mediaUri
     *
     * @param mediaUri The uri for the media to determine the MediaType for
     * @return The resulting MediaType
     */
    @NonNull
    public static String getMimeType(String mediaUri) {
        String extension = getExtension(mediaUri);
        if (extension == null) {
            return "";
        }

        //Finds the MediaType with the same extension
        for (MediaType type : MediaType.values()) {
            if (type.getExtension().equals(extension)) {
                return type.getMimeType();
            }
        }

        return "";
    }

    @Nullable
    private static String getExtension(String mediaUri) {
        if (mediaUri == null || mediaUri.trim().isEmpty()) {
            return null;
        }

        Uri uri = Uri.parse(mediaUri);
        String lastPath = uri.getLastPathSegment();

        int periodIndex = lastPath.lastIndexOf('.');
        if (periodIndex == -1 || periodIndex >= lastPath.length()) {
            return null;
        }

        String rawExtension = lastPath.substring(periodIndex);
        return rawExtension.toLowerCase();
    }
}
