package com.grace.budgtey.adapter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grace.budgtey.ListItemClickListener;
import com.grace.budgtey.R;
import com.grace.budgtey.database.entity.TransactionEntity;

import java.util.ArrayList;

public class TransactionItemsRecyclerAdapter extends RecyclerView.Adapter<TransactionItemsRecyclerAdapter.ViewHolder> {

    View view;
    Context context;

    ArrayList<TransactionEntity> transactionEntityList;
    final private ListItemClickListener mOnClickListener;

    public TransactionItemsRecyclerAdapter(Context context, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context)
                .inflate(R.layout.transaction_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionEntity entity = transactionEntityList.get(position);

        holder.name.setText(entity.getNote());
        holder.itemAmount.setText(entity.getStringAmount());

    }

    @Override
    public int getItemCount() {
        return transactionEntityList.size();
    }

    public void addTransactions(ArrayList<TransactionEntity> transactionEntities){
        this.transactionEntityList = transactionEntities;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView name, itemAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name_home);
            itemAmount = itemView.findViewById(R.id.item_amount_home);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(transactionEntityList.get(position));
        }


        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemLongClick(transactionEntityList.get(position));
            return true;
        }
    }
}
