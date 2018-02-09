package org.lds.ldssa.ui.sidebar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.sidebarhistoryitem.SidebarHistoryItem;
import org.lds.ldssa.model.database.gl.sidebarhistoryitem.SidebarHistoryItemManager;
import org.lds.ldssa.model.database.types.SideBarSourceType;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;
import org.lds.ldssa.model.navigation.sidebar.SideBarHistoryExtrasAnnotation;
import org.lds.ldssa.model.navigation.sidebar.SideBarHistoryExtrasRelatedContent;
import org.lds.ldssa.model.navigation.sidebar.SideBarHistoryExtrasRelatedContentItem;
import org.lds.ldssa.model.navigation.sidebar.SideBarHistoryExtrasUri;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.mobile.ui.util.LdsDrawableUtil;
import org.lds.mobile.ui.widget.EmptyStateIndicator;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SideBar extends FrameLayout {
    public static final double ALLOWED_SIDEBAR_PERCENTAGE = .33;
    @BindView(R.id.contentDrawerToolbar)
    Toolbar toolbar;
    @BindView(R.id.contentDrawerToolbarTitleTextView)
    TextView toolbarTitleTextView;
    @BindView(R.id.emptyStateIndicator)
    EmptyStateIndicator emptyStateIndicator;
    @BindView(R.id.sideBarContainer)
    FrameLayout sideBarContainer;

    @Inject
    InternalIntents internalIntents;
    @Inject
    SidebarHistoryItemManager sidebarHistoryItemManager;
    @Inject
    AnnotationManager annotationManager;
    @Inject
    Prefs prefs;
    @Inject
    Analytics analytics;
    @Inject
    ContentItemUtil contentItemUtil;
    @Inject
    Gson gson;

    @Nonnull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private long currentContentTabId;
    private long currentContentContentItemId;
    private long currentContentSubItemId;

    private boolean pinned;
    private boolean canShowPinned;

    private ContentDrawerListener contentDrawerListener;
    private ScrollPositionRequestListener scrollPositionRequestListener;
    private SideBarView currentSideBarView;

    public SideBar(Context context) {
        super(context);
        init(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SideBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(@Nonnull Context context, @javax.annotation.Nullable AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        Injector.INSTANCE.get().inject(this);
        View view = LayoutInflater.from(context).inflate(R.layout.side_bar, this, true);
        ButterKnife.bind(this, view);
        setupToolbar();

        canShowPinned = determineAbilityToPin(context);
        setPinned(prefs.isSidebarPinned());
    }

    public void setTitle(CharSequence title) {
        toolbarTitleTextView.setText(title);
    }

    public void setTitle(@StringRes int titleRes) {
        toolbarTitleTextView.setText(titleRes);
    }

    private void setupToolbar() {
        //Menu
        toolbar.setOnMenuItemClickListener(menuItem -> onOptionsItemSelected(menuItem));

        setToolbarToCloseNavigation();
    }

    private void updateToolbarNavigation() {
        if (sidebarHistoryItemManager.findCount() > 1) {
            setToolbarToBackNavigation();
        } else {
            setToolbarToCloseNavigation();
        }
    }

    public void setToolbarToCloseNavigation() {
        //Navigation Icon
        toolbar.setNavigationIcon(LdsDrawableUtil.INSTANCE.tintDrawableForTheme(getContext(), R.drawable.ic_lds_action_clear_24dp, R.attr.themeStyleColorToolbarIcon));

        // Click handler
        toolbar.setNavigationOnClickListener(view -> closeSidebar());
    }

    public void setToolbarToBackNavigation() {
        //Navigation Icon
        toolbar.setNavigationIcon(LdsDrawableUtil.INSTANCE.tintDrawableForTheme(getContext(), R.drawable.ic_lds_arrow_back_24dp, R.attr.themeStyleColorToolbarIcon));

        // Click handler
        toolbar.setNavigationOnClickListener(view -> onBack());
    }

    public void onContentChanged(long screenId, long contentItemId, long subItemId) {
        boolean contentChanged = currentContentContentItemId != contentItemId || currentContentSubItemId != subItemId;

        this.currentContentTabId = screenId;
        this.currentContentContentItemId = contentItemId;
        this.currentContentSubItemId = subItemId;

        SideBarSourceType currentHistoryItemType = sidebarHistoryItemManager.findCurrentHistoryItemType();
        if (currentHistoryItemType == SideBarSourceType.RELATED_CONTENT) {
            if (contentChanged) {
                showRelatedContent(contentItemId, subItemId, false);
            }
        } else if (currentHistoryItemType == SideBarSourceType.UNKNOWN) {
            showRelatedContent(contentItemId, subItemId, false);
        } else {
            switchToTopHistoryItem();
        }
    }

    public void showRelatedContent() {
        showRelatedContent(currentContentContentItemId, currentContentSubItemId);
    }

    public void showRelatedContent(long contentItemId, long subItemId) {
        showRelatedContent(contentItemId, subItemId, true);
    }

    private void showRelatedContent(long contentItemId, long subItemId, boolean openSidebar) {
        // currently... we should always clear history on related content clicks
        clearHistory();

        switchToRelatedContent(contentItemId, subItemId);

        // save side bar history
        saveSideBarItemHistory(SideBarSourceType.RELATED_CONTENT, new SideBarHistoryExtrasRelatedContent(contentItemId, subItemId, 0));

        // after save of history... update toolbar navigation
        updateToolbarNavigation();

        if (openSidebar) {
            openSidebar();
        }
    }

    private void updateSidebarScrollPosition() {
        if (!(currentSideBarView instanceof SideBarRelatedContent)) {
            // No need to update scroll position if the current view is not related content
            return;
        }

        int scrollPosition = ((SideBarRelatedContent)currentSideBarView).getScrollPosition();
        if (scrollPosition <= 0) {
            return;
        }

        SidebarHistoryItem currentHistoryItem = sidebarHistoryItemManager.findCurrentHistoryItem();
        if (currentHistoryItem == null) {
            return;
        }

        SideBarHistoryExtrasRelatedContent extras = new SideBarHistoryExtrasRelatedContent(gson, currentHistoryItem.getExtrasJson());
        extras.setScrollPosition(scrollPosition);
        currentHistoryItem.setExtrasJson(extras.serialize(gson));
        sidebarHistoryItemManager.save(currentHistoryItem);
    }

    protected void switchToCurrentRelatedContent() {
        showRelatedContent(currentContentContentItemId, currentContentSubItemId, false);
    }

    private void switchToRelatedContent(final long contentItemId, final long subItemId) {
        switchToRelatedContent(contentItemId, subItemId, 0);
    }

    private void switchToRelatedContent(final long contentItemId, final long subItemId, final int scrollPosition) {
        toolbar.getMenu().clear();

        // content
        final SideBarRelatedContent sideBarRelatedContent = new SideBarRelatedContent(getContext());
        replaceView(sideBarRelatedContent);
        sideBarRelatedContent.setOnItemClickListener(new SideBarRelatedContent.OnItemClickListener() {
            @Override
            public void onRelatedContentItemClicked(long contentItemId, long subItemId, String refId) {
                showRelatedContentItem(contentItemId, subItemId, refId, "", false);
            }

            @Override
            public void onAnnotationClicked(long annotationId) {
                showAnnotation(annotationId, false);
            }
        });
        sideBarRelatedContent.setOnScrollPositionRequestListener(() -> requestCurrentScrollPositionAid());
        sideBarRelatedContent.loadUi(currentContentTabId, contentItemId, subItemId, scrollPosition);

        addDisposable(annotationManager.tableChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tableChange -> sideBarRelatedContent.loadUi(currentContentTabId, contentItemId, subItemId)));

        // menu
        updateMenu();
    }

    public void showRelatedContentItem(long contentItemId, long subItemId, String refId, String title) {
        showRelatedContentItem(contentItemId, subItemId, refId, title, true);
    }

    private void showRelatedContentItem(long contentItemId, long subItemId, String refId, String title, boolean clearHistory) {
        if (clearHistory) {
            clearHistory();
        } else {
            updateSidebarScrollPosition();
        }

        switchToRelatedContentItem(contentItemId, subItemId, refId, title);

        // save side bar history
        saveSideBarItemHistory(SideBarSourceType.RELATED_CONTENT_ITEM, new SideBarHistoryExtrasRelatedContentItem(contentItemId, subItemId, title, refId));

        // after save of history... update toolbar navigation
        updateToolbarNavigation();

        openSidebar();
    }

    private void switchToRelatedContentItem(long contentItemId, long subItemId, String refId, String title) {
        toolbar.getMenu().clear();

        // content
        SideBarRelatedContentItem sideBarRelatedContentItem = new SideBarRelatedContentItem(getContext());
        replaceView(sideBarRelatedContentItem);
        sideBarRelatedContentItem.loadUi(currentContentTabId, contentItemId, subItemId, refId, title);

        // menu
        updateMenu();
    }

    public void showUri(@Nonnull String title, @Nonnull String uri) {
        // currently... we should always clear history on uri clicks
        clearHistory();

        switchToUri(title, uri);

        // save side bar history
        saveSideBarItemHistory(SideBarSourceType.URI, new SideBarHistoryExtrasUri(title, uri));

        // after save of history... update toolbar navigation
        updateToolbarNavigation();

        openSidebar();
    }

    private void switchToUri(@Nonnull String title, @Nonnull String uri) {
        toolbar.getMenu().clear();

        // content
        SideBarUri sideBarAllRelatedContent = new SideBarUri(getContext());
        replaceView(sideBarAllRelatedContent);
        sideBarAllRelatedContent.loadUi(currentContentTabId, title, uri);

        // menu
        updateMenu();
    }

    public void showAnnotation(long annotationId, boolean clearHistory) {
        if (clearHistory) {
            clearHistory();
        } else {
            updateSidebarScrollPosition();
        }

        switchToAnnotation(annotationId);

        // save side bar history
        // don't save inverse annotations in history
        if (!Annotation.isInverseLinkAnnotation(annotationId)) {
            saveSideBarItemHistory(SideBarSourceType.ANNOTATION, new SideBarHistoryExtrasAnnotation(annotationId));
        }

        // after save of history... update toolbar navigation
        updateToolbarNavigation();

        openSidebar();
    }

    private void switchToAnnotation(final long annotationId) {
        // Get the valid annotation id (if this is an inverse annotation we need to get the real annotation id)
        long validAnnotationId = annotationId;
        if (Annotation.isInverseLinkAnnotation(annotationId)) {
            validAnnotationId = Annotation.getInverseAnnotationId(annotationId);
        }

        // Use the valid annotation id to make sure the annotation exists, but use the passed in annotation id when loading below
        if (!annotationManager.findAnnotationExists(validAnnotationId)) {
            clearHistory();
            switchToCurrentRelatedContent();
            return;
        }

        // content
        final SideBarAnnotation sideBarAllRelatedContent = new SideBarAnnotation(getContext());
        replaceView(sideBarAllRelatedContent);
        sideBarAllRelatedContent.loadUi(currentContentTabId, currentContentContentItemId, currentContentSubItemId, annotationId);

        addDisposable(annotationManager.tableChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tableChange -> {
                    if (tableChange.hasChangeForRowId(annotationId)) {
                        sideBarAllRelatedContent.loadUi(currentContentTabId, currentContentContentItemId, currentContentSubItemId, annotationId);
                    }
                }));

        // menu
        updateMenu();
    }

    private void updateMenu() {
        toolbar.getMenu().clear();

        if (currentSideBarView != null) {
            currentSideBarView.onCreateOptionsMenu(toolbar);
        }

        toolbar.inflateMenu(R.menu.menu_pin);
        updatePinMenuOption();

        LdsDrawableUtil.INSTANCE.tintAllMenuIconsForTheme(getContext(), toolbar.getMenu(), R.attr.themeStyleColorToolbarIcon);
    }

    private boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_item_pin_drawer) {
            togglePinned();
            return true;
        }

        return currentSideBarView != null && currentSideBarView.onOptionsItemSelected(menuItem);
    }

    private void togglePinned() {
        // If the sidebar is pinned but not visible, then inform the listener the user wants to pin the drawer again (No change to pinned status)
        if (pinned && !prefs.isSidebarOpened()) {
            contentDrawerListener.onSidebarPinned();
            updatePinMenuOption();
            return;
        }

        setPinned(!pinned);
        if (pinned) {
            contentDrawerListener.onSidebarPinned();
        } else {
            contentDrawerListener.onSidebarUnpinned(true);
        }

        updatePinMenuOption();

        // Log analytics
        Map<String, String> attributes = new HashMap<>();
        attributes.put(Analytics.Attribute.SIDEBAR_PIN_STATUS, pinned ? Analytics.Value.PINNED : Analytics.Value.UNPINNED);
        analytics.postEvent(Analytics.Event.SIDEBAR_PIN_CHANGED, attributes);
    }

    public void updatePinMenuOption() {
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.menu_item_pin_drawer);
        if (menuItem == null) {
            return;
        }

        if (pinned && prefs.isSidebarOpened()) {
            menuItem.setTitle(R.string.unpin);
        } else {
            menuItem.setTitle(R.string.pin);
        }

        menuItem.setVisible(canShowPinned);
    }

    private void replaceView(SideBarView newView) {
        sideBarContainer.removeAllViews();
        newView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        sideBarContainer.addView(newView);

        emptyStateIndicator.setVisibility(View.GONE);

        currentSideBarView = newView;
        currentSideBarView.setSideBar(this);
    }

    public void clearHistory() {
        sidebarHistoryItemManager.deleteAll();
    }

    public void saveSideBarItemHistory(SideBarSourceType type, @Nullable NavigationHistoryItemExtras extras) {
        SidebarHistoryItem sideBarHistoryItem = new SidebarHistoryItem();
        sideBarHistoryItem.setSourceType(type);
        sideBarHistoryItem.setHistoryPosition(sidebarHistoryItemManager.findNextHistoryPosition());
        if (extras != null) {
            sideBarHistoryItem.setExtrasJson(extras.serialize(gson));
        }

        sidebarHistoryItemManager.save(sideBarHistoryItem);
    }

    protected void openSidebar() {
        if (contentDrawerListener != null) {
            contentDrawerListener.onShowContentDrawerRequested();
        }
    }

    protected void closeSidebar() {
        if (contentDrawerListener != null) {
            contentDrawerListener.onCloseContentDrawerRequested();
        }
    }

    public void requestCurrentScrollPositionAid() {
        if (scrollPositionRequestListener != null) {
            scrollPositionRequestListener.onCurrentScrollPositionAidRequested();
        }
    }

    private void onBack() {
        if (sidebarHistoryItemManager.findCount() <= 1) {
            clearHistory();
            closeSidebar();
            return;
        }

        // pop the top item
        sidebarHistoryItemManager.deleteTopItem();

        // move to the next top item
        switchToTopHistoryItem();

        // after save of history... update toolbar navigation
        updateToolbarNavigation();
    }

    private void switchToTopHistoryItem() {
        SidebarHistoryItem currentHistoryItem = sidebarHistoryItemManager.findCurrentHistoryItem();

        if (currentHistoryItem == null) {
            return;
        }

        switch (currentHistoryItem.getSourceType()) {
            case RELATED_CONTENT:
                SideBarHistoryExtrasRelatedContent relatedContentExtras = new SideBarHistoryExtrasRelatedContent(gson, currentHistoryItem.getExtrasJson());
                if (!verifyContentInstalled(relatedContentExtras.getContentItemId())) {
                    break;
                }

                // if the user changed chapters... make sure we only show the related content for the chapter they are currently looking at
                if (relatedContentExtras.getContentItemId() == currentContentContentItemId && relatedContentExtras.getSubItemId() == currentContentSubItemId) {
                    switchToRelatedContent(relatedContentExtras.getContentItemId(), relatedContentExtras.getSubItemId(), relatedContentExtras.getScrollPosition());
                } else {
                    switchToRelatedContent(currentContentContentItemId, currentContentSubItemId);
                }
                break;
            case RELATED_CONTENT_ITEM:
                SideBarHistoryExtrasRelatedContentItem relatedContentItemExtras = new SideBarHistoryExtrasRelatedContentItem(gson, currentHistoryItem.getExtrasJson());
                if (verifyContentInstalled(relatedContentItemExtras.getContentItemId())) {
                    switchToRelatedContentItem(relatedContentItemExtras.getContentItemId(), relatedContentItemExtras.getSubItemId(), relatedContentItemExtras.getRefId(), relatedContentItemExtras.getTitle());
                }
                break;
            case ANNOTATION:
                SideBarHistoryExtrasAnnotation annotationExtras = new SideBarHistoryExtrasAnnotation(gson, currentHistoryItem.getExtrasJson());
                switchToAnnotation(annotationExtras.getAnnotationId());
                break;
            case URI:
                SideBarHistoryExtrasUri uriExtras = new SideBarHistoryExtrasUri(gson, currentHistoryItem.getExtrasJson());
                switchToUri(uriExtras.getTitle(), uriExtras.getUri());
                break;
            default:
                // do nothing
        }
    }

    private boolean verifyContentInstalled(long contentItemId) {
        if (!contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            clearHistory();
            switchToRelatedContent(currentContentContentItemId, currentContentSubItemId);
            return false;
        }
        return true;
    }

    public void setContentDrawerListener(@Nullable ContentDrawerListener contentDrawerListener) {
        this.contentDrawerListener = contentDrawerListener;
    }

    public void setScrollPositionRequestListener(@Nullable ScrollPositionRequestListener scrollPositionRequestListener) {
        this.scrollPositionRequestListener = scrollPositionRequestListener;
    }

    public void updateAnnotation(long annotationId) {
        if (currentSideBarView instanceof SideBarAnnotation) {
            ((SideBarAnnotation) currentSideBarView).loadUi(currentContentTabId, currentContentContentItemId, currentContentSubItemId, annotationId);
        } else if (currentSideBarView instanceof SideBarRelatedContent) {
            ((SideBarRelatedContent) currentSideBarView).loadUi(currentContentTabId, currentContentContentItemId, currentContentSubItemId);
        }
    }

    private boolean determineAbilityToPin(@Nonnull Context context) {
        Resources resources = context.getResources();
        boolean isLandscape = resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        boolean withinAllowablePinToleranceWidth = resources.getDisplayMetrics().widthPixels * ALLOWED_SIDEBAR_PERCENTAGE > resources.getDimension(R.dimen.sidebar_min_width);
        return isLandscape || withinAllowablePinToleranceWidth;
    }

    public void scrollToParagraphAid(String paragraphAid) {
        currentSideBarView.scrollToParagraphAid(paragraphAid);
    }

    public boolean canShowPinned() {
        return canShowPinned;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
        prefs.setSidebarPinned(pinned);
    }

    public void addDisposable(@Nonnull Disposable disposable) {
        if (compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDetachedFromWindow() {
        compositeDisposable.dispose();
        super.onDetachedFromWindow();
    }

    public interface ContentDrawerListener {
        void onShowContentDrawerRequested();
        void onCloseContentDrawerRequested();
        void onSidebarPinned();
        void onSidebarUnpinned(boolean requestUnpinnedUi);
    }

    public interface ScrollPositionRequestListener {
        void onCurrentScrollPositionAidRequested();
    }
}
