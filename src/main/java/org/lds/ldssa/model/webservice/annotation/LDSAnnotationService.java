package org.lds.ldssa.model.webservice.annotation;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Streaming;

public interface LDSAnnotationService {
    // https://www.lds.org/ws/annotation/v1.4/Services
    // PAYLOAD to show all normal lists (not deleted)
    //{"syncLists": { // NOSONAR
    //    "since": "1969-12-31T17:00:00.000-07:00",
    //}} // NOSONAR

    /**
     * Sync Notebooks
     */
    @PUT("/ws/annotation/v1.4/Services/rest/sync/folders?xver=2")
    @Streaming
    Call<ResponseBody> folders(@Body RequestBody foldersJsonFile);

    /**
     * Test Sending Notebooks
     */
    @PUT("/ws/annotation/v1.4/Services/rest/test/folders?xver=2")
    @Streaming
    Call<ResponseBody> testFolders(@Body RequestBody foldersJsonFile);

    /**
     * Sync annotations with the server just returning the aid/docId pairs of the changed annotations.
     * This method works in conjunction with the xformAnnotations method to return the specified version of an annotation.
     */
    @PUT("/ws/annotation/v1.4/Services/rest/sync/annotations-ids?xver=2")
    @Streaming
    Call<ResponseBody> sendAnnotations(@Body RequestBody annotationsJsonFile);

    /**
     * Test Sending Folders
     */
    @PUT("/ws/annotation/v1.4/Services/rest/test/annotations?xver=2")
    @Streaming
    Call<ResponseBody> testSendAnnotations(@Body RequestBody annotationsJsonFile);

    /**
     * Retrieve the annotations for the logged-in user, using the annotation ID's/ver's and docId's/vers's passed in the HTTP body.
     * Invoked via an http PUT call with the base URL. Annotations will be returned for the aid's specified and for
     * all the annotations associated with the docId's specified in the docIdVers object.
     * The refXforms object is only used to transform the refs part of a reference annotation that is being returned.
     */
    @PUT("/ws/annotation/v1.4/Services/rest/xform/annotations?xver=2")
    @Streaming
    Call<ResponseBody> getAnnotations(@Body RequestBody annotationVersionsJsonFile);
}
