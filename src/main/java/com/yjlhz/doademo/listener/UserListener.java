package com.yjlhz.doademo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.UserDTO;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lhz
 * @title: CourseListener
 * @projectName doademo
 * @description: 监听读取课程
 * @date 2022/3/18 15:38
 */
public class UserListener extends AnalysisEventListener<UserDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserListener.class);

    private static final int BATCH_COUNT = 100;
    List<UserDTO> list = new ArrayList<>();

    private final UserMapper userMapper;

    public UserListener(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void invoke(UserDTO data, AnalysisContext context) {

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
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        Iterator<UserDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            userMapper.upload(iterator.next());
        }
        LOGGER.info("存储数据库成功！");
    }
}
