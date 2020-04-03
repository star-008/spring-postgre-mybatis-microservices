package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.microservice.score.api.SysExamSubjectMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.SysExamSubject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sys-exam-subject")
public class SysExamSubjectController extends AbstractScoreCRUDController<SysExamSubject> implements SysExamSubjectMicroApi {

    @Override
    public void validate(MethodType methodType, Object p) {}
}