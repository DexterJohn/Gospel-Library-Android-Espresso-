package org.lds.ldssa.event.sync;

public class AnnotationSyncStatusEvent {
    private String message;

    public AnnotationSyncStatusEvent(String message, int maxProgress, int progress) {
        if (maxProgress > 0) {
            this.message = message + "(" + progress + " / " + maxProgress + ")";
        } else {
            this.message = message;
        }
    }

    public String getMessage() {
        return message;
    }
}
