package com.grace.budgtey.models;

public class MoneyModel {

    String budget, expenses, balance;

    public MoneyModel(String budget, String expenses, String balance) {
        this.budget = budget;
        this.expenses = expenses;
        this.balance = balance;
    }

    public MoneyModel() {
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
