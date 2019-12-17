package com.ucokm.myjavanewnews.Dashboard;

import com.ucokm.myjavanewnews.DataModel.RespSourceNewsModel;
import com.ucokm.myjavanewnews.DataModel.SourceNews;
import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;
import com.ucokm.myjavanewnews.Utils.OnLoadDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ucokm.myjavanewnews.Screens.BaseActivity.API_KEY;

class SyncLoadSourceNewsInteractor {
    final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

    public void searchSource(final OnLoadDataListener listener, String category) {
        Call<RespSourceNewsModel> call = service.getSourceNews(category, API_KEY);
        call.enqueue(new Callback<RespSourceNewsModel>() {
            @Override
            public void onResponse(Call<RespSourceNewsModel> call, Response<RespSourceNewsModel> response) {
                // check response if status is ok
                if(response.body().getStatus().equals("ok")) {
                    List<SourceNews> sources = response.body().getSources();
                    listener.onLoadSourceNewsSuccess(sources);
                }
            }

            @Override
            public void onFailure(Call<RespSourceNewsModel> call, Throwable t) {
                listener.onLoadSourceNewsFail();
            }
        });
    }
}
