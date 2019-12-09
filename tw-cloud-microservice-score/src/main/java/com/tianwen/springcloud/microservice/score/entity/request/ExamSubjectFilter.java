package com.tianwen.springcloud.microservice.score.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by kimchh on 11/11/2018.
 */
public class ExamSubjectFilter extends ExamGradeFilter {

    public ExamSubjectFilter() {

    }

    public ExamSubjectFilter(String examId) {
        super(examId);
    }

    public ExamSubjectFilter(String examId, String gradeId) {
        this(examId);
        setGradeId(gradeId);
    }

    public ExamSubjectFilter(String examId, String gradeId, String classType) {
        this(examId, gradeId);
        setClassType(classType);
    }
}
