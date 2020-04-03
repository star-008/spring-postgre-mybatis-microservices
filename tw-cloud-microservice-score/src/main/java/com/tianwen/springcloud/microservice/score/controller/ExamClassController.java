package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.api.ExamClassMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.ExamClass;
import com.tianwen.springcloud.microservice.score.entity.request.ExamClassFilter;
import com.tianwen.springcloud.microservice.score.service.ExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exam-class")
public class ExamClassController extends AbstractScoreCRUDController<ExamClass> implements ExamClassMicroApi {

    @Autowired
    private ExamClassService examClassScoreService;

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    public Response<String> getClassIdList(@RequestBody ExamClassFilter filter) {
        return new Response<>(examClassScoreService.getClassIdList(filter));
    }
}