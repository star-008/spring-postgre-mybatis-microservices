package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.ClassTopCountStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.ClassTopCountStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/class-top-count-stats")
public class ClassTopCountStatsController extends AbstractScoreCRUDController<ClassTopCountStats> implements ClassTopCountStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}