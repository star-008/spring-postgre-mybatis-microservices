package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentSubjectScoreMapper extends MyMapper<StudentSubjectScore>, BaseMapper<StudentSubjectScore> {

    List<StudentSubjectScore> getScoreEnteredList(Map<String, Object> filter);
    List<StudentSubjectScore> getScoreUnenteredList(Map<String, Object> filter);
    List<StudentSubjectScore> getExamMissedList(Map<String, Object> filter);
    List<StudentSubjectScore> getScoreInvalidList(Map<String, Object> filter);

    Integer getScoreEnteredCount(Map<String, Object> filter);
    Integer getScoreUnenteredCount(Map<String, Object> filter);
    Integer getExamMissedCount(Map<String, Object> filter);
    Integer getScoreInvalidCount(Map<String, Object> filter);
}
