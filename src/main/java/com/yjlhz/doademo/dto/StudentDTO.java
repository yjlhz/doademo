package com.yjlhz.doademo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: StudentDTO
 * @projectName doademo
 * @description: 批量上传学生信息
 * @date 2022/3/30 23:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    @ExcelProperty(value = "学号",index = 0)
    private String sNum;

    @ExcelProperty(value = "姓名",index = 1)
    private String sName;

    @ExcelProperty(value = "性别",index = 2)
    private Integer gender;

    @ExcelProperty(value = "专业",index = 3)
    private String major;

    @ExcelProperty(value = "年级",index = 4)
    private Integer grade;

    @ExcelProperty(value = "班级",index = 5)
    private String classes;

    @ExcelProperty(value = "电话",index = 6)
    private String phone;

    @ExcelProperty(value = "邮箱",index = 7)
    private String email;

}
