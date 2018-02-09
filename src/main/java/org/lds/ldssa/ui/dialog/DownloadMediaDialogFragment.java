package org.lds.ldssa.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import org.lds.ldssa.R;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.event.BackgroundSnackbarEvent;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.content.navcollection.NavCollection;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItem;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.types.ItemMediaType;
import org.lds.ldssa.model.database.types.SnackbarAction;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType;
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideo;
import org.lds.ldssa.ui.adapter.DownloadMediaDialogAdapter;
import org.lds.ldssa.ui.loader.DownloadableMediaListLoader;
import org.lds.ldssa.ui.widget.LoadingView;
import org.lds.mobile.util.LdsTagUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketbus.Bus;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;

import static org.lds.ldssa.ui.loader.DownloadableMediaListLoader.DownloadMediaDialogItem;

/**
 * A Dialog for prompting the user which file we should
 * download for the selected nav item
 */
public class DownloadMediaDialogFragment extends AppCompatDialogFragment implements LoaderManager.LoaderCallbacks<List<DownloadMediaDialogItem>>,
        DownloadMediaDialogAdapter.OnMediaDialogItemClickListener {
    private static final String TAG = LdsTagUtil.createTag(DownloadMediaDialogFragment.class);
    private static final int LOADER_ID = 1;

    private static final String ARG_CONTENT_ITEM_ID = "ARG_CONTENT_ITEM_ID";
    private static final String ARG_SUB_ITEM_ID = "ARG_SUB_ITEM_ID";
    private static final String ARG_NAV_COLLECTION_ID = "ARG_NAV_COLLECTION_ID";
    private static final String ARG_INLINE_VIDEO = "ARG_INLINE_VIDEO";
    private static final String ARG_MEDIA_TYPE = "ARG_MEDIA_TYPE";

    @Inject
    Bus bus;
    @Inject
    GLDownloadManager glDownloadManager;
    @Inject
    RelatedAudioItemManager relatedAudioItemManager;
    @Inject
    Prefs prefs;

    @BindArgument(ARG_CONTENT_ITEM_ID)
    long contentItemId;
    @BindArgument(ARG_SUB_ITEM_ID)
    long subItemId;
    @BindArgument(ARG_NAV_COLLECTION_ID)
    long navCollectionId;
    @Nullable
    @BindArgument(ARG_INLINE_VIDEO)
    DtoInlineVideo video;
    @BindArgument(ARG_MEDIA_TYPE)
    ItemMediaType mediaType;

    @BindView(R.id.download_media_dialog_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.download_media_dialog_progress)
    LoadingView loadingView;

    private DownloadMediaDialogAdapter adapter;

    public static DownloadMediaDialogFragment newInstance(long contentItemId) {
        return newInstance(contentItemId, 0, null, ItemMediaType.AUDIO);
    }

    public static DownloadMediaDialogFragment newInstance(long contentItemId, long navCollectionId) {
        return newInstance(contentItemId, 0, navCollectionId, null, ItemMediaType.AUDIO);
    }

    public static DownloadMediaDialogFragment newInstance(long contentItemId, long subItemId, @Nullable DtoInlineVideo video, ItemMediaType mediaType) {
        return newInstance(contentItemId, subItemId, 0, video, mediaType);
    }

    public static DownloadMediaDialogFragment newInstance(long contentItemId, long subItemId, long navCollectionId, @Nullable DtoInlineVideo video, ItemMediaType mediaType) {
        DownloadMediaDialogFragment dialog = new DownloadMediaDialogFragment();

        Bundle args = new Bundle();
        args.putLong(ARG_CONTENT_ITEM_ID, contentItemId);
        args.putLong(ARG_SUB_ITEM_ID, subItemId);
        args.putLong(ARG_NAV_COLLECTION_ID, navCollectionId);
        args.putSerializable(ARG_INLINE_VIDEO, video);
        args.putSerializable(ARG_MEDIA_TYPE, mediaType);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Injector.INSTANCE.get().inject(this);
        PocketKnife.bindArguments(this);

        return new MaterialDialog.Builder(getActivity())
                .title(determineTitleByMediaType(mediaType))
                .customView(getContentView(getActivity().getLayoutInflater()), false)
                .positiveText(R.string.download)
                .negativeText(R.string.cancel)
                .onPositive((materialDialog, dialogAction) -> onMediaDialogItemClick(adapter.getItem(0)))
                .onNegative((materialDialog, dialogAction) -> dismiss())
                .build();
    }

    @Override
    public Loader<List<DownloadMediaDialogItem>> onCreateLoader(int id, Bundle args) {
        return new DownloadableMediaListLoader(getActivity(), contentItemId, subItemId, navCollectionId, video, mediaType, mediaType == ItemMediaType.AUDIO && subItemId == 0);
    }

    @Override
    public void onLoadFinished(Loader<List<DownloadMediaDialogItem>> loader, List<DownloadMediaDialogItem> data) {
        adapter = new DownloadMediaDialogAdapter(data);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadingView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<DownloadMediaDialogItem>> loader) {
        //Purposefully left blank
    }

    @Override
    public void onMediaDialogItemClick(DownloadMediaDialogItem item) {
        if (item.getTag() == DownloadMediaDialogItem.TAG_DOWNLOAD_ALL_AUDIO) {
            AudioPlaybackVoiceType audioVoice = prefs.getAudioVoice();
            if (audioVoice == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
                // If the user has Text-to-speech as their default, then allow downloading of the audio default voice (male)
                audioVoice = AudioPlaybackVoiceType.MALE;
            }

            List<RelatedAudioItem> relatedAudioItemList;
            if (navCollectionId > NavCollection.ROOT_NAV_COLLECTION_ID) {
                relatedAudioItemList = relatedAudioItemManager.findAllByNavCollectionIdAndVoiceId(contentItemId, navCollectionId, audioVoice.getVoiceId());
            } else {
                relatedAudioItemList = relatedAudioItemManager.findAllByVoiceId(contentItemId, audioVoice.getVoiceId());
            }
            glDownloadManager.downloadAllAudioItems(contentItemId, relatedAudioItemList);
            if (glDownloadManager.networkUsable()) {
                postMediaDownloadingEvent(item);
            }
        } else {
            //Download the selected item
            glDownloadManager.downloadMedia(contentItemId, subItemId, item.getTertiaryId(), item.getTitle(), item.getDownloadUrl(), item.getType());
            if (glDownloadManager.networkUsable()) {
                postMediaDownloadingEvent(item);
            }
        }

        dismiss();
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    private @StringRes int determineTitleByMediaType(ItemMediaType mediaType) {
        if (mediaType == ItemMediaType.AUDIO) {
            return R.string.download_audio;
        } else {
            return R.string.download_video;
        }
    }

    private void postMediaDownloadingEvent(DownloadMediaDialogItem item) {
        int stringRes;
        if (item.getType() == ItemMediaType.VIDEO) {
            stringRes = R.string.downloading_video;
        } else {
            stringRes = R.string.downloading_audio;
        }
        bus.post(new BackgroundSnackbarEvent(getContext().getResources().getString(stringRes, item.getTitle()), SnackbarAction.VIEW_DOWNLOADS));
    }

    /**
     * Creates the content view and performs any necessary setup.
     *
     * @param inflater The LayoutInflater to use when creating the view
     * @return The resulting View
     */
    @SuppressLint("InflateParams")
    private View getContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_download_media, null);
        ButterKnife.bind(this, view);
        PocketKnife.bindArguments(this);

        getLoaderManager().initLoader(LOADER_ID, null, this);
        return view;
    }
}
