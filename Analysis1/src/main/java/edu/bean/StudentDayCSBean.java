package edu.bean;

import java.io.Serializable;
import java.sql.Date;

public class StudentDayCSBean implements Serializable {
    private String sid;
    private Date day;
    private double consumption_total_money;

    public StudentDayCSBean() {
    }

    public StudentDayCSBean(String sid, Date day, double consumption_total_money) {
        this.sid = sid;
        this.day = day;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StudentDayCSBean{" +
                "sid='" + sid + '\'' +
                ", day=" + day +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public double getConsumption_total_money() {
        return consumption_total_money;
    }

    public void setConsumption_total_money(double consumption_total_money) {
        this.consumption_total_money = consumption_total_money;
    }
}
