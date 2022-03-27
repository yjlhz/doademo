package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.dto.FirstRequirementDTO;
import com.yjlhz.doademo.dto.SecondRequirementDTO;
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

    SecondRequirement querySecondRequirementByNo(String no);

    int addSecondRequirement(SecondRequirement secondRequirement);

    int updateSecondRequirementById(SecondRequirement secondRequirement);

    int deleteSecondRequirementByNo(String no);

    /**
     * 通过文件新增二级指标点
     * @param record
     * @return
     */
    int uploadSecondRequirement(SecondRequirementDTO record);

}
