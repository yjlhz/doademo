package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: SecondRequirement
 * @projectName doademo
 * @description: 二级指标点实体类
 * @date 2022/1/2421:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondRequirement implements Serializable {

    private Integer id;

    private Integer firstRequirementNo;

    private String secondRequirementNo;

    private String description;

    private static final long serialVersionUID = 1L;

}
