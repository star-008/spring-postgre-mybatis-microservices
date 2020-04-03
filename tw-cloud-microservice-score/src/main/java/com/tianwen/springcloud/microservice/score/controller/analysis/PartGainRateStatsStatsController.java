package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.PartGainRateStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartGainRateStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/part-gain-rate-stats")
public class PartGainRateStatsStatsController extends AbstractScoreCRUDController<PartGainRateStats> implements PartGainRateStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}