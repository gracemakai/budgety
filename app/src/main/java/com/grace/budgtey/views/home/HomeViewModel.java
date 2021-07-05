package com.grace.budgtey.views.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.repository.TransactionRepo;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;
    private MutableLiveData<ArrayList<TransactionEntity>> allTransactionsMutableLiveData;
    private MutableLiveData<Float> totalMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        transactionRepo = new TransactionRepo(application.getApplicationContext());

    }

    private void addItems() {
        transactionRepo.addTransaction(new TransactionEntity("Food", "05-07-2021 Mon", "Fries", (float) 100));
        transactionRepo.addTransaction(new TransactionEntity("MakeUp", "13-06-2021 Wed", "Lipstick", (float) 1000));
        transactionRepo.addTransaction(new TransactionEntity("Books", "05-07-2021 Mon", "Chemistry", (float) 1200));
        transactionRepo.addTransaction(new TransactionEntity("Food", "05-07-2021 Mon", "Burger", (float) 500));
        transactionRepo.addTransaction(new TransactionEntity("Transport", "05-07-2021 Mon", "Matatu", (float) 120));
        transactionRepo.addTransaction(new TransactionEntity("Debt", "11-07-2021 Mon", "Pesa", (float) 150));
    }

    public MutableLiveData<ArrayList<TransactionEntity>> getAllTransactionsMutableLiveData() {

        if (allTransactionsMutableLiveData == null){
            allTransactionsMutableLiveData = transactionRepo.getAllTransactions();
        }

        return allTransactionsMutableLiveData;
    }

    public MutableLiveData<Float> getTotalMutableLiveData() {

        if (totalMutableLiveData == null){
            totalMutableLiveData = transactionRepo.getTotalAmountSpent();
        }
        return totalMutableLiveData;
    }

    public void deleteTransaction(TransactionEntity transactionEntity){
        transactionRepo.deleteTransaction(transactionEntity);
    }
}