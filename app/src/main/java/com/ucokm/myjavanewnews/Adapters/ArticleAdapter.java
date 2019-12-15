package com.ucokm.myjavanewnews.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucokm.myjavanewnews.DataModel.Article;
import com.ucokm.myjavanewnews.R;
import com.ucokm.myjavanewnews.Utils.OnRecyclerViewItemClickListener;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> articleArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ArticleAdapter(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_article, viewGroup, false);
        return new ArticleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder viewHolder, int i) {
        final Article articleModel = articleArrayList.get(i);
        if(!TextUtils.isEmpty(articleModel.getTitle())) {
            viewHolder.titleText.setText(articleModel.getTitle());
        }
        if(!TextUtils.isEmpty(articleModel.getDescription())) {
            viewHolder.descriptionText.setText(articleModel.getDescription());
        }
        viewHolder.layout.setTag(articleModel);
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView descriptionText;
        private LinearLayout layout;
        ViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.article_title);
            descriptionText = view.findViewById(R.id.article_description);
            layout = view.findViewById(R.id.article_layout);
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

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
