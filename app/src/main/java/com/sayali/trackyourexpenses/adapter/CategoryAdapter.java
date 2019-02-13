package com.sayali.trackyourexpenses.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.model.Category;
import com.sayali.trackyourexpenses.ui.EditCategory;

import java.util.ArrayList;
import static com.sayali.trackyourexpenses.util.Constants.CID;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CustomViewHolder> {

    private Context mContext;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context mContext, ArrayList<Category> categories){
        this.mContext = mContext;
        this.categories = categories;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public AppCompatTextView mLabelDesc;
        public AppCompatTextView mLabelLimit;
        public LinearLayout mHeaders;

        public CustomViewHolder(View convertView) {
            super(convertView);
            this.mLabelDesc = (AppCompatTextView) convertView.findViewById(R.id.labelDesc);
            this.mLabelLimit = (AppCompatTextView) convertView.findViewById(R.id.labelLimit);
            this.mHeaders = (LinearLayout) convertView.findViewById(R.id.headers);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View converView =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.each_category, viewGroup, false);

        return new CustomViewHolder(converView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Category category = categories.get(i);
        customViewHolder.mLabelDesc.setText(category.getCategoryDesc());
        customViewHolder.mLabelLimit.setText("Rs. "+category.getLimit());
        if(categories.get(i).getColor() != null)
            customViewHolder.mHeaders.setBackgroundColor(Color.parseColor(categories.get(i).getColor()));

        customViewHolder.mHeaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCategory.class);
                Bundle bundle = new Bundle();
                bundle.putInt(CID, categories.get(i).getcId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void removeItem(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Category item, int position) {
        categories.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Category> getData() {
        return categories;
    }
}
