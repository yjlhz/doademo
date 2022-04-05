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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
