package edu.gyj.graduate.entity;

import java.io.Serializable;

public class SchoolUser implements Serializable {
    private String name;
    private String password;

    public SchoolUser() {
    }

    public SchoolUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SchoolUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
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
