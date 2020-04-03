package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.ExamClass;
import com.tianwen.springcloud.microservice.score.entity.request.ExamClassFilter;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/exam-class")
public interface ExamClassMicroApi extends ICRUDMicroApi<ExamClass> {
    
    @RequestMapping(value = "/getClassIdList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetClassList")
    Response<String> getClassIdList(@RequestBody ExamClassFilter filter);
}
