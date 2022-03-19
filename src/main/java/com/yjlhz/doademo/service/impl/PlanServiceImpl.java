package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.form.PlanForm;
import com.yjlhz.doademo.mapper.PlanMapper;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: PlanServiceImpl
 * @projectName doademo
 * @description: 培养计划实现类
 * @date 2022/3/19 15:56
 */

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanMapper planMapper;

    @Override
    public ResultVO queryPlans() {
        List<Plan> planList = planMapper.queryPlanList();
        return ResultVOUtil.success(planList);
    }

    @Override
    public ResultVO addPlan(PlanForm planForm) {
        return null;
    }

    @Override
    public ResultVO deletePlan(Integer id) {
        return null;
    }
}
