package com.yjlhz.doademo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: MajorDTO
 * @projectName doademo
 * @description: 批量导入major
 * @date 2022/3/20 15:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorDTO {

    @ExcelProperty(value = "专业名称",index = 0)
    private String majorName;

}
