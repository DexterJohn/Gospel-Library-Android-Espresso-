package org.lds.ldssa.ui.loader;

import android.content.Context;

import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.userdata.tag.Tag;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.mobile.loader.AsyncLoader;

import java.util.List;

import javax.inject.Inject;

/**
 * A Loader that will retrieve a list of tags
 */
public class AnnotationTagsLoader extends AsyncLoader<List<Tag>> {
    @Inject
    TagManager tagManager;

    private long annotationId;

    /**
     * @param context The context to use for dependency injection
     */
    public AnnotationTagsLoader(Context context, long annotationId) {
        super(context);
        this.annotationId = annotationId;
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    public List<Tag> loadInBackground() {
        return tagManager.findAllByAnnotationId(annotationId);
    }
}
