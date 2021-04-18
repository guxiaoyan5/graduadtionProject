package edu.gyj.backend.entity.classCS;

import java.io.Serializable;

public class ClassEntity implements Serializable {
    private int id;
    private String name;
    private int majorId;

    public ClassEntity(int id, String name, int majorId) {
        this.id = id;
        this.name = name;
        this.majorId = majorId;
    }

    public ClassEntity(int id) {
        this.id = id;
    }

    public ClassEntity(String name, int majorId) {
        this.name = name;
        this.majorId = majorId;
    }

    public ClassEntity() {
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classId=" + majorId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }
}
