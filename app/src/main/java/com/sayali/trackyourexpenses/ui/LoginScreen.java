package com.sayali.trackyourexpenses.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.util.AppPreferences;
import com.sayali.trackyourexpenses.util.Constants;
import com.sayali.trackyourexpenses.util.Utils;
import com.sayali.trackyourexpenses.util.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener, Constants {

    @BindView(R.id.txtEmail)
    AppCompatEditText mTxtEmail;

    @BindView(R.id.txtPassword)
    AppCompatEditText mTxtPassword;

    @BindView(R.id.btnLogin)
    AppCompatButton mBtnLogin;

    @BindView(R.id.btnShowPass)
    AppCompatImageButton mBtnShowPass;

    @BindView(R.id.btnForgotPassword)
    AppCompatTextView mBtnForgotPassword;

    @BindView(R.id.btnSignup)
    AppCompatTextView mBtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);

        setClickListeners();
    }

    private void setClickListeners(){
        mBtnLogin.setOnClickListener(this);
        mBtnShowPass.setOnClickListener(this);
        mBtnForgotPassword.setOnClickListener(this);
        mBtnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                authenticateUser();
                break;

            case R.id.btnForgotPassword:
                startActivity(new Intent(LoginScreen.this, ResetPassword.class));
                break;

            case R.id.btnShowPass:
                handlePasswordVisibility();
                break;

            case R.id.btnSignup:
                startActivity(new Intent(LoginScreen.this, SignupScreen.class));
                break;
        }
    }

    private void handlePasswordVisibility(){
        if(mTxtPassword.getTransformationMethod() != null) {
            mTxtPassword.setTransformationMethod(null);
            mBtnShowPass.setImageResource(R.drawable.show_password);
        }else{
            mTxtPassword.setTransformationMethod(new PasswordTransformationMethod());
            mBtnShowPass.setImageResource(R.drawable.hide_password);
        }
    }

    private void authenticateUser(){
        String email = mTxtEmail.getText().toString().trim();
        String password = mTxtPassword.getText().toString().trim();
        if(Validator.validateAuthenticationFields(this, mTxtEmail, mTxtPassword)){
            int userId = DBManager.authenticateUser(email,password);
            if(userId != 0){
                AppPreferences.setBoolean(this, IS_LOGIN, true);
                AppPreferences.setInt(this, USER_ID, userId);
                startActivity(new Intent(this, DashboardScreen.class));
            }else{
                Utils.showAlertDialog(this, "Error", "Something went wrong. Please try after some time.");
            }
        }
    }
}
