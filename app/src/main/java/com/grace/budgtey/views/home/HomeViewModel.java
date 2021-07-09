package com.grace.budgtey.views.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.models.MoneyModel;
import com.grace.budgtey.repository.TransactionRepo;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private TransactionRepo transactionRepo;
    private final MoneyModel moneyModel;

    private MutableLiveData<ArrayList<TransactionEntity>> allTransactionsMutableLiveData;
    private MutableLiveData<Float> totalMutableLiveData;
    public MutableLiveData<MoneyModel> moneyModelMutableLiveData = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);

        transactionRepo = new TransactionRepo(application.getApplicationContext());
        moneyModel = new MoneyModel("", "", "");
        moneyModelMutableLiveData.setValue(moneyModel);
    }

    public void setBudget(String budget) {
        moneyModel.setBudget(budget);
        moneyModelMutableLiveData.setValue(moneyModel);
    }

    public void setExpenses(Float expenses) {
        moneyModel.setExpenses(String.valueOf(expenses));
        moneyModelMutableLiveData.setValue(moneyModel);
        setBalance();
    }

    //Get balance from subtracting expenses from budget
    public void setBalance() {
        if (moneyModel.getExpenses().equals("null")){
            moneyModel.setExpenses("0");
        }

        float balance = Float.parseFloat(moneyModel.getBudget()) -
                Float.parseFloat(moneyModel.getExpenses());

        moneyModel.setBalance(String.valueOf(balance));
        moneyModelMutableLiveData.setValue(moneyModel);
    }

    //Get all transactions
    public MutableLiveData<ArrayList<TransactionEntity>> getAllTransactionsMutableLiveData() {

        if (allTransactionsMutableLiveData == null){
            allTransactionsMutableLiveData = transactionRepo.getAllTransactions();
        }

        return allTransactionsMutableLiveData;
    }

    //Get total expenses
    public MutableLiveData<Float> getTotalMutableLiveData() {

        if (totalMutableLiveData == null){
            totalMutableLiveData = transactionRepo.getTotalAmountSpent();
        }

        return totalMutableLiveData;
    }

    //Delete transaction
    public void deleteTransaction(TransactionEntity transactionEntity){
        transactionRepo.deleteTransaction(transactionEntity);
    }
}