package com.yjlhz.doademo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: CourseDTO
 * @projectName doademo
 * @description: 批量上传数据
 * @date 2022/3/18 15:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    @ExcelProperty(value = "课程编号",index = 0)
    private Integer courseId;

    @ExcelProperty(value = "课程名称",index = 1)
    private String courseName;

    @ExcelProperty(value = "课程学分",index = 2)
    private Double courseCredit;

    @ExcelProperty(value = "课程支撑指标点",index = 3)
    private String requirementNo;

}
