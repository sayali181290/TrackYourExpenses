package com.sayali.trackyourexpenses.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.sayali.trackyourexpenses.database.DBManager;

import static com.sayali.trackyourexpenses.util.Constants.USER_ID;

public class Validator {

    public static boolean validateAuthenticationFields(Context mContext, AppCompatEditText mUsername, AppCompatEditText mPassword){
        if (validateEmail(mUsername, mContext) && validatePassword(mContext, mPassword, "Password"))
            return true;
        else{
            Utils.showAlertDialog(mContext, "Error", "Please enter valid credentials");
            return false;
        }
    }

    public static boolean validateEmail(AppCompatEditText mUsername, Context mContext){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = mUsername.getText().toString().trim();
        if (email.length() != 0){
            if(email.matches(emailPattern)){
                mUsername.setError(null);
                return true;
            }else{
                mUsername.setError("Please enter valid Email");
//                Utils.showAlertDialog(mContext, "Error", "Please enter valid Email");
                return false;
            }
        }else{
            mUsername.setError("Please enter Email");
//            Utils.showAlertDialog(mContext, "Error", "Please enter Email");
            return false;
        }
    }

    public static boolean validatePassword(Context mContext, AppCompatEditText mPassword, String fieldName){
       String password = mPassword.getText().toString().trim();
        if(password.length() != 0){
            if(password.length() >= 6){
                mPassword.setError(null);
                return true;
            }else{
                mPassword.setError("Password should be minimum of 6 characters");
//                Utils.showAlertDialog(mContext, "Error", "Please enter valid "+fieldName);
                return false;
            }
        }else{
            mPassword.setError("Please enter "+fieldName);
//            Utils.showAlertDialog(mContext, "Error", "Please enter "+fieldName);
            return false;
        }
    }

    public static boolean validatePassAndConfirmPass(Context mContext, AppCompatEditText mPassword, AppCompatEditText mConfirmPass){
        String password = mPassword.getText().toString().trim();
        String confirmPass = mConfirmPass.getText().toString().trim();

        if(validatePassword(mContext, mPassword, "Password")){
            if(validatePassword(mContext, mConfirmPass, "Confirm Password")){
                if(password.equals(confirmPass)){
                    mConfirmPass.setError(null);
                    return true;
                }else{
                    mConfirmPass.setError("Password & Confirm password should be same");
                    Utils.showAlertDialog(mContext, "Error", "Password & Confirm password should be same");
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean validateCharacters(Context mContext, AppCompatEditText mName, String fieldName){
        String name = mName.getText().toString().trim();

        if(name.length() != 0){
            if(name.matches("^[a-zA-Z ]+$")){
                mName.setError(null);
                return true;
            }else{
                mName.setError("Please enter valid "+fieldName);
//                Utils.showAlertDialog(mContext, "Error", "Please enter valid name");
                return false;
            }
        }else{
            mName.setError("Please enter "+fieldName);
//            Utils.showAlertDialog(mContext, "Error", "Please enter "+fieldName);
            return false;
        }
    }

    public static boolean validateNumbers(Context mContext, AppCompatEditText mTxtSalary, String fieldName){

        String salary = mTxtSalary.getText().toString().trim();
        if(salary.length() != 0){
            mTxtSalary.setError(null);
            return true;
        }else{
            mTxtSalary.setError("Please enter "+fieldName);
//            Utils.showAlertDialog(mContext, "Error", "Please enter "+fieldName);
            return false;
        }
    }

    public static boolean validateUser(Context mContext, AppCompatEditText mFname, AppCompatEditText mLname, AppCompatEditText mEmail, AppCompatEditText mPassword, AppCompatEditText mConfirmPass, AppCompatEditText salary, AppCompatEditText limit){
        if(validateCharacters(mContext, mFname, "First Name")){
            if(validateCharacters(mContext, mLname, "Last Name")){
                if(validateEmail(mEmail, mContext)){
                    if(validatePassAndConfirmPass(mContext, mPassword, mConfirmPass)){
                        if(validateNumbers(mContext, salary, "Salary")){
                            if(validateNumbers(mContext, limit, "Limit")){
                                if(Integer.parseInt(limit.getText().toString().trim()) <= Integer.parseInt(salary.getText().toString().trim())){
                                return true;
                                }else{
                                    Utils.showAlertDialog(mContext, "Error", "You cannot spend more than your salary");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean validateCategory(Context mContext, AppCompatEditText mDesc, AppCompatEditText limit, String color){
        String desc = mDesc.getText().toString().trim();
        if(validateCharacters(mContext, mDesc, "Category description")){
            if(validateNumbers(mContext, limit, "Budget for category")){
                if(DBManager.isInBudget(Integer.parseInt(limit.getText().toString().trim()), AppPreferences.getInt(mContext, USER_ID))){
                    if(!DBManager.isCategoryExists(desc)) {
                        if(!DBManager.isCategoryColorExists(color)) {
                            return true;
                        }else{
                            Utils.showAlertDialog(mContext, "Error", "You have already added this color, Please pick another");
                            return false;
                        }
                    }else{
                        Utils.showAlertDialog(mContext, "Error", "You already have a category named :  "+desc);
                        return false;
                    }
                }else{
                    Utils.showAlertDialog(mContext, "Error", "Monthly budget exceeds your salary! Do you want to increase the salary amount?", "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean validateExpense(Context mContext, AppCompatEditText mDesc, AppCompatEditText amt, String date){
        if(validateCharacters(mContext, mDesc, "Item Description")){
            if(validateNumbers(mContext, amt, "Amount")){
                if(date.length() != 0){
                    return true;
                }else{
                    Utils.showAlertDialog(mContext, "Error", "Please pick a date");
                    return false;
                }

            }
        }
        return false;
    }
}
