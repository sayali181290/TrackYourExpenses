package com.sayali.trackyourexpenses.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.adapter.CategoryAdapter;
import com.sayali.trackyourexpenses.customviews.SwipeToDeleteCallback;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayCategory extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.listCategories)
    RecyclerView mListCategories;

    @BindView(R.id.labelNoCat)
    AppCompatTextView mLabelNoCat;

    @BindView(R.id.btnAddCat)
    AppCompatButton mBtnAddCat;

    @BindView(R.id.btnBack)
    AppCompatImageView mBtnBack;

    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);
        ButterKnife.bind(this);

        setClickListeners();
    }

    private void setClickListeners(){
        mBtnAddCat.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddCat :
                startActivity(new Intent(this, AddCategory.class));
                break;

            case R.id.btnBack :
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        categories = DBManager.getCategories();
        initList();
    }

    private void initList(){
        if(categories.size() != 0){
            mListCategories.setVisibility(View.VISIBLE);
            mLabelNoCat.setVisibility(View.GONE);
            mListCategories.setLayoutManager(new LinearLayoutManager(this));
            categoryAdapter = new CategoryAdapter(this, categories);
            mListCategories.setAdapter(categoryAdapter);

            enableSwipeToDeleteAndUndo();
        }else{
            mListCategories.setVisibility(View.GONE);
            mLabelNoCat.setVisibility(View.VISIBLE);
        }
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                if(DBManager.deleteCategory(categories.get(position).getcId())){
                   Utils.showLongLengthToastMessage(getApplicationContext(), "Category : "+categories.get(position).getCategoryDesc()+" deleted successfully");
                   categories.remove(position);
                   categoryAdapter.notifyDataSetChanged();
                }else
                   Utils.showAlertDialog(getApplicationContext(), "Error", "Unable to delete category from database");
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mListCategories);
    }
}
