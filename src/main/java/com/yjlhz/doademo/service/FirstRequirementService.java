package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @title: FirstRequirementService
 * @projectName doademo
 * @description: 一级指标点服务层接口
 * @date 2022/3/19 16:50
 */
public interface FirstRequirementService {

    ResultVO queryFirstRequirements();

    ResultVO addFirstRequirement(FirstRequirementForm firstRequirementForm);

    ResultVO updateFirstRequirement(FirstRequirementForm firstRequirementForm);

    ResultVO deleteFirstRequirementByNo(Integer no);

    /**
     * 通过文件新增一级指标点
     * @param multipartFile
     * @return
     */
    ResultVO uploadFirstRequirement(MultipartFile multipartFile);

    /**
     * 批量导出一级指标点
     */
    void exportFirstRequirement(HttpServletResponse response);

}
