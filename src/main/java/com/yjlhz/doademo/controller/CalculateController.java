package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Objective;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.service.CalculateService;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.service.ObjectiveService;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.vo.ObjectiveVO;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.PanelUI;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: CalculateController
 * @projectName doademo
 * @description: 计算达成度控制层
 * @date 2022/4/5 13:59
 */

@Controller
@Slf4j
@RequestMapping("/calculate")
public class CalculateController {

    @Autowired
    private CalculateService calculateService;

    @Autowired
    private PlanService planService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ObjectiveService objectiveService;

    @PostMapping("/calculate")
    String calculate(@Param("planId") Integer planId, @Param("courseId")Integer courseId,Model model){
        calculateService.calculate(planId, courseId);
        List<Objective> objectiveList1 = (List<Objective>) objectiveService.queryByPlanIdAndCourseId(planId, courseId).getData();
        List<ObjectiveVO> objectiveList = new ArrayList<>();
        Plan plan = (Plan) planService.queryPlanById(planId).getData();
        Course course = (Course) courseService.queryCourseById(courseId).getData();
        for (Objective objective : objectiveList1){
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
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planName",plan.getName());
        model.addAttribute("courseName",course.getCourseName());
        model.addAttribute("objectiveList",objectiveList);
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "calculateResult";
    }

    @GetMapping("/toCalculate")
    public String toCalculate(Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "calculate";
    }

    @GetMapping("/updateStudent")
    ResultVO updateStudent(Integer planId){
        return calculateService.updateStudent(planId);
    }

    @GetMapping("/downloadResult")
    void downloadCourse(HttpServletRequest request, HttpServletResponse response,Integer planId,Integer courseId){
        try {
            calculateService.downloadCourse(request, response, planId, courseId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
