package com.ucokm.myjavanewnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ucokm.myjavanewnews.Adapters.ArticleAdapter;
import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.RespNewsModel;
import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;
import com.ucokm.myjavanewnews.Utils.OnRecyclerViewItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {

    public static final String API_KEY = "f396737da3054126bfe8d95f230a6b42";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView rcv_main = findViewById(R.id.activity_main_rv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_main.setLayoutManager(linearLayoutManager);

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
                        final ArticleAdapter articleAdapter = new ArticleAdapter(articles);
                        articleAdapter.setOnRecyclerViewItemClickListener(MainActivity.this);
                        rcv_main.setAdapter(articleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespNewsModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {

    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getApplicationContext(), "onItemClick : ", Toast.LENGTH_LONG).show();
//        switch (view.getId()) {
//            case R.id.article_adapter_ll_parent:
//                Article article = (Article) view.getTag();
//                if (!TextUtils.isEmpty(article.getUrl())) {
//                    Intent webActivity = new Intent(this, WebActivity.class);
//                    webActivity.putExtra("url", article.getUrl());
//                    startActivity(webActivity);
//                }
//                break;
//        }
    }
}
