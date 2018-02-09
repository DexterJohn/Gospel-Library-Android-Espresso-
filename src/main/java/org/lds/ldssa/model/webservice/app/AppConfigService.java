package org.lds.ldssa.model.webservice.app;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppConfigService {
    String BASE_URL = "http://tech.lds.org/mobile/gospellibrary/android/3.5.0/";

    @GET("config.json")
    Call<DtoAppConfig> getAppConfig();
}
