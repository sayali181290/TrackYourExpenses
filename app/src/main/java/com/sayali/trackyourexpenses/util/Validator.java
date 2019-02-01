package com.sayali.trackyourexpenses.util;

import android.content.Context;

public class Validator {

    public static boolean validateAuthenticationFields(Context mContext, String username, String password){
        if (validateEmail(username, mContext) && validatePassword(mContext, password, "Password"))
            return true;
        else{
            Utils.showAlertDialog(mContext, "Error", "Please enter valid credentials");
            return false;
        }
    }

    public static boolean validateEmail(String email, Context mContext){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.length() != 0){
            if(email.matches(emailPattern)){
                return true;
            }else{
                Utils.showAlertDialog(mContext, "Error", "Please enter valid Email");
                return false;
            }
        }else{
            Utils.showAlertDialog(mContext, "Error", "Please enter Email");
            return false;
        }
    }

    public static boolean validatePassword(Context mContext, String password, String fieldName){
        if(password.length() != 0){
            if(password.length() >= 6){
                return true;
            }else{
                Utils.showAlertDialog(mContext, "Error", "Please enter valid "+fieldName);
                return false;
            }
        }else{
            Utils.showAlertDialog(mContext, "Error", "Please enter "+fieldName);
            return false;
        }
    }

    public static boolean validatePassAndConfirmPass(Context mContext, String password, String confirmPass){
        if(validatePassword(mContext, password, "Password")){
            if(validatePassword(mContext, confirmPass, "Confirm Password")){
                if(password.equals(confirmPass)){
                    return true;
                }else{
                    Utils.showAlertDialog(mContext, "Error", "Password & Confirm password should be same");
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean validateCharacters(Context mContext, String name, String fieldName){
        if(name.length() != 0){
            if(name.matches("[A-Z][a-zA-Z]*")){
                return true;
            }else{
                Utils.showAlertDialog(mContext, "Error", "Please enter valid name");
                return false;
            }
        }else{
            Utils.showAlertDialog(mContext, "Error", "Please enter "+fieldName);
            return false;
        }
    }

    public static boolean validateNumbers(Context mContext, String salary, String fieldName){
        if(salary.length() != 0){
            return true;
        }else{
            Utils.showAlertDialog(mContext, "Error", "Please enter "+fieldName);
            return false;
        }
    }

    public static boolean validateUser(Context mContext, String fname, String lname, String email, String password, String confirmPass, String salary, String limit){
        if(validateCharacters(mContext, fname, "First Name")){
            if(validateCharacters(mContext, lname, "Last Name")){
                if(validateEmail(email, mContext)){
                    if(validatePassAndConfirmPass(mContext, password, confirmPass)){
                        if(validateNumbers(mContext, salary, "Salary")){
                            if(validateNumbers(mContext, limit, "Limit")){
                                if(Integer.parseInt(limit) <= Integer.parseInt(salary)){
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

    public static boolean validateCategory(Context mContext, String desc, String limit){
        if(validateCharacters(mContext, desc, "Category description")){
            if(validateNumbers(mContext, limit, "Budget for category")){
                return true;
            }
        }
        return false;
    }
}
