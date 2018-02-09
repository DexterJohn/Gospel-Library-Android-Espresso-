package org.lds.ldssa.ux.annotations.links;

import android.arch.lifecycle.ViewModelProvider;
import android.view.inputmethod.InputMethodManager;
import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.model.database.content.navsection.NavSectionManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LinksActivity_MembersInjector implements MembersInjector<LinksActivity> {
  private final Provider<Bus> busProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<AccountUtil> accountUtilProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<CommonMenu> commonMenuProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  private final Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private final Provider<InputMethodManager> inputMethodManagerProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<NavItemManager> navItemManagerProvider;

  private final Provider<NavSectionManager> navSectionManagerProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public LinksActivity_MembersInjector(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<InputMethodManager> inputMethodManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<NavItemManager> navItemManagerProvider,
      Provider<NavSectionManager> navSectionManagerProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.busProvider = busProvider;
    this.prefsProvider = prefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.accountUtilProvider = accountUtilProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.analyticsProvider = analyticsProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.commonMenuProvider = commonMenuProvider;
    this.ccProvider = ccProvider;
    this.gsonProvider = gsonProvider;
    this.themeUtilProvider = themeUtilProvider;
    this.paragraphMetadataManagerProvider = paragraphMetadataManagerProvider;
    this.inputMethodManagerProvider = inputMethodManagerProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.navItemManagerProvider = navItemManagerProvider;
    this.navSectionManagerProvider = navSectionManagerProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<LinksActivity> create(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<InputMethodManager> inputMethodManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<NavItemManager> navItemManagerProvider,
      Provider<NavSectionManager> navSectionManagerProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new LinksActivity_MembersInjector(
        busProvider,
        prefsProvider,
        internalIntentsProvider,
        languageManagerProvider,
        accountUtilProvider,
        screenUtilProvider,
        screenLauncherUtilProvider,
        analyticsProvider,
        analyticsUtilProvider,
        commonMenuProvider,
        ccProvider,
        gsonProvider,
        themeUtilProvider,
        paragraphMetadataManagerProvider,
        inputMethodManagerProvider,
        itemManagerProvider,
        navItemManagerProvider,
        navSectionManagerProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(LinksActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, busProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, screenUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(
        instance, screenLauncherUtilProvider.get());
    BaseActivity_MembersInjector.injectAnalytics(instance, analyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, ccProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, gsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    injectParagraphMetadataManager(instance, paragraphMetadataManagerProvider.get());
    injectInputMethodManager(instance, inputMethodManagerProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectNavItemManager(instance, navItemManagerProvider.get());
    injectNavSectionManager(instance, navSectionManagerProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectParagraphMetadataManager(
      LinksActivity instance, ParagraphMetadataManager paragraphMetadataManager) {
    instance.paragraphMetadataManager = paragraphMetadataManager;
  }

  public static void injectInputMethodManager(
      LinksActivity instance, InputMethodManager inputMethodManager) {
    instance.inputMethodManager = inputMethodManager;
  }

  public static void injectItemManager(LinksActivity instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectNavItemManager(LinksActivity instance, NavItemManager navItemManager) {
    instance.navItemManager = navItemManager;
  }

  public static void injectNavSectionManager(
      LinksActivity instance, NavSectionManager navSectionManager) {
    instance.navSectionManager = navSectionManager;
  }

  public static void injectViewModelFactory(
      LinksActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
