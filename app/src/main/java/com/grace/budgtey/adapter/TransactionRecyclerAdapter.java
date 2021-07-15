package com.grace.budgtey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.grace.budgtey.R;
import com.grace.budgtey.database.entity.TransactionEntity;

import java.util.ArrayList;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder> {

    View view;
    Context context;

    ArrayList<TransactionEntity> transactionEntityList = new ArrayList<>();

    public TransactionRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.transaction_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionEntity transactionEntity = transactionEntityList.get(position);

        holder.date.setText(transactionEntity.getDate());
        holder.dayTotalAmount.setText(String.valueOf(getDayTotalAmount()));
        holder.name.setText(transactionEntity.getNote());
        holder.itemAmount.setText(String.valueOf(transactionEntity.getAmount()));

    }

    private float getDayTotalAmount(){
        float totalAmount = 0;

        for (int i = 0; i < transactionEntityList.size(); i++){
            totalAmount += transactionEntityList.get(i).getAmount();
        }

        return totalAmount;
    }

    public void addTransactions(ArrayList<TransactionEntity> transactionEntities){
        this.transactionEntityList = transactionEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, dayTotalAmount, name, itemAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_home);
            dayTotalAmount = itemView.findViewById(R.id.day_total_amount_home);
            name = itemView.findViewById(R.id.item_name_home);
            itemAmount = itemView.findViewById(R.id.item_amount_home);

        }
    }
}
