package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.ProblemForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: ProblemService
 * @projectName doademo
 * @description: 考核问题接口
 * @date 2022/3/20 12:48
 */
public interface ProblemService {

    ResultVO queryProblemsByExamineId(Integer id);

    ResultVO addProblem(ProblemForm problemForm);

}
