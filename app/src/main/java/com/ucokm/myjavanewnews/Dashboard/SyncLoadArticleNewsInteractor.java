package com.ucokm.myjavanewnews.Dashboard;

import com.ucokm.myjavanewnews.DataModel.RespArticleNewsModel;
import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;
import com.ucokm.myjavanewnews.Utils.OnLoadDataListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ucokm.myjavanewnews.Screens.BaseActivity.API_KEY;

class SyncLoadArticleNewsInteractor {
    final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

    public SyncLoadArticleNewsInteractor() {

    }

    public void searchArticle(final OnLoadDataListener listener, String keyword) {
        Call<RespArticleNewsModel> call = service.searchArticleNews(keyword, API_KEY);
        call.enqueue(new Callback<RespArticleNewsModel>() {
            @Override
            public void onResponse(Call<RespArticleNewsModel> call, Response<RespArticleNewsModel> response) {
                listener.onLoadArticleNewsSuccess(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<RespArticleNewsModel> call, Throwable t) {

            }
        });
    }
}
