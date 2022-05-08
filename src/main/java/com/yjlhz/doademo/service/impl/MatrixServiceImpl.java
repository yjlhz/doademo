package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.form.WeightForm;
import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.MatrixService;
import com.yjlhz.doademo.utils.ArithUtil;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhz
 * @title: MatrixServiceImpl
 * @projectName doademo
 * @description: 关系矩阵实现类
 * @date 2022/4/4 13:54
 */

@Service
public class MatrixServiceImpl implements MatrixService {

    @Autowired
    private ProblemObjectiveMapper problemObjectiveMapper;

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Autowired
    private ProblemRequirementMapper problemRequirementMapper;

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private PlanCourseMapper planCourseMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    @Transactional
    public ResultVO addMatrix(List<MatrixForm> matrixForms) {
        int res = -1;
        for (MatrixForm matrixForm : matrixForms){
            ProblemObjective problemObjective = new ProblemObjective();
            problemObjective.setObjectiveId(matrixForm.getObjectiveId());
            problemObjective.setProblemId(matrixForm.getProblemId());
            res = problemObjectiveMapper.addProblemObjective(problemObjective);
            int no = objectiveMapper.queryById(matrixForm.getObjectiveId()).getRequirementNo();
            ProblemRequirement problemRequirement = new ProblemRequirement();
            problemRequirement.setProblemId(matrixForm.getProblemId());
            problemRequirement.setRequirementNo(no);
            res = problemRequirementMapper.addProblemRequirement(problemRequirement);
        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO addWeight(List<WeightForm> weightForms) {
        int res = -1;
        for (WeightForm weightForm : weightForms){
            Examine examine = examineMapper.queryExamineById(weightForm.getExamineId());
            examine.setWeight(weightForm.getWeight());
            res = examineMapper.updateExamine(examine);
        }
//        List<Examine> examineList = null;
//        Map<Integer,Integer> map = new HashMap<>();
//        for (WeightForm weightForm : weightForms){
//            Examine examine = examineMapper.queryExamineById(weightForm.getExamineId());
//            examine.setWeight(weightForm.getWeight());//存储用户指定的权重
//            res = examineMapper.updateExamine(examine);
//            examineList = examineMapper.queryExamineByPlanCourseId(examine.getPlanId(),examine.getCourseId());
//            map.put(weightForm.getExamineId(),1);
//        }
//        double nowWight = 0.0;
//        for (Examine examine : examineList){
//            if (map.getOrDefault(examine.getId(),0)==1){
//                nowWight = ArithUtil.add(nowWight,examine.getWeight());
//            }
//        }
//        nowWight = ArithUtil.sub(1,nowWight);
//        double sum = 0.0;
//        for (Examine examine : examineList){
//            if (map.getOrDefault(examine.getId(),0) != 1){
//                List<Problem> problemList = problemMapper.queryProblemByExamineId(examine.getId());
//                for (Problem problem : problemList){
//                    sum = ArithUtil.add(sum,problem.getMaxScore());
//                }
//            }
//        }
//        for (Examine examine : examineList){
//            if (map.getOrDefault(examine.getId(),0) != 1){
//                double examineSum = 0.0;
//                List<Problem> problemList = problemMapper.queryProblemByExamineId(examine.getId());
//                for (Problem problem : problemList){
//                    examineSum = ArithUtil.add(examineSum,problem.getMaxScore());
//                }
//                double weight = ArithUtil.div(examineSum,sum);
//                weight = ArithUtil.mul(weight,nowWight);
//                examine.setWeight(weight);
//                res = examineMapper.updateExamine(examine);
//            }
//        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}