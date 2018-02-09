package org.lds.ldssa.model.webservice.rolecontent;


import org.lds.ldssa.model.webservice.rolecontent.dto.DtoCustomCatalogs;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoleBasedContentService {
    String BASE_URL = "https://mobileaccess.lds.org";

    @GET("/Services/v1/access/app/GospelLibraryiOS")
    Call<DtoCustomCatalogs> getCatalogsProd();

    @GET("/Services/v1/access/app/GospelLibraryiOS-Staging")
    Call<DtoCustomCatalogs> getCatalogsStaging();
}
