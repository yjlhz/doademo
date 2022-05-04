package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.ProblemRequirementMapper;
import com.yjlhz.doademo.service.ProblemRequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: ProblemRequirementServiceImpl
 * @projectName doademo
 * @description: ProblemRequirementServiceImpl
 * @date 2022/5/4 10:01
 */

@Service
public class ProblemRequirementServiceImpl implements ProblemRequirementService {

    @Autowired
    private ProblemRequirementMapper problemRequirementMapper;

    @Override
    public ResultVO deleteByProblemId(Integer id) {
        int res = problemRequirementMapper.deleteByProblemId(id);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
