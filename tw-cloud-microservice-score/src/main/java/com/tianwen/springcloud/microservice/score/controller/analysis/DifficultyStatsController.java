package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.DifficultyStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.DifficultyStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/difficulty-stats")
public class DifficultyStatsController extends AbstractScoreCRUDController<DifficultyStats> implements DifficultyStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}