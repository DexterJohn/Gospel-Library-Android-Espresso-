package org.lds.ldssa.ui.loader;

import android.content.Context;

import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.gl.history.History;
import org.lds.ldssa.model.database.gl.history.HistoryManager;
import org.lds.mobile.loader.AsyncLoader;

import java.util.List;

import javax.inject.Inject;


public class HistoryLoader extends AsyncLoader<List<History>> {

    @Inject
    HistoryManager historyManager;

    public HistoryLoader(Context context) {
        super(context);
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    public List<History> loadInBackground() {
        return historyManager.findAllSorted();
    }
}
