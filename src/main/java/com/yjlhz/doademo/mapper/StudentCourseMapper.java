package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.PlanRequirement;
import com.yjlhz.doademo.pojo.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: StudentCourseMapper
 * @projectName doademo
 * @description: 学生选课表dao层接口
 * @date 2022/1/27 17:23
 */

@Mapper
@Repository
public interface StudentCourseMapper {

    List<Integer> queryCourseByStuId(Integer sNum);

    int addStudentCourse(StudentCourse studentCourse);

    List<Integer> queryStuByCourseId(Integer courseId);

    int deleteStudentCourseById(Integer id);
}
