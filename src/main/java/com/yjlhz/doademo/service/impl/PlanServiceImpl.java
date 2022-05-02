package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.PlanForm;
import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.*;
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
    private RequirementMapper requirementMapper;

    @Autowired
    private PlanCourseMapper planCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ResultVO queryPlans() {
        List<Plan> planList = planMapper.queryPlanList();
        return ResultVOUtil.success(planList);
    }

    @Override
    @Transactional
    public ResultVO addPlan(PlanForm planForm) {
        //培养计划编号唯一
        if (planMapper.queryPlanById(planForm.getId())!=null){
            return ResultVOUtil.error(ResultEnum.CONFLICT_ERROR);
        }
        Plan plan = new Plan();
        plan.setId(planForm.getId());
        plan.setName(planForm.getName());
        plan.setDescription(planForm.getDescription());
        plan.setGrade(planForm.getGrade());
        plan.setMajor(planForm.getMajor());
        plan.setMinScore(planForm.getMinScore());
        int res = planMapper.addPlan(plan);
//        //如果不指定培养计划的指标点，就默认全选
//        if (planForm.getRequirement() == null){
//            List<Requirement> requirements = requirementMapper.queryRequirementList();
//            //绑定指标点
//            for (Requirement requirement : requirements){
//                PlanRequirement planRequirement = new PlanRequirement();
//                planRequirement.setPlanId(planForm.getId());
//                planRequirement.setRequirementNo(requirement.getRequirementNo());
//                res = planRequirementMapper.addPlanRequirement(planRequirement);
//            }
//        }else {
//            List<String> requirements = planForm.getRequirement();
//            for (String no : requirements){
//                PlanRequirement planRequirement = new PlanRequirement();
//                planRequirement.setPlanId(planForm.getId());
//                planRequirement.setRequirementNo(Integer.valueOf(no));
//                res = planRequirementMapper.addPlanRequirement(planRequirement);
//            }
//        }
//        if (planForm.getCourseId() == null){
//            return ResultVOUtil.error(ResultEnum.BINDING_ERROR);
//        }
//        //绑定专业课程，确定课程占计划权重
//        List<Integer> courseIds = planForm.getCourseId();
//        for (Integer courseId : courseIds){
//            PlanCourse planCourse = new PlanCourse();
//            planCourse.setPlanId(planForm.getId());
//            planCourse.setCourseId(courseId);
//            Course course = courseMapper.queryCourseById(courseId);
//            double credit = course.getCourseCredit();
//            double weight = credit / planForm.getMinScore();
//            planCourse.setWeight(weight);
//            res = planCourseMapper.addPlanCourse(planCourse);
//        }
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
        int res = planMapper.deletePlanById(id);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
