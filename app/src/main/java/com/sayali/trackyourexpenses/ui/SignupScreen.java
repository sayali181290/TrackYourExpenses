package com.sayali.trackyourexpenses.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.model.User;
import com.sayali.trackyourexpenses.util.Utils;
import com.sayali.trackyourexpenses.util.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignupScreen extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imgProfile)
    CircleImageView mImgProfile;

    @BindView(R.id.txtFname)
    AppCompatEditText mTxtFname;

    @BindView(R.id.txtLname)
    AppCompatEditText mTxtLname;

    @BindView(R.id.txtUsername)
    AppCompatEditText mTxtUsername;

    @BindView(R.id.txtPassword)
    AppCompatEditText mTxtPassword;

    @BindView(R.id.txtConfirmPassword)
    AppCompatEditText mTxtConfirmPassword;

    @BindView(R.id.txtSalary)
    AppCompatEditText mTxtSalary;

    @BindView(R.id.txtLimit)
    AppCompatEditText mTxtLimit;

    @BindView(R.id.btnSignup)
    AppCompatButton mBtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        ButterKnife.bind(this);

        setClickListeners();
        addTextChangeListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup :
                addUser();
                break;
        }
    }

    private void addTextChangeListeners(){
        mTxtFname.addTextChangedListener(textWatcher);
        mTxtLname.addTextChangedListener(textWatcher);
        mTxtUsername.addTextChangedListener(textWatcher);
        mTxtPassword.addTextChangedListener(textWatcher);
        mTxtConfirmPassword.addTextChangedListener(textWatcher);
        mTxtSalary.addTextChangedListener(textWatcher);
        mTxtLimit.addTextChangedListener(textWatcher);
    }

    private void setClickListeners(){
        mBtnSignup.setOnClickListener(this);
    }

    private void addUser(){
        if(Validator.validateUser(this,
                mTxtFname,
                mTxtLname,
                mTxtUsername,
                mTxtPassword,
                mTxtConfirmPassword,
                mTxtSalary,
                mTxtLimit)){
            User user = new User(mTxtFname.getText().toString().trim(),
                    mTxtLname.getText().toString().trim(),
                    mTxtUsername.getText().toString().trim(),
                    mTxtPassword.getText().toString().trim(),
            Integer.parseInt(mTxtSalary.getText().toString().trim()),
            Integer.parseInt(mTxtLimit.getText().toString().trim()));
            if(DBManager.addUser(user)) {
                Toast.makeText(this, "User added successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, LoginScreen.class));
            }else
                Utils.showAlertDialog(this, "Error", "Something went wrong. Please try again later.");
        }else{
            Utils.showAlertDialog(this, "Error","Please enter valid values");
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            int fname = mTxtFname.getText().hashCode();
            int lname = mTxtLname.getText().hashCode();
            int username = mTxtUsername.getText().hashCode();
            int password = mTxtPassword.getText().hashCode();
            int confirmPass = mTxtConfirmPassword.getText().hashCode();
            int salary = mTxtSalary.getText().hashCode();
            int limit = mTxtLimit.getText().hashCode();

            if(fname == s.hashCode()){
                Validator.validateCharacters(getApplicationContext(), mTxtFname, "First Name");
            }else if(lname == s.hashCode()){
                Validator.validateCharacters(getApplicationContext(), mTxtLname, "Last Name");
            }else if(username == s.hashCode()){
                Validator.validateEmail(mTxtUsername, getApplicationContext());
            }else if(password == s.hashCode()){
                Validator.validatePassword(getApplicationContext(), mTxtPassword, "password");
            }else if(confirmPass == s.hashCode()){
                Validator.validatePassAndConfirmPass(getApplicationContext(), mTxtPassword, mTxtConfirmPassword);
            }else if(salary == s.hashCode()){
                Validator.validateNumbers(getApplicationContext(), mTxtSalary, "Salary");
            }else if(limit == s.hashCode()){
                Validator.validateNumbers(getApplicationContext(), mTxtLimit, "Limit");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

//    private void validateData()
}
