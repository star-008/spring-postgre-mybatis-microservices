package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.microservice.score.api.analysis.SubjectAverageScoreMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.SubjectAverageScore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subject-average-score")
public class SubjectAverageScoreController extends AbstractScoreCRUDController<SubjectAverageScore> implements SubjectAverageScoreMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {

    }

}