package org.lds.ldssa.ui.adapter.viewholder;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.glide.GlideRequests;
import org.lds.ldssa.inject.Injector;
import org.lds.mobile.glide.ImageRenditions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentDirectoryViewHolder extends ClickableViewHolder {
    private static final float SUPERSCRIPT_RELATIVE_SIZE = 0.66f;

    @BindView(R.id.content_item_title)
    TextView titleTextView;

    //Only in the list items
    @Nullable
    @BindView(R.id.content_item_sub_title)
    TextView listSubTitleTextView;
    @Nullable
    @BindView(R.id.content_item_indentation_spacing)
    View listIndentationView;

    //Only in the grid items
    @Nullable
    @BindView(R.id.content_item_chapter)
    TextView gridChapterTextView;
    @Nullable
    @BindView(R.id.content_item_preview)
    TextView gridPreviewTextView;
    @Nullable
    @BindView(R.id.content_item_image)
    ImageView contentImage;

    private final int singleIndentationSize;

    public static ContentDirectoryViewHolder newInstance(ViewGroup parent, boolean useListLayout) {
        int layoutRes = useListLayout ? R.layout.list_item_content : R.layout.grid_item_content;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ContentDirectoryViewHolder(view);
    }

    public ContentDirectoryViewHolder(View view) {
        super(view);
        Injector.INSTANCE.get().inject(this);
        ButterKnife.bind(this, view);

        singleIndentationSize = view.getResources().getDimensionPixelSize(R.dimen.content_list_item_single_indent);
    }

    /**
     * Sets the level of indentation (such as a hierarchy level) for the item.
     * This is used when a story (such as in Alma) enters a tangential story.
     *
     * @param level The indentation level
     */
    public void setIndentationLevel(int level) {
        if (listIndentationView == null) {
            return;
        }

        if (level < 0) {
            level = 0;
        }

        listIndentationView.getLayoutParams().width = level * singleIndentationSize;
    }

    public void setTitleText(String text) {
        titleTextView.setVisibility(View.VISIBLE);

        if (gridChapterTextView != null) {
            gridChapterTextView.setVisibility(View.INVISIBLE);
        }

        Spanned spanned = Html.fromHtml(text);
        SpannableString spannableString = new SpannableString(spanned);
        for (Object span : spanned.getSpans(0, spanned.length(), Object.class)) {
            if (span instanceof SuperscriptSpan) {
                spannableString.setSpan(new RelativeSizeSpan(SUPERSCRIPT_RELATIVE_SIZE), spanned.getSpanStart(span), spanned.getSpanEnd(span),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        titleTextView.setText(spannableString);
    }

    public void setSubTitleText(@Nullable String text) {
        if (listSubTitleTextView != null) {
            listSubTitleTextView.setVisibility(!StringUtils.isBlank(text) ? View.VISIBLE : View.GONE);
            listSubTitleTextView.setText(!StringUtils.isBlank(text) ? Html.fromHtml(text).toString() : null);
        }
    }

    public void setChapterText(int chapterNum) {
        titleTextView.setVisibility(View.INVISIBLE);

        if (gridChapterTextView != null) {
            gridChapterTextView.setVisibility(View.VISIBLE);
            gridChapterTextView.setText(String.valueOf(chapterNum));
        }
    }

    public void setPreviewText(String text) {
        if (gridPreviewTextView != null) {
            gridPreviewTextView.setText(text);
        }
    }

    public void setImage(GlideRequests glide, String imageRenditions) {
        if (contentImage == null) {
            return;
        }

        if (StringUtils.isBlank(imageRenditions)) {
            setImageVisibility(View.GONE);
            return;
        }

        glide.load(new ImageRenditions(imageRenditions))
                .placeholder(R.drawable.cover_default_grid)
                .fallback(R.drawable.cover_default_grid)
                .error(R.drawable.cover_default_grid)
                .into(contentImage);
    }

    private void setImageVisibility(int visibility) {
        if (contentImage != null) {
            contentImage.setVisibility(visibility);
        }
    }
}
