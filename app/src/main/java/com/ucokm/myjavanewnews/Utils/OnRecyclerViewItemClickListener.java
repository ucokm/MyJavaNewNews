package com.ucokm.myjavanewnews.Utils;

import android.view.View;

public interface OnRecyclerViewItemClickListener {
    void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener);
    void onItemClick(int position, View view);
}
