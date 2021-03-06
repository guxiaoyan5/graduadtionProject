package edu.gyj.backend.entity;

public class SchoolUserEntity {
    private String id;
    private String name;
    private String password;

    public SchoolUserEntity() {
    }

    public SchoolUserEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public SchoolUserEntity(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SchoolUserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
