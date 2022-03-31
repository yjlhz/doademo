package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.RequirementForm;
import com.yjlhz.doademo.service.RequirementService;
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
 * @description: 指标点控制层
 * @date 2022/3/19 17:05
 */

@RestController
@Slf4j
public class RequirementController {

    @Autowired
    private RequirementService requirementService;

    @PostMapping("/uploadRequirement")
    @ApiOperation("批量上传指标点")
    ResultVO upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        return requirementService.uploadRequirement(file);
    }

    @GetMapping("/exportRequirement")
    @ApiOperation("批量导出指标点")
    void export(HttpServletResponse response){
        requirementService.exportRequirement(response);
    }

    @GetMapping("/queryRequirementList")
    ResultVO queryRequirementList(){
        return requirementService.queryRequirements();
    }

    @PostMapping("/addRequirement")
    ResultVO addRequirement(@Valid RequirementForm requirementForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return requirementService.addRequirement(requirementForm);
    }

    @PostMapping("/updateRequirement")
    ResultVO updateRequirement(@Valid RequirementForm requirementForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return requirementService.updateRequirement(requirementForm);
    }

    @PostMapping("/deleteRequirement")
    ResultVO deleteRequirement(Integer no){
        return requirementService.deleteRequirementByNo(no);
    }

}
