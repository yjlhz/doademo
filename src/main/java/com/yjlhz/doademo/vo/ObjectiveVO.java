package com.yjlhz.doademo.vo;

import lombok.Data;

/**
 * @author lhz
 * @title: ObjectiveVO
 * @projectName doademo
 * @description: ObjectiveVO
 * @date 2022/5/3 23:19
 */

@Data
public class ObjectiveVO {

    private Integer objectiveId;

    private Integer objectiveNo;

    private String planName;

    private String courseName;

    private String description;

    private Integer requirementNo;

    private Double achieve;

}
