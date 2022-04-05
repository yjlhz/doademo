package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.form.WeightForm;
import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.MatrixService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            examine.setWeight(weightForm.getWeight());//存储用户指定的权重
            res = examineMapper.updateExamine(examine);
        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}