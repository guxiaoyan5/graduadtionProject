package edu.gyj.backend.entity;

public class CollegeEntity {
    private int id;
    private String college;

    public CollegeEntity() {
    }

    public CollegeEntity(int id, String college) {
        this.id = id;
        this.college = college;
    }

    @Override
    public String toString() {
        return "College{" +
                "id=" + id +
                ", college='" + college + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
