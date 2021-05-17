package edu.gyj.bean.student;

import java.io.Serializable;

public class StudentMonthCS implements Serializable {
    private String sid;
    private int month;
    private int year;
    private int count;
    private double consumption_total_money;

    public StudentMonthCS() {
    }

    public StudentMonthCS(String sid, int month, int year, int count, double consumption_total_money) {
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.count = count;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StudentMonthCS{" +
                "sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", count=" + count +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public double getConsumption_total_money() {
        return consumption_total_money;
    }

    public void setConsumption_total_money(double consumption_total_money) {
        this.consumption_total_money = consumption_total_money;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
