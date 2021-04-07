package edu.gyj.graduate.mapper;

import edu.gyj.graduate.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface AdminUserMapper {
    AdminUser findByName(String name);
}
