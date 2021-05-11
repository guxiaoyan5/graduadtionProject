package edu.bean;

import java.io.Serializable;
import java.sql.Date;

public class StudentDayTCSBean implements Serializable {
    private String sid;
    private Date day;
    private String consumption_category;
    private double consumption_total_money;

    public StudentDayTCSBean() {
    }

    public StudentDayTCSBean(String sid, Date day, String consumption_category, double consumption_total_money) {
        this.sid = sid;
        this.day = day;
        this.consumption_category = consumption_category;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "studentDayTCSBean{" +
                "sid='" + sid + '\'' +
                ", day=" + day +
                ", consumption_category='" + consumption_category + '\'' +
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
}
