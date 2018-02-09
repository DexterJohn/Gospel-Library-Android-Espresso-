package org.lds.ldssa.model.webservice.tips;

import org.lds.ldssa.model.webservice.catalog.dto.DtoCatalogVersion;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TipsService {
    String BASE_URL = "https://edge.ldscdn.org/mobile/gltips/android/v1/";

    @GET("index.json")
    Call<DtoCatalogVersion> getTipsConfig();
}
