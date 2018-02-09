package org.lds.ldssa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.localytics.android.Localytics;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.event.StartupProgressEvent;
import org.lds.ldssa.task.StartupTask;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import pocketbus.Bus;
import pocketbus.Subscribe;
import pocketbus.ThreadMode;

public class StartupActivity extends AppCompatActivity {
    @Inject
    Bus bus;
    @Inject
    Provider<StartupTask> startupTaskProvider;

    @BindView(R.id.startupProgressBar)
    ProgressBar startupProgressBar;

    @SuppressWarnings("FieldCanBeLocal")
    private boolean debugStartup = false;

    public StartupActivity() {
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        ButterKnife.bind(this);

        setupProgressBar();

        if (debugStartup) {
            devPauseStartup();
        } else {
            startup();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Localytics.onNewIntent(this, intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(StartupProgressEvent event) {
        startupProgressBar.setIndeterminate(event.isIndeterminate());
        startupProgressBar.setProgress(event.getCurrentTaskCount());
    }

    private void startup() {
        startupTaskProvider.get().init(this).execute();
    }

    private void setupProgressBar() {
        IndeterminateHorizontalProgressDrawable indeterminateDrawable = new IndeterminateHorizontalProgressDrawable(this);
        indeterminateDrawable.setUseIntrinsicPadding(false);
        startupProgressBar.setIndeterminateDrawable(indeterminateDrawable);
        startupProgressBar.setMax(StartupTask.TOTAL_STARTUP_TASKS);
    }

    private void devPauseStartup() {
        new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content("Paused for debugger attachment")
                .positiveText(R.string.ok)
                .onPositive((materialDialog, dialogAction) -> startup())
                .show();
    }
}