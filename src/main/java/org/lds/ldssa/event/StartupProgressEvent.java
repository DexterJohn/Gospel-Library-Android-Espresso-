package org.lds.ldssa.event;


public class StartupProgressEvent {
    private String logText;
    private int currentTaskCount = 0;
    private boolean indeterminate = false;

    public StartupProgressEvent(String logText, boolean indeterminate) {
        this.logText = logText;
        this.indeterminate = indeterminate;
    }

    public StartupProgressEvent(String logText, int currentTaskCount) {
        this.logText = logText;
        this.currentTaskCount = currentTaskCount;
    }

    public int getCurrentTaskCount() {
        return currentTaskCount;
    }

    public String getLogText() {
        return logText;
    }

    public boolean isIndeterminate() {
        return indeterminate;
    }
}
