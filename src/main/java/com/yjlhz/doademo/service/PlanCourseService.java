package com.yjlhz.doademo.service;

import com.yjlhz.doademo.pojo.PlanCourse;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: PlanCourseService
 * @projectName doademo
 * @description: planCourse服务层接口
 * @date 2022/4/3 16:33
 */
public interface PlanCourseService {

    ResultVO queryPlanCourseByPlanId(Integer planId);

    ResultVO addPlanCourse(PlanCourse planCourse);

    ResultVO deletePlanCourseByPlanId(Integer planId);

    ResultVO queryCourseByPlanId(Integer planId);


}
