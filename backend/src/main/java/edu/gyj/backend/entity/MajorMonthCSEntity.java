package edu.gyj.backend.entity;

public class MajorMonthCSEntity {
    private int id;
    private int majorId;
    private int month;
    private int year;
    private int consumptionCount;
    private float consumptionTotalMoney;
    private float consumptionAverageMoney;
    private float consumptionStudentAverageMoney;
    private int studentCount;

    public MajorMonthCSEntity() {
    }

    public MajorMonthCSEntity(int id, int majorId, int month, int year, int consumptionCount, float consumptionTotalMoney, float consumptionAverageMoney, float consumptionStudentAverageMoney, int studentCount) {
        this.id = id;
        this.majorId = majorId;
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
        return "MajorMonthCSEntity{" +
                "id=" + id +
                ", majorId=" + majorId +
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

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
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
