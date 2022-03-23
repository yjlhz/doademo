package com.yjlhz.doademo.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: ProblemTarget
 * @projectName doademo
 * @description: 绑定问题和课程目标实体类
 * @date 2022/3/23 21:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("绑定问题和课程目标实体类")
public class ProblemTarget implements Serializable {

    private Integer id;

    private Integer problemId;

    private Integer targetId;

    private static final long serialVersionUID = 1L;

}
