package com.ucokm.myjavanewnews;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public static final String API_KEY = "f396737da3054126bfe8d95f230a6b42";
    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String title, String msg, boolean isCancelable) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(isCancelable);
        if (title != null)
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
