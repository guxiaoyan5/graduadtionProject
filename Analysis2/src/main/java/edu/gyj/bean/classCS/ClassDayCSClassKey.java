package edu.gyj.bean.classCS;

import java.io.Serializable;
import java.util.Objects;

public class ClassDayCSClassKey implements Serializable {
    private int class_id;
    private int year;
    private int month;
    private int day;

    public ClassDayCSClassKey() {
    }

    public ClassDayCSClassKey(int class_id, int year, int month, int day) {
        this.class_id = class_id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassDayCSClassKey)) return false;
        ClassDayCSClassKey that = (ClassDayCSClassKey) o;
        return getClass_id() == that.getClass_id() && getYear() == that.getYear() && getMonth() == that.getMonth() && getDay() == that.getDay();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass_id(), getYear(), getMonth(), getDay());
    }

    @Override
    public String toString() {
        return "ClassDayCSClassKey{" +
                "class_id=" + class_id +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
