package edu.bean;

import java.io.Serializable;

public class LevelMonthTCSBean implements Serializable {
    private int id;
    private int year;
    private int month;
    private String consumption_category;
    private double consumption_total_money;
    private double consumption_average_money;
    private double max;
    private double min;
    private int count;
    private int high;
    private int low;

    public LevelMonthTCSBean() {
    }

    public LevelMonthTCSBean(int id, int year, int month, String consumption_category, double consumption_total_money, double consumption_average_money, double max, double min, int count, int high, int low) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.consumption_category = consumption_category;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.max = max;
        this.min = min;
        this.count = count;
        this.high = high;
        this.low = low;
    }

    @Override
    public String toString() {
        return "LevelMonthTCSBean{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", consumption_category='" + consumption_category + '\'' +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                ", high=" + high +
                ", low=" + low +
                '}';
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

    public String getConsumption_category() {
        return consumption_category;
    }

    public void setConsumption_category(String consumption_category) {
        this.consumption_category = consumption_category;
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
