package com.ucokm.myjavanewnews.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ucokm.myjavanewnews.Adapters.ArticleAdapter;
import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.RespNewsModel;
import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;
import com.ucokm.myjavanewnews.R;
import com.ucokm.myjavanewnews.Utils.OnRecyclerViewItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleNewsActivity extends BaseActivity implements OnRecyclerViewItemClickListener{
    private RecyclerView rcvArticleNews;
    final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_news);
        initViews();

        final String sources = getIntent().getStringExtra("sources");
        doLoadNews(sources);
    }

    private void initViews() {
        rcvArticleNews = findViewById(R.id.rcv_articles_news);
        rcvArticleNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void doLoadNews(String sources) {
        showProgressDialog(null, getString(R.string.progress_loading), false);
        Call<RespNewsModel> call = service.getArticleNews(sources, API_KEY);
        call.enqueue(new Callback<RespNewsModel>() {
            @Override
            public void onResponse(Call<RespNewsModel> call, Response<RespNewsModel> response) {
                dismissProgressDialog();
                // check response if status is ok
                if(response.body().getStatus().equals("ok")) {
                    List<Article> articles = response.body().getArticles();
                    if(articles.size() > 0) {
                        final ArticleAdapter articleAdapter = new ArticleAdapter(articles);
                        articleAdapter.setOnRecyclerViewItemClickListener(ArticleNewsActivity.this);
                        rcvArticleNews.setAdapter(articleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespNewsModel> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.setOnRecyclerViewItemClickListener(listener);
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.article_layout:
                Article article = (Article) view.getTag();
                if (!TextUtils.isEmpty(article.getUrl())) {
                    Intent webActivity = new Intent(this, DetailArticleActivity.class);
                    webActivity.putExtra("url", article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }
}
