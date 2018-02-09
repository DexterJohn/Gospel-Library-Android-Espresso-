package org.lds.ldssa.ui.adapter;

import android.view.ViewGroup;

import com.devbrackets.android.recyclerext.adapter.RecyclerListAdapter;

import org.apache.commons.io.FileUtils;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.ui.adapter.viewholder.DownloadMediaViewHolder;

import java.util.List;

import static org.lds.ldssa.ui.loader.DownloadableMediaListLoader.DownloadMediaDialogItem;

public class DownloadMediaDialogAdapter extends RecyclerListAdapter<DownloadMediaViewHolder, DownloadMediaDialogItem> {

    public interface OnMediaDialogItemClickListener {
        void onMediaDialogItemClick(DownloadMediaDialogItem item);
    }

    public DownloadMediaDialogAdapter(List<DownloadMediaDialogItem> items) {
        Injector.INSTANCE.get().inject(this);
        addAll(items);
    }

    @Override
    public DownloadMediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DownloadMediaViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(DownloadMediaViewHolder holder, int position) {
        DownloadMediaDialogItem item = getItem(position);
        if (item == null) {
            return;
        }

        holder.setPosition(position);
        holder.setTitle(item.getTitle());
        if (item.getFileSize() > 0) {
            holder.setDetails(FileUtils.byteCountToDisplaySize(item.getFileSize()));
        } else {
            holder.hideDetails();
        }
        holder.setType(item.getType());
    }
}
