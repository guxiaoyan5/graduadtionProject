package edu.gyj.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Consume implements Serializable {
    private String sid;
    private Timestamp execution_time;
    private float money;
    private int store_id;
    private String mode;

    public Consume() {
    }

    public Consume(String sid, Timestamp execution_time, float money) {
        this.sid = sid;
        this.execution_time = execution_time;
        this.money = money;
    }

    public Consume(String sid, Timestamp execution_time, float money, int store_id, String mode) {
        this.sid = sid;
        this.execution_time = execution_time;
        this.money = money;
        this.store_id = store_id;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Consume{" +
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
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
