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
import com.yjlhz.doademo.vo.ObjectiveVO;
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

    @GetMapping("/queryObjective")
    ResultVO queryObjective(Integer planId,Integer courseId){
        return objectiveService.queryByPlanIdAndCourseId(planId,courseId);
    }

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

    @GetMapping("/toObjective")
    public String toObjective(Model model){
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
        return "queryObjectiveByPlanIdAndCourseId";
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
