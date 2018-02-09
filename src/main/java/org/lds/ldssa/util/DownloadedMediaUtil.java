package org.lds.ldssa.util;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.types.ItemMediaType;

import java.io.File;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class DownloadedMediaUtil {

    private GLFileUtil fileUtil;
    private DownloadedMediaManager downloadedMediaManager;
    private GLDownloadManager downloadManager;

    @Inject
    public DownloadedMediaUtil(GLFileUtil fileUtil, DownloadedMediaManager downloadedMediaManager, GLDownloadManager downloadManager) {
        this.fileUtil = fileUtil;
        this.downloadedMediaManager = downloadedMediaManager;
        this.downloadManager = downloadManager;
    }

    /**
     * A utility method for determining the downloaded file url for the given media.
     * If the system thinks the file should be downloaded and the file does not exist, it will re-download the file.
     *
     * @param contentItemId - the content item id of the media
     * @param subItemId - the subItem id of the media
     * @param tertiaryId - the tertiary id of the media
     * @param mediaType - the type of media (Audio, Video)
     * @param downloadUrl - the url to download the media if it should be downloaded but is not
     * @return the file url where the media is/would be stored on disk
     */
    public String getDownloadedMediaUrl(long contentItemId, long subItemId, String tertiaryId, ItemMediaType mediaType, @Nullable String downloadUrl) {
        DownloadedMedia downloadedMedia = downloadedMediaManager.findByIds(contentItemId, subItemId, tertiaryId, mediaType);
        return getDownloadedMediaUrl(downloadedMedia, downloadUrl);
    }

    /**
     * A utility method for determining the downloaded file url for the given media.
     * If the system thinks the file should be downloaded and the file does not exist, it will re-download the file.
     *
     * @param downloadedMedia - the downloaded media to get the file url for
     * @param downloadUrl - the url to download the media if it should be downloaded but is not
     * @return the file url where the media is/would be stored on disk
     */
    public String getDownloadedMediaUrl(DownloadedMedia downloadedMedia, @Nullable String downloadUrl) {
        String downloadedMediaUrl = null;
        if (downloadedMedia != null) {
            File downloadedFile = fileUtil.getContentMediaFile(downloadedMedia.getFile(), downloadedMedia.getType());
            if (downloadedFile != null && downloadedFile.exists()) {
                downloadedMediaUrl = downloadedFile.getAbsolutePath();
            } else {
                // If the file does not exist then delete the DownloadedMedia record and re-download it
                downloadedMediaManager.delete(downloadedMedia);
                if (!StringUtils.isEmpty(downloadUrl)) {
                    downloadManager.downloadMedia(downloadedMedia.getContentItemId(), downloadedMedia.getSubItemId(), downloadedMedia.getTertiaryId(),
                            downloadedMedia.getTitle(), downloadUrl, downloadedMedia.getType());
                }
            }
        }

        return downloadedMediaUrl;
    }
}