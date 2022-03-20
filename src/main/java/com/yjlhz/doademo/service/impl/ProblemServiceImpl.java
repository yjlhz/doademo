package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ProblemForm;
import com.yjlhz.doademo.mapper.ProblemMapper;
import com.yjlhz.doademo.pojo.Problem;
import com.yjlhz.doademo.service.ProblemService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: ProblemServiceImpl
 * @projectName doademo
 * @description: 考核问题实现类
 * @date 2022/3/20 12:51
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public ResultVO queryProblemsByExamineId(Integer id) {
        List<Problem> problemList = problemMapper.queryProblemByExamineId(id);
        return ResultVOUtil.success(problemList);
    }

    @Override
    public ResultVO addProblem(ProblemForm problemForm) {
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemForm,problem);
        int res = problemMapper.addProblem(problem);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
