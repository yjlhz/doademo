package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/queryCourseList")
    public List<Course> queryCourseList(){
        List<Course> courses = courseMapper.queryCourseList();
        for (Course course : courses){
            System.out.println(course);
        }
        return courses;
    }
}
