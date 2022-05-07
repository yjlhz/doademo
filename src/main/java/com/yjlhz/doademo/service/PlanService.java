package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.PlanForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: PlanService
 * @projectName doademo
 * @description: 培养计划服务层接口
 * @date 2022/3/19 15:54
 */
public interface PlanService {

    ResultVO queryPlans();

    ResultVO addPlan(PlanForm planForm);

    ResultVO updatePlan(PlanForm planForm);

    ResultVO deletePlan(Integer id);

    ResultVO queryPlanById(Integer planId);

    ResultVO queryByMajorAndGrade(String major,Integer grade);

}
