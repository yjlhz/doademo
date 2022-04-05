package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: CalculateService
 * @projectName doademo
 * @description: 计算服务接口
 * @date 2022/4/5 14:07
 */
public interface CalculateService {

    ResultVO calculate(Integer planId,Integer courseId);

}
