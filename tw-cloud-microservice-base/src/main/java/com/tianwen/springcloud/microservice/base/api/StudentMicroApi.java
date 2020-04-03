package com.tianwen.springcloud.microservice.base.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.Student;
import org.springframework.cloud.netflix.feign.FeignClient;
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
@FeignClient(value = "base-service", url = "http://localhost:2226/base-service/student")
public interface StudentMicroApi extends ICRUDMicroApi<Student>
{
    @RequestMapping(value = "/getStudentList", method = RequestMethod.POST)
    public Response<Student> getStudentList(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/batchAddUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi批量新增")
    public Response<Student> batchAddUpdate(@RequestBody List<Student> StudentList);

    @RequestMapping(value = "/getClassIdList", method = RequestMethod.POST)
    public Response<String> getClassIdList(@RequestBody QueryTree queryTree);
}
