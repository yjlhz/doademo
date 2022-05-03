package com.yjlhz.doademo.service;

import com.yjlhz.doademo.pojo.PlanRequirement;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: PlanRequirementService
 * @projectName doademo
 * @description: 服务层
 * @date 2022/5/3 14:07
 */
public interface PlanRequirementService {

    ResultVO addPlanRequirement(PlanRequirement planRequirement);

    ResultVO deletePlanRequirementByPlanId(Integer planId);

    ResultVO queryRequirementByPlanId(Integer planId);

}
