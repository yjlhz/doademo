package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.ProblemRequirement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: ProblemRequirementMapper
 * @projectName doademo
 * @description: 绑定问题和指标点的dao接口
 * @date 2022/3/23 21:11
 */

@Mapper
@Repository
public interface ProblemRequirementMapper {

    int addProblemRequirement(ProblemRequirement problemRequirement);

    List<ProblemRequirement> queryProblemRequirementByProblemId(Integer problemId);

    int deleteByProblemId(Integer id);

}
