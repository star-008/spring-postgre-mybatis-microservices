package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.microservice.score.api.ExamGradeClassSubjectMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.ExamGradeClassSubject;
import com.tianwen.springcloud.microservice.score.service.ExamGradeClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exam-grade-class-subject")
public class ExamGradeClassSubjectController extends AbstractScoreCRUDController<ExamGradeClassSubject> implements ExamGradeClassSubjectMicroApi {

    @Autowired
    protected ExamGradeClassSubjectService examGradeClassSubjectService;

    @Override
    public void validate(MethodType methodType, Object p) {}
}