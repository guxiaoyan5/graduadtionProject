package edu.bean;

import java.io.Serializable;

public class StudentMonthCSBean implements Serializable {
    private String sid;
    private int year;
    private int month;
    private double consumption_total_money;

    public StudentMonthCSBean() {
    }

    public StudentMonthCSBean(String sid, int year, int month, double consumption_total_money) {
        this.sid = sid;
        this.year = year;
        this.month = month;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StudentMonthCSBean{" +
                "sid='" + sid + '\'' +
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
