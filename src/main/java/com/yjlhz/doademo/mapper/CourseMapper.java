package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {

    List<Course> queryCourseList();

}
