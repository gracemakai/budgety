package com.grace.budgtey.views.newTransaction;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.repository.TransactionRepo;

import java.util.ArrayList;

public class NewTransactionViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;
    private TransactionEntity transactionEntity;

    public MutableLiveData<TransactionEntity> transactionMutableLiveData = new MutableLiveData<>();
    public ArrayList<String> categoryEntries = new ArrayList<>();
    public MutableLiveData<String> newValue = new MutableLiveData<>();


    public NewTransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepo = new TransactionRepo(application.getApplicationContext());

        fillCategoryEntries();

        transactionEntity = new TransactionEntity("Food",
                new Utils().getCurrentTimeOrDate(), "", (float) 0.0);

        transactionMutableLiveData.setValue(transactionEntity);

    }

    private void fillCategoryEntries() {
        categoryEntries.add("Food");
        categoryEntries.add("Transport");
        categoryEntries.add("MakeUp");
        categoryEntries.add("Debt");
        categoryEntries.add("Books");
        categoryEntries.add("Data");
    }


    public void addTransaction(TransactionEntity entity) {
        transactionMutableLiveData.getValue().setCategory(entity.getCategory());

        transactionRepo.addTransaction(transactionMutableLiveData.getValue());
    }
}