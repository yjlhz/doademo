package com.yjlhz.doademo.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.form.QueryExamineForm;
import com.yjlhz.doademo.listener.ExamineListener;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.service.ExamineService;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ExamineVO;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: ExamineController
 * @projectName doademo
 * @description: 考核控制层类
 * @date 2022/3/20 0:48
 */

@Controller
@Slf4j
@RequestMapping("/examine")
public class ExamineController {

    @Autowired
    private ExamineService examineService;

    @Autowired
    private PlanService planService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/uploadExamineData")
    String uploadExamineData(@RequestParam(value = "file", required = false) MultipartFile file,Integer planId,Integer courseId){
        examineService.uploadExamineData(file,planId,courseId);
        return "redirect:/examine/queryExamineList";
    }

    @GetMapping("/toUpload")
    public String toUpload(Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "uploadExamine";
    }

    @GetMapping("/queryExamineById")
    ResultVO queryExamineById(Integer planId,Integer courseId){
        return examineService.queryExaminesByPlanCourseId(planId, courseId);
    }

    @PostMapping("/addExamine")
    ResultVO addExamine(@Valid ExamineForm examineForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return examineService.addExamine(examineForm);
    }

    @GetMapping("/queryExamineList")
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
            List<ExamineVO> examineList = (List<ExamineVO>) examineService.queryExamines().getData();
            System.out.println("分页数据："+examineList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ExamineVO> pageInfo = new PageInfo<ExamineVO>(examineList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("examineList",examineList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "examineList";
    }

    @PostMapping("/toQuery")
    public String toQuery(QueryExamineForm queryExamineForm,Model model,
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
            List<Examine> examineList1 = (List<Examine>) examineService.queryExaminesByPlanCourseId(queryExamineForm.getPlanId(),queryExamineForm.getCourseId()).getData();
            List<ExamineVO> examineList = new ArrayList<>();
            for (Examine examine : examineList1){
                ExamineVO examineVO = new ExamineVO();
                examineVO.setId(examine.getId());
                Plan plan = (Plan) planService.queryPlanById(examine.getPlanId()).getData();
                examineVO.setPlanName(plan.getName());
                Course course = (Course) courseService.queryCourseById(examine.getCourseId()).getData();
                examineVO.setCourseName(course.getCourseName());
                examineVO.setDescription(examine.getDescription());
                examineVO.setWeight(examine.getWeight());
                examineList.add(examineVO);
            }
            System.out.println("分页数据："+examineList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ExamineVO> pageInfo = new PageInfo<ExamineVO>(examineList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("examineList",examineList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        Plan thisPlan = (Plan) planService.queryPlanById(queryExamineForm.getPlanId()).getData();
        Course thisCourse = (Course) courseService.queryCourseById(queryExamineForm.getCourseId()).getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("planId",queryExamineForm.getPlanId());
        model.addAttribute("courseId",queryExamineForm.getCourseId());
        model.addAttribute("planName",thisPlan.getName());
        model.addAttribute("courseName",thisCourse.getCourseName());
        return "queryExamineByPlanIdAndCourseId";
    }

}
