package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.service.FirstRequirementService;
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
 * @title: FirstRequirementController
 * @projectName doademo
 * @description: 一级指标点控制层
 * @date 2022/3/19 17:05
 */

@RestController
@Slf4j
public class FirstRequirementController {

    @Autowired
    private FirstRequirementService firstRequirementService;

    @GetMapping("/queryFirstRequirementList")
    ResultVO queryFirstRequirementList(){
        return firstRequirementService.queryFirstRequirements();
    }

    @PostMapping("/addFirstRequirement")
    ResultVO addFirstRequirement(@Valid FirstRequirementForm firstRequirementForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return firstRequirementService.addFirstRequirement(firstRequirementForm);
    }

}
