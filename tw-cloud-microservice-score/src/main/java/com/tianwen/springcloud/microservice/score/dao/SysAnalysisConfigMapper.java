package com.tianwen.springcloud.microservice.score.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysAnalysisConfigMapper extends MyMapper<SysAnalysisConfig> {
}
