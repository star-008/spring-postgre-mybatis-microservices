package com.tianwen.springcloud.microservice.score.dao.analysis;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartGainRateStats;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuPartGainRateStats;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StuPartGainRateStatsMapper extends MyMapper<StuPartGainRateStats>, BaseMapper<StuPartGainRateStats> {

}
