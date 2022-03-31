package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.service.ExamineService;
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
 * @title: ExamineController
 * @projectName doademo
 * @description: 考核控制层类
 * @date 2022/3/20 0:48
 */

@RestController
@Slf4j
public class ExamineController {

    @Autowired
    private ExamineService examineService;

    @GetMapping("/queryExamineById")
    ResultVO queryExamineById(Integer planId,Integer courseId){
        return examineService.queryExaminesByPlanCourseId(planId, courseId);
    }

    @PostMapping("/addExamine")
    ResultVO addExamine(@Valid ExamineForm examineForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return examineService.addExamine(examineForm);
    }

}
