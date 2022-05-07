package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.PersonService;
import com.yjlhz.doademo.utils.ArithUtil;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.PersonVO;
import com.yjlhz.doademo.vo.ResultVO;
import com.yjlhz.doademo.vo.StudentObjectiveVO;
import com.yjlhz.doademo.vo.StudentProblemVO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhz
 * @title: PersonServiceImpl
 * @projectName doademo
 * @description: PersonServiceImpl
 * @date 2022/5/7 17:41
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private ProblemObjectiveMapper problemObjectiveMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Autowired
    private StudentProblemMapper studentProblemMapper;

    @Override
    public ResultVO query(String sNum, Integer courseId) {
        Student student = studentMapper.queryStudentById(sNum);
        Plan plan = planMapper.queryPlanByMajorAndGrade(student.getGrade(), student.getMajor());
        Course course = courseMapper.queryCourseById(courseId);
        List<Examine> examineList = examineMapper.queryExamineByPlanCourseId(plan.getId(), courseId);
        List<Problem> problemList = new ArrayList<>();
        List<StudentProblemVO> studentProblemVOList = new ArrayList<>();
        //查询该课程下所有考核问题
        for (Examine examine : examineList){
            List<Problem> problemList1 = problemMapper.queryProblemByExamineId(examine.getId());
            for (Problem problem : problemList1){
                problemList.add(problem);
                StudentProblemVO studentProblemVO = new StudentProblemVO();
                studentProblemVO.setExamineName(examine.getDescription());
                studentProblemVO.setProblemNo(problem.getProblemNo());
                StudentProblem studentProblem = studentProblemMapper.queryStudentProblemsBySNumAndProblemId(sNum, problem.getId());
                studentProblemVO.setScore(studentProblem.getScore());
                studentProblemVOList.add(studentProblemVO);
            }
        }
        //根据支撑的培养目标将问题分类
        List<Objective> objectiveList = objectiveMapper.queryByPlanIdAndCourseId(plan.getId(), courseId);
        Map<Integer,List<Problem>> map = new HashMap<>();
        for (Objective objective : objectiveList){
            map.put(objective.getObjectiveId(),new ArrayList<>());
        }
        for (Problem problem : problemList){
            List<ProblemObjective> problemObjectives = problemObjectiveMapper.queryByProblemId(problem.getId());
            List<Problem> problems = map.get(problemObjectives.get(0).getObjectiveId());
            problems.add(problem);
            map.put(problemObjectives.get(0).getObjectiveId(),problems);
        }
        List<StudentObjectiveVO> studentObjectiveVOList = new ArrayList<>();
        //计算各培养目标达成度
        for (Map.Entry entry : map.entrySet()){
            Integer key = (Integer) entry.getKey();
            List<Problem> value = (List<Problem>) entry.getValue();
            double sum = 0.0;
            double myScore = 0.0;
            for (Problem p : value){
                sum = ArithUtil.add(sum,p.getMaxScore());
                StudentProblem studentProblem = studentProblemMapper.queryStudentProblemsBySNumAndProblemId(sNum, p.getId());
                myScore = ArithUtil.add(myScore,studentProblem.getScore());
            }
            double achieve = ArithUtil.div(myScore,sum);
            achieve = achieve * 100;
            Objective objective = objectiveMapper.queryById(key);
            StudentObjectiveVO studentObjectiveVO = new StudentObjectiveVO();
            studentObjectiveVO.setCourseName(course.getCourseName());
            studentObjectiveVO.setObjectiveNo(objective.getObjectiveNo());
            studentObjectiveVO.setDescription(objective.getDescription());
            studentObjectiveVO.setAchieve(achieve);
            studentObjectiveVOList.add(studentObjectiveVO);
        }
        PersonVO personVO = new PersonVO();
        personVO.setStudentProblemVOList(studentProblemVOList);
        personVO.setStudentObjectiveVOList(studentObjectiveVOList);
        return ResultVOUtil.success(personVO);
    }
}
