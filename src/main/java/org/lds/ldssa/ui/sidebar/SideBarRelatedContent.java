package org.lds.ldssa.ui.sidebar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadata;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItem;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.ui.adapter.RelatedContentAdapter;
import org.lds.ldssa.ui.adapter.RelatedContentAdapter.RelatedContentListItem;
import org.lds.ldssa.util.annotations.HighlightUtil;
import org.lds.mobile.ui.widget.EmptyStateIndicator;
import org.lds.mobile.task.RxTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideBarRelatedContent extends SideBarView {
    public static final int SCROLL_Y_POSITION_OFFSET = 1;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyStateIndicator)
    EmptyStateIndicator emptyStateIndicator;

    @Inject
    RelatedContentItemManager relatedContentItemManager;
    @Inject
    AnnotationManager annotationManager;
    @Inject
    SubItemManager subItemManager;
    @Inject
    ParagraphMetadataManager paragraphMetadataManager;
    @Inject
    HighlightUtil highlightUtil;

    private long screenId;
    private long contentItemId;
    private long subItemId;
    private int scrollPosition;

    private LinearLayoutManager layoutManager;
    private RelatedContentAdapter adapter;
    private OnItemClickListener onItemClickListener;
    private OnScrollPositionRequestListener onScrollPositionRequestListener;

    public SideBarRelatedContent(Context context) {
        super(context);
        init(context);
    }

    public SideBarRelatedContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideBarRelatedContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SideBarRelatedContent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void scrollToParagraphAid(String paragraphAid) {
        if (StringUtils.equals(paragraphAid, SCROLL_TO_TOP)) {
            layoutManager.scrollToPositionWithOffset(0, SCROLL_Y_POSITION_OFFSET);
        }

        ParagraphMetadata paragraphMetadata = paragraphMetadataManager.findByParagraphAid(contentItemId, subItemId, paragraphAid);
        if (paragraphMetadata != null) {
            int startIndex = paragraphMetadata.getStartIndex();
            int position = adapter.findBestScrollPositionByStartIndex(startIndex);

            RelatedContentListItem firstVisibleItem = adapter.getItem(layoutManager.findFirstCompletelyVisibleItemPosition());
            RelatedContentListItem bestPositionItem = adapter.getItem(position);

            // Scroll to the top if the position is greater than zero and the first visible item's start index does not match the startIndex of the position to scroll to.
            if (position >= 0 && (firstVisibleItem == null || bestPositionItem == null || firstVisibleItem.getStartIndex() != bestPositionItem.getStartIndex())) {
                layoutManager.scrollToPositionWithOffset(position, SCROLL_Y_POSITION_OFFSET);
            }
        }
    }

    private void init(@Nonnull Context context) {
        Injector.INSTANCE.get().inject(this);
        View view = LayoutInflater.from(context).inflate(R.layout.side_bar_related_content, this, true);
        ButterKnife.bind(this, view);
    }

    public void loadUi(long screenId, long contentItemId, long subItemId) {
        loadUi(screenId, contentItemId, subItemId, 0);
    }

    public void loadUi(long screenId, long contentItemId, long subItemId, int scrollPosition) {
        this.screenId = screenId;
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.scrollPosition = scrollPosition;

        setTitle(R.string.related_content);

        setupRecyclerView();
        new LoadRelatedContentTask().execute();
    }

    private void setupRecyclerView() {
        adapter = new RelatedContentAdapter(screenId);
        adapter.setListener(new RelatedContentAdapter.OnItemClickListener() {
            @Override
            public void onRelatedContentItemClicked(String refId) {
                SideBarRelatedContent.this.onRelatedContentItemClicked(refId);
            }

            @Override
            public void onAnnotationClicked(long annotationId) {
                SideBarRelatedContent.this.onAnnotationClicked(annotationId);
            }
        });
        adapter.setHighlightUtil(highlightUtil);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void onRelatedContentItemClicked(String refId) {
        if (onItemClickListener != null) {
            onItemClickListener.onRelatedContentItemClicked(contentItemId, subItemId, refId);
        }
    }

    private void onAnnotationClicked(long annotationId) {
        if (onItemClickListener != null) {
            onItemClickListener.onAnnotationClicked(annotationId);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnScrollPositionRequestListener(OnScrollPositionRequestListener onScrollPositionRequestListener) {
        this.onScrollPositionRequestListener = onScrollPositionRequestListener;
    }

    private void requestCurrentScrollPosition() {
        if (onScrollPositionRequestListener != null) {
            onScrollPositionRequestListener.onCurrentScrollPositionRequested();
        }
    }

    public int getScrollPosition() {
        return layoutManager.findFirstCompletelyVisibleItemPosition();
    }

    public class LoadRelatedContentTask extends RxTask<List<RelatedContentListItem>> {

        @NonNull
        @Override
        protected List<RelatedContentListItem> run() {
            List<RelatedContentListItem> fullList = new ArrayList<>();
            String docId = subItemManager.findDocIdById(contentItemId, subItemId);

            if (docId != null) {
                // find list of annotations
                List<Annotation> relatedAnnotationList = annotationManager.findRelatedAnnotationsFullActiveByDocId(docId);
                for (Annotation annotation : relatedAnnotationList) {
                    String firstHighlightParagraphAid = annotation.getFirstHighlightParagraphAid();
                    if (firstHighlightParagraphAid == null) {
                        continue;
                    }
                    RelatedContentListItem listItem = new RelatedContentListItem(annotation);
                    ParagraphMetadata paragraphMetadata = paragraphMetadataManager.findByParagraphAid(contentItemId, subItemId, firstHighlightParagraphAid);
                    if (paragraphMetadata != null) {
                        listItem.setVerseNumber(paragraphMetadata.getVerseNumber());
                        listItem.setStartIndex(paragraphMetadata.getStartIndex());
                    }
                    listItem.setType(RelatedContentAdapter.RelatedContentListType.ANNOTATION);
                    fullList.add(listItem);
                }
            }

            // find list of related content items
            List<RelatedContentItem> relatedContentItemList = relatedContentItemManager.findAllBySubItemId(contentItemId, subItemId);
            for (RelatedContentItem relatedContentItem : relatedContentItemList) {
                RelatedContentListItem listItem = new RelatedContentListItem(relatedContentItem);
                listItem.setStartIndex(paragraphMetadataManager.findStartIndexByParagraphId(contentItemId, subItemId, relatedContentItem.getOriginId()));
                listItem.setType(RelatedContentAdapter.RelatedContentListType.RELATED_CONTENT);
                fullList.add(listItem);
            }

            // sort by start Index
            Collections.sort(fullList, (item2, item1) -> {
                int result = longCompareCompat(item2.getStartIndex(), item1.getStartIndex());
                if (result == 0) {
                    result = item2.getType().getDisplayOrder() < item1.getType().getDisplayOrder() ? -1 : 0;
                }
                return result;
            });

            return fullList;
        }

        @Override
        protected void onResult(List<RelatedContentListItem> result) {
            adapter.setRelatedContentList(result);
            emptyStateIndicator.setVisibility(result.isEmpty() ? View.VISIBLE : View.GONE);
            if (scrollPosition > 0) {
                layoutManager.scrollToPositionWithOffset(scrollPosition, SCROLL_Y_POSITION_OFFSET);
            } else {
                requestCurrentScrollPosition();
            }
        }
    }

    private int longCompareCompat(long x, long y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Long.compare(x, y);
        } else {
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        }
    }

    public interface OnItemClickListener {
        void onRelatedContentItemClicked(long contentItemId, long subItemId, String refId);
        void onAnnotationClicked(long annotationId);
    }

    public interface OnScrollPositionRequestListener {
        void onCurrentScrollPositionRequested();
    }
}


