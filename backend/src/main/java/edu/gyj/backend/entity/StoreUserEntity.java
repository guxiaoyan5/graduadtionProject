package edu.gyj.backend.entity;

public class StoreUserEntity {
    private String name;
    private int id;
    private String Password;

    public StoreUserEntity() {
    }

    public StoreUserEntity(String name, int id, String password) {
        this.name = name;
        this.id = id;
        Password = password;
    }

    @Override
    public String toString() {
        return "StoreUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", Password='" + Password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
