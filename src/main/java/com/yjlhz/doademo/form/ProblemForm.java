package com.yjlhz.doademo.form;

import lombok.Data;

/**
 * @author lhz
 * @title: ProblemForm
 * @projectName doademo
 * @description: 问题提交表单
 * @date 2022/3/20 12:50
 */
@Data
public class ProblemForm {

    private Integer problemNo;

    private Integer examineId;

    private Double maxScore;//满分分数(根据 学生得分/满分 得到达成度)

    private Double weight;//根据课程所占权重和本题所占分数算出本题权重，作为二级指标点的数据支撑

}
