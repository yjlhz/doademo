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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/myUpload")
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
    public ResultVO deleteCourse(String courseName){
        return courseService.deleteCourse(courseName);
    }

    @GetMapping("/queryCourserByName")
    public ResultVO queryCourserByName(String courseName){
        return courseService.queryCourseByName(courseName);
    }
}
