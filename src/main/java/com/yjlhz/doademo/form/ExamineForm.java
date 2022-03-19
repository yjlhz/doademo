package com.yjlhz.doademo.form;

import lombok.Data;

/**
 * @author lhz
 * @title: ExamineForm
 * @projectName doademo
 * @description: 考核提交表单
 * @date 2022/3/20 0:40
 */
@Data
public class ExamineForm {

    private Integer planCourseId;

    private String description;

    private Double weight;//此处存的权重是对二级指标点的支撑权重

}
