package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.ProblemObjectiveMapper;
import com.yjlhz.doademo.service.ProblemObjectiveService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: ProblemObjectiveServiceImpl
 * @projectName doademo
 * @description: ProblemObjectiveServiceImpl
 * @date 2022/5/4 9:35
 */

@Service
public class ProblemObjectiveServiceImpl implements ProblemObjectiveService {

    @Autowired
    private ProblemObjectiveMapper problemObjectiveMapper;

    @Override
    public ResultVO deleteByObjectiveId(Integer objectiveId) {
        int res = problemObjectiveMapper.deleteByObjectiveId(objectiveId);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteByProblemId(Integer problemId) {
        int res = problemObjectiveMapper.deleteByProblemId(problemId);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
