package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: StudentProblem
 * @projectName doademo
 * @description: StudentProblemService
 * @date 2022/5/3 16:11
 */
public interface StudentProblemService {

    ResultVO queryBysNum(String sNum);

    ResultVO deleteBysNum(String sNum);

}
