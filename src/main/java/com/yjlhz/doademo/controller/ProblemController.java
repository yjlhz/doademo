package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ProblemForm;
import com.yjlhz.doademo.service.ProblemService;
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
 * @title: ProblemController
 * @projectName doademo
 * @description: 考核问题控制层接口
 * @date 2022/3/20 12:54
 */

@RestController
@Slf4j
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping("/queryProblemsByPlanIdAndCourseId")
    ResultVO queryProblemsByExamineId(Integer planId,Integer courseId){
        return problemService.queryProblemsByPlanIdAndCourseId(planId, courseId);
    }

    @PostMapping("/addProblem")
    ResultVO addProblem(@Valid ProblemForm problemForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return problemService.addProblem(problemForm);
    }

}
