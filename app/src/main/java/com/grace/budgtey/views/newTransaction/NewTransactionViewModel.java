package com.grace.budgtey.views.newTransaction;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.repository.TransactionRepo;

import java.util.ArrayList;

public class NewTransactionViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;
    private TransactionEntity transactionEntity;
   Application application;

    public MutableLiveData<TransactionEntity> transactionMutableLiveData = new MutableLiveData<>();
    public ArrayList<String> categoryEntries = new ArrayList<>();

    public NewTransactionViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        transactionRepo = new TransactionRepo(application.getApplicationContext());

        fillCategoryEntries();

        transactionEntity = new TransactionEntity( );

        transactionEntity.setCategory("Food");
        transactionEntity.setDate(new Utils().getCurrentTimeOrDate());

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