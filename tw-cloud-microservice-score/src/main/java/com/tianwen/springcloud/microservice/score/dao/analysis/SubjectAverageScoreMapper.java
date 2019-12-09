package com.tianwen.springcloud.microservice.score.dao.analysis;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.score.base.BaseMapper;
import com.tianwen.springcloud.microservice.score.entity.analysis.SubjectAverageScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectAverageScoreMapper extends MyMapper<SubjectAverageScore>, BaseMapper<SubjectAverageScore> {

}
