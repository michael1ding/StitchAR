package com.example.ggupt.htn2019;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NodeServerApi {

    @GET("test")
    Call<List<AutodeskResponse>> getTest();
}
