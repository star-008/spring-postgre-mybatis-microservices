package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "t_e_sys_exam_subject")
@Fillable
public class SysExamSubject extends BaseEntity {
    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid", required = true)
    @JsonProperty("gradeid")
    private String gradeId;

    @Fill
    private DictItem grade;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private  DictItem subject;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public DictItem getGrade() {
        return grade;
    }

    public void setGrade(DictItem grade) {
        this.grade = grade;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }
}
