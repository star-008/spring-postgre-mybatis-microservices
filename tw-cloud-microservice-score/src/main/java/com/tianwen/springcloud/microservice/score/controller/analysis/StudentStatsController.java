package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.StudentStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.StudentStats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student-stats")
public class StudentStatsController extends AbstractScoreCRUDController<StudentStats> implements StudentStatsMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}