package org.lds.ldssa.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.DatabaseManagerConst;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.service.BookmarkWidgetService;
import org.lds.ldssa.ui.activity.StartupActivity;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.UserdataDbUtil;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class BookmarkWidgetProvider extends AppWidgetProvider {
    public static final String SELECT_ACTION = "org.lds.ldssa.ui.widget.SELECT_ACTION";
    public static final String BOOKMARK_ID = "org.lds.ldssa.ui.widget.BOOKMARK_ID";

    private RemoteViews views;

    @Inject
    InternalIntents internalIntents;
    @Inject
    DatabaseManager databaseManager;
    @Inject
    UserdataDbUtil userdataDbUtil;
    @Inject
    ScreenUtil screenUtil;
    @Inject
    Prefs prefs;

    @Override
    public void onUpdate(Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        Injector.INSTANCE.get().inject(this);

        if (views == null) {
            final Intent service = new Intent(context, BookmarkWidgetService.class);
            service.setData(Uri.parse(service.toUri(Intent.URI_INTENT_SCHEME)));

            views = new RemoteViews(context.getPackageName(), R.layout.widget_launcher_bookmark);
            views.setRemoteAdapter(R.id.list, service);
            views.setEmptyView(R.id.list, R.id.empty);
            views.setOnClickPendingIntent(R.id.titleLayout, createStartupIntent(context));

            final Intent toastIntent = new Intent(context, BookmarkWidgetProvider.class);
            toastIntent.setAction(BookmarkWidgetProvider.SELECT_ACTION);
            toastIntent.setData(Uri.parse(toastIntent.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            views.setPendingIntentTemplate(R.id.list, toastPendingIntent);
        }

        // make sure the user database is available in case the app isn't running
        userdataDbUtil.openCurrentDatabase();

        appWidgetManager.updateAppWidget(appWidgetIds, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list);
    }

    @Override
    public void onReceive(@Nonnull Context context, @Nonnull Intent intent) {
        Injector.INSTANCE.get().inject(this);

        if (intent.getAction().equals(SELECT_ACTION) && intent.hasExtra(BOOKMARK_ID)) {
            // make sure databases are added just in case the app isn't running
            if (!databaseManager.containsDatabase(DatabaseManagerConst.CATALOG_DATABASE_NAME)) {
                databaseManager.getDatabaseConfig().identifyDatabases(databaseManager);
            }
            internalIntents.showContentFromBookmarkWidget(context, intent.getLongExtra(BOOKMARK_ID, -1));
        }

        super.onReceive(context, intent);
    }

    private PendingIntent createStartupIntent(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setComponent(new ComponentName(context, StartupActivity.class));
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}
