package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.microservice.score.api.StudentSubjectModifyScoreMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student-subject-modify-score")
public class StudentSubjectModifyScoreController extends AbstractScoreCRUDController<StudentSubjectModifyScore> implements StudentSubjectModifyScoreMicroApi {

    @Autowired
    public StudentSubjectModifyScoreController() {
        useGetListForSearch();
    }

    @Override
    public void validate(MethodType methodType, Object p) {}
}