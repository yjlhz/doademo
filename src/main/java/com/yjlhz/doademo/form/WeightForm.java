package com.yjlhz.doademo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: WeightForm
 * @projectName doademo
 * @description: 考核项目权重指定
 * @date 2022/4/5 14:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightForm {

    private Integer examineId;

    private Double weight;

}
