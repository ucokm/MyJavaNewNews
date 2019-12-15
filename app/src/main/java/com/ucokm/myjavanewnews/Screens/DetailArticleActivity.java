package com.ucokm.myjavanewnews.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ucokm.myjavanewnews.R;

public class DetailArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        final String url = getIntent().getStringExtra("url");
        WebView wv = findViewById(R.id.activity_web_wv);
        wv.loadUrl(url);
    }
}
