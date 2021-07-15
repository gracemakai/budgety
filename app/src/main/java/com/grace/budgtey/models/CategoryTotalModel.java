package com.grace.budgtey.models;

import com.grace.budgtey.database.entity.TransactionEntity;

import java.util.ArrayList;

public class CategoryTotalModel {

    String name;
    Integer total;
    Integer percentage;
    ArrayList<TransactionEntity> categoryItems;

    public CategoryTotalModel(String name, Integer total, Integer percentage, ArrayList<TransactionEntity> categoryItems) {
        this.name = name;
        this.total = total;
        this.percentage = percentage;
        this.categoryItems = categoryItems;
    }

    public CategoryTotalModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public ArrayList<TransactionEntity> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(ArrayList<TransactionEntity> categoryItems) {
        this.categoryItems = categoryItems;
    }
}
