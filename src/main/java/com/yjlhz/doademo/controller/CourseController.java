package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/export")
    @ApiOperation("批量导出课程")
    void exportCourse(HttpServletResponse response){
        courseService.exportCourse(response);
    }

    @PostMapping("/upload")
    @ApiOperation("批量上传课程")
    ResultVO uploadCourse(@RequestParam(value = "file", required = false) MultipartFile file) {
        return courseService.uploadCourse(file);
    }

    @GetMapping("/queryCourseList")
    public ResultVO queryCourseList(){
        return courseService.queryCourses();
    }

    @PostMapping("/addCourse")
    public ResultVO addCourse(@Valid CourseForm courseForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return courseService.addCourse(courseForm);
    }

    @PostMapping("/updateCourse")
    public ResultVO updateCourseById(@Valid CourseForm courseForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return courseService.updateCourse(courseForm);
    }

    @GetMapping("/deleteCourse")
    public ResultVO deleteCourse(Integer courseId){
        return courseService.deleteCourse(courseId);
    }

    @GetMapping("/queryCourserByName")
    public ResultVO queryCourserByName(String courseName){
        return courseService.queryCourseByName(courseName);
    }

    @GetMapping("/queryCourserById")
    public ResultVO queryCourserById(Integer courseId){
        return courseService.queryCourseById(courseId);
    }
}
