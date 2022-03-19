package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.dto.CourseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {

    List<Course> queryCourseList();

    Course queryCourseById(Integer courseId);

    int addCourse(Course course);

    int updateCourseById(Course course);

    int deleteCourseById(Integer courseId);

    Course queryCourserByName(String courseName);

    /**
     * 导入excel中数据
     * @param record
     * @return
     */
    int upload(CourseDTO record);

}
