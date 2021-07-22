package com.grace.budgtey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grace.budgtey.ListItemClickListener;
import com.grace.budgtey.R;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.databinding.TransactionViewBinding;
import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.models.DayTransactionsModel;

import java.util.ArrayList;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder> {

    View view;
    Context context;
    TransactionItemsRecyclerAdapter itemsRecyclerAdapter;
    final private ListItemClickListener mOnClickListener;
    TransactionViewBinding binding;

    ArrayList<TransactionEntity> transactionEntityList = new ArrayList<>();

    public TransactionRecyclerAdapter(Context context, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.mOnClickListener = mOnClickListener;
        itemsRecyclerAdapter = new TransactionItemsRecyclerAdapter(context, mOnClickListener);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Bind view
        binding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.transaction_view, parent, false);

        view = binding.getRoot();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionEntity transactionEntity = transactionEntityList.get(position);

        binding.noteHome.setText(transactionEntity.getNote());
        binding.amountHome.setText(String.valueOf(transactionEntity.getAmount()));

        binding.dateMonth.setText(new Utils()
                .changeTimeOrDateFormat(new Utils().yearMonthDateDayPattern,
                        new Utils().dateMonthPattern, transactionEntity.getDate()));

        binding.dayOfWeek.setText(new Utils()
                .changeTimeOrDateFormat(new Utils().yearMonthDateDayPattern,
                        new Utils().dayOfWeekPattern, transactionEntity.getDate()));
    }


    public void addTransactions(ArrayList<TransactionEntity> transactionEntities){
        this.transactionEntityList = transactionEntities;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return transactionEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }
}
