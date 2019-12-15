package com.ucokm.myjavanewnews.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucokm.myjavanewnews.DataModel.SourceNews;
import com.ucokm.myjavanewnews.R;
import com.ucokm.myjavanewnews.Utils.OnRecyclerViewItemClickListener;

import java.util.List;

public class SourceNewsAdapter extends RecyclerView.Adapter<SourceNewsAdapter.ViewHolder> {
    private List<SourceNews> sourceNewsList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public SourceNewsAdapter(List<SourceNews> sourceNewsList) {
        this.sourceNewsList = sourceNewsList;
    }

    @NonNull
    @Override
    public SourceNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_source_news, viewGroup, false);
        return new SourceNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceNewsAdapter.ViewHolder viewHolder, int i) {
        final SourceNews sourceNews = sourceNewsList.get(i);
        if(!TextUtils.isEmpty(sourceNews.getName())) {
            viewHolder.txtName.setText(sourceNews.getName());
        }
        if(!TextUtils.isEmpty(sourceNews.getDescription())) {
            viewHolder.txtDescription.setText(sourceNews.getDescription());
        }
        viewHolder.layout.setTag(sourceNews);
    }

    @Override
    public int getItemCount() {
        return sourceNewsList.size();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private TextView txtDescription;
        private LinearLayout layout;
        ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.source_news_name);
            txtDescription = view.findViewById(R.id.source_news_description);
            layout = view.findViewById(R.id.source_news_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(), view);
                    }
                }
            });
        }
    }

    public void reset() {
        sourceNewsList = null;
        this.notifyDataSetChanged();
    }
}
