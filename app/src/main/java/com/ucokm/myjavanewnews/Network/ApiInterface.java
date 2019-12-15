package com.ucokm.myjavanewnews.Network;

import com.ucokm.myjavanewnews.DataModel.RespArticleNewsModel;
import com.ucokm.myjavanewnews.DataModel.RespSourceNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<RespArticleNewsModel> getArticleNews(@Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("sources")
    Call<RespSourceNewsModel> getSourceNews(@Query("category") String category, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<RespArticleNewsModel> searchArticleNews(@Query("q") String sources, @Query("apiKey") String apiKey);
}
