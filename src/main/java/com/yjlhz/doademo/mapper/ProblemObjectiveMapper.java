package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.ProblemObjective;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: ProblemTargetMapper
 * @projectName doademo
 * @description: 绑定问题和课程目标dao接口
 * @date 2022/3/23 21:11
 */

@Mapper
@Repository
public interface ProblemObjectiveMapper {

    int addProblemObjective(ProblemObjective problemObjective);

    List<ProblemObjective> queryByObjectiveId(Integer id);

    List<ProblemObjective> queryByProblemId(Integer problemId);

    int deleteByObjectiveId(Integer objectiveId);

    int deleteByProblemId(Integer problemId);

}
