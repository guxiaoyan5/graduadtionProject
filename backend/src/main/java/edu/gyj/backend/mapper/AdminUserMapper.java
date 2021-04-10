package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper {
    AdminUserEntity findById(String id);
}
