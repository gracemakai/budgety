package com.grace.budgtey.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.grace.budgtey.database.DatabaseClient;
import com.grace.budgtey.database.entity.TransactionEntity;

import java.util.ArrayList;


public class TransactionRepo {

    Context context;

    public TransactionRepo(Context context) {
        this.context = context;
    }

    public MutableLiveData<ArrayList<TransactionEntity>> getAllTransactions() {
        final MutableLiveData<ArrayList<TransactionEntity>> mutableLiveData = new MutableLiveData<>();

        new Thread(() -> {
            ArrayList<TransactionEntity> transactionEntities = (ArrayList<TransactionEntity>) DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .transactionDao()
                    .getAllTransactions();
            mutableLiveData.postValue(transactionEntities);
        }).start();

        return mutableLiveData;
    }

    public MutableLiveData<Float> getTotalAmountSpent(){
        final MutableLiveData<Float> mutableLiveData = new MutableLiveData<>();

        new Thread(() -> {
            float totalAmount = DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .transactionDao()
                    .getTotalAmountSpent();

            mutableLiveData.postValue(totalAmount);
        }).start();

        return mutableLiveData;
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
}
