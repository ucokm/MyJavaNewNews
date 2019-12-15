package com.ucokm.myjavanewnews.Utils;

import android.view.View;
import android.widget.AdapterView;

public class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getSelectedItem().toString();
        String country = CommonUtil.Mapper.NewsCountry(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
