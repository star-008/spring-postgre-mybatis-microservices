package com.tianwen.springcloud.microservice.score.dao.analysis;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.analysis.DifficultyStats;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DifficultyStatsMapper extends MyMapper<DifficultyStats>, BaseMapper<DifficultyStats> {

}
