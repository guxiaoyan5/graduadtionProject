package edu.gyj.backend.entity.store;

import java.io.Serializable;

public class StoreMonthCSEntity implements Serializable {
    private int store_id;
    private int month;
    private int year;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    private float consumption_student_average_money;
    private int student_count;

    public StoreMonthCSEntity() {
    }

    public StoreMonthCSEntity(int store_id, int month, int year, int consumption_count, float consumption_total_money, float consumption_average_money, float consumption_student_average_money, int student_count) {
        this.store_id = store_id;
        this.month = month;
        this.year = year;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.consumption_student_average_money = consumption_student_average_money;
        this.student_count = student_count;
    }

    @Override
    public String toString() {
        return "StoreMonthCSEntity{" +
                "store_id=" + store_id +
                ", month=" + month +
                ", year=" + year +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                ", consumption_student_average_money=" + consumption_student_average_money +
                ", student_count=" + student_count +
                '}';
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
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

    public float getConsumption_student_average_money() {
        return consumption_student_average_money;
    }

    public void setConsumption_student_average_money(float consumption_student_average_money) {
        this.consumption_student_average_money = consumption_student_average_money;
    }

    public int getStudent_count() {
        return student_count;
    }

    public void setStudent_count(int student_count) {
        this.student_count = student_count;
    }
}
