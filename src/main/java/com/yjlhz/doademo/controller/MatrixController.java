package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.form.WeightForm;
import com.yjlhz.doademo.service.MatrixService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lhz
 * @title: MatrixController
 * @projectName doademo
 * @description: 关系矩阵控制层
 * @date 2022/4/4 13:48
 */

@RestController
@Slf4j
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    @PostMapping("/addMatrix")
    ResultVO addMatrix(@Valid @RequestBody List<MatrixForm> matrixForms, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return matrixService.addMatrix(matrixForms);
    }

    @PostMapping("/addWeight")
    ResultVO addWeight(@Valid @RequestBody List<WeightForm> weightForms,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return matrixService.addWeight(weightForms);
    }

}
