package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yjlhz.doademo.dto.RequirementDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.RequirementForm;
import com.yjlhz.doademo.listener.RequirementListener;
import com.yjlhz.doademo.mapper.RequirementMapper;
import com.yjlhz.doademo.pojo.Requirement;
import com.yjlhz.doademo.service.RequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: FirstRequirementImpl
 * @projectName doademo
 * @description: 一级指标点实现类
 * @date 2022/3/19 16:53
 */

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    private RequirementMapper requirementMapper;

    @Override
    public ResultVO queryRequirements() {
        List<Requirement> requirementList = requirementMapper.queryRequirementList();
        return ResultVOUtil.success(requirementList);
    }

    @Override
    public ResultVO addRequirement(RequirementForm requirementForm) {
        Requirement requirement = new Requirement();
        BeanUtils.copyProperties(requirementForm, requirement);
        int res = requirementMapper.addRequirement(requirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateRequirement(RequirementForm requirementForm) {
        Requirement requirement = requirementMapper.queryRequirementByNo(requirementForm.getRequirementNo());
        BeanUtils.copyProperties(requirementForm, requirement);
        int res = requirementMapper.updateRequirement(requirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteRequirementByNo(Integer no) {
        int res = requirementMapper.deleteRequirementByNo(no);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO uploadRequirement(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), RequirementDTO.class, new RequirementListener(requirementMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public void exportRequirement(HttpServletResponse response) {
        try {
            List<Requirement> dataLists = requirementMapper.queryRequirementList();
            List<RequirementDTO> dataList = new ArrayList<>();
            for (Requirement requirement : dataLists){
                RequirementDTO requirementDTO = new RequirementDTO();
                requirementDTO.setRequirementNo(requirement.getRequirementNo());
                requirementDTO.setDescription(requirement.getDescription());
                dataList.add(requirementDTO);
            }
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置头居中
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置 水平居中
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("指标点信息表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), RequirementDTO.class).autoCloseStream(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).sheet("指标点信息表").doWrite(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
