package edu.gyj.graduate.entity;

import java.io.Serializable;

public class StoreUser implements Serializable {
    private String name;
    private String password;
    private int storeId;

    public StoreUser() {
    }

    public StoreUser(String name, String password, int storeId) {
        this.name = name;
        this.password = password;
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "StoreUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", storeId=" + storeId +
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

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
