package com.tianwen.springcloud.microservice.score.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by kimchh on 11/11/2018.
 */
public class ExamClassFilter extends ExamGradeFilter {
    public ExamClassFilter() {

    }

    public ExamClassFilter(String examId) {
        super(examId);
    }
}
