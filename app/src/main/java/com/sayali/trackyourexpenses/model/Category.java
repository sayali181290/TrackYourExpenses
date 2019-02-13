package com.sayali.trackyourexpenses.model;

public class Category {

    private int cId = 0;
    private String categoryDesc;
    private int limit;

    public int getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(int totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    private int totalExpenditure;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color;

    public Category(){}

    public Category(int cId, String categoryDesc, int limit, String color){
        this.cId = cId;
        this.categoryDesc = categoryDesc;
        this.limit = limit;
        this.color = color;
    }

    public Category(String categoryDesc, int limit, String color){
        this.cId = cId;
        this.categoryDesc = categoryDesc;
        this.limit = limit;
        this.color = color;
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
