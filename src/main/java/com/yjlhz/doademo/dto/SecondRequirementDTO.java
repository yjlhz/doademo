package com.yjlhz.doademo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: SecondRequirementDTO
 * @projectName doademo
 * @description: 二级指标点批量上传
 * @date 2022/3/27 15:01
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondRequirementDTO {

    @ExcelProperty(value = "一级指标点序号",index = 0)
    private Integer firstRequirementNo;

    @ExcelProperty(value = "二级指标点序号",index = 1)
    private String secondRequirementNo;

    @ExcelProperty(value = "二级指标点要求",index = 2)
    private String description;
}
