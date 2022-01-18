package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/queryCourseById")
    public Course queryCourseById(Integer courseId){
        return courseMapper.queryCourseById(courseId);
    }
//
//    @PostMapping("/addCourse")
//    public void addCourse(Course course){
//        courseMapper.addCourse(course);
//    }
//
//    @PostMapping("/updateCourseById")
//    public void updateCourseById(Course course){
//        courseMapper.updateCourseById(course);
//    }
//
//    @GetMapping("/deleteCourseById")
//    public void deleteCourseById(Integer courseId){
//        courseMapper.deleteCourseById(courseId);
//    }

    @GetMapping("/queryCourserByName")
    public Course queryCourserByName(String courseName){
        return courseMapper.queryCourserByName(courseName);
    }
}
