package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @title: CalculateService
 * @projectName doademo
 * @description: 计算服务接口
 * @date 2022/4/5 14:07
 */
public interface CalculateService {

    ResultVO calculate(Integer planId,Integer courseId);

    ResultVO updateStudent(Integer planId);

    void downloadCourse(HttpServletRequest request, HttpServletResponse response, Integer planId, Integer courseId);

}
