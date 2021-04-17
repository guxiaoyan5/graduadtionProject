package edu.gyj.backend.entity.college;

import java.io.Serializable;

public class CollegeEntity implements Serializable {
    private int id;
    private String college;

    public CollegeEntity() {
    }

    public CollegeEntity(String college) {
        this.college = college;
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
