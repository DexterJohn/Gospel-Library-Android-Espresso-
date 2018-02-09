package org.lds.ldssa.inject;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.converter.gson.GsonConverterFactory;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideGsonConverterFactory$gospel_library_debugFactory
    implements Factory<GsonConverterFactory> {
  private final AppModule module;

  private final Provider<Gson> arg0Provider;

  public AppModule_ProvideGsonConverterFactory$gospel_library_debugFactory(
      AppModule module, Provider<Gson> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public GsonConverterFactory get() {
    return Preconditions.checkNotNull(
        module.provideGsonConverterFactory$gospel_library_debug(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GsonConverterFactory> create(
      AppModule module, Provider<Gson> arg0Provider) {
    return new AppModule_ProvideGsonConverterFactory$gospel_library_debugFactory(
        module, arg0Provider);
  }
}
