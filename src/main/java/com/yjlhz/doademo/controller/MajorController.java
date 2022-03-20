package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MajorForm;
import com.yjlhz.doademo.service.MajorService;
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

    @PostMapping("/upload")
    @ApiOperation("批量上传专业")
    ResultVO uploadCourse(@RequestParam(value = "file", required = false) MultipartFile file) {
        return majorService.uploadMajor(file);
    }

    @GetMapping("/export")
    @ApiOperation("批量导出专业")
    void exportCourse(HttpServletResponse response){
        majorService.exportMajor(response);
    }

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

    @GetMapping("/queryMajorByName")
    ResultVO queryMajorByName(String name){
        return majorService.queryMajorByName(name);
    }

}
