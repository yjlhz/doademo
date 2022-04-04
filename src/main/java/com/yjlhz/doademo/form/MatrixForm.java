package com.yjlhz.doademo.form;

import com.yjlhz.doademo.pojo.Matrix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lhz
 * @title: MatrixForm
 * @projectName doademo
 * @description: 关系绑定
 * @date 2022/4/3 17:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatrixForm {

    private Integer problemId;

    private Integer objectiveId;

}
