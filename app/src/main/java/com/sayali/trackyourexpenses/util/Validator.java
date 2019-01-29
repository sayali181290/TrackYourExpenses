package com.sayali.trackyourexpenses.util;

import android.content.Context;

public class Validator {

    public static boolean validateAuthenticationFields(Context mContext, String username, String password){
        if (validateEmail(username, mContext) && validatePassword(mContext, password))
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

    public static boolean validatePassword(Context mContext, String password){
        if(password.length() != 0){
            if(password.length() >= 6){
                return true;
            }else{
                Utils.showAlertDialog(mContext, "Error", "Please enter valid Password");
                return false;
            }
        }else{
            Utils.showAlertDialog(mContext, "Error", "Please enter Password");
            return false;
        }
    }
}
