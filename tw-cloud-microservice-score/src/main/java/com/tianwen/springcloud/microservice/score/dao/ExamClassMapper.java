package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.entity.ExamClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExamClassMapper extends MyMapper<ExamClass> {
    List<String> getClassIdList(Map<String, Object> filter);
}
