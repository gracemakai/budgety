package com.grace.budgtey.database.dao;

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
    List<TransactionEntity> getAllTransactions();

    @Query("SELECT TOTAL(amount) FROM `transaction`")
    float getTotalAmountSpent();

    @Insert
    void insertTransaction(TransactionEntity transactionEntity);

    @Delete
    void deleteTransaction(TransactionEntity transactionEntity);

    @Update
    void updateTransaction(TransactionEntity transactionEntity);
}
