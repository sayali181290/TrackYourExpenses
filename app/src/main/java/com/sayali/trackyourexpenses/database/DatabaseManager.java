package com.sayali.trackyourexpenses.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.model.Expense;
import com.sayali.trackyourexpenses.model.User;
import com.sayali.trackyourexpenses.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.sayali.trackyourexpenses.util.Constants.AMOUNT;
import static com.sayali.trackyourexpenses.util.Constants.CATEGORY;
import static com.sayali.trackyourexpenses.util.Constants.CATEGORY_TABLE;
import static com.sayali.trackyourexpenses.util.Constants.CID;
import static com.sayali.trackyourexpenses.util.Constants.COLOR;
import static com.sayali.trackyourexpenses.util.Constants.DAY;
import static com.sayali.trackyourexpenses.util.Constants.DESCRIPTION;
import static com.sayali.trackyourexpenses.util.Constants.EXPENSE_TABLE;
import static com.sayali.trackyourexpenses.util.Constants.FIRST_NAME;
import static com.sayali.trackyourexpenses.util.Constants.LAST_NAME;
import static com.sayali.trackyourexpenses.util.Constants.LIMIT;
import static com.sayali.trackyourexpenses.util.Constants.MONTH;
import static com.sayali.trackyourexpenses.util.Constants.PASSWORD;
import static com.sayali.trackyourexpenses.util.Constants.PAYMENT_MODE;
import static com.sayali.trackyourexpenses.util.Constants.PHOTO;
import static com.sayali.trackyourexpenses.util.Constants.SALARY;
import static com.sayali.trackyourexpenses.util.Constants.UID;
import static com.sayali.trackyourexpenses.util.Constants.USERNAME;
import static com.sayali.trackyourexpenses.util.Constants.USER_TABLE;
import static com.sayali.trackyourexpenses.util.Constants.YEAR;

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
        }catch(Exception e){ e.printStackTrace(); }
        return new User();
    }

    public static int getUserCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM user",null);
        return cursor.getCount();
    }

    public static int getCategoryCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM category",null);
        return cursor.getCount();
    }

    public static long addUser(ContentValues values){
        return db.insert(USER_TABLE, "",values);
    }

    public static long addCategory(ContentValues values){
        return db.insert(CATEGORY_TABLE, "",values);
    }

    public static User getUser(int uId){
        try {
            Cursor cursor = db.rawQuery("select * from user where uid = " + uId, null);
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
        }catch(Exception e){ e.printStackTrace(); }
        return new User();
    }

    public static ArrayList<Category> getAllCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from category", null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Category category = new Category(cursor.getInt(cursor.getColumnIndex(CID)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                                cursor.getInt(cursor.getColumnIndex(LIMIT)),
                        cursor.getString(cursor.getColumnIndex(COLOR)));

                categories.add(category);
                }
        }catch(Exception e){ e.printStackTrace(); }
        return categories;
    }

    public static Category getCategory(int cId){
        try {
            Cursor cursor = db.rawQuery("select * from category where cid = "+cId, null);
            if(cursor != null && cursor.moveToFirst()) {
                Category category = new Category(cursor.getInt(cursor.getColumnIndex(CID)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(LIMIT)),
                        cursor.getString(cursor.getColumnIndex(COLOR)));
                return category;
            }
        }catch(Exception e){ e.printStackTrace(); }
        return new Category();
    }

    public static int getBudget(){
        int total = 0;
        try {
            Cursor cursor = db.rawQuery("select * from category", null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                total = total + cursor.getInt(cursor.getColumnIndex(LIMIT));
            }
            cursor.close();
        }catch(Exception e){ e.printStackTrace(); }
        return total;
    }

    public static int getSalary(int uId){
        try {
            Cursor cursor = db.rawQuery("select * from user where uid = " + uId, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(LIMIT));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return 0;
    }

    public static int updateCategory(ContentValues values){
        return db.update(CATEGORY_TABLE, values, "cId = "+values.get(CID),null);
    }

    public static int isCategoryExists(String name){
        try {
            Cursor cursor = db.rawQuery("select * from category where category = \'" + name + "\'", null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getCount();
            }
        }catch(Exception e){ e.printStackTrace(); }
        return 0;
    }

    public static int isCategoryColorExists(String color){
        try {
            Cursor cursor = db.rawQuery("select * from category where color = \'" + color + "\'", null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getCount();
            }
        }catch(Exception e){ e.printStackTrace(); }
        return 0;
    }

    public static ArrayList<String> getAllCategoryDesc(){
        ArrayList<String> categories = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select category from category", null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                categories.add(cursor.getString(cursor.getColumnIndex(CATEGORY)));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return categories;
    }

    public static int getCategoryId(String catName){
        try {
            Cursor cursor = db.rawQuery("select cid from category where category = \'" + catName + "\'", null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(CID));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return 0;
    }

    public static int getExpenseCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM expense",null);
        return cursor.getCount();
    }

    public static long addExpense(ContentValues values){
        return db.insert(EXPENSE_TABLE, "",values);
    }

    public static int deleteCategory(int catId){
        return db.delete(CATEGORY_TABLE, "cid = "+catId, null);
    }

    public static ArrayList<Category> getAllCategoriesWithExpenditure(){
        ArrayList<Category> categories = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from category", null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Category category = new Category(cursor.getInt(cursor.getColumnIndex(CID)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(LIMIT)),
                        cursor.getString(cursor.getColumnIndex(COLOR)));

                category.setTotalExpenditure(getExpenditureForCategory(category.getcId()));
                categories.add(category);
            }
        }catch(Exception e){ e.printStackTrace(); }
        return categories;
    }

    public static int getExpenditureForCategory(int catId){
        int totalExpense = 0;
        try {
            Cursor cursor = db.rawQuery("select amount from expense where cid = "+catId, null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                totalExpense += cursor.getInt(cursor.getColumnIndex(AMOUNT));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return totalExpense;
    }

    public static ArrayList<Expense> getExpenseFromCategoryForCurrentMonth(int catId){
        ArrayList<Expense> expenses = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from expense where cid = "+catId, null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Expense expense = new Expense(cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(PAYMENT_MODE)),
                cursor.getInt(cursor.getColumnIndex(AMOUNT)),
                        cursor.getInt(cursor.getColumnIndex(CID)),
                                cursor.getInt(cursor.getColumnIndex(UID)),
                                        cursor.getInt(cursor.getColumnIndex(DAY)),
                                                cursor.getInt(cursor.getColumnIndex(MONTH)),
                                                        cursor.getInt(cursor.getColumnIndex(YEAR)));

                if(expense.getMonth() == (Calendar.getInstance().get(Calendar.MONTH)+1))
                    expenses.add(expense);
            }
        }catch(Exception e){ e.printStackTrace(); }
        return expenses;
    }

    public static HashMap<String, ArrayList<Expense>> getExpenseListData(){
        HashMap<String, ArrayList<Expense>> expandableListDetail = new HashMap<String, ArrayList<Expense>>();

        int month = (Calendar.getInstance().get(Calendar.MONTH))+1;
        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i=month; i>0; i--){
            expandableListDetail.put(Utils.getMonthString(i), getExpenseForSpecificMnthNYr(i,year));
        }
        return expandableListDetail;
    }

    public static ArrayList<Expense> getExpenseForSpecificMnthNYr(int month, int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from expense where month = "+month+" and year = "+year, null);
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Expense expense = new Expense(cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(PAYMENT_MODE)),
                        cursor.getInt(cursor.getColumnIndex(AMOUNT)),
                        cursor.getInt(cursor.getColumnIndex(CID)),
                        cursor.getInt(cursor.getColumnIndex(UID)),
                        cursor.getInt(cursor.getColumnIndex(DAY)),
                        cursor.getInt(cursor.getColumnIndex(MONTH)),
                        cursor.getInt(cursor.getColumnIndex(YEAR)));
                 expenses.add(expense);
            }
        }catch(Exception e){ e.printStackTrace(); }
        return expenses;
    }


}
