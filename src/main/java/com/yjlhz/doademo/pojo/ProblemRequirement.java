package com.yjlhz.doademo.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: ProblemRequirement
 * @projectName doademo
 * @description: 绑定问题和二级指标点的实体类
 * @date 2022/3/23 21:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("绑定问题和二级指标点的实体类")
public class ProblemRequirement implements Serializable {

    private Integer id;

    private Integer problemId;

    private Integer requirementNo;

    private static final long serialVersionUID = 1L;

}
