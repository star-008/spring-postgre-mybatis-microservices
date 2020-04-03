package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.PartDifficultyStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartDifficultyStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/part-difficulty-stats")
public class PartDifficultyStatsController extends AbstractScoreCRUDController<PartDifficultyStats> implements PartDifficultyStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}