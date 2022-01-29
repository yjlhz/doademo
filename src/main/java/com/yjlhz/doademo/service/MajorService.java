package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.MajorForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: MajorService
 * @projectName doademo
 * @description: major业务层
 * @date 2022/1/29 19:55
 */
public interface MajorService {

    ResultVO addMajor(MajorForm majorForm);

}
