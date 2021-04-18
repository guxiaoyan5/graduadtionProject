package edu.gyj.backend.entity.student;

import java.io.Serializable;

public class StudentEntity implements Serializable {
    private String id;
    private String name;
    private int classId;
    private int majorId;
    private int collegeId;
    private String sex;

    public StudentEntity() {
    }

    public StudentEntity(String id, String name, int classId, int majorId, int collegeId, String sex) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.majorId = majorId;
        this.collegeId = collegeId;
        this.sex = sex;
    }

    public StudentEntity(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", classId=" + classId +
                ", majorId=" + majorId +
                ", collegeId=" + collegeId +
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
