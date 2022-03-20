package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.MajorForm;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @title: MajorService
 * @projectName doademo
 * @description: major业务层
 * @date 2022/1/29 19:55
 */
public interface MajorService {

    ResultVO addMajor(MajorForm majorForm);

    ResultVO queryMajors();

    ResultVO queryMajorByName(String name);

    /**
     * 通过文件新增专业
     * @param multipartFile
     * @return
     */
    ResultVO uploadMajor(MultipartFile multipartFile);

    /**
     * 批量导出专业
     */
    void exportMajor(HttpServletResponse response);

}
