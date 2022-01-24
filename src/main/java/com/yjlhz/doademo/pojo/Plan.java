package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: Plan
 * @projectName doademo
 * @description: 教学计划实体类
 * @date 2022/1/2421:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan implements Serializable {

    private Integer id;

    private String name;

    private Integer grade;

    private String major;

    private Double minScore;//最低毕业学分

    private String description;

    private static final long serialVersionUID = 1L;

}
