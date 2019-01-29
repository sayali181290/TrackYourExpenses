package com.sayali.trackyourexpenses.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sayali.trackyourexpenses.model.User;

import java.io.IOException;

import static com.sayali.trackyourexpenses.util.Constants.FIRST_NAME;
import static com.sayali.trackyourexpenses.util.Constants.LAST_NAME;
import static com.sayali.trackyourexpenses.util.Constants.LIMIT;
import static com.sayali.trackyourexpenses.util.Constants.PASSWORD;
import static com.sayali.trackyourexpenses.util.Constants.PHOTO;
import static com.sayali.trackyourexpenses.util.Constants.SALARY;
import static com.sayali.trackyourexpenses.util.Constants.UID;
import static com.sayali.trackyourexpenses.util.Constants.USERNAME;

public class DatabaseManager {

    public static DatabaseHelper dbHelper;
    public static SQLiteDatabase db;

    void init(Context mContext) throws IOException {
        dbHelper = new DatabaseHelper(mContext);
        dbHelper.openDatabase();
        db = dbHelper.getWritableDatabase();
    }

    public static User authenticateUser(String username, String password){
        try {
            Cursor cursor = db.rawQuery("select * from user where username = \'" + username + "\' and password = \'" + password + "\'", null);
            if (cursor != null && cursor.moveToFirst()) {
                User user = new User(cursor.getInt(cursor.getColumnIndex(UID)),
                        cursor.getString(cursor.getColumnIndex(FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(LAST_NAME)),
                        cursor.getString(cursor.getColumnIndex(USERNAME)),
                        cursor.getString(cursor.getColumnIndex(PASSWORD)),
                        cursor.getInt(cursor.getColumnIndex(SALARY)),
                        cursor.getInt(cursor.getColumnIndex(LIMIT)));
                return user;

            }
        }catch(Exception e){
                e.printStackTrace();
        }

            return new User();
        }


}
