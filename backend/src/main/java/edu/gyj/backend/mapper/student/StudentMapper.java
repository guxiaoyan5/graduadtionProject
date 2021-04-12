package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    List<StudentEntity> findAll();
    StudentEntity findById(String id);
    List<StudentEntity> findByClassId(int classId);
    List<StudentEntity> findByMajorId(int majorId);
    List<StudentEntity> findByCollegeId(int collegeId);
    int insertStudent(StudentEntity studentEntity);
    int updateStudent(StudentEntity studentEntity);
    int deleteStudent(String id);
}
