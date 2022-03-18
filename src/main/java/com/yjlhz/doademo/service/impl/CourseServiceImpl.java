package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.CourseDTO;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @author lhz
 * @title: CourseServiceImpl
 * @projectName doademo
 * @description: 课程信息实现类
 * @date 2022/1/20 23:57
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ResultVO addCourse(CourseForm courseForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseForm,course);
        int res = courseMapper.addCourse(course);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateCourse(CourseForm courseForm) {
        Course course = courseMapper.queryCourserByName(courseForm.getCourseName());
        BeanUtils.copyProperties(courseForm,course);
        int res = courseMapper.updateCourseById(course);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteCourse(String courseName) {
        Course course = courseMapper.queryCourserByName(courseName);
        if (course==null){
            return ResultVOUtil.error(ResultEnum.NULLPOINT_ERROR);
        }
        int res = courseMapper.deleteCourseById(course.getCourseId());
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryCourseByName(String courseName) {
        Course course = courseMapper.queryCourserByName(courseName);
        if (course == null){
            return ResultVOUtil.error(ResultEnum.NULLPOINT_ERROR);
        }
        return ResultVOUtil.success(course);
    }

    @Override
    public ResultVO queryCourses() {
        List<Course> courseList = courseMapper.queryCourseList();
        return ResultVOUtil.success(courseList);
    }

    @Override
    public ResultVO uploadCourse(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), CourseDTO.class, new CourseListener(courseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

//    @Override
//    public ResultVO queryCoursesByCredit(Double CourseCredit) {
//        List<Course> courseList = new ArrayList<>();
//    }

}
