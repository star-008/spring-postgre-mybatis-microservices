package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.RankStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rank-stats")
public class RankStatsController extends AbstractScoreCRUDController<RankStats> implements RankStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}