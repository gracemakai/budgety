package com.grace.budgtey.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.grace.budgtey.database.DatabaseClient;
import com.grace.budgtey.database.entity.TransactionEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class TransactionRepo {

    Context context;
    LiveData<List<TransactionEntity>> allTransactionsLiveData;
    LiveData<Float> totalLiveData;

    public TransactionRepo(Context context) {
        this.context = context;
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {

        allTransactionsLiveData = DatabaseClient
                .getInstance(context)
                .getAppDatabase()
                .transactionDao()
                .getAllTransactions();

        return allTransactionsLiveData;
    }

    public LiveData<Float> getTotalAmountSpent(){

        totalLiveData = DatabaseClient
                .getInstance(context)
                .getAppDatabase()
                .transactionDao()
                .getTotalAmountSpent();

        return totalLiveData;
    }

    public void addTransaction(TransactionEntity transactionEntity) {

        new Thread(() -> {
            DatabaseClient.getInstance(context)
                    .getAppDatabase()
                    .transactionDao()
                    .insertTransaction(transactionEntity);
        }).start();
    }

    public void deleteTransaction(TransactionEntity transactionEntity) {

        new Thread(() -> {
            DatabaseClient.getInstance(context)
                    .getAppDatabase()
                    .transactionDao()
                    .deleteTransaction(transactionEntity);
        }).start();
    }

    public void updateTransaction(TransactionEntity transactionEntity) {

        new Thread(() -> {
            DatabaseClient.getInstance(context)
                    .getAppDatabase()
                    .transactionDao()
                    .updateTransaction(transactionEntity);
        }).start();
    }

    public void hh() {
        allTransactionsLiveData = new LiveData<List<TransactionEntity>>() {
            @Override
            public void observe(@NonNull @NotNull LifecycleOwner owner, @NonNull @NotNull Observer<? super List<TransactionEntity>> observer) {
                super.observe(owner, observer);
            }
        };
    }
}
