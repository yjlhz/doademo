package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: problem
 * @projectName doademo
 * @description: 题目实体类(细分到小题)
 * @date 2022/1/2421:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem implements Serializable {

    private Integer id;

    private Integer problemNo;

    private Integer examineId;

    private Double maxScore;//满分分数(根据 学生得分/满分 得到达成度)

    private Double achieve;//该问题的达成度

    private static final long serialVersionUID = 1L;

}
