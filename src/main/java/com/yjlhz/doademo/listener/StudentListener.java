package com.yjlhz.doademo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.StudentDTO;
import com.yjlhz.doademo.form.StudentForm;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lhz
 * @title: CourseListener
 * @projectName doademo
 * @description: 监听读取学生信息
 * @date 2022/3/18 15:38
 */
public class StudentListener extends AnalysisEventListener<StudentDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentListener.class);

    private static final int BATCH_COUNT = 100;
    List<StudentDTO> list = new ArrayList<>();

    private final StudentService studentService;

    public StudentListener(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void invoke(StudentDTO data, AnalysisContext context) {

        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    @Transactional
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        Iterator<StudentDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            StudentDTO studentDTO = iterator.next();
            StudentForm studentForm = new StudentForm();
            BeanUtils.copyProperties(studentDTO,studentForm);
            studentService.addStudent(studentForm);
        }
        LOGGER.info("存储数据库成功！");
    }
}
