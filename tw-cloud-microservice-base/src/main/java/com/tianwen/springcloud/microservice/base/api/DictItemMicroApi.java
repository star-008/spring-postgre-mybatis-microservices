package com.tianwen.springcloud.microservice.base.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 用户相关对外接口
 * 
 * @author wangbin
 * @version [版本号, 2017年5月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@FeignClient(value = "base-service", url = "http://localhost:2226/base-service/dictitem")
public interface DictItemMicroApi extends ICRUDMicroApi<DictItem>
{

    @RequestMapping(value = "/getByDictInfo", method = RequestMethod.POST)
    Response<DictItem> getByDictInfo(@RequestBody DictItem dictItem);

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Response<DictItem> getList(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/getCount", method = RequestMethod.POST)
    public Response<Integer> getCount(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response<DictItem> insert(@RequestBody DictItem entity);

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Response<DictItem> modify(@RequestBody DictItem entity);

    @RequestMapping(value = "/remove/{dictid}", method = RequestMethod.GET)
    public Response<DictItem> remove(@PathVariable(value = "dictid") String dictid);

    @RequestMapping(value = "/batchAddUpdate", method = RequestMethod.POST)
    public Response<DictItem> batchAddUpdate(@RequestBody List<DictItem> itemList);
}
