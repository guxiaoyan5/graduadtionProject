package edu.gyj.backend.result;

public class MajorResult {
    private int id;
    private String major;
    private String college;
    private int collegeId;

    public MajorResult() {
    }

    public MajorResult(int id, String major, String college, int collegeId) {
        this.id = id;
        this.major = major;
        this.college = college;
        this.collegeId = collegeId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MajorResult{" +
                "id=" + id +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                ", collegeId=" + collegeId +
                '}';
    }
}
