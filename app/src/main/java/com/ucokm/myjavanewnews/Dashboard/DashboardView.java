package com.ucokm.myjavanewnews.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.ucokm.myjavanewnews.Adapters.ArticleAdapter;
import com.ucokm.myjavanewnews.Adapters.SourceNewsAdapter;
import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.SourceNews;
import com.ucokm.myjavanewnews.R;
import com.ucokm.myjavanewnews.Screens.ArticleNewsActivity;
import com.ucokm.myjavanewnews.Screens.BaseActivity;
import com.ucokm.myjavanewnews.Screens.DetailArticleActivity;
import com.ucokm.myjavanewnews.Utils.CommonUtil;
import com.ucokm.myjavanewnews.Utils.OnRecyclerViewItemClickListener;

import java.util.List;

public class DashboardView extends BaseActivity implements IDashboardView, OnRecyclerViewItemClickListener {
    private RecyclerView rcvSourceNews, rcvArticleNews;
    private Spinner spnCategory;
    private Button btnLoadSourceNews, btnLoadArticleNews;
    private EditText editKeyword;
    private String category = "";
    AwesomeValidation validation;
    private DashboardPresenter dashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        dashboardPresenter = new DashboardPresenter(this);
    }

    private void initViews() {
        rcvSourceNews = findViewById(R.id.rcv_source_news);
        rcvSourceNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rcvArticleNews = findViewById(R.id.rcv_articles_news_main);
        rcvArticleNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        editKeyword = findViewById(R.id.edit_query_keyword);

        spnCategory = findViewById(R.id.spinner_category);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getSelectedItem().toString();
                category = CommonUtil.Mapper.NewsCategory(selected);
                editKeyword.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        btnLoadSourceNews = findViewById(R.id.btn_load_sources_news);
//        btnLoadSourceNews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showProgressDialog(null, getString(R.string.progress_loading), false);
//                rcvSourceNews.setVisibility(View.VISIBLE);
//                rcvArticleNews.setVisibility(View.GONE);
//                dashboardPresenter.doSyncLoadSourceNews(category);
////                if(validation != null) {
////                    validation.clear();
////                }
////                setupValidation();
////                if(validation.validate()) {
////                    doLoadSourceNews();
////                }
//            }
//        });

        btnLoadArticleNews = findViewById(R.id.btn_load_article_news);
        btnLoadArticleNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(null, getString(R.string.progress_loading), false);
                rcvSourceNews.setVisibility(View.GONE);
                rcvArticleNews.setVisibility(View.VISIBLE);
                dashboardPresenter.doSyncLoadArticleNews(editKeyword.getText().toString());
//                if(validation != null) {
//                    validation.clear();
//                }
//                setupValidation2();
//                if(validation.validate()) {
//                    spnCategory.setSelection(0);
//                    final String keyword = editKeyword.getText().toString();
//                    doSearchArticleNews(keyword);
//                }
            }
        });
    }

    @Override
    public void navigateToArticleList(List<Article> articles) {
        dismissProgressDialog();
        if(articles.size() > 0) {
            final ArticleAdapter articleAdapter = new ArticleAdapter(articles);
            articleAdapter.setOnRecyclerViewItemClickListener(this);
            rcvArticleNews.setAdapter(articleAdapter);
        }
    }

    @Override
    public void navigateToSourceList(List<SourceNews> sourceNews) {
        dismissProgressDialog();
        if(sourceNews.size() > 0) {
            final SourceNewsAdapter sourceNewsAdapter = new SourceNewsAdapter(sourceNews);
            sourceNewsAdapter.setOnRecyclerViewItemClickListener(this);
            rcvSourceNews.setAdapter(sourceNewsAdapter);
        }
    }

    @Override
    public void onErrorArticleList() {
        dismissProgressDialog();
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorSourceList() {
        dismissProgressDialog();
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.setOnRecyclerViewItemClickListener(listener);
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.source_news_layout:
                SourceNews sourceNews = (SourceNews) view.getTag();
                if (!TextUtils.isEmpty(sourceNews.getId())) {
                    Intent articleNewsActivity = new Intent(this, ArticleNewsActivity.class);
                    articleNewsActivity.putExtra("sources", sourceNews.getId());
                    startActivity(articleNewsActivity);
                }
                break;
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
