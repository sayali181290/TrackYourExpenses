package com.sayali.trackyourexpenses.database;

import android.content.ContentValues;
import android.content.Context;

import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.model.User;
import com.sayali.trackyourexpenses.util.Constants;

import java.io.IOException;

public class DBManager implements Constants {

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

    public static boolean addUser(User user){
        user.setuId(DatabaseManager.getUserCount());
        ContentValues values = new ContentValues();
        values.put(UID, user.getuId());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(FIRST_NAME, user.getFname());
        values.put(LAST_NAME, user.getLname());
        values.put(SALARY, user.getSalary());
        values.put(LIMIT, user.getLimit());
        values.put(PHOTO, "");

        long res = DatabaseManager.addUser(values);
        if(res != -1)
            return true;
        return false;
    }

    public static boolean addCategory(Category category){
        category.setcId(DatabaseManager.getCategoryCount());
        ContentValues values = new ContentValues();
        values.put(CID, category.getcId());
        values.put(DESCRIPTION, category.getCategoryDesc());
        values.put(LIMIT, category.getLimit());

        long res = DatabaseManager.addCategory(values);
        if(res != -1)
            return true;
        return false;
    }
}
