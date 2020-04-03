package com.tianwen.springcloud.microservice.score.api.analysis;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartGainRateStats;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuPartGainRateStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/stu-part-gain-rate-stats")
public interface StuPartGainRateStatsMicroApi extends ICRUDMicroApi<StuPartGainRateStats> {

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    Response<StuPartGainRateStats> getList(Request request);

}
