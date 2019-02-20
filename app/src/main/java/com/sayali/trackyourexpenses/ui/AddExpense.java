package com.sayali.trackyourexpenses.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.database.DatabaseManager;
import com.sayali.trackyourexpenses.model.Expense;
import com.sayali.trackyourexpenses.util.AppPreferences;
import com.sayali.trackyourexpenses.util.Utils;
import com.sayali.trackyourexpenses.util.Validator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sayali.trackyourexpenses.util.Constants.PAYMENT_MODES;
import static com.sayali.trackyourexpenses.util.Constants.USER_ID;

public class AddExpense extends AppCompatActivity implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, View.OnTouchListener {

    @BindView(R.id.btnBack)
    AppCompatImageView mBtnBack;

    @BindView(R.id.txtDesc)
    AppCompatEditText mTxtDesc;

    @BindView(R.id.txtAmt)
    AppCompatEditText mTxtAmt;

    @BindView(R.id.txtDate)
    AppCompatEditText mTxtDate;

    @BindView(R.id.category)
    MaterialSpinner mCategorySpinner;

    @BindView(R.id.paymentMode)
    MaterialSpinner mPaymentModeSpinner;

    @BindView(R.id.btnAdd)
    AppCompatButton mBtnAdd;

    private ArrayList<String> categories;
    private Calendar calendar ;
    private DatePickerDialog datePickerDialog ;
    private int Year, Month, Day ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ButterKnife.bind(this);

        initUI();
    }

    private void initUI(){
        setOnClickListeners();
        setAdapterToSpinner();
        initCalendar();
    }

    private void setOnClickListeners(){
        mBtnBack.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);

        mTxtDate.setOnTouchListener(this);

        mPaymentModeSpinner.setOnItemSelectedListener(this);
        mCategorySpinner.setOnItemSelectedListener(this);
    }

    private void setAdapterToSpinner(){
        categories = DBManager.getAllCategoryDesc();
        if(categories.size() != 0){
            mCategorySpinner.setItems(categories);
        }
        mPaymentModeSpinner.setItems(PAYMENT_MODES);
    }

    private void initCalendar(){
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH)+1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        setDateText(Year, Month, Day);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnBack :
                onBackPressed();
                break;

            case R.id.btnAdd :
                addExpense();
                break;
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (view.getId()){
            case R.id.paymentMode :
                break;

            case R.id.category :
                break;
        }
    }

    private void addExpense(){
        if(Validator.validateExpense(this, mTxtDesc, mTxtAmt, mTxtDate.getText().toString().trim())){

            Expense expense = new Expense(mTxtDesc.getText().toString().trim(),
                    PAYMENT_MODES[mPaymentModeSpinner.getSelectedIndex()],
                    Integer.parseInt(mTxtAmt.getText().toString().trim()),
                    DBManager.getCategoryId(categories.get(mCategorySpinner.getSelectedIndex())),
                    AppPreferences.getInt(this, USER_ID),Day, Month, Year);


            if(DBManager.addExpense(expense)){
                Utils.displayShortLengthToast(this,"Expense added successfully");
                finish();
            }
        }
    }

    private void showDatePicker(){
        datePickerDialog = DatePickerDialog.newInstance(this, Year, Month-1, Day);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));
        datePickerDialog.setTitle("Track Your Expenses");
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        setDateText(year, monthOfYear+1, dayOfMonth);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.txtDate:
                    showDatePicker();
                    break;
            }
        }
        return false;
    }

    private void setDateText(int year, int month, int day){
        Year = year; Month = month; Day = day;
        mTxtDate.setText(day+"."+month+"."+year);
    }
}
