package com.sayali.trackyourexpenses.model;

public class Expense {

    private int eid;
    private String desc;
    private String paymentMode;
    private int amount;
    private int cId;
    private int uId;
    private int day;
    private int month;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public Expense(){}

    public Expense(String desc, String paymentMode, int amount, int cId, int uId, int day, int month, int year){
        this.desc = desc;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.cId = cId;
        this.uId = uId;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Expense(int eid, String desc, String paymentMode, int amount, int cId, int uId, int day, int month, int year){
        this.eid = eid;
        this.desc = desc;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.cId = cId;
        this.uId = uId;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }
}
