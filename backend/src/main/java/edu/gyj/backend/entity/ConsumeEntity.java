package edu.gyj.backend.entity;

import java.io.Serializable;
import java.sql.Date;

public class ConsumeEntity implements Serializable {
    private int id;
    private String sid;
    private Date execution_time;
    private float money;
    private int store_id;
    private String mode;

    public ConsumeEntity() {
    }

    public ConsumeEntity(int id, String sid, Date execution_time, float money, int store_id, String mode) {
        this.id = id;
        this.sid = sid;
        this.execution_time = execution_time;
        this.money = money;
        this.store_id = store_id;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Consume{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", execution_time=" + execution_time +
                ", money=" + money +
                ", store_id=" + store_id +
                ", mode='" + mode + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(Date execution_time) {
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
