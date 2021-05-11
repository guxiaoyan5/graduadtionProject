package edu.bean;

import java.io.Serializable;
import java.sql.Date;

public class StudentLevelDayTCSBean implements Serializable {
    private String sid;
    private int id;
    private Date day;
    private String consumption_category;
    private double consumption_total_money;

    public StudentLevelDayTCSBean() {
    }

    public StudentLevelDayTCSBean(String sid, int id, Date day, String consumption_category, double consumption_total_money) {
        this.sid = sid;
        this.id = id;
        this.day = day;
        this.consumption_category = consumption_category;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StudentLevelDayTCSBean{" +
                "sid='" + sid + '\'' +
                ", id=" + id +
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
