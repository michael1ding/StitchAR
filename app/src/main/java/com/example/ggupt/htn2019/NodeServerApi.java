package com.example.ggupt.htn2019;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NodeServerApi {

    @GET("test")
    Call<List<AutodeskResponse>> getTest();

    @FormUrlEncoded
    @POST("api/firebase")
    Call<NodeResponse> sendImage(
            @Field("imageArray") ArrayList<String> images);


}
