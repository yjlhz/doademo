package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ProblemForm;
import com.yjlhz.doademo.mapper.ExamineMapper;
import com.yjlhz.doademo.mapper.ProblemMapper;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.pojo.Problem;
import com.yjlhz.doademo.service.ProblemService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ProblemVO;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private ExamineMapper examineMapper;

    @Override
    public ResultVO queryProblemsByPlanIdAndCourseId(Integer planId,Integer courseId) {
        List<Examine> examineList = examineMapper.queryExamineByPlanCourseId(planId, courseId);
        List<ProblemVO> problemVOList = new ArrayList<>();
        for (Examine examine : examineList){
            List<Problem> problemList = problemMapper.queryProblemByExamineId(examine.getId());
            for (Problem problem : problemList){
                ProblemVO problemVO = new ProblemVO();
                problemVO.setExamineId(examine.getId());
                problemVO.setExamineName(examine.getDescription());
                problemVO.setId(problem.getId());
                problemVO.setNo(problem.getProblemNo());
                problemVOList.add(problemVO);
            }
        }
        return ResultVOUtil.success(problemVOList);
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
