package com.tianwen.springcloud.scoreapi.api.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartGainRateStats;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuPartGainRateStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

public interface StuPartGainRateStatsApi {

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    Response<StuPartGainRateStats> getList(@RequestBody Request request);
}


