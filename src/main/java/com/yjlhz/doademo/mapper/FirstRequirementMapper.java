package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.FirstRequirement;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: FirstRequirementMapper
 * @projectName doademo
 * @description: 一级指标点dao层接口
 * @date 2022/1/27 17:13
 */
@Mapper
@Repository
public interface FirstRequirementMapper {

    List<FirstRequirement> queryFirstRequirementList();

    int addFirstRequirement(FirstRequirement firstRequirement);

    int updateFirstRequirement(FirstRequirement firstRequirement);

    int deleteFirstRequirement(Integer id);

}
