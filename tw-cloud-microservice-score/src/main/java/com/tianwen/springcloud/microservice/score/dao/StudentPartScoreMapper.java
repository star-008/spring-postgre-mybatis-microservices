package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.StudentPartScore;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentPartScoreMapper extends MyMapper<StudentPartScore>, BaseMapper<StudentPartScore> {
}
