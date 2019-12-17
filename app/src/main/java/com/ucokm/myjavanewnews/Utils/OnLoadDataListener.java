package com.ucokm.myjavanewnews.Utils;

import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.SourceNews;

import java.util.List;

public interface OnLoadDataListener {
    void onLoadArticleNewsSuccess(List<Article> articles);
    void onLoadArticleNewsFail();
    void onLoadSourceNewsSuccess(List<SourceNews> sourceNews);
    void onLoadSourceNewsFail();
}
