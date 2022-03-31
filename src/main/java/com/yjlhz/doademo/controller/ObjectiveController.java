package com.yjlhz.doademo.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ObjectiveForm;
import com.yjlhz.doademo.pojo.Objective;
import com.yjlhz.doademo.service.ObjectiveService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lhz
 * @title: ObjectiveController
 * @projectName doademo
 * @description: 教学目标控制层
 * @date 2022/3/31 20:04
 */

@RestController
@Slf4j
public class ObjectiveController {

    @Autowired
    private ObjectiveService objectiveService;

    @GetMapping("/queryObjective")
    ResultVO queryObjective(Integer planId,Integer courseId){
        return objectiveService.queryByPlanIdAndCourseId(planId,courseId);
    }

    @PostMapping("/addObjective")
    ResultVO addObjective(@Valid ObjectiveForm objectiveForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return objectiveService.addObjective(objectiveForm);
    }

    @PostMapping("/updateObjective")
    ResultVO updateObjective(@Valid ObjectiveForm objectiveForm,Integer id,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return objectiveService.updateObjective(objectiveForm,id);
    }

    @GetMapping("/deleteObjective")
    ResultVO deleteObjective(Integer id){
        return objectiveService.deleteObjective(id);
    }

}
