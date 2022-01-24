package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: StudentProblem
 * @projectName doademo
 * @description: 学生考试题目的得分情况
 * @date 2022/1/2421:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProblem implements Serializable {

    private String sNum;

    private Integer problemId;

    private Double score;

    private static final long serialVersionUID = 1L;

}
