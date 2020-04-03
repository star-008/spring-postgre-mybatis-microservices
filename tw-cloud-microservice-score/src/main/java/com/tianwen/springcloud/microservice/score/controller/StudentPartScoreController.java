package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.microservice.score.api.StudentPartScoreMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.StudentPartScore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student-part-score")
public class StudentPartScoreController extends AbstractScoreCRUDController<StudentPartScore> implements StudentPartScoreMicroApi {

    public StudentPartScoreController() {
        useGetListForSearch();
    }

    @Override
    public void validate(MethodType methodType, Object p) {}
}