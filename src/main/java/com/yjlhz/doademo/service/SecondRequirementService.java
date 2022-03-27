package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.form.SecondRequirementForm;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @title: SecondRequirementService
 * @projectName doademo
 * @description: 二级指标点服务层接口
 * @date 2022/3/19 20:38
 */
public interface SecondRequirementService {
    ResultVO querySecondRequirements();

    ResultVO addSecondRequirement(SecondRequirementForm secondRequirementForm);

    ResultVO updateSecondRequirementById(SecondRequirementForm secondRequirementForm);

    ResultVO deleteSecondRequirementByNo(String no);

    /**
     * 通过文件新增二级指标点
     * @param multipartFile
     * @return
     */
    ResultVO uploadSecondRequirement(MultipartFile multipartFile);

    /**
     * 批量导出二级指标点
     */
    void exportSecondRequirement(HttpServletResponse response);
}
