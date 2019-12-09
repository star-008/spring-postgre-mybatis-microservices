package com.tianwen.springcloud.microservice.score.dao.analysis;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.analysis.EqualityStats;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EqualityStatsMapper extends MyMapper<EqualityStats>, BaseMapper<EqualityStats> {

}
