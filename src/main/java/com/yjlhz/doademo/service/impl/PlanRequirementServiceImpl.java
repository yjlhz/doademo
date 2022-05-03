package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.PlanRequirementMapper;
import com.yjlhz.doademo.mapper.RequirementMapper;
import com.yjlhz.doademo.pojo.PlanRequirement;
import com.yjlhz.doademo.pojo.Requirement;
import com.yjlhz.doademo.service.PlanRequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: PlanRequirementServiceImpl
 * @projectName doademo
 * @description: PlanRequirementService实现类
 * @date 2022/5/3 14:09
 */

@Service
public class PlanRequirementServiceImpl implements PlanRequirementService {

    @Autowired
    private PlanRequirementMapper planRequirementMapper;

    @Autowired
    private RequirementMapper requirementMapper;

    @Override
    public ResultVO addPlanRequirement(PlanRequirement planRequirement) {
        int res = planRequirementMapper.addPlanRequirement(planRequirement);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deletePlanRequirementByPlanId(Integer planId) {
        int res = planRequirementMapper.deletePlanRequirementByPlanId(planId);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryRequirementByPlanId(Integer planId) {
        List<Integer> list = planRequirementMapper.queryRequirementByPlanId(planId);
        List<Requirement> requirementList = new ArrayList<>();
        for (Integer no : list){
            Requirement requirement = requirementMapper.queryRequirementByNo(no);
            requirementList.add(requirement);
        }
        return ResultVOUtil.success(requirementList);
    }
}
