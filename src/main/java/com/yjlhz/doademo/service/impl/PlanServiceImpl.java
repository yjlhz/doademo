package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.PlanForm;
import com.yjlhz.doademo.mapper.PlanMapper;
import com.yjlhz.doademo.mapper.PlanRequirementMapper;
import com.yjlhz.doademo.mapper.SecondRequirementMapper;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.pojo.PlanRequirement;
import com.yjlhz.doademo.pojo.SecondRequirement;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PlanRequirementMapper planRequirementMapper;

    @Autowired
    private SecondRequirementMapper secondRequirementMapper;

    @Override
    public ResultVO queryPlans() {
        List<Plan> planList = planMapper.queryPlanList();
        return ResultVOUtil.success(planList);
    }

    @Override
    @Transactional
    public ResultVO addPlan(PlanForm planForm) {
        Plan plan = new Plan();
        plan.setId(planForm.getId());
        plan.setName(planForm.getName());
        plan.setDescription(planForm.getDescription());
        plan.setGrade(planForm.getGrade());
        plan.setMajor(planForm.getMajor());
        plan.setMinScore(planForm.getMinScore());
        int res = planMapper.addPlan(plan);
        //如果不指定培养计划的指标点，就默认全选
        if (planForm.getRequirement() == null || planForm.getRequirement().equals("")){
            List<SecondRequirement> secondRequirements = secondRequirementMapper.querySecondRequirementList();
            //绑定指标点
            for (SecondRequirement secondRequirement : secondRequirements){
                PlanRequirement planRequirement = new PlanRequirement();
                planRequirement.setPlanId(planForm.getId());
                planRequirement.setRequirementNo(secondRequirement.getSecondRequirementNo());
                res = planRequirementMapper.addPlanRequirement(planRequirement);
            }
        }else {
            String str = planForm.getRequirement();
            String[] requirements = str.split(",");
            for (String no : requirements){
                PlanRequirement planRequirement = new PlanRequirement();
                planRequirement.setPlanId(planForm.getId());
                planRequirement.setRequirementNo(no);
                res = planRequirementMapper.addPlanRequirement(planRequirement);
            }
        }
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updatePlan(PlanForm planForm) {
        return null;
    }

    @Override
    public ResultVO deletePlan(Integer id) {
        return null;
    }
}
