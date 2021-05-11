package edu.bean;

import java.io.Serializable;
import java.sql.Date;

public class StudentLevelMonthCSBean implements Serializable {
    private String sid;
    private int id;
    private int year;
    private int month;
    private double consumption_total_money;

    public StudentLevelMonthCSBean() {
    }

    public StudentLevelMonthCSBean(String sid, int id, int year, int month, double consumption_total_money) {
        this.sid = sid;
        this.id = id;
        this.year = year;
        this.month = month;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StudentLevelMonthCSBean{" +
                "sid='" + sid + '\'' +
                ", id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getConsumption_total_money() {
        return consumption_total_money;
    }

    public void setConsumption_total_money(double consumption_total_money) {
        this.consumption_total_money = consumption_total_money;
    }
}
