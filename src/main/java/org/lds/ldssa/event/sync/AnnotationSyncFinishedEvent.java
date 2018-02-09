package org.lds.ldssa.event.sync;

public class AnnotationSyncFinishedEvent {

    private boolean success;
    private boolean containsAnnotationAndNotebookChanges = false;

    public AnnotationSyncFinishedEvent(boolean success, boolean containsAnnotationAndNotebookChanges) {
        this.success = success;
        this.containsAnnotationAndNotebookChanges = containsAnnotationAndNotebookChanges;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean containsAnnotationAndNotebookChanges() {
        return containsAnnotationAndNotebookChanges;
    }
}
