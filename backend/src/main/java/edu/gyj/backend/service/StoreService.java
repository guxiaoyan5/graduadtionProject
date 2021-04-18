package edu.gyj.backend.service;

import edu.gyj.backend.entity.store.StoreEntity;

import java.util.List;

public interface StoreService {
    public List<StoreEntity> getAll();
    public int update(StoreEntity storeEntity);
    public int add(StoreEntity storeEntity);
    public int delete(StoreEntity storeEntity);
}
