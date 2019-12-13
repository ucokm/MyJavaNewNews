package com.ucokm.myjavanewnews.Network;

import com.ucokm.myjavanewnews.DataModel.RespNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<RespNewsModel> getNewsCategories(@Query("category") String category, @Query("apiKey") String apiKey);
}
