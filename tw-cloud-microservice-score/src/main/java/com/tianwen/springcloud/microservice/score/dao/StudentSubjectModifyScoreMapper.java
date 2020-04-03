package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentSubjectModifyScoreMapper extends MyMapper<StudentSubjectModifyScore>, BaseMapper<StudentSubjectModifyScore> {
}
