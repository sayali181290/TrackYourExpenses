package com.sayali.trackyourexpenses.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.adapter.CategoryAdapter;
import com.sayali.trackyourexpenses.adapter.CategoryExpAdapter;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.model.User;
import com.sayali.trackyourexpenses.util.AppPreferences;

import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sayali.trackyourexpenses.util.Constants.USER_ID;

public class DashboardScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.appBar)
    View mAppBar;

    @BindView(R.id.contentLayout)
    View mContentLayout;

    @BindView(R.id.progressView)
    PieChart mProgressView;

    @BindView(R.id.fabAdd)
    FloatingActionButton mFabAdd;

    @BindView(R.id.categoryExpList)
    RecyclerView mCategoryExpList;

    private TextView mLabelName, mLabelEmail;
    private View headerView;
    private User user;
    private ArrayList<Category> categories;
    private CategoryExpAdapter mCategoryExpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);
        ButterKnife.bind(this);

        initUser();
     }

    private void initUI(){
        initProgressView();
        initToolbar();
        initDrawer();
        initHeader();
    }

    private void initProgressView(){
        mProgressView.setUsePercentValues(true);
        mProgressView.setDrawHoleEnabled(false);
        mProgressView.getLegend().setEnabled(false);
        mProgressView.getDescription().setEnabled(false);

        categories = DBManager.getAllCategoriesWithExpenditure();

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        int myColors[] = new int[30];
        float remaining = 0;
        for (int i=0; i<categories.size();i++){
            yvalues.add(new PieEntry(((float)categories.get(i).getLimit()/user.getSalary())*100, i));
            myColors[i] = Color.parseColor(categories.get(i).getColor());
            remaining += ((float)categories.get(i).getLimit()/user.getSalary())*100;
        }

        myColors[categories.size()] = Color.parseColor("#c0c0c0");
        yvalues.add(new PieEntry(100-remaining, categories.size()));

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

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }

    private void initHeader(){
        mFabAdd.setOnClickListener(this);
    }

    private void initDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        mLabelName = (TextView) headerView.findViewById(R.id.labelName);
        mLabelEmail = (TextView) headerView.findViewById(R.id.labelEmail);

        if(user != null){
            mLabelName.setText(user.getFname()+" "+user.getLname());
            mLabelEmail.setText(user.getUsername());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
        categories = DBManager.getAllCategoriesWithExpenditure();
        initList();
    }

    private void initList(){
        if(categories.size() != 0){
            mCategoryExpList.setLayoutManager(new LinearLayoutManager(this));
            mCategoryExpAdapter = new CategoryExpAdapter(this, categories);
            mCategoryExpList.setAdapter(mCategoryExpAdapter);
        }
    }

    private void initUser(){
        user = DBManager.getUser(AppPreferences.getInt(this, USER_ID));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_screen, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_category :
                startActivity(new Intent(this, DisplayCategory.class));
                break;

            case R.id.nav_expense :
                startActivity(new Intent(this, DisplayExpense.class));
                break;

            case R.id.nav_csv_report :
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fabAdd :
                startActivity(new Intent(this, AddExpense.class));
                break;
        }
    }
}
