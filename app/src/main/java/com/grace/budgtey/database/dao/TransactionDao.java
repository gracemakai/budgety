package com.grace.budgtey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grace.budgtey.database.entity.TransactionEntity;


import java.util.List;


@Dao
public interface TransactionDao {

    @Query("SELECT * FROM `transaction` ORDER BY date")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Query("SELECT TOTAL(amount) FROM `transaction`")
    LiveData<Float> getTotalAmountSpent();

    @Insert
    void insertTransaction(TransactionEntity transactionEntity);

    @Delete
    void deleteTransaction(TransactionEntity transactionEntity);

    @Update
    void updateTransaction(TransactionEntity transactionEntity);
}
