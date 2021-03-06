package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.ExamGradeClassSubject;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExamGradeClassSubjectMapper extends MyMapper<ExamGradeClassSubject> {

    List<String> getGradeIdList(Map<String, Object> filter);

    List<String> getClassIdList(Map<String, Object> filter);

    List<String> getSubjectIdList(Map<String, Object> filter);
}
