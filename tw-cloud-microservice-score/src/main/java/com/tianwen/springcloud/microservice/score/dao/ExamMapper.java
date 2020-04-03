package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExamMapper extends MyMapper<Exam>, BaseMapper<Exam> {

    List<Exam> getSimpleList(Map<String, Object> filter);

    List<String> getTermIdList(Map<String, Object> filter);

    List<String> getTypeIdList(Map<String, Object> filter);
}
