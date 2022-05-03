package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.StudentProblem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: StudentProblemMapper
 * @projectName doademo
 * @description: 学生作答小题得分
 * @date 2022/1/27 17:24
 */

@Mapper
@Repository
public interface StudentProblemMapper {

    int addStudentProblem(StudentProblem studentProblem);

    List<StudentProblem> queryStudentProblemsByProblemId(Integer id);

    List<StudentProblem> queryStudentProblemsBySNum(String sNum);

    StudentProblem queryStudentProblemsBySNumAndProblemId(String sNum,Integer problemId);

    int deleteStudentProblemBysNum(String sNum);

}
