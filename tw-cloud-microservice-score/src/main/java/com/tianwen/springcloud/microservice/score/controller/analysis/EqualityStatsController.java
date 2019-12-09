package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.EqualityStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.EqualityStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/equality-stats")
public class EqualityStatsController extends AbstractScoreCRUDController<EqualityStats> implements EqualityStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}