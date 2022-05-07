package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: PersonService
 * @projectName doademo
 * @description: PersonService
 * @date 2022/5/7 17:40
 */
public interface PersonService {

    ResultVO query(String sNum,Integer courseId);

}
