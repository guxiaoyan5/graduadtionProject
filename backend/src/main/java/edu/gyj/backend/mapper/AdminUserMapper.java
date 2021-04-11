package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminUserMapper {
    AdminUserEntity findById(String id);

    void updatePassword(AdminUserEntity adminUserEntity);
}
