package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.mapper.ProblemObjectiveMapper;
import com.yjlhz.doademo.pojo.ProblemObjective;
import com.yjlhz.doademo.service.MatrixService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultVO addMatrix(List<MatrixForm> matrixForms) {
        int res = -1;
        for (MatrixForm matrixForm : matrixForms){
            ProblemObjective problemObjective = new ProblemObjective();
            problemObjective.setObjectiveId(matrixForm.getObjectiveId());
            problemObjective.setProblemId(matrixForm.getProblemId());
            res = problemObjectiveMapper.addProblemObjective(problemObjective);
        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
