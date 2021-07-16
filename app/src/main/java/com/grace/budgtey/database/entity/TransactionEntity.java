package com.grace.budgtey.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "transaction")
public class TransactionEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "note")
    String note;

    @ColumnInfo(name = "amount")
    Float amount;

    public TransactionEntity(String category, String date, String note, Float amount) {
        this.category = category;
        this.date = date;
        this.note = note;
        this.amount = amount;
    }



    public TransactionEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getAmount() {
        return amount;
    }

    public String getStringAmount(){
        if (amount == null){
            return "";
        }
        else {
            return String.valueOf(amount);
        }
    }

    public void setStringAmount(String amount){
        if (!amount.isEmpty()) {
            this.amount = Float.valueOf(amount);
        }
    }

    public void setAmount(Float amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }
}
