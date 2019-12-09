package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.ExamGradeFilter;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import com.tianwen.springcloud.microservice.score.service.ExamSubjectScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exam-subject-score")
public class ExamSubjectScoreController extends AbstractScoreCRUDController<ExamSubjectScore> implements ExamSubjectScoreMicroApi {

    @Autowired
    private ExamSubjectScoreService examSubjectScoreService;

    public ExamSubjectScoreController() {
        useGetListForSearch();
    }

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    public Response<String> getGradeIdList(@RequestBody ExamGradeFilter filter) {
        return new Response<>(examSubjectScoreService.getGradeIdList(filter));
    }

    @Override
    public Response<String> getSubjectIdList(@RequestBody ExamSubjectFilter filter) {
        return new Response<>(examSubjectScoreService.getSubjectIdList(filter));
    }

    @Override
    public Response<ExamSubjectScore> getPubStatusList(@RequestBody QueryTree queryTree) {
        return new Response<>(examSubjectScoreService.getPubStatusList(queryTree));
    }
}