package org.lds.ldssa.media.texttospeech;

import android.app.Application;
import android.app.NotificationManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextToSpeechEngine_Factory implements Factory<TextToSpeechEngine> {
  private final Provider<Application> arg0Provider;

  private final Provider<TextToSpeechManager> arg1Provider;

  private final Provider<ContentRenderer> arg2Provider;

  private final Provider<ItemManager> arg3Provider;

  private final Provider<LanguageUtil> arg4Provider;

  private final Provider<TextToSpeechGenerator> arg5Provider;

  private final Provider<ScreenUtil> arg6Provider;

  private final Provider<InternalIntents> arg7Provider;

  private final Provider<MediaPlaybackPositionManager> arg8Provider;

  private final Provider<Analytics> arg9Provider;

  private final Provider<AnalyticsUtil> arg10Provider;

  private final Provider<CoroutineContextProvider> arg11Provider;

  private final Provider<NotificationManager> arg12Provider;

  public TextToSpeechEngine_Factory(
      Provider<Application> arg0Provider,
      Provider<TextToSpeechManager> arg1Provider,
      Provider<ContentRenderer> arg2Provider,
      Provider<ItemManager> arg3Provider,
      Provider<LanguageUtil> arg4Provider,
      Provider<TextToSpeechGenerator> arg5Provider,
      Provider<ScreenUtil> arg6Provider,
      Provider<InternalIntents> arg7Provider,
      Provider<MediaPlaybackPositionManager> arg8Provider,
      Provider<Analytics> arg9Provider,
      Provider<AnalyticsUtil> arg10Provider,
      Provider<CoroutineContextProvider> arg11Provider,
      Provider<NotificationManager> arg12Provider) {
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
  }

  @Override
  public TextToSpeechEngine get() {
    return new TextToSpeechEngine(
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
        arg12Provider.get());
  }

  public static Factory<TextToSpeechEngine> create(
      Provider<Application> arg0Provider,
      Provider<TextToSpeechManager> arg1Provider,
      Provider<ContentRenderer> arg2Provider,
      Provider<ItemManager> arg3Provider,
      Provider<LanguageUtil> arg4Provider,
      Provider<TextToSpeechGenerator> arg5Provider,
      Provider<ScreenUtil> arg6Provider,
      Provider<InternalIntents> arg7Provider,
      Provider<MediaPlaybackPositionManager> arg8Provider,
      Provider<Analytics> arg9Provider,
      Provider<AnalyticsUtil> arg10Provider,
      Provider<CoroutineContextProvider> arg11Provider,
      Provider<NotificationManager> arg12Provider) {
    return new TextToSpeechEngine_Factory(
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
        arg12Provider);
  }
}
