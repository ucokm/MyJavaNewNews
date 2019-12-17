package com.ucokm.myjavanewnews.Dashboard;

import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.SourceNews;
import com.ucokm.myjavanewnews.Utils.OnLoadDataListener;

import java.util.List;

public class DashboardPresenter implements OnLoadDataListener {
    private IDashboardView iDashboardView;
    private SyncLoadArticleNewsInteractor syncLoadArticleNewsInteractor;
    private SyncLoadSourceNewsInteractor syncLoadSourceNewsInteractor;

    public DashboardPresenter(IDashboardView iView) {
        this.iDashboardView = iView;
        syncLoadArticleNewsInteractor = new SyncLoadArticleNewsInteractor();
        syncLoadSourceNewsInteractor = new SyncLoadSourceNewsInteractor();
    }

    public void doSyncLoadArticleNews(String keyword) {
        syncLoadArticleNewsInteractor.searchArticle(this, keyword);
    }

    public void doSyncLoadSourceNews(String category) {
        syncLoadSourceNewsInteractor.searchSource(this, category);
    }

    @Override
    public void onLoadArticleNewsSuccess(List<Article> a) {
        iDashboardView.navigateToArticleList(a);
    }

    @Override
    public void onLoadArticleNewsFail() {

    }

    @Override
    public void onLoadSourceNewsSuccess(List<SourceNews> sourceNews) {
        iDashboardView.navigateToSourceList(sourceNews);
    }

    @Override
    public void onLoadSourceNewsFail() {

    }
}
