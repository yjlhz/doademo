package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

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

    @PostMapping("/addCourse")
    public ResultVO addCourse(@Valid CourseForm courseForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return courseService.addCourse(courseForm);
    }
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
