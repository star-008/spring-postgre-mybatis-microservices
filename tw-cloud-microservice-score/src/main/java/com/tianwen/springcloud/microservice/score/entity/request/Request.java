package com.tianwen.springcloud.microservice.score.entity.request;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.util.QueryUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.*;
import java.util.Map;

/**
 * Created by kimchh on 11/22/2018.
 */
public class Request extends QueryTree {

    @ApiModelProperty(value = "查询条件(支持嵌套)")
    private Filter filter = new Filter();

    public Request() {

    }

    public Request(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Map<String, Object> toMap() {
        this.setConditions(filter.toQueryConditionList());
        Map<String, Object> map = QueryUtils.queryTree2Map(this);

        map.put("filter", filter);

        return map;
    }

    public Request copy()
    {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            ObjectInputStream in = new ObjectInputStream(byteIn);
            return (Request)in.readObject();

        } catch (IOException | ClassNotFoundException e) {

        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {

                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {

                }
            }
        }
        return null;
    }
}
