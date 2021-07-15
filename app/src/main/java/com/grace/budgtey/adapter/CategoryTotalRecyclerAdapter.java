package com.grace.budgtey.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grace.budgtey.R;
import com.grace.budgtey.models.CategoryTotalModel;

import java.util.ArrayList;

public class CategoryTotalRecyclerAdapter extends RecyclerView.Adapter<CategoryTotalRecyclerAdapter.ViewHolder> {

    View view;
    Context context;

    ArrayList<CategoryTotalModel> categoryList;

    public CategoryTotalRecyclerAdapter(Context context, ArrayList<CategoryTotalModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.category_spends, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryTotalModel categoryTotalModel = categoryList.get(position);
        holder.categoryName.setText(categoryTotalModel.getName());
        holder.categoryTotal.setText(String.valueOf(categoryTotalModel.getTotal()));
        holder.progressBar.setProgress(categoryTotalModel.getPercentage());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        TextView categoryName, categoryTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progress_bar);
            categoryName = itemView.findViewById(R.id.category_name_spends);
            categoryTotal = itemView.findViewById(R.id.category_total_spends);


        }
    }
}
