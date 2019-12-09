package com.tianwen.springcloud.microservice.score.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by kimchh on 11/11/2018.
 */
public class StudentSubjectFilter extends ExamSubjectFilter {

    public StudentSubjectFilter() {

    }

    public StudentSubjectFilter(String examId) {
        super(examId);
    }

    public StudentSubjectFilter(String examId, String gradeId) {
        super(examId, gradeId);
    }
}
