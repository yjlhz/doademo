package com.yjlhz.doademo.form;

import lombok.Data;

/**
 * @author lhz
 * @title: PlanForm
 * @projectName doademo
 * @description: 培养计划提交表单
 * @date 2022/3/19 15:43
 */

@Data
public class PlanForm {

    private Integer id;

    private String name;

    private Integer grade;

    private String major;

    private Double minScore;//最低毕业学分

    private String description;

    private String requirement;//逗号分隔

}
