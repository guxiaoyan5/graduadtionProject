package edu.gyj.bean.student;

import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    private String name;
    private int class_id;
    private int major_id;
    private int college_id;
    private String sex;

    public Student() {
    }

    public Student(String id, int class_id) {
        this.id = id;
        this.class_id = class_id;
    }

    public Student(String id, String name, int class_id, int major_id, int college_id, String sex) {
        this.id = id;
        this.name = name;
        this.class_id = class_id;
        this.major_id = major_id;
        this.college_id = college_id;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", class_id=" + class_id +
                ", major_id=" + major_id +
                ", college_id=" + college_id +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
