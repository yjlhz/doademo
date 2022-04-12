package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.service.CalculateService;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @title: CalculateController
 * @projectName doademo
 * @description: 计算达成度控制层
 * @date 2022/4/5 13:59
 */

@RestController
@Slf4j
public class CalculateController {

    @Autowired
    private CalculateService calculateService;

    @GetMapping("/calculate")
    ResultVO calculate(Integer planId,Integer courseId){
        return calculateService.calculate(planId, courseId);
    }

    @GetMapping("/updateStudent")
    ResultVO updateStudent(Integer planId){
        return calculateService.updateStudent(planId);
    }

    @GetMapping("/downloadCourse")
    void downloadCourse(HttpServletRequest request, HttpServletResponse response,Integer planId,Integer courseId){
        calculateService.downloadCourse(request, response, planId, courseId);
    }

}
