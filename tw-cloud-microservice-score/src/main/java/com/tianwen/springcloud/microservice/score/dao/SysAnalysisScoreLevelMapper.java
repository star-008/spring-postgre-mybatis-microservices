package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysAnalysisScoreLevelMapper extends MyMapper<SysAnalysisScoreLevel> {

    List<SysAnalysisScoreLevel> getListByExam(Request request);

    List<SysAnalysisScoreLevel> getTypeListByExam(Request request);
    Integer getTypeCountByExam(Request request);
}
