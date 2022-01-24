package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: Major
 * @projectName doademo
 * @description: 专业信息
 * @date 2022/1/2421:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major implements Serializable {

    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;
}
