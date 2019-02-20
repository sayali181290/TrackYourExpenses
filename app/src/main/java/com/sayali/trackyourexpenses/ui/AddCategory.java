package com.sayali.trackyourexpenses.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
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

    @BindView(R.id.btnBack)
    AppCompatImageView mBtnBack;

    @BindView(R.id.colorPicker)
    View mColorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);

        setClickListeners();
    }

    private void setClickListeners(){
        mBtnAdd.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mColorPicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAdd :
                addCategory();
                break;

            case R.id.btnBack :
                finish();
                break;

            case R.id.colorPicker :
                displayColorPicker(this);
                break;
        }
    }

    private void addCategory(){
        if(Validator.validateCategory(this, mTxtDesc,mTxtBudget, Integer.toHexString(((ColorDrawable)mColorPicker.getBackground()).getColor()))){
            Category category = new Category(mTxtDesc.getText().toString().trim(), Integer.parseInt(mTxtBudget.getText().toString().trim()),
                    "#"+Integer.toHexString(((ColorDrawable)mColorPicker.getBackground()).getColor()));
            if(DBManager.addCategory(category)){
                Utils.showLongLengthToastMessage(this, "Category added successfully");
                finish();
            }else{
                Utils.showAlertDialog(this, "Error", "Something went wrong please try again after some time.");
            }
        }
    }

    private void displayColorPicker(Context mContext) {
        ColorPickerDialogBuilder
                .with(mContext)
                .setTitle("Choose category color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {}})
                .setPositiveButton("Ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        mColorPicker.setBackgroundColor(Color.parseColor("#"+Integer.toHexString(selectedColor)));
                        dialog.dismiss(); }})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}})
                .build()
                .show();
    }
}
