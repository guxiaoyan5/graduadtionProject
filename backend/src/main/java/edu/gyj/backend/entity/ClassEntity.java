package edu.gyj.backend.entity;

public class ClassEntity {
    private int id;
    private String name;
    private int classId;

    public ClassEntity(int id, String name, int classId) {
        this.id = id;
        this.name = name;
        this.classId = classId;
    }

    public ClassEntity() {
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classId=" + classId +
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
