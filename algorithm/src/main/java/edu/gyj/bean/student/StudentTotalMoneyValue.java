package edu.gyj.bean.student;

import scala.Serializable;

public class StudentTotalMoneyValue implements Serializable {
    private int consumption_count;
    private double consumption_total_money;

    public StudentTotalMoneyValue() {
    }

    public StudentTotalMoneyValue(int consumption_count, double consumption_total_money) {
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "studentTotalMoneyValue{" +
                "consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    public int getConsumption_count() {
        return consumption_count;
    }

    public void setConsumption_count(int consumption_count) {
        this.consumption_count = consumption_count;
    }

    public double getConsumption_total_money() {
        return consumption_total_money;
    }

    public void setConsumption_total_money(double consumption_total_money) {
        this.consumption_total_money = consumption_total_money;
    }


}
