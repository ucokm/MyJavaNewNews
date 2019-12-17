package com.ucokm.myjavanewnews.Dashboard;

import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.SourceNews;

import java.util.List;

public interface IDashboardView {
    void navigateToArticleList(List<Article> articles);
    void navigateToSourceList(List<SourceNews> sourceNews);
    void onErrorArticleList();
    void onErrorSourceList();
}
