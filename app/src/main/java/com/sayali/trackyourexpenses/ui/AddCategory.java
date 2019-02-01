package com.sayali.trackyourexpenses.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.util.Utils;
import com.sayali.trackyourexpenses.util.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCategory extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.txtDesc)
    AppCompatEditText mTxtDesc;

    @BindView(R.id.txtBudget)
    AppCompatEditText mTxtBudget;

    @BindView(R.id.btnAdd)
    AppCompatButton mBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);

        setClickListeners();
    }

    private void setClickListeners(){
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAdd :
                addCategory();
                break;
        }
    }

    private void addCategory(){
        if(Validator.validateCategory(this, mTxtDesc.getText().toString().trim(),mTxtBudget.getText().toString().trim())){
            Category category = new Category(mTxtDesc.getText().toString().trim(), Integer.parseInt(mTxtBudget.getText().toString().trim()));
            if(DBManager.addCategory(category)){
                Utils.showLongLengthToastMessage(this, "Category added successfully");
            }else{
                Utils.showAlertDialog(this, "Error", "Something went wrong please try again after some time.");
            }
        }
    }
}
