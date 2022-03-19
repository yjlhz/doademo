package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MajorForm;
import com.yjlhz.doademo.service.MajorService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lhz
 * @title: MajorController
 * @projectName doademo
 * @description: major控制层
 * @date 2022/3/20 0:00
 */

@RestController
@Slf4j
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/queryMajorList")
    ResultVO queryMajorList(){
        return majorService.queryMajors();
    }

    @PostMapping("/addMajor")
    ResultVO addMajor(@Valid MajorForm majorForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return majorService.addMajor(majorForm);
    }

}
