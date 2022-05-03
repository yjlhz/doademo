package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.PlanRequirement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: PlanRequirementMapper
 * @projectName doademo
 * @description: 培养计划与一级指标点关联表dao层接口
 * @date 2022/1/27 17:19
 */
@Mapper
@Repository
public interface PlanRequirementMapper {

    List<Integer> queryRequirementByPlanId(Integer planId);

    int addPlanRequirement(PlanRequirement planRequirement);

    int updatePlanRequirement(PlanRequirement planRequirement);

    int deletePlanRequirementById(Integer id);

    int deletePlanRequirementByPlanId(Integer planId);

}
