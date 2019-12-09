package com.tianwen.springcloud.scoreapi.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Term;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.entity.*;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimhj on 2/1/2019.
 */
public class ExamSyncInfo {

    private Exam exam;

    @ApiModelProperty(name = "studentSubjectScoreList", value = "studentSubjectScoreList")
    @JsonProperty("scoreList")
    private List<StudentSubjectScore> studentSubjectScoreList;

    public ExamSyncInfo() {

    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<StudentSubjectScore> getStudentSubjectScoreList() {
        return studentSubjectScoreList;
    }

    public void setStudentSubjectScoreList(List<StudentSubjectScore> studentSubjectScoreList) {
        this.studentSubjectScoreList = studentSubjectScoreList;
    }
}
