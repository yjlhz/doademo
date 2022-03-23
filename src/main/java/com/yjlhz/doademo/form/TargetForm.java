package com.yjlhz.doademo.form;

import lombok.Data;

/**
 * @author lhz
 * @title: TargetForm
 * @projectName doademo
 * @description: 课程目标提交表单
 * @date 2022/3/22 23:25
 */

@Data
public class TargetForm {

    private String targetName;

    private Integer targetNo;

    private Integer planCourseId;

    private String description;

    private Integer secondRequirementId;

}
