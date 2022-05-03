package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: StudentCourseService
 * @projectName doademo
 * @description: StudentCourseService
 * @date 2022/5/3 16:09
 */
public interface StudentCourseService {

    ResultVO queryBysNum(String sNum);

    ResultVO deleteBysNum(String sNum);

}
