package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.request.ExamTypeFilter;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/exam")
public interface ExamMicroApi extends ICRUDMicroApi<Exam> {

    @RequestMapping(value = "/getSimpleList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetList")
    Response<Exam> getSimpleList(@RequestBody Request request);

    @RequestMapping(value = "/getTermList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetTermList")
    Response<String> getTermIdList(@RequestBody Filter filter);

    @RequestMapping(value = "/getTypeIdList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp GetTypeList")
    Response<String> getTypeIdList(@RequestBody ExamTypeFilter filter);
}
