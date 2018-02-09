package org.lds.ldssa.ui.web;

import org.lds.ldssa.model.database.userdata.annotation.Annotation;

import java.util.List;

public class ContentRelatedItems {
    private List<Annotation> annotations;

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }
}
