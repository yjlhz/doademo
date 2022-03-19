package com.yjlhz.doademo.form;

import lombok.Data;

/**
 * @author lhz
 * @title: SecondRequirementForm
 * @projectName doademo
 * @description: 二级指标点提交表单
 * @date 2022/3/19 20:26
 */
@Data
public class SecondRequirementForm {

    private Integer firstRequirementNo;

    private String secondRequirementNo;

    private String description;

}
