package edu.gyj.backend.entity.student;

import java.io.Serializable;
import java.sql.Date;

public class StudentDayTCSEntity implements Serializable {
    private String sid;
    private String name;
    private String consumption_category;
    private Date day;
    private int count;
    private float totalMoney;
    private float averageMoney;

    public StudentDayTCSEntity() {
    }

    public StudentDayTCSEntity(String sid, String name, String consumption_category, Date day, int count, float totalMoney, float averageMoney) {
        this.sid = sid;
        this.name = name;
        this.consumption_category = consumption_category;
        this.day = day;
        this.count = count;
        this.totalMoney = totalMoney;
        this.averageMoney = averageMoney;
    }

    @Override
    public String toString() {
        return "StudentDayTCSEntity{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", consumption_category='" + consumption_category + '\'' +
                ", day=" + day +
                ", count=" + count +
                ", totalMoney=" + totalMoney +
                ", averageMoney=" + averageMoney +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumption_category() {
        return consumption_category;
    }

    public void setConsumption_category(String consumption_category) {
        this.consumption_category = consumption_category;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(float averageMoney) {
        this.averageMoney = averageMoney;
    }
}
