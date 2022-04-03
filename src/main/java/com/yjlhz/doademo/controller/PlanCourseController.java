package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.service.PlanCourseService;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhz
 * @title: PlanCourseController
 * @projectName doademo
 * @description: planCourse控制层
 * @date 2022/4/3 16:36
 */

@RestController
@Slf4j
public class PlanCourseController {

    @Autowired
    private PlanCourseService planCourseService;

    @GetMapping("/queryByPlanId")
    ResultVO queryByPlanId(Integer planId){
        return planCourseService.queryPlanCourseByPlanId(planId);
    }

}
