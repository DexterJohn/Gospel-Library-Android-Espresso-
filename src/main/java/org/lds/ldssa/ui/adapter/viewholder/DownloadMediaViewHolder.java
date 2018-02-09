package org.lds.ldssa.ui.adapter.viewholder;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.lds.ldssa.R;
import org.lds.ldssa.model.database.types.ItemMediaType;
import org.lds.mobile.ui.util.LdsDrawableUtil;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadMediaViewHolder extends ClickableViewHolder {
    @BindView(R.id.titleView)
    TextView titleView;
    @BindView(R.id.detailView)
    TextView detailView;
    @BindView(R.id.typeIcon)
    ImageView typeIcon;

    public static DownloadMediaViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_download_media, parent, false);
        return new DownloadMediaViewHolder(view);
    }

    public DownloadMediaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPosition(int position) {
        itemView.setTag(position);
    }

    public void reset() {
        typeIcon.setVisibility(View.VISIBLE);
        titleView.setText("");
        detailView.setText("");
    }

    public void setTitle(@Nullable String title) {
        if (title == null) {
            return;
        }

        titleView.setText(Html.fromHtml(title));
    }

    public void setDetails(String details) {
        detailView.setText(Html.fromHtml(details));
    }

    public void setType(ItemMediaType type) {
        Drawable drawable = LdsDrawableUtil.INSTANCE.tintDrawable(itemView.getContext(), type.getDrawableResId(), R.color.secondary_text_default_material_light);
        typeIcon.setImageDrawable(drawable);
    }

    public void hideDetails() {
        detailView.setVisibility(View.GONE);
    }
}