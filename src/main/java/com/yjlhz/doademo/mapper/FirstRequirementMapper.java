package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.dto.FirstRequirementDTO;
import com.yjlhz.doademo.pojo.FirstRequirement;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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

    FirstRequirement queryFirstRequirementByNo(Integer no);

    int updateFirstRequirement(FirstRequirement firstRequirement);

    int deleteFirstRequirementByNo(Integer no);

    /**
     * 通过文件新增课程
     * @param record
     * @return
     */
    int uploadFirstRequirement(FirstRequirementDTO record);

}
