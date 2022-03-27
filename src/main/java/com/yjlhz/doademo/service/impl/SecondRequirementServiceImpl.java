package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yjlhz.doademo.dto.FirstRequirementDTO;
import com.yjlhz.doademo.dto.SecondRequirementDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.SecondRequirementForm;
import com.yjlhz.doademo.listener.FirstRequirementListener;
import com.yjlhz.doademo.listener.SecondRequirementListener;
import com.yjlhz.doademo.mapper.SecondRequirementMapper;
import com.yjlhz.doademo.pojo.FirstRequirement;
import com.yjlhz.doademo.pojo.SecondRequirement;
import com.yjlhz.doademo.service.SecondRequirementService;
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
 * @title: SecondRequirementImpl
 * @projectName doademo
 * @description: 二级指标点实现类
 * @date 2022/3/19 20:40
 */
@Service
public class SecondRequirementServiceImpl implements SecondRequirementService {

    @Autowired
    private SecondRequirementMapper secondRequirementMapper;

    @Override
    public ResultVO querySecondRequirements() {
        List<SecondRequirement> secondRequirementList = secondRequirementMapper.querySecondRequirementList();
        return ResultVOUtil.success(secondRequirementList);
    }

    @Override
    public ResultVO addSecondRequirement(SecondRequirementForm secondRequirementForm) {
        SecondRequirement secondRequirement = new SecondRequirement();
        BeanUtils.copyProperties(secondRequirementForm,secondRequirement);
        int res = secondRequirementMapper.addSecondRequirement(secondRequirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateSecondRequirementById(SecondRequirementForm secondRequirementForm) {
        SecondRequirement secondRequirement = secondRequirementMapper.querySecondRequirementByNo(secondRequirementForm.getSecondRequirementNo());
        BeanUtils.copyProperties(secondRequirementForm,secondRequirement);
        int res = secondRequirementMapper.updateSecondRequirementById(secondRequirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteSecondRequirementByNo(String no) {
        int res = secondRequirementMapper.deleteSecondRequirementByNo(no);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO uploadSecondRequirement(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), SecondRequirementDTO.class, new SecondRequirementListener(secondRequirementMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public void exportSecondRequirement(HttpServletResponse response) {
        try {
            List<SecondRequirement> dataLists = secondRequirementMapper.querySecondRequirementList();
            List<SecondRequirementDTO> dataList = new ArrayList<>();
            for (SecondRequirement secondRequirement : dataLists){
                SecondRequirementDTO secondRequirementDTO = new SecondRequirementDTO();
                secondRequirementDTO.setFirstRequirementNo(secondRequirement.getFirstRequirementNo());
                secondRequirementDTO.setDescription(secondRequirement.getDescription());
                secondRequirementDTO.setSecondRequirementNo(secondRequirement.getSecondRequirementNo());
                dataList.add(secondRequirementDTO);
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
            String fileName = URLEncoder.encode("二级指标点信息表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), SecondRequirementDTO.class).autoCloseStream(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).sheet("二级指标点信息表").doWrite(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
