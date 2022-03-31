package com.yjlhz.doademo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: FirstRequirementDTO
 * @projectName doademo
 * @description: 批量上传一级指标点
 * @date 2022/3/23 22:08
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDTO {

    @ExcelProperty(value = "指标点序号",index = 0)
    private Integer requirementNo;

    @ExcelProperty(value = "指标点要求",index = 1)
    private String description;

}
