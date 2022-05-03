package com.yjlhz.doademo.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ObjectiveForm;
import com.yjlhz.doademo.form.QueryExamineForm;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Objective;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.pojo.Requirement;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.service.ObjectiveService;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.service.RequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.awt.*;
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

    @GetMapping("/queryObjective")
    ResultVO queryObjective(Integer planId,Integer courseId){
        return objectiveService.queryByPlanIdAndCourseId(planId,courseId);
    }

    @PostMapping("/addObjective")
    ResultVO addObjective(@Valid ObjectiveForm objectiveForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return objectiveService.addObjective(objectiveForm);
    }

    @GetMapping("/toAdd")
    public String toAdd(Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        List<Requirement> requirementList = (List<Requirement>) requirementService.queryRequirements().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("requirementList",requirementList);
        return "objective";
    }

    @GetMapping("/toObjective")
    public String toObjective(Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "objective";
    }

    @GetMapping("/toQuery")
    public String toObjective(QueryExamineForm queryExamineForm){

        return "objective";
    }

    @PostMapping("/updateObjective")
    ResultVO updateObjective(@Valid ObjectiveForm objectiveForm,Integer id,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return objectiveService.updateObjective(objectiveForm,id);
    }

    @GetMapping("/deleteObjective")
    ResultVO deleteObjective(Integer id){
        return objectiveService.deleteObjective(id);
    }

}
