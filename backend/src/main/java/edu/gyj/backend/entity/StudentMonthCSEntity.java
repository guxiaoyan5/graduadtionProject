package edu.gyj.backend.entity;

public class StudentMonthCSEntity {
    private int id;
    private String sid;
    private int month;
    private int year;
    private int consumptionCount;
    private float consumptionTotalMoney;
    private float consumptionAverageMoney;

    public StudentMonthCSEntity() {
    }

    public StudentMonthCSEntity(int id, String sid, int month, int year, int consumptionCount, float consumptionTotalMoney, float consumptionAverageMoney) {
        this.id = id;
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.consumptionCount = consumptionCount;
        this.consumptionTotalMoney = consumptionTotalMoney;
        this.consumptionAverageMoney = consumptionAverageMoney;
    }

    @Override
    public String toString() {
        return "StudentMonthCSEntity{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
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
