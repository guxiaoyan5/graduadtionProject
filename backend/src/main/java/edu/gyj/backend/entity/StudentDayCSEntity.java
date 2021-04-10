package edu.gyj.backend.entity;

import java.sql.Date;

public class StudentDayCSEntity {
    private int id;
    private String sid;
    private Date day;
    private int consumptionCount;
    private float consumptionTotalMoney;
    private float consumptionAverageMoney;

    public StudentDayCSEntity() {
    }

    public StudentDayCSEntity(int id, String sid, Date day, int consumptionCount, float consumptionTotalMoney, float consumptionAverageMoney) {
        this.id = id;
        this.sid = sid;
        this.day = day;
        this.consumptionCount = consumptionCount;
        this.consumptionTotalMoney = consumptionTotalMoney;
        this.consumptionAverageMoney = consumptionAverageMoney;
    }

    @Override
    public String toString() {
        return "StudentDayCSEntity{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", day=" + day +
                ", consumptionCount=" + consumptionCount +
                ", consumptionTotalMoney=" + consumptionTotalMoney +
                ", consumptionAverageMoney=" + consumptionAverageMoney +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getConsumptionCount() {
        return consumptionCount;
    }

    public void setConsumptionCount(int consumptionCount) {
        this.consumptionCount = consumptionCount;
    }

    public float getConsumptionTotalMoney() {
        return consumptionTotalMoney;
    }

    public void setConsumptionTotalMoney(float consumptionTotalMoney) {
        this.consumptionTotalMoney = consumptionTotalMoney;
    }

    public float getConsumptionAverageMoney() {
        return consumptionAverageMoney;
    }

    public void setConsumptionAverageMoney(float consumptionAverageMoney) {
        this.consumptionAverageMoney = consumptionAverageMoney;
    }
}
