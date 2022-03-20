package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.MajorDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MajorForm;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.listener.MajorListener;
import com.yjlhz.doademo.mapper.MajorMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Major;
import com.yjlhz.doademo.service.MajorService;
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
 * @title: MajorServiceImpl
 * @projectName doademo
 * @description: major业务层实现类
 * @date 2022/1/29 19:57
 */

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public ResultVO addMajor(MajorForm majorForm) {
        Major major = new Major();
        BeanUtils.copyProperties(majorForm,major);
        int res = majorMapper.addMajor(major);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryMajors() {
        List<Major> majorList = majorMapper.queryMajorList();
        return ResultVOUtil.success(majorList);
    }

    @Override
    public ResultVO queryMajorByName(String name) {
        Major major = majorMapper.queryMajorByName(name);
        return ResultVOUtil.success(major);
    }

    @Override
    public ResultVO uploadMajor(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), MajorDTO.class, new MajorListener(majorMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public void exportMajor(HttpServletResponse response) {
        try {
            List<Major> dataLists = majorMapper.queryMajorList();
            List<MajorDTO> dataList = new ArrayList<>();
            for (Major major : dataLists){
                MajorDTO majorDTO = new MajorDTO();
                majorDTO.setMajorName(major.getMajorName());
                dataList.add(majorDTO);
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
            String fileName = URLEncoder.encode("专业信息表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), MajorDTO.class).autoCloseStream(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).sheet("专业信息表").doWrite(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
