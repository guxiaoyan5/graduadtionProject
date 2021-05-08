package edu.gyj.bean.classCS;

import java.sql.Timestamp;

public class ClassDayCSInputData {
    private String id;
    private int class_id;
    private Timestamp execution_time;
    private double money;

    public ClassDayCSInputData() {
    }

    public ClassDayCSInputData(String id, int class_id, Timestamp execution_time, double money) {
        this.id = id;
        this.class_id = class_id;
        this.execution_time = execution_time;
        this.money = money;
    }

    @Override
    public String toString() {
        return "ClassDayCSInputData{" +
                "id='" + id + '\'' +
                ", class_id=" + class_id +
                ", execution_time=" + execution_time +
                ", money=" + money +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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
}
