package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.FirstRequirement;
import com.yjlhz.doademo.pojo.SecondRequirement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: SecondRequirementMapper
 * @projectName doademo
 * @description: 二级指标点dao层接口
 * @date 2022/1/27 17:22
 */
@Mapper
@Repository
public interface SecondRequirementMapper {

    List<SecondRequirement> querySecondRequirementList();

    int addSecondRequirement(SecondRequirement secondRequirement);

    int updateSecondRequirement(SecondRequirement secondRequirement);

    int deleteSecondRequirement(Integer id);

}
