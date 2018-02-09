package com.bumptech.glide;

import android.content.Context;
import android.util.Log;
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
import java.lang.Class;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.Set;
import org.lds.ldssa.glide.AppGlideModule;
import org.lds.mobile.glide.LdsNetworkGlideModule;

@SuppressWarnings("deprecation")
final class GeneratedAppGlideModuleImpl extends GeneratedAppGlideModule {
  private final AppGlideModule appGlideModule;

  GeneratedAppGlideModuleImpl() {
    appGlideModule = new AppGlideModule();
    if (Log.isLoggable("Glide", Log.DEBUG)) {
      Log.d("Glide", "Discovered AppGlideModule from annotation: org.lds.ldssa.glide.AppGlideModule");
      Log.d("Glide", "Discovered LibraryGlideModule from annotation: com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule");
      Log.d("Glide", "Discovered LibraryGlideModule from annotation: org.lds.mobile.glide.LdsNetworkGlideModule");
    }
  }

  @Override
  public void applyOptions(Context context, GlideBuilder builder) {
    appGlideModule.applyOptions(context, builder);
  }

  @Override
  public void registerComponents(Context context, Glide glide, Registry registry) {
    new OkHttpLibraryGlideModule().registerComponents(context, glide, registry);
    new LdsNetworkGlideModule().registerComponents(context, glide, registry);
    appGlideModule.registerComponents(context, glide, registry);
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return appGlideModule.isManifestParsingEnabled();
  }

  @Override
  public Set<Class<?>> getExcludedModuleClasses() {
    return Collections.emptySet();
  }

  @Override
  GeneratedRequestManagerFactory getRequestManagerFactory() {
    return new GeneratedRequestManagerFactory();
  }
}
