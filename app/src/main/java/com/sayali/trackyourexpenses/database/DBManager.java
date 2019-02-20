package com.sayali.trackyourexpenses.database;

import android.content.ContentValues;
import android.content.Context;

import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.model.Expense;
import com.sayali.trackyourexpenses.model.User;
import com.sayali.trackyourexpenses.util.AppPreferences;
import com.sayali.trackyourexpenses.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBManager implements Constants {

    private static DatabaseManager databaseManager;

    public static void initDatabase(Context mContext) throws IOException {
        databaseManager = new DatabaseManager();
        databaseManager.init(mContext);
    }

    public static int authenticateUser(String username, String password){
        User user = DatabaseManager.authenticateUser(username, password);
        if(user != null)
            return user.getuId();
        return 0;
    }

    public static boolean addUser(User user){
        ContentValues values = new ContentValues();
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
        ContentValues values = new ContentValues();
        values.put(CATEGORY, category.getCategoryDesc());
        values.put(LIMIT, category.getLimit());
        values.put(COLOR, category.getColor());

        long res = DatabaseManager.addCategory(values);
        if(res != -1)
            return true;
        return false;
    }

    public static User getUser(int uId){
        User user = DatabaseManager.getUser(uId);
        if(user != null)
            return user;
        return new User();
    }

    public static ArrayList<Category> getCategories(){
        return DatabaseManager.getAllCategories();
    }

    public static Category getCategory(int cId){
        return DatabaseManager.getCategory(cId);
    }

    public static boolean isInBudget(int limit, int uId){
        if(DatabaseManager.getSalary(uId) >= DatabaseManager.getBudget()+limit)
            return true;
        return false;
    }

    public static boolean isCategoryExists(String catName){
        if(DatabaseManager.isCategoryExists(catName) != 0)
            return true;
        return false;
    }

    public static boolean isCategoryColorExists(String color){
        if(DatabaseManager.isCategoryColorExists(color) != 0)
            return true;
        return false;
    }

    public static boolean updateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(CID, category.getcId());
        values.put(CATEGORY, category.getCategoryDesc());
        values.put(LIMIT, category.getLimit());

        int res = DatabaseManager.updateCategory(values);
        if(res != 0)
            return true;
        return false;
    }

    public static ArrayList<String> getAllCategoryDesc(){
        return DatabaseManager.getAllCategoryDesc();
    }

    public static int getCategoryId(String catName){
        return DatabaseManager.getCategoryId(catName);
    }

    public static boolean addExpense(Expense expense){
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, expense.getDesc());
        values.put(PAYMENT_MODE, expense.getPaymentMode());
        values.put(AMOUNT, expense.getAmount());
        values.put(CID, expense.getcId());
        values.put(UID, expense.getuId());
        values.put(DAY, expense.getDay());
        values.put(MONTH, expense.getMonth());
        values.put(YEAR, expense.getYear());

        long res = DatabaseManager.addExpense(values);
        if(res != -1)
            return true;
        return false;
    }

    public static boolean deleteCategory(int catId){
        if(DatabaseManager.deleteCategory(catId) != 0)
            return true;
        return false;
    }

    public static ArrayList<Category> getAllCategoriesWithExpenditure(){
        return DatabaseManager.getAllCategoriesWithExpenditure();
    }

    public static ArrayList<Expense> getExpenseFromCategoryForCurrentMonth(int catId){
        return DatabaseManager.getExpenseFromCategoryForCurrentMonth(catId);
    }

    public static HashMap<String, ArrayList<Expense>> getaExpenseListData(){
        return DatabaseManager.getExpenseListData();
    }

    public static void deleteAllDataFromDb(){
        DatabaseManager.deleteAllDataFromDb();
    }
}
