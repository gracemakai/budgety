package com.grace.budgtey.models;

import com.grace.budgtey.database.entity.TransactionEntity;

import java.util.ArrayList;

public class DayTransactionsModel {

    String date;
    Integer dayTotal;
    ArrayList<TransactionEntity> dayTransactions;

    public DayTransactionsModel(String date, Integer dayTotal,
                                ArrayList<TransactionEntity> dayTransactions) {
        this.date = date;
        this.dayTotal = dayTotal;
        this.dayTransactions = dayTransactions;
    }

    public DayTransactionsModel() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(Integer dayTotal) {
        this.dayTotal = dayTotal;
    }

    public ArrayList<TransactionEntity> getDayTransactions() {
        return dayTransactions;
    }

    public void setDayTransactions(ArrayList<TransactionEntity> dayTransactions) {
        this.dayTransactions = dayTransactions;
    }
}
