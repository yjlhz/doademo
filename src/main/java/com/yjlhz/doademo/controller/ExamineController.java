package com.yjlhz.doademo.controller;

import com.alibaba.excel.EasyExcel;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.listener.ExamineListener;
import com.yjlhz.doademo.service.ExamineService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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

    @PostMapping("/uploadExamineData")
    ResultVO uploadExamineData(@RequestParam(value = "file", required = false) MultipartFile file,Integer planId,Integer courseId){
        return examineService.uploadExamineData(file,planId,courseId);
    }

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
