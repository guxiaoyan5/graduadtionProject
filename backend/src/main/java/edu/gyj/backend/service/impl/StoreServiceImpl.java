package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.store.StoreEntity;
import edu.gyj.backend.mapper.store.StoreMapper;
import edu.gyj.backend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreMapper storeMapper;

    @Override
    public List<StoreEntity> getAll() {
        return storeMapper.findAll();
    }

    @Override
    public int update(StoreEntity storeEntity) {
        return storeMapper.updateStore(storeEntity);
    }

    @Override
    public int add(StoreEntity storeEntity) {
        return storeMapper.insertStore(storeEntity);
    }

    @Override
    public int delete(StoreEntity storeEntity) {
        return storeMapper.deleteStore(storeEntity.getId());
    }
}
