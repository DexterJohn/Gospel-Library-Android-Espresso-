package org.lds.ldssa.ui.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollection;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItem;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.gl.Downloadable;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.types.ItemMediaType;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType;
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideo;
import org.lds.ldssa.util.VideoUtil;
import org.lds.mobile.loader.AsyncLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Determines the list of downloadable items for the specified
 * contentItemId and subItemId.
 */
public class DownloadableMediaListLoader extends AsyncLoader<List<DownloadableMediaListLoader.DownloadMediaDialogItem>> {

    @Inject
    VideoUtil videoUtil;
    @Inject
    ItemManager itemManager;
    @Inject
    NavItemManager navItemManager;
    @Inject
    NavCollectionManager navCollectionManager;
    @Inject
    LibraryItemManager libraryItemManager;
    @Inject
    DownloadedMediaManager downloadedMediaManager;
    @Inject
    DownloadQueueItemManager downloadQueueItemManager;
    @Inject
    RelatedAudioItemManager relatedAudioItemManager;
    @Inject
    Prefs prefs;

    private long contentItemId;
    private long subItemId;
    private long navCollectionId;
    private DtoInlineVideo video;
    private ItemMediaType itemMediaType;
    private boolean onlyAllAudio;

    public DownloadableMediaListLoader(Context context, long contentItemId, long subItemId, long navCollectionId, @Nullable DtoInlineVideo video, ItemMediaType itemMediaType, boolean onlyAllAudio) {
        super(context);
        Injector.INSTANCE.get().inject(this);

        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.navCollectionId = navCollectionId;
        this.video = video != null ? video : new DtoInlineVideo();
        this.itemMediaType = itemMediaType;
        this.onlyAllAudio = onlyAllAudio;
    }

    @Override
    public List<DownloadMediaDialogItem> loadInBackground() {
        List<DownloadMediaDialogItem> items = new ArrayList<>();
        if (onlyAllAudio) {
            return addAllAudioOption(items);
        }

        Set<String> downloadUrls = new HashSet<>();
        String title = navItemManager.findTitleById(contentItemId, subItemId);
        if (itemMediaType == ItemMediaType.VIDEO) {
            items.addAll(getVideoItems(downloadUrls, title));
        } else if (itemMediaType == ItemMediaType.AUDIO) {
            items.addAll(getAudioItems(downloadUrls, title));
        }

//        items = removeCurrentDownloads(items); // TODO Until we handle items that are already downloaded, we need to display the item in the list
        return items;
    }

    /**
     * Retrieves all the video items to download
     *
     * @param addedUrls The Set of URLs to compare and add items to
     * @param fallbackTitle The title to use when the video doesn't have it's own title
     * @return A List of downloads for the video items
     */
    private List<DownloadMediaDialogItem> getVideoItems(@NonNull Set<String> addedUrls, String fallbackTitle) {
        List<DownloadMediaDialogItem> items = new LinkedList<>();

        String videoDownloadUrl = videoUtil.getVideoDownloadUrl(video);
        long videoDownloadSize = videoUtil.getVideoDownloadSize(video);
        if (videoDownloadUrl != null && !addedUrls.contains(videoDownloadUrl)) {
            String videoTitle = video.getTitle() != null ? video.getTitle() : fallbackTitle;
            if (video.getSources() != null)
            items.add(new DownloadMediaDialogItem(videoTitle, videoDownloadUrl, ItemMediaType.VIDEO, video.getVideoId(), videoDownloadSize, DownloadMediaDialogItem.TAG_DOWNLOAD_SINGLE_MEDIA));
            addedUrls.add(videoDownloadUrl);
        }

        return items;
    }

    /**
     * Retrieves all the audio items to download
     *
     * @param addedUrls The Set of URLs to compare and add items to
     * @param fallbackTitle The title to use when the audio doesn't have it's own title
     * @return A List of downloads for the audio items
     */
    private List<DownloadMediaDialogItem> getAudioItems(@NonNull Set<String> addedUrls, String fallbackTitle) {
        List<DownloadMediaDialogItem> items = new LinkedList<>();
        if (subItemId <= 0) {
            return items;
        }

        AudioPlaybackVoiceType voice = prefs.getAudioVoice();
        if (voice == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
            // If the user has Text-to-speech as their default, then allow downloading of the audio default voice (male)
            voice = AudioPlaybackVoiceType.MALE;
        }
        RelatedAudioItem relatedAudioItem = relatedAudioItemManager.findBySubItemIdAndVoiceId(contentItemId, subItemId, voice.getVoiceId());
        if (relatedAudioItem != null && !addedUrls.contains(relatedAudioItem.getMediaUrl())) {
            items.add(new DownloadMediaDialogItem(fallbackTitle, relatedAudioItem.getMediaUrl(), ItemMediaType.AUDIO, relatedAudioItem.getMediaUrl(), relatedAudioItem.getFileSize(), DownloadMediaDialogItem.TAG_DOWNLOAD_SINGLE_MEDIA));
            addedUrls.add(relatedAudioItem.getMediaUrl());
        }

        return items;
    }

    /**
     * filter out existing downloads and current downloads
     *
     * @param items The items to remove the already downloaded items from
     * @return The updated list of items to select from
     */
    private List<DownloadMediaDialogItem> removeCurrentDownloads(List<DownloadMediaDialogItem> items) {
        // Removes items already downloaded
        List<DownloadedMedia> downloadedItems = downloadedMediaManager.findAllByContentItemAndPage(contentItemId, subItemId);
        items = removeDownloaded(items, downloadedItems);

        // Removes items being downloaded
        List<DownloadQueueItem> downloadingItems = downloadQueueItemManager.findAllByContentItemAndPage(contentItemId, subItemId);
        items = removeDownloaded(items, downloadingItems);

        return items;
    }

    private List<DownloadMediaDialogItem> removeDownloaded(List<DownloadMediaDialogItem> items, List<? extends Downloadable> downloadableItems) {
        if (items.isEmpty() || downloadableItems.isEmpty()) {
            return items;
        }

        for (Downloadable downloadable : downloadableItems) {
            Iterator<DownloadMediaDialogItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                DownloadMediaDialogItem item = iterator.next();
                if (downloadable.getType() == item.getType() &&
                        downloadable.getTertiaryId() != null && downloadable.getTertiaryId().equals(item.getTertiaryId())) {
                    iterator.remove();
                }
            }
        }

        return items;
    }

    private List<DownloadMediaDialogItem> addAllAudioOption(List<DownloadMediaDialogItem> items) {
        AudioPlaybackVoiceType voice = prefs.getAudioVoice();
        if (voice == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
            // If the user has Text-to-speech as their default, then allow downloading of the audio default voice (male)
            voice = AudioPlaybackVoiceType.MALE;
        }

        if (navCollectionId > NavCollection.ROOT_NAV_COLLECTION_ID) {
            // Adds an option to download all audio for the nav collection (1 Nephi, Alma, etc.)
            String title = navCollectionManager.findTitleById(contentItemId, navCollectionId);
            long totalDownloadSizeForVoice = relatedAudioItemManager.getTotalDownloadSizeForNavCollectionAndVoice(contentItemId, navCollectionId, voice.getVoiceId());
            items.add(new DownloadMediaDialogItem(title, "", ItemMediaType.AUDIO, "", totalDownloadSizeForVoice, DownloadMediaDialogItem.TAG_DOWNLOAD_ALL_AUDIO));
        } else {
            // Adds an option to download all audio for the content item
            String title = itemManager.findTitleById(contentItemId);
            long totalDownloadSizeForVoice = relatedAudioItemManager.getTotalDownloadSizeForVoice(contentItemId, voice.getVoiceId());
            items.add(new DownloadMediaDialogItem(title, "", ItemMediaType.AUDIO, "", totalDownloadSizeForVoice, DownloadMediaDialogItem.TAG_DOWNLOAD_ALL_AUDIO));
        }

        return items;
    }

    public static class DownloadMediaDialogItem {
        public static final int TAG_DOWNLOAD_SINGLE_MEDIA = 1;
        public static final int TAG_DOWNLOAD_ALL_AUDIO = 2;

        private final String title;
        private final String downloadUrl;
        private final ItemMediaType type;
        private final long fileSize;
        private final int tag;

        @Nullable
        private String tertiaryId;

        public DownloadMediaDialogItem(String title, String downloadUrl, ItemMediaType type, @Nullable String tertiaryId, long fileSize, int tag) {
            this.title = title;
            this.downloadUrl = downloadUrl;
            this.type = type;
            this.fileSize = fileSize;
            this.tertiaryId = tertiaryId;
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public ItemMediaType getType() {
            return type;
        }

        public int getTag() {
            return tag;
        }

        @Nullable
        public String getTertiaryId() {
            return tertiaryId;
        }

        public long getFileSize() {
            return fileSize;
        }
    }
}
