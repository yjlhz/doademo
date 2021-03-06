package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: PlanMapper
 * @projectName doademo
 * @description: 培养计划dao层接口
 * @date 2022/1/27 17:17
 */

@Mapper
@Repository
public interface PlanMapper {

    List<Plan> queryPlanList();

    int addPlan(Plan plan);

    Plan queryPlanById(Integer id);

    int deletePlanById(Integer planId);

    Plan queryPlanByMajorAndGrade(Integer grade,String major);

}
