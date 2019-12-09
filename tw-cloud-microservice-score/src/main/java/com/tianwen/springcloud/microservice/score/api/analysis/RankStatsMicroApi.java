package com.tianwen.springcloud.microservice.score.api.analysis;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/rank-stats")
public interface RankStatsMicroApi extends ICRUDMicroApi<RankStats> {
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @SystemControllerLog(description = "IStatsMicroApi根据条件 查询，带分页")
    Response<RankStats> getList(@RequestBody Request request);

}
