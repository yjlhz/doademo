package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.pojo.PlanCourse;
import com.yjlhz.doademo.pojo.Student;
import com.yjlhz.doademo.service.*;
import com.yjlhz.doademo.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: PersonController
 * @projectName doademo
 * @description: PersonController
 * @date 2022/5/7 17:02
 */

@Controller
@Slf4j
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PlanService planService;

    @Autowired
    private PlanCourseService planCourseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PersonService personService;

    @GetMapping("/toPerson")
    public String toPerson(HttpSession session, Model model){
        String userName = (String) session.getAttribute("loginUser");
        Student student = (Student) studentService.queryStudentByNum(userName).getData();
        Plan plan = (Plan) planService.queryByMajorAndGrade(student.getMajor(),student.getGrade()).getData();
        List<PlanCourse> planCourseList = (List<PlanCourse>) planCourseService.queryPlanCourseByPlanId(plan.getId()).getData();
        List<Course> courseList = new ArrayList<>();
        for (PlanCourse planCourse : planCourseList){
            Course course = (Course) courseService.queryCourseById(planCourse.getCourseId()).getData();
            courseList.add(course);
        }
        model.addAttribute("courseList",courseList);
        return "person";
    }

    @PostMapping("/query")
    public String query(HttpSession session, @Param("courseId")Integer courseId,Model model){
        PersonVO personVO = (PersonVO) personService.query((String) session.getAttribute("loginUser"),courseId).getData();
        Course course = (Course) courseService.queryCourseById(courseId).getData();
        model.addAttribute("studentObjectiveVOList",personVO.getStudentObjectiveVOList());
        model.addAttribute("studentProblemVOList",personVO.getStudentProblemVOList());
        model.addAttribute("course",course);
        String userName = (String) session.getAttribute("loginUser");
        Student student = (Student) studentService.queryStudentByNum(userName).getData();
        Plan plan = (Plan) planService.queryByMajorAndGrade(student.getMajor(),student.getGrade()).getData();
        List<PlanCourse> planCourseList = (List<PlanCourse>) planCourseService.queryPlanCourseByPlanId(plan.getId()).getData();
        List<Course> courseList = new ArrayList<>();
        for (PlanCourse planCourse : planCourseList){
            Course c = (Course) courseService.queryCourseById(planCourse.getCourseId()).getData();
            courseList.add(c);
        }
        model.addAttribute("courseList",courseList);
        return "queryPerson";
    }

}
