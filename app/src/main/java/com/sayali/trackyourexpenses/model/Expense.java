package com.sayali.trackyourexpenses.model;

public class Expense {

    private int eid;
    private String desc;
    private String paymentMode;
    private int amount;
    private int cId;
    private int uId;

    public Expense(int eid, String desc, String paymentMode, int amount, int cId, int uId){
        this.eid = eid;
        this.desc = desc;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.cId = cId;
        this.uId = uId;
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
