package com.tianwen.springcloud.microservice.base.dao;

import com.tianwen.springcloud.datasource.mapper.MyMapper;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DictItemMapper extends MyMapper<DictItem> {

    List<DictItem> queryDictItemForList(Map<String, Object> map);

    int count(Map<String, Object> map);

    String getMaxId();

    void doUpdate(DictItem entity);

    void updateType(Map<String, Object> map);

    void doRemove(String dictid);

    DictItem getByDictInfo(DictItem dictItem);
}
