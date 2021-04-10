package edu.gyj.backend.entity;

public class StoreMonthCSEntity {
    private int id;
    private String storeId;
    private int month;
    private int year;
    private int consumptionCount;
    private float consumptionTotalMoney;
    private float consumptionAverageMoney;
    private float consumptionStudentAverageMoney;
    private int studentCount;

    public StoreMonthCSEntity() {
    }

    public StoreMonthCSEntity(int id, String storeId, int month, int year, int consumptionCount, float consumptionTotalMoney, float consumptionAverageMoney, float consumptionStudentAverageMoney, int studentCount) {
        this.id = id;
        this.storeId = storeId;
        this.month = month;
        this.year = year;
        this.consumptionCount = consumptionCount;
        this.consumptionTotalMoney = consumptionTotalMoney;
        this.consumptionAverageMoney = consumptionAverageMoney;
        this.consumptionStudentAverageMoney = consumptionStudentAverageMoney;
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "StoreMonthCSEntity{" +
                "id=" + id +
                ", storeId='" + storeId + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", consumptionCount=" + consumptionCount +
                ", consumptionTotalMoney=" + consumptionTotalMoney +
                ", consumptionAverageMoney=" + consumptionAverageMoney +
                ", consumptionStudentAverageMoney=" + consumptionStudentAverageMoney +
                ", studentCount=" + studentCount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public float getConsumptionStudentAverageMoney() {
        return consumptionStudentAverageMoney;
    }

    public void setConsumptionStudentAverageMoney(float consumptionStudentAverageMoney) {
        this.consumptionStudentAverageMoney = consumptionStudentAverageMoney;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
}
