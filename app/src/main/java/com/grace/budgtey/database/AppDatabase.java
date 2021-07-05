package com.grace.budgtey.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.grace.budgtey.database.dao.TransactionDao;
import com.grace.budgtey.database.entity.TransactionEntity;

@Database(entities = {TransactionEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();
}
