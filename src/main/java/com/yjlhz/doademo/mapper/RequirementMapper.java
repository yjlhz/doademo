package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.dto.RequirementDTO;
import com.yjlhz.doademo.pojo.Requirement;
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
public interface RequirementMapper {

    List<Requirement> queryRequirementList();

    int addRequirement(Requirement requirement);

    Requirement queryRequirementByNo(Integer no);

    int updateRequirement(Requirement requirement);

    int deleteRequirementByNo(Integer no);

    int deleteRequirementById(Integer id);

    Requirement queryRequirementById(Integer id);

    /**
     * 通过文件新增一级指标点
     * @param record
     * @return
     */
    int uploadRequirement(RequirementDTO record);

}
