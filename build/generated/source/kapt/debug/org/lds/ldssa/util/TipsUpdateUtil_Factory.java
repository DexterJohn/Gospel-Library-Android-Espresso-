package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.download.TipsDownloader;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.database.tips.tipsmetadata.TipsMetaDataManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.util.LdsNetworkUtil;
import org.lds.mobile.util.LdsZipUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsUpdateUtil_Factory implements Factory<TipsUpdateUtil> {
  private final Provider<Prefs> arg0Provider;

  private final Provider<Bus> arg1Provider;

  private final Provider<GLFileUtil> arg2Provider;

  private final Provider<LdsNetworkUtil> arg3Provider;

  private final Provider<TipsUtil> arg4Provider;

  private final Provider<TipsMetaDataManager> arg5Provider;

  private final Provider<GLDownloadManager> arg6Provider;

  private final Provider<TipManager> arg7Provider;

  private final Provider<DatabaseManager> arg8Provider;

  private final Provider<TipsDownloader> arg9Provider;

  private final Provider<LdsZipUtil> arg10Provider;

  public TipsUpdateUtil_Factory(
      Provider<Prefs> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<GLFileUtil> arg2Provider,
      Provider<LdsNetworkUtil> arg3Provider,
      Provider<TipsUtil> arg4Provider,
      Provider<TipsMetaDataManager> arg5Provider,
      Provider<GLDownloadManager> arg6Provider,
      Provider<TipManager> arg7Provider,
      Provider<DatabaseManager> arg8Provider,
      Provider<TipsDownloader> arg9Provider,
      Provider<LdsZipUtil> arg10Provider) {
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
  }

  @Override
  public TipsUpdateUtil get() {
    return new TipsUpdateUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider,
        arg10Provider.get());
  }

  public static Factory<TipsUpdateUtil> create(
      Provider<Prefs> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<GLFileUtil> arg2Provider,
      Provider<LdsNetworkUtil> arg3Provider,
      Provider<TipsUtil> arg4Provider,
      Provider<TipsMetaDataManager> arg5Provider,
      Provider<GLDownloadManager> arg6Provider,
      Provider<TipManager> arg7Provider,
      Provider<DatabaseManager> arg8Provider,
      Provider<TipsDownloader> arg9Provider,
      Provider<LdsZipUtil> arg10Provider) {
    return new TipsUpdateUtil_Factory(
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
        arg10Provider);
  }
}
