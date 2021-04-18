package edu.gyj.backend.result;

public class ClassResult {
    private int id;
    private String className;
    private String major;
    private String college;
    private int majorId;
    private int collegeId;

    public ClassResult() {
    }

    public ClassResult(int id, String className, String major, String college, int majorId, int collegeId) {
        this.id = id;
        this.className = className;
        this.major = major;
        this.college = college;
        this.majorId = majorId;
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "ClassResult{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                ", majorId=" + majorId +
                ", collegeId=" + collegeId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
}
