package org.lds.ldssa.ui.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.userdata.screen.Screen;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager;

import java.util.List;

import javax.inject.Inject;

public class ScreensLoader extends AsyncTaskLoader<List<Screen>> {
    @Inject
    ScreenManager screenManager;

    public ScreensLoader(Context context) {
        super(context);
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    public List<Screen> loadInBackground() {
        return screenManager.findAllOrderedByTimestamp();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
