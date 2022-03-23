package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.FirstRequirementDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.listener.FirstRequirementListener;
import com.yjlhz.doademo.mapper.FirstRequirementMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.FirstRequirement;
import com.yjlhz.doademo.service.FirstRequirementService;
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
public class FirstRequirementServiceImpl implements FirstRequirementService {

    @Autowired
    private FirstRequirementMapper firstRequirementMapper;

    @Override
    public ResultVO queryFirstRequirements() {
        List<FirstRequirement> firstRequirementList = firstRequirementMapper.queryFirstRequirementList();
        return ResultVOUtil.success(firstRequirementList);
    }

    @Override
    public ResultVO addFirstRequirement(FirstRequirementForm firstRequirementForm) {
        FirstRequirement firstRequirement = new FirstRequirement();
        BeanUtils.copyProperties(firstRequirementForm,firstRequirement);
        int res = firstRequirementMapper.addFirstRequirement(firstRequirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateFirstRequirement(FirstRequirementForm firstRequirementForm) {
        FirstRequirement firstRequirement = firstRequirementMapper.queryFirstRequirementByNo(firstRequirementForm.getFirstRequirementNo());
        BeanUtils.copyProperties(firstRequirementForm,firstRequirement);
        int res = firstRequirementMapper.updateFirstRequirement(firstRequirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteFirstRequirementByNo(Integer no) {
        int res = firstRequirementMapper.deleteFirstRequirementByNo(no);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO uploadFirstRequirement(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), FirstRequirementDTO.class, new FirstRequirementListener(firstRequirementMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public void exportFirstRequirement(HttpServletResponse response) {
        try {
            List<FirstRequirement> dataLists = firstRequirementMapper.queryFirstRequirementList();
            List<FirstRequirementDTO> dataList = new ArrayList<>();
            for (FirstRequirement firstRequirement : dataLists){
                FirstRequirementDTO firstRequirementDTO = new FirstRequirementDTO();
                firstRequirementDTO.setFirstRequirementNo(firstRequirement.getFirstRequirementNo());
                firstRequirementDTO.setDescription(firstRequirement.getDescription());
                dataList.add(firstRequirementDTO);
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
            String fileName = URLEncoder.encode("一级指标点信息表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), FirstRequirementDTO.class).autoCloseStream(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).sheet("一级指标点信息表").doWrite(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
