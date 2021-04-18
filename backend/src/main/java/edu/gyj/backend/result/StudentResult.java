package edu.gyj.backend.result;

public class StudentResult {
    private String sid;
    private String name;
    private String className;
    private String major;
    private String college;
    private String sex;
    private int classId;
    private int majorId;
    private int collegeId;

    public StudentResult() {
    }

    public StudentResult(String sid, String name, String className, String major, String college, String sex, int classId, int majorId, int collegeId) {
        this.sid = sid;
        this.name = name;
        this.className = className;
        this.major = major;
        this.college = college;
        this.sex = sex;
        this.classId = classId;
        this.majorId = majorId;
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "StudentResult{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                ", sex='" + sex + '\'' +
                ", classId=" + classId +
                ", majorId=" + majorId +
                ", collegeId=" + collegeId +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
}
