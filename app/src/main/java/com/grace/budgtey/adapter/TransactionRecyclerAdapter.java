package com.grace.budgtey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grace.budgtey.ListItemClickListener;
import com.grace.budgtey.R;
import com.grace.budgtey.models.DayTransactionsModel;

import java.util.ArrayList;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder> {

    View view;
    Context context;
    TransactionItemsRecyclerAdapter itemsRecyclerAdapter;
    final private ListItemClickListener mOnClickListener;

    ArrayList<DayTransactionsModel> transactionEntityList = new ArrayList<>();

    public TransactionRecyclerAdapter(Context context, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.mOnClickListener = mOnClickListener;
        itemsRecyclerAdapter = new TransactionItemsRecyclerAdapter(context, mOnClickListener);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context)
                .inflate(R.layout.transaction_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayTransactionsModel transactionEntity = transactionEntityList.get(position);

        holder.date.setText(transactionEntity.getDate());
        holder.dayTotalAmount.setText(String.valueOf(transactionEntity.getDayTotal()));

        holder.transactionItems.setLayoutManager(new LinearLayoutManager(context));
        holder.transactionItems.setAdapter(itemsRecyclerAdapter);

        itemsRecyclerAdapter.addTransactions(transactionEntity.getDayTransactions());

    }


    public void addTransactions(ArrayList<DayTransactionsModel> transactionEntities){
        this.transactionEntityList = transactionEntities;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return transactionEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView date, dayTotalAmount;
        RecyclerView transactionItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_home);
            dayTotalAmount = itemView.findViewById(R.id.day_total_amount_home);
            transactionItems = itemView.findViewById(R.id.recycler_view_transaction_items);
        }

    }
}
