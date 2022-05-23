package com.yjlhz.doademo.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ObjectiveForm;
import com.yjlhz.doademo.form.QueryExamineForm;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.*;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ExamineVO;
import com.yjlhz.doademo.vo.ObjectiveVO;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: ObjectiveController
 * @projectName doademo
 * @description: 教学目标控制层
 * @date 2022/3/31 20:04
 */

@Controller
@Slf4j
@RequestMapping("/objective")
public class ObjectiveController {

    @Autowired
    private ObjectiveService objectiveService;

    @Autowired
    private PlanService planService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RequirementService requirementService;

    @Autowired
    private ProblemObjectiveService problemObjectiveService;

    @PostMapping("/addObjective")
    String addObjective(ObjectiveForm objectiveForm){
        objectiveService.addObjective(objectiveForm);
        return "redirect:/objective/toObjective";
    }

    @GetMapping("/toAdd")
    public String toAdd(Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        List<Requirement> requirementList = (List<Requirement>) requirementService.queryRequirements().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("requirementList",requirementList);
        return "addObjective";
    }

    @GetMapping("/toAdd1")
    public String toAdd1(Model model,
                         @RequestParam(value = "planId")Integer planId,
                         @RequestParam(value = "courseId")Integer courseId){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        List<Requirement> requirementList = (List<Requirement>) requirementService.queryRequirements().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("requirementList",requirementList);
        model.addAttribute("planId",planId);
        model.addAttribute("courseId",courseId);
        return "addObjective";
    }

    @GetMapping("/toObjective")
    public String toObjective(Model model,
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
            List<Objective> objectiveList1 = (List<Objective>) objectiveService.queryObjectives().getData();
            List<ObjectiveVO> objectiveList = new ArrayList<>();
            for (Objective objective : objectiveList1){
                Plan plan = (Plan) planService.queryPlanById(objective.getPlanId()).getData();
                Course course = (Course) courseService.queryCourseById(objective.getCourseId()).getData();
                ObjectiveVO objectiveVO = new ObjectiveVO();
                objectiveVO.setObjectiveId(objective.getObjectiveId());
                objectiveVO.setObjectiveNo(objective.getObjectiveNo());
                objectiveVO.setPlanName(plan.getName());
                objectiveVO.setCourseName(course.getCourseName());
                objectiveVO.setDescription(objective.getDescription());
                objectiveVO.setAchieve(objective.getAchieve());
                objectiveVO.setRequirementNo(objective.getRequirementNo());
                objectiveList.add(objectiveVO);
            }
            System.out.println("分页数据："+objectiveList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ObjectiveVO> pageInfo = new PageInfo<ObjectiveVO>(objectiveList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("objectiveList",objectiveList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "objective";
    }

    @PostMapping("/toQuery")
    public String toObjective(QueryExamineForm queryExamineForm,Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        List<Objective> objectives = (List<Objective>) objectiveService.queryByPlanIdAndCourseId(queryExamineForm.getPlanId(),queryExamineForm.getCourseId()).getData();
        List<ObjectiveVO> objectiveList = new ArrayList<>();
        for (Objective objective : objectives){
            Plan plan = (Plan) planService.queryPlanById(objective.getPlanId()).getData();
            Course course = (Course) courseService.queryCourseById(objective.getCourseId()).getData();
            ObjectiveVO objectiveVO = new ObjectiveVO();
            objectiveVO.setObjectiveId(objective.getObjectiveId());
            objectiveVO.setObjectiveNo(objective.getObjectiveNo());
            objectiveVO.setPlanName(plan.getName());
            objectiveVO.setCourseName(course.getCourseName());
            objectiveVO.setDescription(objective.getDescription());
            objectiveVO.setAchieve(objective.getAchieve());
            objectiveVO.setRequirementNo(objective.getRequirementNo());
            objectiveList.add(objectiveVO);
        }
        Plan plan = (Plan) planService.queryPlanById(queryExamineForm.getPlanId()).getData();
        Course course = (Course) courseService.queryCourseById(queryExamineForm.getCourseId()).getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("objectiveList",objectiveList);
        model.addAttribute("planName",plan.getName());
        model.addAttribute("courseName",course.getCourseName());
        model.addAttribute("planId",plan.getId());
        model.addAttribute("courseId",course.getCourseId());
        return "queryObjectiveByPlanIdAndCourseId";
    }

    @PostMapping("/updateObjective/{id}")
    String updateObjective(ObjectiveForm objectiveForm,@PathVariable("id")Integer id){
        objectiveService.updateObjective(objectiveForm,id);
        return "redirect:/objective/toObjective";
    }

    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id")Integer id, Model model){
        Objective objective = (Objective) objectiveService.queryById(id).getData();
        Plan plan = (Plan) planService.queryPlanById(objective.getPlanId()).getData();
        Course course = (Course) courseService.queryCourseById(objective.getCourseId()).getData();
        List<Requirement> requirementList = (List<Requirement>) requirementService.queryRequirements().getData();
        model.addAttribute("objective",objective);
        model.addAttribute("requirementList",requirementList);
        model.addAttribute("planName",plan.getName());
        model.addAttribute("courseName",course.getCourseName());
        return "updateObjective";
    }

    @GetMapping("/deleteObjective/{id}")
    String deleteObjective(@PathVariable("id")Integer id){
        objectiveService.deleteObjective(id);
        problemObjectiveService.deleteByObjectiveId(id);
        return "redirect:/objective/toObjective";
    }

}
