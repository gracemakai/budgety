package com.grace.budgtey.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    Context context;
    static DatabaseClient databaseClientInstance;
    AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "Budgety").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){

        if (databaseClientInstance == null){
            databaseClientInstance = new DatabaseClient(context);
        }

        return databaseClientInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
