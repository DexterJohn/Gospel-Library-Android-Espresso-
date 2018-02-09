package org.lds.ldssa.media.texttospeech;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextToSpeechControlsManager_MembersInjector
    implements MembersInjector<TextToSpeechControlsManager> {
  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<LanguageUtil> languageUtilProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<TextToSpeechManager> textToSpeechManagerProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  public TextToSpeechControlsManager_MembersInjector(
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider,
      Provider<CoroutineContextProvider> ccProvider) {
    this.itemManagerProvider = itemManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.languageUtilProvider = languageUtilProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.textToSpeechManagerProvider = textToSpeechManagerProvider;
    this.ccProvider = ccProvider;
  }

  public static MembersInjector<TextToSpeechControlsManager> create(
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider,
      Provider<CoroutineContextProvider> ccProvider) {
    return new TextToSpeechControlsManager_MembersInjector(
        itemManagerProvider,
        subItemManagerProvider,
        languageUtilProvider,
        internalIntentsProvider,
        textToSpeechManagerProvider,
        ccProvider);
  }

  @Override
  public void injectMembers(TextToSpeechControlsManager instance) {
    injectItemManager(instance, itemManagerProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
    injectLanguageUtil(instance, languageUtilProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectTextToSpeechManager(instance, textToSpeechManagerProvider.get());
    injectCc(instance, ccProvider.get());
  }

  public static void injectItemManager(
      TextToSpeechControlsManager instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectSubItemManager(
      TextToSpeechControlsManager instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }

  public static void injectLanguageUtil(
      TextToSpeechControlsManager instance, LanguageUtil languageUtil) {
    instance.languageUtil = languageUtil;
  }

  public static void injectInternalIntents(
      TextToSpeechControlsManager instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectTextToSpeechManager(
      TextToSpeechControlsManager instance, TextToSpeechManager textToSpeechManager) {
    instance.textToSpeechManager = textToSpeechManager;
  }

  public static void injectCc(TextToSpeechControlsManager instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }
}
