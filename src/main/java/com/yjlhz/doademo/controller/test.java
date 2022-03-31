package com.yjlhz.doademo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.listener.CourseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lhz
 * @title: test
 * @projectName doadem
 * @description: TODO
 * @date 2022/3/22 21:33
 */

@RestController
@Slf4j
public class test {

    @PostMapping("/test")
    void test(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(),null, new testListener()).sheet().doRead();
    }
}
