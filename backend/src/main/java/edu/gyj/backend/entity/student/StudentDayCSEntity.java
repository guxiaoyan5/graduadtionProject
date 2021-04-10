package edu.gyj.backend.entity.student;

import java.io.Serializable;
import java.sql.Date;

public class StudentDayCSEntity implements Serializable {
    private String sid;
    private Date day;
    private int count;
    private float totalMoney;
    private float averageMoney;
    //private int consumption_low_count;
    //private int consumption_high_count;

    public StudentDayCSEntity() {
    }

    public StudentDayCSEntity(String sid, Date day, int count, float totalMoney, float averageMoney) {
        this.sid = sid;
        this.day = day;
        this.count = count;
        this.totalMoney = totalMoney;
        this.averageMoney = averageMoney;
    }

    @Override
    public String toString() {
        return "StudentDayCSEntity{" +
                "sid='" + sid + '\'' +
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
