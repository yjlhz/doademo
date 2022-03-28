package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhz
 * @title: PlanController
 * @projectName doademo
 * @description: 培养计划信息控制层接口
 * @date 2022/3/19 15:59
 */

@RestController
@Slf4j
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/queryPlanList")
    ResultVO queryPlanList(){
        return planService.queryPlans();
    }

    @PostMapping("/addPlan")
    ResultVO addPlan(){
        return null;
    }

}
