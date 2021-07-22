package com.grace.budgtey.views.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.models.MoneyModel;
import com.grace.budgtey.repository.TransactionRepo;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    TransactionRepo transactionRepo;

    LiveData<List<TransactionEntity>> allTransactionsMutableLiveData;
    LiveData<Float> totalMutableLiveData;
    public MutableLiveData<MoneyModel> moneyModelMutableLiveData = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);

        transactionRepo = new TransactionRepo(application.getApplicationContext());
        allTransactionsMutableLiveData = transactionRepo.getAllTransactions();
        totalMutableLiveData = transactionRepo.getTotalAmountSpent();
    }

    //Get all transactions
    public LiveData<List<TransactionEntity>> getAllTransactionsMutableLiveData() {

        return allTransactionsMutableLiveData;
    }

    //Get total expenses
    public LiveData<Float> getTotalMutableLiveData() {

        return totalMutableLiveData;
    }

    //Delete transaction
    public void deleteTransaction(TransactionEntity transactionEntity){
        transactionRepo.deleteTransaction(transactionEntity);

    }
}