package com.tianwen.springcloud.microservice.score.base;

import java.util.List;
import java.util.Map;

public interface
BaseMapper<T> {

    List<T> selectList(Map<String, Object> filter);

    Integer countList(Map<String, Object> filter);

    T get(Map<String, Object> filter);
}
