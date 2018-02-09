package org.lds.ldssa.task;

import android.support.annotation.NonNull;

import org.lds.ldssa.util.TipsUpdateUtil;
import org.lds.mobile.task.RxTask;

import javax.inject.Inject;

public class TipsUpdateTask extends RxTask<Boolean> {
    private TipsUpdateUtil tipsUpdateUtil;

    @Inject
    public TipsUpdateTask(TipsUpdateUtil tipsUpdateUtil) {
        this.tipsUpdateUtil = tipsUpdateUtil;
    }

    @NonNull
    @Override
    protected Boolean run() {
        // update tips
        tipsUpdateUtil.updateTips();

        return true;
    }

    @Override
    protected void onResult(Boolean success) {
    }
}
