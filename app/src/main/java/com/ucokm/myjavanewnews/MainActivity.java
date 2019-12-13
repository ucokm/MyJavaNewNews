package com.ucokm.myjavanewnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.RespNewsModel;
import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "f396737da3054126bfe8d95f230a6b42";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<RespNewsModel> call = service.getNewsCategories("business", "id", API_KEY);
        call.enqueue(new Callback<RespNewsModel>() {
            @Override
            public void onResponse(Call<RespNewsModel> call, Response<RespNewsModel> response) {
                // check response if status is ok
                if(response.body().getStatus().equals("ok")) {
                    List<Article> articles = response.body().getArticles();
                    if(articles.size() > 0) {
                        Toast.makeText(getApplicationContext(), "Total articles : " + articles.size(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespNewsModel> call, Throwable t) {

            }
        });
    }
}
