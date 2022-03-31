package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Objective;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: ObjectiveMapper
 * @projectName doademo
 * @description: 教学目标dao层
 * @date 2022/3/31 19:37
 */

@Mapper
@Repository
public interface ObjectiveMapper {

    List<Objective> queryByPlanIdAndCourseId(Integer planId,Integer courseId);

    Objective queryById(Integer id);

    int addObjective(Objective objective);

    int updateObjective(Objective objective);

    int deleteObjectiveById(Integer id);

}
