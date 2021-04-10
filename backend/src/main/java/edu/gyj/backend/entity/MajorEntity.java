package edu.gyj.backend.entity;

public class MajorEntity {
    private int id;
    private String major;
    private int collegeId;

    public MajorEntity() {
    }

    public MajorEntity(int id, String major, int collegeId) {
        this.id = id;
        this.major = major;
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", major='" + major + '\'' +
                ", collegeId=" + collegeId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }
}
