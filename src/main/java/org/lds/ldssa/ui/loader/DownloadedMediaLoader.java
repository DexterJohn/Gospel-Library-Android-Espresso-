package org.lds.ldssa.ui.loader;

import android.content.Context;

import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager;
import org.lds.mobile.loader.AsyncLoader;

import java.util.List;

import javax.inject.Inject;

public class DownloadedMediaLoader extends AsyncLoader<List<?>> {

    @Inject
    DownloadedMediaManager downloadedMediaManager;
    @Inject
    DownloadedMediaCollectionManager downloadedMediaCollectionManager;

    private long contentItemId;
    private boolean sortBySize;

    public DownloadedMediaLoader(Context context, boolean sortBySize, long contentItemId) {
        super(context);
        Injector.INSTANCE.get().inject(this);

        this.sortBySize = sortBySize;
        this.contentItemId = contentItemId;
    }

    @Override
    public List<?> loadInBackground() {
        return contentItemId != -1 ? getDownloadedMedia() : getDownloadedMediaCollections();
    }

    private List<?> getDownloadedMedia() {
        return downloadedMediaManager.findAllByContentItem(contentItemId, sortBySize);
    }

    private List<?> getDownloadedMediaCollections() {
        return downloadedMediaCollectionManager.findAll();
    }
}
