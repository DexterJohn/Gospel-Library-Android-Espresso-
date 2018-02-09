package org.lds.ldssa.sync;

import android.app.Application;
import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.annotation.LDSAnnotationService;
import org.lds.ldssa.task.AnnotationFixTask;
import org.lds.ldssa.ui.notification.AnnotationSyncNotification;
import org.lds.ldssa.ui.notification.AuthenticationFailureNotification;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.UserdataDbUtil;
import org.lds.ldssa.util.WebServiceUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.util.LdsNetworkUtil;
import org.lds.mobile.util.LdsThreadUtil;
import org.lds.mobile.util.LdsTimeUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationSync_Factory implements Factory<AnnotationSync> {
  private final Provider<Application> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<CoroutineContextProvider> arg2Provider;

  private final Provider<LDSAccountPrefs> arg3Provider;

  private final Provider<Bus> arg4Provider;

  private final Provider<LDSAnnotationService> arg5Provider;

  private final Provider<AnnotationSyncProcessor> arg6Provider;

  private final Provider<AnnotationChangeProcessor> arg7Provider;

  private final Provider<FolderSyncProcessor> arg8Provider;

  private final Provider<LdsNetworkUtil> arg9Provider;

  private final Provider<GLFileUtil> arg10Provider;

  private final Provider<LDSAccountUtil> arg11Provider;

  private final Provider<AnnotationManager> arg12Provider;

  private final Provider<NotebookManager> arg13Provider;

  private final Provider<NotebookAnnotationManager> arg14Provider;

  private final Provider<DatabaseManager> arg15Provider;

  private final Provider<UserdataDbUtil> arg16Provider;

  private final Provider<AnnotationSyncNotification> arg17Provider;

  private final Provider<LdsTimeUtil> arg18Provider;

  private final Provider<AuthenticationFailureNotification> arg19Provider;

  private final Provider<WebServiceUtil> arg20Provider;

  private final Provider<CatalogUpdateUtil> arg21Provider;

  private final Provider<AnnotationFixTask> arg22Provider;

  private final Provider<Gson> arg23Provider;

  private final Provider<LdsThreadUtil> arg24Provider;

  public AnnotationSync_Factory(
      Provider<Application> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider,
      Provider<LDSAccountPrefs> arg3Provider,
      Provider<Bus> arg4Provider,
      Provider<LDSAnnotationService> arg5Provider,
      Provider<AnnotationSyncProcessor> arg6Provider,
      Provider<AnnotationChangeProcessor> arg7Provider,
      Provider<FolderSyncProcessor> arg8Provider,
      Provider<LdsNetworkUtil> arg9Provider,
      Provider<GLFileUtil> arg10Provider,
      Provider<LDSAccountUtil> arg11Provider,
      Provider<AnnotationManager> arg12Provider,
      Provider<NotebookManager> arg13Provider,
      Provider<NotebookAnnotationManager> arg14Provider,
      Provider<DatabaseManager> arg15Provider,
      Provider<UserdataDbUtil> arg16Provider,
      Provider<AnnotationSyncNotification> arg17Provider,
      Provider<LdsTimeUtil> arg18Provider,
      Provider<AuthenticationFailureNotification> arg19Provider,
      Provider<WebServiceUtil> arg20Provider,
      Provider<CatalogUpdateUtil> arg21Provider,
      Provider<AnnotationFixTask> arg22Provider,
      Provider<Gson> arg23Provider,
      Provider<LdsThreadUtil> arg24Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
    this.arg9Provider = arg9Provider;
    this.arg10Provider = arg10Provider;
    this.arg11Provider = arg11Provider;
    this.arg12Provider = arg12Provider;
    this.arg13Provider = arg13Provider;
    this.arg14Provider = arg14Provider;
    this.arg15Provider = arg15Provider;
    this.arg16Provider = arg16Provider;
    this.arg17Provider = arg17Provider;
    this.arg18Provider = arg18Provider;
    this.arg19Provider = arg19Provider;
    this.arg20Provider = arg20Provider;
    this.arg21Provider = arg21Provider;
    this.arg22Provider = arg22Provider;
    this.arg23Provider = arg23Provider;
    this.arg24Provider = arg24Provider;
  }

  @Override
  public AnnotationSync get() {
    return new AnnotationSync(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get(),
        arg10Provider.get(),
        arg11Provider.get(),
        arg12Provider.get(),
        arg13Provider.get(),
        arg14Provider.get(),
        arg15Provider.get(),
        arg16Provider.get(),
        arg17Provider.get(),
        arg18Provider.get(),
        arg19Provider.get(),
        arg20Provider.get(),
        arg21Provider.get(),
        arg22Provider,
        arg23Provider.get(),
        arg24Provider.get());
  }

  public static Factory<AnnotationSync> create(
      Provider<Application> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider,
      Provider<LDSAccountPrefs> arg3Provider,
      Provider<Bus> arg4Provider,
      Provider<LDSAnnotationService> arg5Provider,
      Provider<AnnotationSyncProcessor> arg6Provider,
      Provider<AnnotationChangeProcessor> arg7Provider,
      Provider<FolderSyncProcessor> arg8Provider,
      Provider<LdsNetworkUtil> arg9Provider,
      Provider<GLFileUtil> arg10Provider,
      Provider<LDSAccountUtil> arg11Provider,
      Provider<AnnotationManager> arg12Provider,
      Provider<NotebookManager> arg13Provider,
      Provider<NotebookAnnotationManager> arg14Provider,
      Provider<DatabaseManager> arg15Provider,
      Provider<UserdataDbUtil> arg16Provider,
      Provider<AnnotationSyncNotification> arg17Provider,
      Provider<LdsTimeUtil> arg18Provider,
      Provider<AuthenticationFailureNotification> arg19Provider,
      Provider<WebServiceUtil> arg20Provider,
      Provider<CatalogUpdateUtil> arg21Provider,
      Provider<AnnotationFixTask> arg22Provider,
      Provider<Gson> arg23Provider,
      Provider<LdsThreadUtil> arg24Provider) {
    return new AnnotationSync_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider,
        arg10Provider,
        arg11Provider,
        arg12Provider,
        arg13Provider,
        arg14Provider,
        arg15Provider,
        arg16Provider,
        arg17Provider,
        arg18Provider,
        arg19Provider,
        arg20Provider,
        arg21Provider,
        arg22Provider,
        arg23Provider,
        arg24Provider);
  }
}
