package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.pojo.PlanCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: PlanCourseMapper
 * @projectName doademo
 * @description: 培养计划和课程关联dao层接口
 * @date 2022/1/27 17:18
 */
@Mapper
@Repository
public interface PlanCourseMapper {
    List<PlanCourse> queryPlanCourseByPlanId(Integer planId);

    int addPlanCourse(PlanCourse planCourse);

    int deletePlanCourseById(Integer planId);

    int deletePlanCourseByPlanId(Integer planId);

    PlanCourse queryByPlanIdAndCourseId(Integer planId,Integer courseId);
}
