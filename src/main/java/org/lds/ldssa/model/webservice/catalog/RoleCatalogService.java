package org.lds.ldssa.model.webservice.catalog;


import org.lds.ldssa.model.webservice.catalog.dto.DtoCatalogVersion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RoleCatalogService {
    String BASE_URL = "https://mobileaccess.lds.org";

    @GET
    Call<DtoCatalogVersion> getCatalogConfig(@Url String url);
}
