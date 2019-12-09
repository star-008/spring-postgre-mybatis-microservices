package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.StuPartGainRateStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuPartGainRateStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stu-part-gain-rate-stats")
public class StuPartGainRateStatsStatsController extends AbstractScoreCRUDController<StuPartGainRateStats> implements StuPartGainRateStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}