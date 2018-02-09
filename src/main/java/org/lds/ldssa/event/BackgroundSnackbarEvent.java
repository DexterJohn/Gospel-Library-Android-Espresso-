package org.lds.ldssa.event;

import android.support.annotation.StringRes;

import org.lds.ldssa.model.database.types.SnackbarAction;

public class BackgroundSnackbarEvent {

    public static final int DEFAULT_DURATION = 4000;

    private int stringRes;
    private String message;
    private SnackbarAction action;
    private int duration;


    public BackgroundSnackbarEvent(@StringRes int stringRes, SnackbarAction action) {
        this(stringRes, action, DEFAULT_DURATION);
    }

    public BackgroundSnackbarEvent(@StringRes int stringRes, SnackbarAction action, int duration) {
        this.stringRes = stringRes;
        this.action = action;
        this.duration = duration;
    }

    public BackgroundSnackbarEvent(String message, SnackbarAction action) {
        this(message, action, DEFAULT_DURATION);
    }

    public BackgroundSnackbarEvent(String message, SnackbarAction action, int duration) {
        this.message = message;
        this.action = action;
        this.duration = duration;
    }

    public int getStringRes() {
        return stringRes;
    }

    public String getMessage() {
        return message;
    }

    public SnackbarAction getAction() {
        return action;
    }

    public int getDuration() {
        return duration;
    }
}
