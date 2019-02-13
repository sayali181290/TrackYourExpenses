package com.sayali.trackyourexpenses.util;

public interface Constants {

    // Table Names
    String USER_TABLE = "user";
    String EXPENSE_TABLE = "expense";
    String CATEGORY_TABLE = "category";

    // Column Names for "user" table
    String UID = "uid";
    String FIRST_NAME = "fname";
    String LAST_NAME = "lname";
    String USERNAME = "username";
    String PASSWORD = "password";
    String SALARY = "salary";
    String PHOTO = "photo";
    String LIMIT = "budget";

    // Column Names for "expense" table
    String EID = "eid";
    String DESCRIPTION = "desc";
    String PAYMENT_MODE = "payment_mode";
    String AMOUNT = "amount";
    String DAY = "day";
    String MONTH = "month";
    String YEAR = "year";

    // Column Names for "category" table
    String CID = "cid";
    String CATEGORY = "category";
    String COLOR = "color";

    // App preference keys
    String IS_LOGIN = "isLogin";
    String USER_ID = "userId";
    String USER = "user";

    // Log keys
    String TAG = "TrackYourExpenses";

    // Constant values used in App
    String PAYMENT_MODES[] = {"Cash", "Credit Card", "Debit Card", "Net Banking", "UPI"};
}
