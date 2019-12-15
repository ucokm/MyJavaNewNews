package com.ucokm.myjavanewnews.Network;

import com.ucokm.myjavanewnews.DataModel.RespNewsModel;
import com.ucokm.myjavanewnews.DataModel.RespSourceNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<RespNewsModel> getNewsCategories(@Query("category") String category, @Query("country") String country, @Query("apiKey") String apiKey);

    @GET("sources")
    Call<RespSourceNewsModel> getSourceNews(@Query("category") String category, @Query("apiKey") String apiKey);
}
