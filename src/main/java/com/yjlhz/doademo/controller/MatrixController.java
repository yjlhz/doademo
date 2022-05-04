package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.form.WeightForm;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.service.ExamineService;
import com.yjlhz.doademo.service.MatrixService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: MatrixController
 * @projectName doademo
 * @description: 关系矩阵控制层
 * @date 2022/4/4 13:48
 */

@Controller
@Slf4j
@RequestMapping("/matrix")
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    @Autowired
    private ExamineService examineService;

    @PostMapping("/addMatrix")
    String addMatrix(@Param("planId")Integer planId, @Param("courseId")Integer courseId,
                     @Param("problemId")Integer[] problemId, @Param("objectiveId")Integer[] objectiveId,
                     Model model){
        List<MatrixForm> matrixForms = new ArrayList<>();
        for (int i = 0;i<problemId.length;i++){
            MatrixForm matrixForm = new MatrixForm();
            matrixForm.setProblemId(problemId[i]);
            matrixForm.setObjectiveId(objectiveId[i]);
        }
        matrixService.addMatrix(matrixForms);
        List<Examine> examineList = (List<Examine>) examineService.queryExaminesByPlanCourseId(planId, courseId).getData();
        model.addAttribute("examineList",examineList);
        return "addWeight";
    }

    @PostMapping("/addWeight")
    String addWeight(@Param("examineId")Integer[] examineId,@Param("weight")Double[] weight){
        List<WeightForm> weightForms = new ArrayList<>();
        for (int i = 0;i<examineId.length;i++){
            WeightForm weightForm = new WeightForm();
            weightForm.setExamineId(examineId[i]);
            weightForm.setWeight(weight[i]);
            weightForms.add(weightForm);
        }
        matrixService.addWeight(weightForms);
        return "redirect:/examine/queryExamineList";
    }

}
