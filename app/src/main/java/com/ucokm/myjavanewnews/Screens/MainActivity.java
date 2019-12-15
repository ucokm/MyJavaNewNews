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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.ucokm.myjavanewnews.Adapters.SourceNewsAdapter;
import com.ucokm.myjavanewnews.DataModel.RespSourceNewsModel;
import com.ucokm.myjavanewnews.DataModel.SourceNews;
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
    private RecyclerView rcvSourceNews;
    private Spinner spnCountry, spnCategory;
    private Button btnLoadSourceNews;
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
        rcvSourceNews = findViewById(R.id.rcv_source_news);
        rcvSourceNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        spnCountry = findViewById(R.id.spinner_country);
//        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selected = parent.getSelectedItem().toString();
//                country = CommonUtil.Mapper.NewsCountry(selected);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

        btnLoadSourceNews = findViewById(R.id.btn_load_sources_news);
        btnLoadSourceNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation.validate()) {
                    doLoadSourceNews();
                }
            }
        });
    }

    private void setupValidation() {
        validation = new AwesomeValidation(BASIC);
        CustomValidationCallback customValidationCb = setupValidationCb();
        CustomErrorReset customErrReset = setupCustomErrReset();
//        validation.addValidation(this,
//                R.id.spinner_country,
//                setupCustomValidation(getString(R.string.select_country)),
//                customValidationCb,
//                customErrReset,
//                R.string.validation_error_select
//        );
        validation.addValidation(this,
                R.id.spinner_category,
                setupCustomValidation(getString(R.string.select_category)),
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

    private void doLoadSourceNews() {
        showProgressDialog(null, getString(R.string.progress_loading), false);
        Call<RespSourceNewsModel> call = service.getSourceNews(category, API_KEY);
        call.enqueue(new Callback<RespSourceNewsModel>() {
            @Override
            public void onResponse(Call<RespSourceNewsModel> call, Response<RespSourceNewsModel> response) {
                dismissProgressDialog();
                // check response if status is ok
                if(response.body().getStatus().equals("ok")) {
                    List<SourceNews> sources = response.body().getSources();
                    if(sources.size() > 0) {
                        final SourceNewsAdapter sourceNewsAdapter = new SourceNewsAdapter(sources);
                        sourceNewsAdapter.setOnRecyclerViewItemClickListener(MainActivity.this);
                        rcvSourceNews.setAdapter(sourceNewsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespSourceNewsModel> call, Throwable t) {
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
            case R.id.source_news_layout:
                SourceNews sourceNews = (SourceNews) view.getTag();
                if (!TextUtils.isEmpty(sourceNews.getId())) {
                    Intent articleNewsActivity = new Intent(this, ArticleNewsActivity.class);
                    articleNewsActivity.putExtra("sources", sourceNews.getId());
                    startActivity(articleNewsActivity);
                }
                break;
        }
    }
}
