package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.service.FirstRequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author lhz
 * @title: FirstRequirementController
 * @projectName doademo
 * @description: 一级指标点控制层
 * @date 2022/3/19 17:05
 */

@RestController
@Slf4j
public class FirstRequirementController {

    @Autowired
    private FirstRequirementService firstRequirementService;

    @PostMapping("/uploadFirstRequirement")
    @ApiOperation("批量上传一级指标点")
    ResultVO uploadCourse(@RequestParam(value = "file", required = false) MultipartFile file) {
        return firstRequirementService.uploadFirstRequirement(file);
    }

    @GetMapping("/exportFirstRequirement")
    @ApiOperation("批量导出一级指标点")
    void exportCourse(HttpServletResponse response){
        firstRequirementService.exportFirstRequirement(response);
    }

    @GetMapping("/queryFirstRequirementList")
    ResultVO queryFirstRequirementList(){
        return firstRequirementService.queryFirstRequirements();
    }

    @PostMapping("/addFirstRequirement")
    ResultVO addFirstRequirement(@Valid FirstRequirementForm firstRequirementForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return firstRequirementService.addFirstRequirement(firstRequirementForm);
    }

    @PostMapping("/updateFirstRequirement")
    ResultVO updateFirstRequirement(@Valid FirstRequirementForm firstRequirementForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return firstRequirementService.updateFirstRequirement(firstRequirementForm);
    }

    @PostMapping("/deleteFirstRequirement")
    ResultVO deleteFirstRequirement(Integer no){
        return firstRequirementService.deleteFirstRequirementByNo(no);
    }

}
