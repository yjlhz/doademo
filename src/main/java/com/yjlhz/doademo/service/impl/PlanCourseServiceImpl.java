package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.mapper.PlanCourseMapper;
import com.yjlhz.doademo.pojo.PlanCourse;
import com.yjlhz.doademo.service.PlanCourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: PlanCourseServiceImpl
 * @projectName doademo
 * @description: planCourse实现类
 * @date 2022/4/3 16:34
 */

@Service
public class PlanCourseServiceImpl implements PlanCourseService {

    @Autowired
    private PlanCourseMapper planCourseMapper;

    @Override
    public ResultVO queryPlanCourseByPlanId(Integer planId) {
        List<PlanCourse> planCourseList = planCourseMapper.queryPlanCourseByPlanId(planId);
        return ResultVOUtil.success(planCourseList);
    }
}
