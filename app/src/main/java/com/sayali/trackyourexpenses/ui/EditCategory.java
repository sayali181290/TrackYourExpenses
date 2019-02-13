package com.sayali.trackyourexpenses.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

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

import static com.sayali.trackyourexpenses.util.Constants.CID;


public class EditCategory extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnBack)
    AppCompatImageView mBtnBack;

    @BindView(R.id.txtDesc)
    AppCompatEditText mTxtDesc;

    @BindView(R.id.txtBudget)
    AppCompatEditText mTxtBudget;

    @BindView(R.id.btnUpdate)
    AppCompatButton mBtnUpdate;

    @BindView(R.id.colorPicker)
    View mColorPicker;

    private int catId = 0;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey(CID)){
            catId = bundle.getInt(CID);
        }

        setClickListeners();
        getValuesAndFill();
    }

    private void setClickListeners(){
        mBtnUpdate.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mColorPicker.setOnClickListener(this);
    }

    private void getValuesAndFill(){
        if(catId != 0){
            category = DBManager.getCategory(catId);
            if(category.getcId() != 0){
                mTxtDesc.setText(category.getCategoryDesc());
                mTxtBudget.setText(category.getLimit()+"");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack :
                finish();
                break;

            case R.id.btnUpdate :
                updateCategory();
                break;

            case R.id.colorPicker :
                displayColorPicker(this);
                break;
        }
    }

    private void updateCategory(){
        if(Validator.validateCategory(this, mTxtDesc.getText().toString().trim(),mTxtBudget.getText().toString().trim(),"#"+Integer.toHexString(((ColorDrawable)mColorPicker.getBackground()).getColor()))){
            category.setCategoryDesc(mTxtDesc.getText().toString().trim());
            category.setLimit(Integer.parseInt(mTxtBudget.getText().toString().trim()));
            if(DBManager.updateCategory(category)){
                Utils.showLongLengthToastMessage(this, "Category updated successfully");
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
