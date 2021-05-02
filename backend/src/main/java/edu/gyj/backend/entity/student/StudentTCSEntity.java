package edu.gyj.backend.entity.student;

import java.io.Serializable;

public class StudentTCSEntity implements Serializable {
    private String sid;
    private String name;
    private String consumption_category;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;

    public StudentTCSEntity() {
    }

    public StudentTCSEntity(String sid, String name, String consumption_category, int consumption_count, float consumption_total_money, float consumption_average_money) {
        this.sid = sid;
        this.name = name;
        this.consumption_category = consumption_category;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
    }

    @Override
    public String toString() {
        return "StudentTCSEntity{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", consumption_category='" + consumption_category + '\'' +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumption_category() {
        return consumption_category;
    }

    public void setConsumption_category(String consumption_category) {
        this.consumption_category = consumption_category;
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
