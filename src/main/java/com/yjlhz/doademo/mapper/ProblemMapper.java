package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: ProblemMapper
 * @projectName doademo
 * @description: 问题dao层接口
 * @date 2022/1/27 17:21
 */
@Mapper
@Repository
public interface ProblemMapper {

    List<Problem> queryProblemByExamineId(Integer id);

    int addProblem(Problem problem);

    Problem queryById(Integer id);

}
