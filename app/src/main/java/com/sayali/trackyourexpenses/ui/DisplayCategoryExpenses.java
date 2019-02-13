package com.sayali.trackyourexpenses.ui;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.adapter.CategoryAdapter;
import com.sayali.trackyourexpenses.adapter.CategorywiseExpenseAdapter;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.model.Expense;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sayali.trackyourexpenses.util.Constants.CID;

public class DisplayCategoryExpenses extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.progressView)
    PieChart mProgressView;

    @BindView(R.id.categoryExpList)
    RecyclerView mCategoryExpList;

    @BindView(R.id.labelNoData)
    AppCompatTextView mLabelNoData;

    @BindView(R.id.btnBack)
    AppCompatImageView mBtnBack;

    @BindView(R.id.labelTitle)
    AppCompatTextView mLabelTitle;

    private int categoryId;
    private ArrayList<Expense> expenses;
    private Category category;
    private CategorywiseExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category_expenses);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey(CID)){
            categoryId = bundle.getInt(CID);
        }
        initUI();
    }

    private void initUI(){
        initList();
        initProgressView();
        setClickListeners();
    }

    private void initList(){
        expenses = DBManager.getExpenseFromCategoryForCurrentMonth(categoryId);
        category = DBManager.getCategory(categoryId);

        mLabelTitle.setText(category.getCategoryDesc());

        if(expenses.size() != 0){
            mCategoryExpList.setVisibility(View.VISIBLE);
            mLabelNoData.setVisibility(View.GONE);
            mCategoryExpList.setLayoutManager(new LinearLayoutManager(this));
            expenseAdapter = new CategorywiseExpenseAdapter(this, expenses, category);
            mCategoryExpList.setAdapter(expenseAdapter);
        }else{
            mCategoryExpList.setVisibility(View.GONE);
            mLabelNoData.setVisibility(View.VISIBLE);
            mLabelNoData.setText("You've spent Rs.0 on "+category.getCategoryDesc());
        }
    }

    private void initProgressView(){
        mProgressView.setUsePercentValues(true);
        mProgressView.setDrawHoleEnabled(false);
        mProgressView.getLegend().setEnabled(false);
        mProgressView.getDescription().setEnabled(false);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        int myColors[] = new int[30];
        float remaining = 0;
        for (int i=0; i<expenses.size();i++){
             remaining += ((float)expenses.get(i).getAmount()/category.getLimit())*100;
        }
        yvalues.add(new PieEntry(remaining, 0));
        myColors[0] = Color.parseColor(category.getColor());
        myColors[1] = Color.parseColor("#c0c0c0");
        yvalues.add(new PieEntry(100-remaining, 1));

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: myColors) colors.add(c);

        PieDataSet dataSet = new PieDataSet(yvalues, "CATEGORY DESTRIBUTION");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15);
        data.setValueTextColor(Color.parseColor("#ffffff"));
        mProgressView.setData(data);
    }

    private void setClickListeners(){
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnBack :
                onBackPressed();
                break;
        }
    }
}
