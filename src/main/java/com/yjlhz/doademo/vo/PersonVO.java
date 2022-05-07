package com.yjlhz.doademo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lhz
 * @title: PersonVO
 * @projectName doademo
 * @description: PersonVO
 * @date 2022/5/7 19:05
 */

@Data
public class PersonVO {

    private List<StudentProblemVO> studentProblemVOList;

    private List<StudentObjectiveVO> studentObjectiveVOList;

}
