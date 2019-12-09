package com.tianwen.springcloud.microservice.score.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.Page;
import com.tianwen.springcloud.commonapi.query.OrderMethod;
import com.tianwen.springcloud.commonapi.query.Pagination;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.base.BaseService;
import com.tianwen.springcloud.datasource.util.QueryUtils;
import com.tianwen.springcloud.microservice.score.entity.request.Request;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kimchh on 11/5/2018.
 */
public abstract class ScoreBaseService<T> extends BaseService<T> {

    private Map<String, String> jsonPropertyFieldNameMap = null;

    private void initJsonPropertyFieldNameMap() {
        jsonPropertyFieldNameMap = new HashMap<>();
        Class<?> clazz = (Class<?>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        for (Field field : clazz.getDeclaredFields()) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (null != jsonProperty) {
                jsonPropertyFieldNameMap.put(jsonProperty.value(), field.getName());
            }
        }
    }

    public Map<String, String> getJsonPropertyFieldNameMap() {
        if (null == jsonPropertyFieldNameMap) {
            initJsonPropertyFieldNameMap();
        }

        return jsonPropertyFieldNameMap;
    }

    public void convertQueryTreeFromJsonProperty(QueryTree queryTree) {
        if (queryTree != null) {

            if (queryTree.getConditions() != null && !queryTree.getConditions().isEmpty()) {
                for (QueryCondition condition: queryTree.getConditions()) {
                    String jsonPropertyName =  getJsonPropertyFieldNameMap().get(condition.getFieldName());
                    if (null == jsonPropertyName) {
                        continue; //No problem, we don't need to replace those field names which don't have @JsonProperty
                    }

                    condition.setFieldName(jsonPropertyName);
                }
            }

            if (queryTree.getOrderMethods() != null && !queryTree.getOrderMethods().isEmpty()) {
                for (OrderMethod orderMethod: queryTree.getOrderMethods()) {
                    String jsonPropertyName =  getJsonPropertyFieldNameMap().get(orderMethod.getField());
                    if (null == jsonPropertyName) {
                        continue; //No problem, we don't need to replace those field names which don't have @JsonProperty
                    }

                    orderMethod.setField(jsonPropertyName);
                }
            }
        }
    }

    public BaseMapper<T> getBaseMapper() {
        return (BaseMapper<T>)getMapper();
    }

    public List<T> getList(QueryTree queryTree) {
        boolean usePagination = !Pagination.QUERY_ALL.equals(queryTree.getPagination().getPageNo());

        Page<T> page = new Page<>(queryTree.getPagination().getPageNo(), queryTree.getPagination().getNumPerPage());

        Map<String, Object> filter =
                (queryTree instanceof Request) ?
                ((Request) queryTree).toMap() :
                QueryUtils.queryTree2Map(queryTree);

        filter.put("paginationDisabled", usePagination ? "0" : 1);

        page.addAll(getBaseMapper().selectList(filter));
        if (usePagination) {
            page.setTotal(getBaseMapper().countList(filter));
        } else {
            page.setTotal(page.size());
        }

        return page;
    }

    public Integer getCount(QueryTree queryTree) {

        Map<String, Object> filter =
                (queryTree instanceof Request) ?
                        ((Request) queryTree).toMap() :
                        QueryUtils.queryTree2Map(queryTree);


        return (getBaseMapper().countList(filter));
    }

    public T get(Object id) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("id", id);
        return getBaseMapper().get(filter);
    }
}
