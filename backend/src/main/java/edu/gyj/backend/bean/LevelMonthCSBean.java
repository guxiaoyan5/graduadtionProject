package edu.gyj.backend.bean;

import java.io.Serializable;

public class LevelMonthCSBean implements Serializable {
    private int id;
    private int year;
    private int month;
    private String name;
    private double consumption_total_money;
    private double consumption_average_money;
    private double max;
    private double min;
    private int count;
    private int high;
    private int low;

    public LevelMonthCSBean(int id, int year, int month, String name, double consumption_total_money, double consumption_average_money, double max, double min, int count, int high, int low) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.name = name;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.max = max;
        this.min = min;
        this.count = count;
        this.high = high;
        this.low = low;
    }

    public LevelMonthCSBean() {
    }

    @Override
    public String toString() {
        return "LevelMonthCSBean{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", name='" + name + '\'' +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                ", high=" + high +
                ", low=" + low +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getConsumption_average_money() {
        return consumption_average_money;
    }

    public void setConsumption_average_money(double consumption_average_money) {
        this.consumption_average_money = consumption_average_money;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }
}
