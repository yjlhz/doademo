package com.yjlhz.doademo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.RequirementForm;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Requirement;
import com.yjlhz.doademo.service.RequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lhz
 * @title: FirstRequirementController
 * @projectName doademo
 * @description: 指标点控制层
 * @date 2022/3/19 17:05
 */

@Controller
@Slf4j
@RequestMapping("/requirement")
public class RequirementController {

    @Autowired
    private RequirementService requirementService;

    @PostMapping("/upload")
    @ApiOperation("批量上传指标点")
    String upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        requirementService.uploadRequirement(file);
        return "redirect:/requirement/queryRequirementList";
    }

    @GetMapping("/toUpload")
    public String toUpload(){
        return "uploadRequirement";
    }

    @GetMapping("/export")
    @ApiOperation("批量导出指标点")
    void export(HttpServletResponse response){
        requirementService.exportRequirement(response);
    }

    @GetMapping("/queryRequirementList")
    String queryRequirementList(Model model,
                                @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Requirement> requirementList = (List<Requirement>) requirementService.queryRequirements().getData();
            System.out.println("分页数据："+requirementList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Requirement> pageInfo = new PageInfo<Requirement>(requirementList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("requirementList",requirementList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "requirementList";
    }

    @PostMapping("/addRequirement")
    String addRequirement(RequirementForm requirementForm){
        requirementService.addRequirement(requirementForm);
        return "redirect:/requirement/queryRequirementList";
    }

    @GetMapping("/toAdd")
    public String toAdd(){
        return "addRequirement";
    }

    @PostMapping("/updateRequirement")
    String updateRequirement(RequirementForm requirementForm){
        requirementService.updateRequirement(requirementForm);
        return "redirect:/requirement/queryRequirementList";
    }

    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id")Integer id,Model model){
        Requirement requirement = (Requirement) requirementService.queryRequirementById(id).getData();
        model.addAttribute("requirement",requirement);
        return "updateRequirement";
    }

    @GetMapping("/deleteRequirement/{id}")
    String deleteRequirement(@PathVariable Integer id){
        requirementService.deleteRequirementById(id);
        return "redirect:/requirement/queryRequirementList";
    }

}
