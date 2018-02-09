package org.lds.ldssa.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItem;
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.highlight.Highlight;
import org.lds.ldssa.ui.widget.AnnotationView;
import org.lds.ldssa.util.annotations.HighlightUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RelatedContentAdapter extends RecyclerView.Adapter implements ClickableViewHolder.OnClickListener {

    private final long screenId;

    private List<RelatedContentListItem> relatedContentList;
    private OnItemClickListener listener;
    private HighlightUtil highlightUtil;

    public RelatedContentAdapter(long screenId) {
        this.screenId = screenId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelatedContentListType type = RelatedContentListType.getTypeByDisplayOrder(viewType);
        switch (type) {
            case RELATED_CONTENT:
                return createRelatedContentViewHolder(parent);
            case ANNOTATION:
                return createAnnotationViewHolder(parent);
            default:
                throw new IllegalStateException("Invalid type: " + viewType);
        }
    }

    private RelatedContentItemViewHolder createRelatedContentViewHolder(ViewGroup parent) {
        RelatedContentItemViewHolder holder = RelatedContentItemViewHolder.newInstance(parent);
        holder.setOnClickListener(this);
        return holder;
    }

    private RelatedAnnotationViewHolder createAnnotationViewHolder(ViewGroup parent) {
        RelatedAnnotationViewHolder holder = RelatedAnnotationViewHolder.newInstance(parent);
        holder.setHighlightUtil(highlightUtil);
        holder.setOnClickListener(this);
        holder.setAnnotationClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (relatedContentList.get(position).getType()) {
            case RELATED_CONTENT:
                bindRelatedContentItemView((RelatedContentItemViewHolder)holder, position);
                return;
            case ANNOTATION:
                bindAnnotationView((RelatedAnnotationViewHolder)holder, position);
                return;
            default:
                throw new IllegalStateException("Invalid type: " + getItemViewType(position));
        }
    }

    private void bindRelatedContentItemView(RelatedContentItemViewHolder holder, int position) {
        RelatedContentItem relatedContentItem = relatedContentList.get(position).getRelatedContentItem();
        if (relatedContentItem == null) {
            holder.setReference("");
            holder.setContent("");
        } else {
            holder.setReference(relatedContentItem.getLabelHtml());
            holder.setContent(relatedContentItem.getContentHtml());
        }
    }

    private void bindAnnotationView(RelatedAnnotationViewHolder holder, int position) {
        RelatedContentListItem listItem = relatedContentList.get(position);
        if (listItem.getAnnotation() != null) {
            holder.setAnnotation(listItem.getVerseNumber(), screenId, listItem.getAnnotation());
        }
    }

    @Override
    public int getItemCount() {
        return relatedContentList != null ? relatedContentList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return relatedContentList.get(position).getType().getDisplayOrder();
    }

    @Override
    public void onClick(@NonNull ClickableViewHolder viewHolder) {
        if (listener == null) {
            return;
        }

        int position = viewHolder.getAdapterPosition();
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        RelatedContentListItem relatedContentListItem = relatedContentList.get(position);
        if (relatedContentListItem.getType() == RelatedContentListType.RELATED_CONTENT) {
            RelatedContentItem relatedContentItem = relatedContentListItem.getRelatedContentItem();
            if (relatedContentItem == null) {
                return;
            }
            listener.onRelatedContentItemClicked(relatedContentItem.getRefId());
        } else if (relatedContentListItem.getType() == RelatedContentListType.ANNOTATION) {
            Annotation annotation = relatedContentListItem.getAnnotation();
            if (annotation == null) {
                return;
            }
            listener.onAnnotationClicked(annotation.getId());
        }
    }

    /**
     * </p> creates extra newlines when calling Html.fromHtml(...).  This method removes the extra newlines
     */
    private static CharSequence removeTrailingLines(CharSequence text) {
        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    private static Spannable removeUnderlines(Spannable text) {
        URLSpan[] spans = text.getSpans(0, text.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = text.getSpanStart(span);
            int end = text.getSpanEnd(span);
            text.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            text.setSpan(span, start, end, 0);
        }
        return text;
    }

    public int getItemPositionByStartIndex(long startIndex) {
        for (int i = 0; i < relatedContentList.size(); i++) {
            RelatedContentListItem item = relatedContentList.get(i);
            if (item.getStartIndex() == startIndex) {
                return i;
            }
        }
        return -1;
    }

    public RelatedContentListItem getItem(int position) {
        if (relatedContentList == null || position < 0 || position >= getItemCount()) {
            return null;
        }

        return relatedContentList.get(position);
    }

    /**
     * This method will return the best scroll position based on the given start index.
     * @param startIndex The index used to determine the best scroll position
     * @return either the position of the given start index or the next available start index if the given index is not in the item list.
     */
    public int findBestScrollPositionByStartIndex(int startIndex) {
        if (relatedContentList == null || relatedContentList.isEmpty()) {
            return -1;
        }

        for (RelatedContentListItem item : relatedContentList) {
            if (item.getStartIndex() >= startIndex) {
                return getItemPositionByStartIndex(item.getStartIndex());
            }
        }
        return -1;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setRelatedContentList(List<RelatedContentListItem> relatedContentList) {
        this.relatedContentList = relatedContentList;
        notifyDataSetChanged();
    }

    public void setHighlightUtil(HighlightUtil highlightUtil) {
        this.highlightUtil = highlightUtil;
    }

    @SuppressLint("ParcelCreator")
    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        public void updateDrawState(TextPaint drawState) {
            super.updateDrawState(drawState);
            drawState.setUnderlineText(false);
        }
    }

    public static class RelatedContentItemViewHolder extends ClickableViewHolder {
        @BindView(R.id.referenceTextView)
        TextView referenceTextView;
        @BindView(R.id.contentTextView)
        TextView contentTextView;

        public static RelatedContentItemViewHolder newInstance(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_related_content, parent, false);
            return new RelatedContentItemViewHolder(view);
        }

        public RelatedContentItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setReference(String text) {
            referenceTextView.setText(Html.fromHtml(text));
        }

        public void setContent(String html) {
            Spannable spannedText = Spannable.Factory.getInstance().newSpannable(Html.fromHtml(html));
            Spannable processedText = removeUnderlines(spannedText);

            contentTextView.setText(removeTrailingLines(processedText));
            contentTextView.setLinksClickable(false);
        }
    }

    public static class RelatedAnnotationViewHolder extends ClickableViewHolder {
        @BindView(R.id.referenceTextView)
        TextView referenceTextView;
        @BindView(R.id.annotationView)
        AnnotationView annotationView;
        @BindView(R.id.annotationMenuImageButton)
        ImageView annotationMenuImageButton;

        private HighlightUtil highlightUtil;
        private Context context;
        private OnItemClickListener listener;

        public static RelatedAnnotationViewHolder newInstance(ViewGroup parent) {
            Context context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_related_annotations, parent, false);
            return new RelatedAnnotationViewHolder(view, context);
        }

        public RelatedAnnotationViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

        public void setAnnotation(String referenceText, long screenId, @NonNull final Annotation annotation) {
            annotationView.loadUi(screenId, annotation, false, false);
            annotationView.setMenuButton(annotationMenuImageButton);

            Highlight highlight = annotationView.getHighlight();
            if (StringUtils.isEmpty(referenceText) || highlight.getColor() == null) {
                referenceTextView.setText(referenceText);
            } else {
                String style = highlight.getStyle();
                if (style == null) {
                    style = HighlightAnnotationStyle.FILL.getStringValue();
                }
                referenceTextView.setText(highlightUtil.createHighlightSpannableString(context, referenceText, highlight.getColor(), style));
            }

            annotationView.setNoteTitleClickListener(v -> {
                if (listener != null) {
                    listener.onAnnotationClicked(annotation.getId());
                }
            });

            annotationView.setNoteMarkdownClickListener(v -> {
                if (listener != null) {
                    listener.onAnnotationClicked(annotation.getId());
                }
            });

            annotationView.setLastModifiedClickListener(v -> {
                if (listener != null) {
                    listener.onAnnotationClicked(annotation.getId());
                }
            });
        }

        public void setHighlightUtil(HighlightUtil highlightUtil) {
            this.highlightUtil = highlightUtil;
        }

        public void setAnnotationClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    public static class RelatedContentListItem {
        private RelatedContentItem relatedContentItem;
        private Annotation annotation;
        private String verseNumber;
        private long startIndex;
        private RelatedContentListType type;

        public RelatedContentListItem(Annotation annotation) {
            this.annotation = annotation;
        }

        public RelatedContentListItem(RelatedContentItem relatedContentItem) {
            this.relatedContentItem = relatedContentItem;
        }

        public void setType(RelatedContentListType type) {
            this.type = type;
        }

        public RelatedContentListType getType() {
            return type;
        }

        public RelatedContentItem getRelatedContentItem() {
            return relatedContentItem;
        }

        public Annotation getAnnotation() {
            return annotation;
        }

        public String getVerseNumber() {
            return verseNumber;
        }

        public void setVerseNumber(String verseNumber) {
            this.verseNumber = verseNumber;
        }

        public long getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(long startIndex) {
            this.startIndex = startIndex;
        }
    }

    public interface OnItemClickListener {
        void onRelatedContentItemClicked(String refId);
        void onAnnotationClicked(long annotationId);
    }

    public enum RelatedContentListType {
        ANNOTATION(0),
        RELATED_CONTENT(1);

        private final int displayOrder;

        RelatedContentListType(int displayOrder) {
            this.displayOrder = displayOrder;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }

        public static RelatedContentListType getTypeByDisplayOrder(int displayOrder) {
            for (RelatedContentListType type : values()) {
                if (type.getDisplayOrder() == displayOrder) {
                    return type;
                }
            }
            return RELATED_CONTENT;
        }
    }
}