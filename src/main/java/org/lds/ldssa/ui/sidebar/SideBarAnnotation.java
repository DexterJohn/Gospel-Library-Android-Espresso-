package org.lds.ldssa.ui.sidebar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.types.AnnotationDisplayType;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.ui.widget.AnnotationView;
import org.lds.ldssa.util.annotations.LinkUtil;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideBarAnnotation extends SideBarView {
    @BindView(R.id.annotationView)
    AnnotationView annotationView;

    @Inject
    InternalIntents internalIntents;
    @Inject
    AnnotationManager annotationManager;
    @Inject
    LinkUtil linkUtil;

    private long screenId;
    @Nullable
    private Annotation annotation;

    public SideBarAnnotation(Context context) {
        super(context);
        init(context);
    }

    public SideBarAnnotation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideBarAnnotation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SideBarAnnotation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@Nonnull Context context) {
        Injector.INSTANCE.get().inject(this);
        View view = LayoutInflater.from(context).inflate(R.layout.side_bar_annotation, this, true);
        ButterKnife.bind(this, view);

        annotationView.setInSidebar(true);
    }

    @Override
    public void onCreateOptionsMenu(Toolbar toolbar) {
        if (annotation != null && Annotation.isInverseLinkAnnotation(annotation.getId())) {
            return;
        }

        toolbar.inflateMenu(R.menu.menu_side_bar_related_content);
        toolbar.inflateMenu(annotationView.getMenuResourceId());
        super.onCreateOptionsMenu(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // override from annotationView.onOptionsItemSelected(menuItem)
            case R.id.menu_popup_edit:
                if (annotation == null) {
                    return false;
                }
                internalIntents.editNote((Activity) getContext(), screenId, annotation.getId());
                return true;
            case R.id.menu_item_related_content:
                SideBar sideBar = getSideBar();
                if (sideBar == null) {
                    return false;
                }
                sideBar.showRelatedContent();
                return true;
            default:
                return annotationView.onOptionsItemSelected(menuItem) || super.onOptionsItemSelected(menuItem);
        }
    }

    public void loadUi(long screenId, long contentItemId, long subItemId, long annotationId) {
        // Determine if this is an inverse annotation
        boolean inverseLinkAnnotation = Annotation.isInverseLinkAnnotation(annotationId);

        // Get the valid annotation id (if this is an inverse annotation we need to get the real annotation id)
        long validAnnotationId = annotationId;
        if (inverseLinkAnnotation) {
            validAnnotationId = Annotation.getInverseAnnotationId(annotationId);
        }

        // Load the annotation (the real annotation if this is an inverse annotation)
        Annotation annotation = annotationManager.findFullAnnotationByRowId(validAnnotationId);
        if (annotation == null) {
            closeSideBar();
            return;
        }

        // Load the inverse annotation if needed
        if (inverseLinkAnnotation) {
            annotation = linkUtil.findInverseLinkAnnotation(annotation, contentItemId, subItemId);
            if (annotation == null) {
                switchToCurrentRelatedContent();
                closeSideBar();
                return;
            }
        }

        AnnotationDisplayType annotationDisplayType = annotation.determineDisplayType();

        // if there is nothing to display for this annotation... just close the drawer and show the default view
        if (annotationDisplayType == AnnotationDisplayType.OTHER) {
            switchToCurrentRelatedContent();
            closeSideBar();
        }

        // Title
        setTitle(annotationDisplayType.getTitleRes());

        this.screenId = screenId;
        this.annotation = annotation;

        annotationView.loadUi(screenId, annotation, false, true);
    }
}
