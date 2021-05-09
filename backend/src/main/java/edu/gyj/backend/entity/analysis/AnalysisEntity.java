package edu.gyj.backend.entity.analysis;

import java.io.Serializable;

public class AnalysisEntity implements Serializable {
    private String sid;
    private double consumption_average_money;
    private int consumption_count;
    private double consumption_total_money;
    private String name;
    private String sex;
    private String className;
    private String college;
    private String major;

    public AnalysisEntity() {
    }

    public AnalysisEntity(String sid,  double consumption_average_money, int consumption_count, double consumption_total_money, String name, String sex, String className, String college, String major) {
        this.sid = sid;
        this.consumption_average_money = consumption_average_money;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.name = name;
        this.sex = sex;
        this.className = className;
        this.college = college;
        this.major = major;
    }

    @Override
    public String toString() {
        return "AnalysisEntity{" +
                "sid='" + sid + '\'' +
                ", consumption_average_money=" + consumption_average_money +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", className='" + className + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public double getConsumption_average_money() {
        return consumption_average_money;
    }

    public void setConsumption_average_money(double consumption_average_money) {
        this.consumption_average_money = consumption_average_money;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
