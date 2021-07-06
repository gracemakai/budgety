package com.grace.budgtey.views.newTransaction;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.repository.TransactionRepo;

public class NewTransactionViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;

    public NewTransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepo = new TransactionRepo(application.getApplicationContext());
    }


    public void addTransaction(TransactionEntity transactionEntity) {
        transactionRepo.addTransaction(transactionEntity);
    }
}