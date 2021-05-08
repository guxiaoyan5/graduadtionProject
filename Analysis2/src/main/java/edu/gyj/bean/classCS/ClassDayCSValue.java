package edu.gyj.bean.classCS;

import java.io.Serializable;

public class ClassDayCSValue implements Serializable {
    private String sid;
    private double money;
    private int count;
    private double average;
    private int low;
    private int high;
    public ClassDayCSValue() {
    }

    public ClassDayCSValue(double money) {
        this.money = money;
    }

    public ClassDayCSValue(double money, int count) {
        this.money = money;
        this.count = count;
    }

    public ClassDayCSValue(double money, int low, int high) {
        this.money = money;
        this.low = low;
        this.high = high;
    }

    public ClassDayCSValue(double money, int count, double average, int low, int high) {
        this.money = money;
        this.count = count;
        this.average = average;
        this.low = low;
        this.high = high;
    }

    public ClassDayCSValue(double money, int count, double average) {
        this.money = money;
        this.count = count;
        this.average = average;
    }

    public ClassDayCSValue(String sid, double money) {
        this.sid = sid;
        this.money = money;
    }

    public ClassDayCSValue(String sid, double money, int count) {
        this.sid = sid;
        this.money = money;
        this.count = count;
    }

    public ClassDayCSValue(String sid, double money, int count, double average) {
        this.sid = sid;
        this.money = money;
        this.count = count;
        this.average = average;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "ClassDayCSValue{" +
                "sid='" + sid + '\'' +
                ", money=" + money +
                ", count=" + count +
                ", average=" + average +
                ", low=" + low +
                ", high=" + high +
                '}';
    }

    public ClassDayCSValue(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
