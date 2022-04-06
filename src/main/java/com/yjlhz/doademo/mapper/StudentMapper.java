package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: StudentMapper
 * @projectName doademo
 * @description: 学生信息表dao层接口
 * @date 2022/1/27 17:22
 */
@Mapper
@Repository
public interface StudentMapper {
    List<Student> queryStudentList();

    Student queryStudentById(String sNum);

    int addStudent(Student student);

    List<Student> queryStudentByPlanId(String major,Integer grade);

    int updateStudentById(Student student);

    int deleteStudentById(String sNum);

}
