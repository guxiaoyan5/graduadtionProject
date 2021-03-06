package edu.gyj.backend.entity.store;

import java.io.Serializable;

public class StoreEntity implements Serializable {
    private int id;
    private String storeName;

    public StoreEntity() {
    }

    public StoreEntity(String storeName) {
        this.storeName = storeName;
    }

    public StoreEntity(int id, String storeName) {
        this.id = id;
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
