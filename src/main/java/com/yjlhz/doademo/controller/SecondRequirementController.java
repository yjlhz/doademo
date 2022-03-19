package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.form.SecondRequirementForm;
import com.yjlhz.doademo.service.FirstRequirementService;
import com.yjlhz.doademo.service.SecondRequirementService;
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
 * @title: SecondRequirementController
 * @projectName doademo
 * @description: 二级指标点控制层
 * @date 2022/3/19 20:43
 */

@RestController
@Slf4j
public class SecondRequirementController {
    @Autowired
    private SecondRequirementService secondRequirementService;

    @GetMapping("/querySecondRequirementList")
    ResultVO querySecondRequirementList(){
        return secondRequirementService.querySecondRequirements();
    }

    @PostMapping("/addSecondRequirement")
    ResultVO addSecondRequirement(@Valid SecondRequirementForm secondRequirementForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return secondRequirementService.addSecondRequirement(secondRequirementForm);
    }
}
