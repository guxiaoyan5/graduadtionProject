package edu.gyj.graduate.mapper;

import edu.gyj.graduate.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import java.util.List;
@Mapper
@Service
public interface UserMapper {
    User findById(Long id);
    List<User> findAll();
}
