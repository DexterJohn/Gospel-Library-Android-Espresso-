package org.lds.ldssa.model.webservice;

import android.os.Build;

import com.google.gson.stream.JsonWriter;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldsaccount.LDSAccountAuth;
import org.lds.ldsaccount.LDSAccountEnvironment;
import org.lds.ldsaccount.LDSAccountInterceptor;
import org.lds.ldssa.BuildConfig;
import org.lds.ldssa.model.webservice.annotation.LDSAnnotationService;
import org.lds.ldssa.model.webservice.app.AppConfigService;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.ldssa.model.webservice.tips.TipsService;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ServiceModule {
    private static final String STANDARD_CLIENT = "STANDARD_CLIENT"; // client without auth
    private static final String AUTHENTICATED_CLIENT = "AUTHENTICATED_CLIENT";
    private static final int DEFAULT_TIMEOUT_MINUTES = 3;
    private static final String USER_AGENT;
    private static final boolean PRETTY_PRINT_JSON = false;

    public static final LDSAccountEnvironment CURRENT_ENVIRONMENT = LDSAccountEnvironment.PROD; // todo make this dynamic?

    private HttpLoggingInterceptor.Level serviceLogLevel = HttpLoggingInterceptor.Level.BASIC;

    static {
        USER_AGENT = StringUtils.stripAccents(BuildConfig.USER_AGENT_APP_NAME + " " + BuildConfig.VERSION_NAME + " / " + "Android " + Build.VERSION.RELEASE + " " +
                Build.VERSION.INCREMENTAL + " / " +
                Build.MANUFACTURER +
                " " + Build.MODEL);
    }

    @Inject
    public ServiceModule() {
        // Dagger
    }

    public static void setupJsonWriter(@Nonnull JsonWriter jsonWriter) {
        if (PRETTY_PRINT_JSON) {
            jsonWriter.setIndent("   ");
        }
    }

    @Provides
    @Named(AUTHENTICATED_CLIENT)
    public OkHttpClient getAuthenticatedClient(@Nonnull LDSAccountInterceptor ldsAccountInterceptor, LDSAccountAuth ldsAccountAuth) {
        ldsAccountInterceptor.setEnvironment(CURRENT_ENVIRONMENT);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(ldsAccountInterceptor);
//        builder.addNetworkInterceptor(new StethoInterceptor()); // testing
        setupStandardHeader(builder);

        return builder.build();
    }

    @Provides
    @Named(STANDARD_CLIENT)
    public OkHttpClient getStandardClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        setupStandardHeader(builder);
//        builder.addNetworkInterceptor(new StethoInterceptor()); // testing

        return builder.build();
    }

    private void setupStandardHeader(@Nonnull OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        builder.readTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);

        builder.addInterceptor(chain -> {
            Request.Builder builder1 = chain.request().newBuilder();
            builder1.addHeader("http.useragent", USER_AGENT);
            builder1.addHeader("Accept", "application/json");
            builder1.addHeader("Content-Type", "application/json");
            builder1.addHeader("client-app-version", BuildConfig.VERSION_NAME); // used by annotation team to identify version source
            return chain.proceed(builder1.build());
        });

        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(serviceLogLevel));
    }

    private void setupBasicAuth(@Nonnull OkHttpClient.Builder builder,
                                @Nonnull final String username, @Nonnull final String password) {
        builder.addInterceptor(chain -> {
            Request.Builder builder1 = chain.request().newBuilder();
            builder1.addHeader("Authorization", Credentials.basic(username, password));

            return chain.proceed(builder1.build());
        });
    }

    @Provides
    @Singleton
    public LDSAnnotationService getAnnotationService(@Nonnull @Named(AUTHENTICATED_CLIENT) OkHttpClient client) {

        OkHttpClient.Builder authBuilder = client.newBuilder();

        setupBasicAuth(authBuilder, BuildConfig.ANNOTATION_SERVER_TYPE.getUsername(), BuildConfig.ANNOTATION_SERVER_TYPE.getPassword());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ANNOTATION_SERVER_TYPE.getBaseUrl())
                .client(authBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(LDSAnnotationService.class);
    }

    @Provides
    @Singleton
    public CatalogService provideCatalogService(@Nonnull @Named(STANDARD_CLIENT) OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.DEFAULT_CONTENT_SERVER_TYPE.getBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CatalogService.class);
    }

    @Provides
    @Singleton
    public RoleCatalogService provideRoleCatalogService(@Nonnull @Named(AUTHENTICATED_CLIENT) OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RoleCatalogService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RoleCatalogService.class);
    }

    @Provides
    @Singleton
    public RoleBasedContentService provideRoleBasedContentService(@Nonnull @Named(AUTHENTICATED_CLIENT) OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RoleBasedContentService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RoleBasedContentService.class);
    }

    @Provides
    @Singleton
    public AppConfigService provideAppConfigService(@Nonnull @Named(STANDARD_CLIENT) OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfigService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AppConfigService.class);
    }

    @Provides
    @Singleton
    public TipsService provideTipsService(@Nonnull @Named(STANDARD_CLIENT) OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TipsService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TipsService.class);
    }
}
