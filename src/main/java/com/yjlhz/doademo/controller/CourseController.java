package com.yjlhz.doademo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
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
    public String queryCourseList(Model model,
                                  @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                  @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
            System.out.println("分页数据："+courseList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Course> pageInfo = new PageInfo<Course>(courseList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("courseList",courseList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "list";
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
    public String deleteCourse(Integer courseId){
        ResultVO resultVO = courseService.deleteCourse(courseId);

        return "";
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
