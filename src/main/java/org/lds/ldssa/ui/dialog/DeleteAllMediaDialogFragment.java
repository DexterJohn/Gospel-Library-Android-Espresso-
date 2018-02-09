package org.lds.ldssa.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;

import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.mobile.util.LdsTagUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import pocketknife.BindArgument;
import pocketknife.PocketKnife;

public class DeleteAllMediaDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = LdsTagUtil.createTag(DeleteAllMediaDialogFragment.class);
    private static final String ARG_CONTENT_ITEM_ID = "ARG_CONTENT_ITEM_ID";

    public interface DeleteListener {
        void onItemsDeleted();
    }

    @Inject
    DownloadedMediaManager downloadedMediaManager;
    @Inject
    ItemManager itemManager;
    @Inject
    GLFileUtil fileUtil;
    @Inject
    Analytics analytics;
    @Inject
    ToastUtil toastUtil;

    @BindArgument(ARG_CONTENT_ITEM_ID)
    long contentItemId;

    private DeleteListener listener;

    public static DeleteAllMediaDialogFragment newInstance(long contentItemId) {
        DeleteAllMediaDialogFragment dialog = new DeleteAllMediaDialogFragment();

        Bundle args = new Bundle();
        args.putLong(ARG_CONTENT_ITEM_ID, contentItemId);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Injector.INSTANCE.get().inject(this);
        PocketKnife.bindArguments(this);

        return new MaterialDialog.Builder(getActivity())
                .title(R.string.delete_media)
                .iconRes(R.drawable.ic_lds_action_delete_24dp)
                .content(getMessage())
                .positiveText(R.string.delete)
                .onPositive((materialDialog, dialogAction) -> {
                    onDelete();
                    dismiss();
                })
                .negativeText(R.string.cancel)
                .onNegative((materialDialog, dialogAction) -> dismiss())
                .build();
    }

    public void show(FragmentManager supportFragmentManager) {
        show(supportFragmentManager, TAG);
    }

    public void setDeleteListener(@Nullable DeleteListener listener) {
        this.listener = listener;
    }

    private String getMessage() {
        int itemCount = (int)(contentItemId != -1 ? downloadedMediaManager.findCountForContentItem(contentItemId) : downloadedMediaManager.findCount());
        String itemCountString = getResources().getQuantityString(R.plurals.num_items, itemCount, itemCount);

        String message;
        if (contentItemId != -1) {
            message = getString(R.string.delete_media_book_message, itemManager.findTitleById(contentItemId), itemCountString);
        } else {
            message = getString(R.string.delete_media_all_message, itemCountString);
        }

        return message;
    }

    /**
     * Attempts to delete the specified media items
     *
     * @param mediaItems The list of items to delete
     * @return A list of items that failed to correctly delete
     */
    private List<Long> deleteMediaFiles(List<DownloadedMedia> mediaItems) {
        List<Long> deleteFailedItems = new LinkedList<>();

        for(DownloadedMedia mediaItem : mediaItems) {
            //Delete the file
            File file = fileUtil.getContentMediaFile(mediaItem.getFile(), mediaItem.getType());

            //If we fail to delete the file then inform the user
            if (file != null && file.exists() && !file.delete()) {
                deleteFailedItems.add(mediaItem.getId());
            }

            logAnalytics(mediaItem);
        }

        return deleteFailedItems;
    }

    private void onDelete() {
        boolean deleteAll = contentItemId == -1;
        List<DownloadedMedia> media = deleteAll ? downloadedMediaManager.findAll() : downloadedMediaManager.findAllByContentItem(contentItemId);
        List<Long> failedMedia = deleteMediaFiles(media);

        //Remove the deleted items from the database
        if (failedMedia.isEmpty()) {
            if (deleteAll) {
                downloadedMediaManager.deleteAll();
            } else {
                downloadedMediaManager.deleteAllForContentItem(contentItemId);
            }
        } else {
            //Creates a list of ids representing the deleted items
            List<Long> deletedIds = new ArrayList<>();
            Set<Long> failedIds = new HashSet<>(failedMedia);
            for (DownloadedMedia downloadedMedia : media) {
                if (!failedIds.contains(downloadedMedia.getId())) {
                    deletedIds.add(downloadedMedia.getId());
                }
            }

            //Only removes the deleted files from the database and informs the user of the partial failure
            downloadedMediaManager.deleteAllInIds(deletedIds);
            toastUtil.showLong(R.string.delete_media_all_partial_failed);
        }

        if (listener != null) {
            listener.onItemsDeleted();
        }
    }

    private void logAnalytics(DownloadedMedia downloadedMedia) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(Analytics.Attribute.URL, downloadedMedia.getTertiaryId());
        attributes.put(Analytics.Attribute.TITLE, downloadedMedia.getTitle());
        attributes.put(Analytics.Attribute.CONTENT_TYPE, downloadedMedia.getType().name());
        analytics.postEvent(Analytics.Event.ITEM_UNINSTALLED, attributes);
    }
}
