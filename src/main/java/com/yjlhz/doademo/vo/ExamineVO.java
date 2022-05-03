package com.yjlhz.doademo.vo;

import lombok.Data;

/**
 * @author lhz
 * @title: ExamineVO
 * @projectName doademo
 * @description: ExamineVO
 * @date 2022/5/3 16:28
 */
@Data
public class ExamineVO {

    private Integer id;

    private String planName;

    private String courseName;

    private String description;

    private Double weight;//占课程考核权重

}
