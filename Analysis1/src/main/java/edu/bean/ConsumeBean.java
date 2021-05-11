package edu.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class ConsumeBean implements Serializable {
    private String sid;
    private Timestamp execution_time;
    private double money;
    private int store_id;
    private String mode;

    public ConsumeBean() {
    }

    public ConsumeBean(String sid, Timestamp execution_time, double money, int store_id, String mode) {
        this.sid = sid;
        this.execution_time = execution_time;
        this.money = money;
        this.store_id = store_id;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "ConsumeBean{" +
                "sid='" + sid + '\'' +
                ", execution_time=" + execution_time +
                ", money=" + money +
                ", store_id=" + store_id +
                ", mode='" + mode + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Timestamp getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(Timestamp execution_time) {
        this.execution_time = execution_time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
