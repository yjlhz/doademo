package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.CalculateService;
import com.yjlhz.doademo.utils.ArithUtil;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author lhz
 * @title: CalculateServiceImpl
 * @projectName doademo
 * @description: 计算实现类
 * @date 2022/4/5 14:08
 */

@Service
public class CalculateServiceImpl implements CalculateService {

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private StudentProblemMapper studentProblemMapper;

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Autowired
    private ProblemObjectiveMapper problemObjectiveMapper;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PlanRequirementMapper planRequirementMapper;

    @Autowired
    private ProblemRequirementMapper problemRequirementMapper;

    @Override
    public ResultVO calculate(Integer planId, Integer courseId) {
        int res = -1;
        //获取课程培养目标
        List<Objective> objectives = objectiveMapper.queryByPlanIdAndCourseId(planId, courseId);
        //计算每个目标达成度
        for (Objective objective : objectives){
            //根据目标id获取支撑目标的问题id
            List<ProblemObjective> problemObjectives = problemObjectiveMapper.queryByObjectiveId(objective.getObjectiveId());
            Map<Integer,List<Double>> data = new HashMap<>();
            //计算每个问题达成度
            double sumMax = 0;//支撑该目标的问题总分
            for (ProblemObjective problemObjective : problemObjectives){
                Problem problem = problemMapper.queryById(problemObjective.getProblemId());
                sumMax = ArithUtil.add(sumMax,problem.getMaxScore());
            }
            for (ProblemObjective problemObjective : problemObjectives){
                List<StudentProblem> studentProblems = studentProblemMapper.queryStudentProblemsByProblemId(problemObjective.getProblemId());
                double sum = 0;
                int count = 0;
                for (StudentProblem studentProblem : studentProblems){
                    sum = ArithUtil.add(sum,studentProblem.getScore());
                    count++;
                }
                Problem problem = problemMapper.queryById(problemObjective.getProblemId());
                double achieve = ArithUtil.div(sum,ArithUtil.mul(count,problem.getMaxScore()));
                achieve = ArithUtil.mul(achieve,ArithUtil.div(problem.getMaxScore(),sumMax));;
                data.put(problem.getExamineId(),data.getOrDefault(problem.getExamineId(),new ArrayList<>()));
                List<Double> doubles = data.get(problem.getExamineId());
                doubles.add(achieve);
                data.put(problem.getExamineId(),doubles);
            }
            double flag = 1;
            double endAchieve = 0;
            double result = 0;
            //判断用户是否全部缺省权重，如果全部缺省，则按分数自动算
            int c = 0;
            int keys = 0;
            for (Map.Entry entry : data.entrySet()){
                Integer key = (Integer) entry.getKey();
                if (examineMapper.queryExamineById(key).getWeight() == 0.0){
                    c++;
                }
                keys++;
            }
            //如果权重不是全部缺省的时候，要减去权重为0的总分
            if (c!=keys){
                for (Map.Entry entry : data.entrySet()){
                    Integer key = (Integer) entry.getKey();
                    Examine examine = examineMapper.queryExamineById(key);
                    if (examine.getWeight() == 0.0){
                        for (ProblemObjective problemObjective : problemObjectives){
                            Problem problem = problemMapper.queryById(problemObjective.getProblemId());
                            if (problem.getExamineId() == examine.getId()){
                                sumMax = ArithUtil.sub(sumMax,problem.getMaxScore());
                            }
                        }
                    }
                }
                data.clear();//如果出现sumMax变化了，重新计算
                for (ProblemObjective problemObjective : problemObjectives){
                    List<StudentProblem> studentProblems = studentProblemMapper.queryStudentProblemsByProblemId(problemObjective.getProblemId());
                    double sum = 0;
                    int count = 0;
                    for (StudentProblem studentProblem : studentProblems){
                        sum = ArithUtil.add(sum,studentProblem.getScore());
                        count++;
                    }
                    Problem problem = problemMapper.queryById(problemObjective.getProblemId());
                    double achieve = ArithUtil.div(sum,ArithUtil.mul(count,problem.getMaxScore()));
                    achieve = ArithUtil.mul(achieve,ArithUtil.div(problem.getMaxScore(),sumMax));;
                    data.put(problem.getExamineId(),data.getOrDefault(problem.getExamineId(),new ArrayList<>()));
                    List<Double> doubles = data.get(problem.getExamineId());
                    doubles.add(achieve);
                    data.put(problem.getExamineId(),doubles);
                }
            }
            for (Map.Entry entry : data.entrySet()){
                Integer key = (Integer) entry.getKey();
                List<Double> values = (List<Double>) entry.getValue();
                Examine examine = examineMapper.queryExamineById(key);
                if (examine.getWeight() != 0.0){
                    flag = examine.getWeight();
                }else if (examine.getWeight() == 0.0 && c==keys){
                    flag = 1;
                }else if (examine.getWeight() == 0.0){
                    flag = 0.0;
                }
                for (Double d : values){
                    endAchieve = ArithUtil.add(endAchieve,d);
                }
                endAchieve = ArithUtil.mul(endAchieve,flag);
                result =  ArithUtil.add(endAchieve,result);
            }
            objective.setAchieve(result);
            res = objectiveMapper.updateObjective(objective);
        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success(objectives);
    }

    @Override
    @Transactional
    public ResultVO updateStudent(Integer planId) {
        int res = -1;
        //获取培养计划
        Plan plan = planMapper.queryPlanById(planId);
        //根据培养计划获取应该达成的指标点
        List<Integer> requirementNos = planRequirementMapper.queryRequirementByPlanId(planId);
        //根据培养计划的专业年级找到适用该培养计划的学生
        List<Student> studentList = studentMapper.queryStudentByPlanId(plan.getMajor(), plan.getGrade());
        Map<Integer, Set<Integer>> map = new HashMap<>();//指标点对应的问题列表
        for (Integer no : requirementNos) {
            map.put(no, map.getOrDefault(no, new HashSet<>()));
        }
        //更新每个学生指标点达成度情况
        for (Student student : studentList){
            //根据学生学号获取所作答过的所有问题id
            List<StudentProblem> studentProblems = studentProblemMapper.queryStudentProblemsBySNum(student.getSNum());
            //遍历问题id，根据支撑的指标点分类
            for (StudentProblem studentProblem : studentProblems){
                //获取问题
                Problem problem = problemMapper.queryById(studentProblem.getProblemId());
                if (problemRequirementMapper.queryProblemRequirementByProblemId(problem.getId()).isEmpty()){
                    continue;
                }
                List<ProblemRequirement> problemRequirements = problemRequirementMapper.queryProblemRequirementByProblemId(problem.getId());
                for (ProblemRequirement problemRequirement : problemRequirements){
                    Set<Integer> list = map.getOrDefault(problemRequirement.getRequirementNo(), new HashSet<>());
                    list.add(problem.getId());
                    map.put(problemRequirement.getRequirementNo(),list);
                }
            }
            //查询学生需要完成的指标点
            String achieve = student.getAchieve();
            String[] strings = achieve.split(",");
            //更新每个指标点的达成度
            for (Map.Entry entry : map.entrySet()){
                Integer key = (Integer) entry.getKey();
                Set<Integer> value = (Set<Integer>) entry.getValue();
                double sumMax = 0;
                double StuScore = 0;
                for (Integer v : value){
                    Problem problem = problemMapper.queryById(v);
                    sumMax = ArithUtil.add(sumMax,problem.getMaxScore());
                    if (studentProblemMapper.queryStudentProblemsBySNumAndProblemId(student.getSNum(), v) == null){
                        continue;
                    }
                    StudentProblem studentProblem = studentProblemMapper.queryStudentProblemsBySNumAndProblemId(student.getSNum(), v);
                    StuScore = ArithUtil.add(StuScore,studentProblem.getScore());
                }
                double ach = 0.00;
                if (sumMax != 0.0){
                    ach = ArithUtil.div(StuScore,sumMax);
                    DecimalFormat df = new DecimalFormat("#.00");
                    df.format(ach);
                }
                strings[key - 1] = String.valueOf(ach);
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0;i<strings.length;i++){
                if (i!=strings.length-1){
                    sb.append(strings[i]+",");
                }else {
                    sb.append(strings[i]);
                }
            }
            student.setAchieve(sb.toString());
            res = studentMapper.updateStudentById(student);
        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success(studentList);
    }
}
