package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.UserDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.listener.UserListener;
import com.yjlhz.doademo.mapper.UserMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.User;
import com.yjlhz.doademo.service.UserService;
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
 * @title: UserServiceImpl
 * @projectName doademo
 * @description: user实现类
 * @date 2022/4/30 10:41
 */

@Service
public class UserServiceImpl
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVO Login(String name,String password) {
        User user = userMapper.queryByName(name);
        if (user == null){
            return ResultVOUtil.error(ResultEnum.USER_DOESNOT_EXIST);
        }
        if (!password.equals(user.getPassword())){
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryUserByName(String name) {
        User user = userMapper.queryByName(name);
        return ResultVOUtil.success(user);
    }

    @Override
    public ResultVO queryUserList() {
        return ResultVOUtil.success(userMapper.queryUserList());
    }

    @Override
    public ResultVO addUser(User user) {
        User user1 = userMapper.queryByName(user.getUserName());
        if (user1 != null){
            return ResultVOUtil.error(ResultEnum.USER_EXIST);
        }
        int res = userMapper.addUser(user);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteUser(String name) {
        int res = userMapper.deleteUser(name);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateUser(User user) {
        int res = userMapper.updateUser(user);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO uploadUser(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), UserDTO.class, new UserListener(userMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public void exportUser(HttpServletResponse response) {
        try {
            List<User> dataLists = userMapper.queryUserList();
            List<UserDTO> dataList = new ArrayList<>();
            for (User user : dataLists){
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(user,userDTO);
                dataList.add(userDTO);
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
            String fileName = URLEncoder.encode("用户信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), UserDTO.class).autoCloseStream(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).sheet("用户信息表").doWrite(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
