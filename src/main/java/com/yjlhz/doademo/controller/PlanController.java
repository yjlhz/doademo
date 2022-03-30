package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.form.PlanForm;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    ResultVO addPlan(@Valid PlanForm planForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return planService.addPlan(planForm);
    }

}
