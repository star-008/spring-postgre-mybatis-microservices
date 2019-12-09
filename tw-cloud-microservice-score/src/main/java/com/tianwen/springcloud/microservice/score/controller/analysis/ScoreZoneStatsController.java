package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.ScoreZoneStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.ScoreZoneStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/score-zone-stats")
public class ScoreZoneStatsController extends AbstractScoreCRUDController<ScoreZoneStats> implements ScoreZoneStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}