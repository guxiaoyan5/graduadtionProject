package edu.gyj.backend.entity;

public class StudentUserEntity {
    private String name;
    private String Password;

    public StudentUserEntity() {
    }

    public StudentUserEntity(String name, String password) {
        this.name = name;
        Password = password;
    }

    @Override
    public String toString() {
        return "StudentUser{" +
                "name='" + name + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
