package edu.gyj.backend.entity;

import org.springframework.stereotype.Repository;

import java.io.Serializable;


public class AdminUserEntity implements Serializable {
    private String id;
    private String password;

    public AdminUserEntity() {
    }

    public AdminUserEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
