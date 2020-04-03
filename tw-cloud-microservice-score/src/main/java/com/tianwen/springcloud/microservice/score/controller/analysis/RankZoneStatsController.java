package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.RankZoneStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankZoneStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rank-zone-stats")
public class RankZoneStatsController extends AbstractScoreCRUDController<RankZoneStats> implements RankZoneStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}