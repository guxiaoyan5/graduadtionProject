package edu.gyj.backend.result;

import java.io.Serializable;
import java.util.Date;

public class ConsumeDayData implements Serializable {
    private Date date;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    private float consumption_student_average_money;
    private int student_count;
    private int consumption_low_count;
    private int consumption_high_count;
    private int student_low_count;
    private int student_high_count;

    public ConsumeDayData() {
    }

    public ConsumeDayData(Date date, int consumption_count, float consumption_total_money, float consumption_average_money, float consumption_student_average_money, int student_count, int consumption_low_count, int consumption_high_count, int student_low_count, int student_high_count) {
        this.date = date;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.consumption_student_average_money = consumption_student_average_money;
        this.student_count = student_count;
        this.consumption_low_count = consumption_low_count;
        this.consumption_high_count = consumption_high_count;
        this.student_low_count = student_low_count;
        this.student_high_count = student_high_count;
    }

    @Override
    public String toString() {
        return "ConsumeDayData{" +
                "date=" + date +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                ", consumption_student_average_money=" + consumption_student_average_money +
                ", student_count=" + student_count +
                ", consumption_low_count=" + consumption_low_count +
                ", consumption_high_count=" + consumption_high_count +
                ", student_low_count=" + student_low_count +
                ", student_high_count=" + student_high_count +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getConsumption_low_count() {
        return consumption_low_count;
    }

    public void setConsumption_low_count(int consumption_low_count) {
        this.consumption_low_count = consumption_low_count;
    }

    public int getConsumption_high_count() {
        return consumption_high_count;
    }

    public void setConsumption_high_count(int consumption_high_count) {
        this.consumption_high_count = consumption_high_count;
    }

    public int getStudent_low_count() {
        return student_low_count;
    }

    public void setStudent_low_count(int student_low_count) {
        this.student_low_count = student_low_count;
    }

    public int getStudent_high_count() {
        return student_high_count;
    }

    public void setStudent_high_count(int student_high_count) {
        this.student_high_count = student_high_count;
    }
}
