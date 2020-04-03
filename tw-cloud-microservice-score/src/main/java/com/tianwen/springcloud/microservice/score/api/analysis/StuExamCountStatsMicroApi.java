package com.tianwen.springcloud.microservice.score.api.analysis;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuExamCountStats;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/stu-exam-count-stats")
public interface StuExamCountStatsMicroApi extends ICRUDMicroApi<StuExamCountStats> {

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    Response<StuExamCountStats> get(@PathVariable("studentId") String studentId);

}
