package com.grace.budgtey.views.editTransaction;

import android.app.Application;
import android.text.InputType;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.repository.TransactionRepo;

import java.util.ArrayList;

public class EditTransactionViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;
    TransactionEntity transactionEntity;
    public MutableLiveData<TransactionEntity> transactionEntityMutableLiveData;
    public ArrayList<String> categoryEntries = new ArrayList<>();

    public EditTransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepo = new TransactionRepo(application.getApplicationContext());
        transactionEntityMutableLiveData = new MutableLiveData<>();
        fillCategoryEntries();
    }

    private void fillCategoryEntries() {
        categoryEntries.add("Food");
        categoryEntries.add("Transport");
        categoryEntries.add("MakeUp");
        categoryEntries.add("Debt");
        categoryEntries.add("Books");
        categoryEntries.add("Data");
    }

    public void setTransactionEntity(TransactionEntity transactionEntity) {
        this.transactionEntity = transactionEntity;
        transactionEntityMutableLiveData.setValue(transactionEntity);
    }

    public void editTransaction(TransactionEntity entity){
        transactionEntityMutableLiveData.getValue().setCategory(entity.getCategory());
        transactionRepo.updateTransaction(transactionEntityMutableLiveData.getValue());
    }

    public void deleteTransaction(TransactionEntity entity){
        transactionEntityMutableLiveData.getValue().setCategory(entity.getCategory());
        transactionRepo.deleteTransaction(transactionEntityMutableLiveData.getValue());
    }
}