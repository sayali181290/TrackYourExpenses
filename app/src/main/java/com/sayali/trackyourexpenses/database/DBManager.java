package com.sayali.trackyourexpenses.database;

import android.content.Context;

import com.sayali.trackyourexpenses.model.User;

import java.io.IOException;

public class DBManager {

    private static DatabaseManager databaseManager;

    public static void initDatabase(Context mContext) throws IOException {
        databaseManager = new DatabaseManager();
        databaseManager.init(mContext);
    }

    public static boolean authenticateUser(String username, String password){
        User user = DatabaseManager.authenticateUser(username, password);
        if(user != null)
            return true;
        return false;
    }
}
