package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.form.WeightForm;
import com.yjlhz.doademo.vo.ResultVO;

import java.util.List;

/**
 * @author lhz
 * @title: MatrixService
 * @projectName doademo
 * @description: 处理关系矩阵
 * @date 2022/4/4 13:53
 */
public interface MatrixService {

    ResultVO addMatrix(List<MatrixForm> matrixForms);

    ResultVO addWeight(List<WeightForm> weightForms);

}
