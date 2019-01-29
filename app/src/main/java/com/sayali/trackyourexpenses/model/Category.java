package com.sayali.trackyourexpenses.model;

public class Category {

    private int cId;
    private String categoryDesc;
    private int limit;

    public Category(int cId, String categoryDesc, int limit){
        this.cId = cId;
        this.categoryDesc = categoryDesc;
        this.limit = limit;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
