package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.LevelZoneStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.LevelZoneStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/level-zone-stats")
public class LevelZoneStatsController extends AbstractScoreCRUDController<LevelZoneStats> implements LevelZoneStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}