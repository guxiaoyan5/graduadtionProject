package edu.gyj.backend.entity;

import java.io.Serializable;

public class MealsTime implements Serializable {
    private int hour;
    private int minute;
    private int count;

    public MealsTime() {
    }

    public MealsTime(int hour, int minute, int count) {
        this.hour = hour;
        this.minute = minute;
        this.count = count;
    }

    @Override
    public String toString() {
        return "MealsTime{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", count=" + count +
                '}';
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
