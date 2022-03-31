package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Examine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: ExamineMapper
 * @projectName doademo
 * @description: 考核dao层接口
 * @date 2022/1/27 17:17
 */
@Mapper
@Repository
public interface ExamineMapper {

    List<Examine> queryExamineByPlanCourseId(Integer planId,Integer courseId);

    int addExamine(Examine examine);

}
