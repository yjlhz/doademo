package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Major;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: MajorMapper
 * @projectName doademo
 * @description: 专业信息dao层
 * @date 2022/1/27 16:43
 */

@Mapper
@Repository
public interface MajorMapper {

    List<Major> queryMajorList();

    Major queryMajorByName(String name);

    Major queryMajorById(Integer id);

    int addMajor(Major major);

}
