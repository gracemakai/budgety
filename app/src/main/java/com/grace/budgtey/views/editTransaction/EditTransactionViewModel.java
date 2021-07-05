package com.grace.budgtey.views.editTransaction;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.repository.TransactionRepo;

public class EditTransactionViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;

    public EditTransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepo = new TransactionRepo(application.getApplicationContext());
    }


    public void editTransaction(TransactionEntity transactionEntity){
        transactionRepo.updateTransaction(transactionEntity);
    }

    public void deleteTransaction(TransactionEntity transactionEntity){
        transactionRepo.deleteTransaction(transactionEntity);
    }
}