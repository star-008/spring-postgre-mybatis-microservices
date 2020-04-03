package com.tianwen.springcloud.microservice.score.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by kimchh on 11/11/2018.
 */
public class ExamGradeFilter extends ExamTypeFilter {

    public ExamGradeFilter() {

    }

    public ExamGradeFilter(String examId) {
        setExamId(examId);
    }

    public ExamGradeFilter(String examId, String examTypeId) {
        setExamId(examId);
        setExamTypeId(examTypeId);
    }
}
