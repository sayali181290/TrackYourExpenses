package com.sayali.trackyourexpenses.model;

import java.sql.Blob;

public class User {

    private int uId;
    private String fname;
    private String lname;
    private String username;
    private String password;
    private int salary;
    private Blob photo;
    private int limit;

    public User(){}

    public User (int uId, String fname, String lname, String username, String password, int salary, int limit){
        this.uId = uId;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.limit = limit;
    }

    public User (String fname, String lname, String username, String password, int salary, int limit){
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.limit = limit;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
