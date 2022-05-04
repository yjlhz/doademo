package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lhz
 * @title: ExamineService
 * @projectName doademo
 * @description: 考核接口
 * @date 2022/3/20 0:41
 */
public interface ExamineService {

    ResultVO uploadExamineData(MultipartFile file,Integer planId,Integer courseId);

    ResultVO queryExaminesByPlanCourseId(Integer planId,Integer courseId);

    ResultVO addExamine(ExamineForm examineForm);

    ResultVO queryExamines();

    ResultVO deleteExamineById(Integer id);

}
