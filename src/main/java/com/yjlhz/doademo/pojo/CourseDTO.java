package com.yjlhz.doademo.pojo;

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

    @ExcelProperty(value = "课程名称",index = 0)
    private String courseName;

    @ExcelProperty(value = "课程学分",index = 1)
    private Double courseCredit;

}
