package com.ucokm.myjavanewnews.Screens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.ucokm.myjavanewnews.Adapters.ArticleAdapter;
import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.DataModel.RespNewsModel;
import com.ucokm.myjavanewnews.Network.ApiClient;
import com.ucokm.myjavanewnews.Network.ApiInterface;
import com.ucokm.myjavanewnews.R;
import com.ucokm.myjavanewnews.Utils.CommonUtil;
import com.ucokm.myjavanewnews.Utils.OnRecyclerViewItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class MainActivity extends BaseActivity implements OnRecyclerViewItemClickListener {
    private RecyclerView rcv_main;
    private Spinner spnCountry, spnCategory;
    private Button btnSubmit;
    private String country = "";
    private String category = "";
    final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
    AwesomeValidation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupValidation();
    }

    private void initViews() {
        rcv_main = findViewById(R.id.activity_main_rv);
        rcv_main.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        spnCountry = findViewById(R.id.spinner_country);
        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getSelectedItem().toString();
                country = CommonUtil.Mapper.NewsCountry(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCategory = findViewById(R.id.spinner_category);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getSelectedItem().toString();
                category = CommonUtil.Mapper.NewsCategory(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit = findViewById(R.id.btn_load_news);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation.validate()) {
                    doLoadNews();
                }
            }
        });
    }

    private void setupValidation() {
        validation = new AwesomeValidation(BASIC);
        CustomValidationCallback customValidationCb = setupValidationCb();
        CustomErrorReset customErrReset = setupCustomErrReset();
        validation.addValidation(this,
                R.id.spinner_country,
                setupCustomValidation("-- Select Country --"),
                customValidationCb,
                customErrReset,
                R.string.validation_error_select
        );
        validation.addValidation(this,
                R.id.spinner_category,
                setupCustomValidation("-- Select Category --"),
                customValidationCb,
                customErrReset,
                R.string.validation_error_select
        );
    }

    private CustomValidation setupCustomValidation(final String strNoSelectedItem) {
        return new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals(strNoSelectedItem)) {
                    return false;
                } else {
                    return true;
                }
            }
        };
    }

    private CustomValidationCallback setupValidationCb() {
        return new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        };
    }

    private CustomErrorReset setupCustomErrReset() {
        return new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        };
    }

    private void doLoadNews() {
        showProgressDialog(null, getString(R.string.progress_loading), false);
        Call<RespNewsModel> call = service.getNewsCategories(category, country, API_KEY);
        call.enqueue(new Callback<RespNewsModel>() {
            @Override
            public void onResponse(Call<RespNewsModel> call, Response<RespNewsModel> response) {
                dismissProgressDialog();
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
        Toast.makeText(getApplicationContext(), "onItemClick : ", Toast.LENGTH_LONG).show();
        switch (view.getId()) {
            case R.id.article_adapter_ll_parent:
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
