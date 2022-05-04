package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: ProblemObjectiveService
 * @projectName doademo
 * @description: ProblemObjectiveService
 * @date 2022/5/4 9:34
 */
public interface ProblemObjectiveService {

    ResultVO deleteByObjectiveId(Integer objectiveId);

    ResultVO deleteByProblemId(Integer problemId);

}
