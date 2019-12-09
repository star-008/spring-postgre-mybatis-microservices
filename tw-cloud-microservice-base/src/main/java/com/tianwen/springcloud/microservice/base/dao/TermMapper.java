package com.tianwen.springcloud.microservice.base.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.base.entity.Term;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TermMapper extends MyMapper<Term>
{
    List<Term> getList(Map<String, Object> param);

    int getCount(Map<String, Object> map);
}
