package com.ucokm.myjavanewnews.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ucokm.myjavanewnews.R;
import com.ucokm.myjavanewnews.Utils.WebViewClientImpl;

public class DetailArticleActivity extends AppCompatActivity {
    private WebView wv = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        initViews();
    }

    private void initViews() {
        final String url = getIntent().getStringExtra("url");
        wv = findViewById(R.id.activity_web_wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient =  new WebViewClientImpl(this);
        wv.setWebViewClient(webViewClient);
        wv.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.wv.canGoBack()) {
            this.wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
