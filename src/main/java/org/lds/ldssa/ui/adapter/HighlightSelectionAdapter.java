package org.lds.ldssa.ui.adapter;

import android.support.annotation.DrawableRes;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.RecyclerListAdapter;
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.lds.ldssa.R;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.webview.content.dto.DtoHighlightInfo;
import org.lds.ldssa.util.HighlightColor;
import org.lds.ldssa.util.annotations.HighlightUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighlightSelectionAdapter extends RecyclerListAdapter<HighlightSelectionAdapter.HighlightSelectionViewHolder, DtoHighlightInfo> {
    private HighlightUtil highlightUtil;
    private List<DtoHighlightInfo> highlights;
    private AnnotationManager annotationManager;
    private OnAnnotationClickListener listener;

    public HighlightSelectionAdapter(HighlightUtil highlightUtil, List<DtoHighlightInfo> items, AnnotationManager annotationManager) {
        this.highlightUtil = highlightUtil;
        highlights = items;
        this.annotationManager = annotationManager;
    }

    @Override
    public HighlightSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HighlightSelectionViewHolder holder = HighlightSelectionViewHolder.newInstance(parent);
        holder.setOnClickListener(viewHolder -> {
            if (listener != null) {
                DtoHighlightInfo highlightInfo = getHighlightInfo(viewHolder.getAdapterPosition());
                if (highlightInfo != null) {
                    listener.onAnnotationClick(highlightInfo);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(HighlightSelectionViewHolder holder, int position) {
        DtoHighlightInfo item = getHighlightInfo(position);

        if (Annotation.isInverseLinkAnnotation(item.getUniqueId())) {
            holder.setText(highlightUtil.createHighlightSpannableString(holder.itemView.getContext(), item.getText(), HighlightColor.CLEAR.getHtmlName(), item.getStyle()));
            holder.setStickyIcon(R.drawable.ic_lds_link_24dp);
            return;
        }

        holder.setText(highlightUtil.createHighlightSpannableString(holder.itemView.getContext(), item.getText(), item.getColor(), item.getStyle()));

        int iconRes;
        Annotation annotation = annotationManager.findByUniqueId(item.getUniqueId());
        if (annotation != null) {
            annotationManager.findFullAnnotationData(annotation);
            switch (annotation.determineDisplayType()) {
                case NOTE:
                    iconRes = R.drawable.ic_lds_note_24dp;
                    break;
                case NOTEBOOK:
                    iconRes = R.drawable.ic_lds_notebook_24dp;
                    break;
                case TAG:
                    iconRes = R.drawable.ic_lds_tag_24dp;
                    break;
                case LINK:
                    iconRes = R.drawable.ic_lds_link_24dp;
                    break;
                default:
                    iconRes = R.drawable.ic_lds_annotation_mark_24dp;
            }

            holder.setStickyIcon(iconRes);
        }
    }

    @Override
    public int getItemCount() {
        return highlights != null ? highlights.size() : 0;
    }

    @Override
    public void onViewRecycled(HighlightSelectionViewHolder holder) {
        holder.setOnClickListener(null);
    }

    public void setOnAnnotationClickListener(OnAnnotationClickListener listener) {
        this.listener = listener;
    }

    public DtoHighlightInfo getHighlightInfo(int position) {
        if (position < 0 || position > getItemCount()) {
            return null;
        }

        return highlights.get(position);
    }

    public interface OnAnnotationClickListener {
        void onAnnotationClick(DtoHighlightInfo item);
    }

    public static class HighlightSelectionViewHolder extends ClickableViewHolder {
        @BindView(R.id.listItemTextView)
        TextView textView;
        @BindView(R.id.stickyIcon)
        ImageView stickyIcon;

        public static HighlightSelectionViewHolder newInstance(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_highlight_selection, parent, false);
            return new HighlightSelectionViewHolder(view);
        }

        public HighlightSelectionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setText(SpannableString text) {
            textView.setText(text);
        }

        public void setStickyIcon(@DrawableRes int iconRes) {
            stickyIcon.setImageResource(iconRes);
        }
    }
}
