package com.tianwen.springcloud.microservice.score.dao.analysis;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartDifficultyStats;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartDifficultyStatsMapper extends MyMapper<PartDifficultyStats>, BaseMapper<PartDifficultyStats> {

}
