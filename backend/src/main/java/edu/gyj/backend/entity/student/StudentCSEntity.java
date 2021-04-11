package edu.gyj.backend.entity.student;

import java.io.Serializable;

public class StudentCSEntity implements Serializable {
    private String sid;
    private String name;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    //private int consumption_low_count;
    //private int consumption_high_count;

    public StudentCSEntity() {
    }

    public StudentCSEntity(String sid, String name, int consumption_count, float consumption_total_money, float consumption_average_money) {
        this.sid = sid;
        this.name = name;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
    }

    @Override
    public String toString() {
        return "StudentCSEntity{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getConsumption_count() {
        return consumption_count;
    }

    public void setConsumption_count(int consumption_count) {
        this.consumption_count = consumption_count;
    }

    public float getConsumption_total_money() {
        return consumption_total_money;
    }

    public void setConsumption_total_money(float consumption_total_money) {
        this.consumption_total_money = consumption_total_money;
    }

    public float getConsumption_average_money() {
        return consumption_average_money;
    }

    public void setConsumption_average_money(float consumption_average_money) {
        this.consumption_average_money = consumption_average_money;
    }
}
