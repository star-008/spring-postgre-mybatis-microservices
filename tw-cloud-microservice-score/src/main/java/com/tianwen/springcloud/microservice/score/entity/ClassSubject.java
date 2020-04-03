package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimchh on 11/10/2018.
 */
@Fillable
public class ClassSubject {

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid", required = true)
    @JsonProperty("gradeid")
    private String gradeId;

    @Fill
    private DictItem grade;

    @Fill
    private List<ExamClass> examClassList = new ArrayList<>();

    @Fill
    private List<ExamSubjectScore> examSubjectList = new ArrayList<>();

    @Fill
    private List<SpecialityClass> examSpecialityClassList = new ArrayList<>();

    public ClassSubject() {

    }
    public ClassSubject(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public DictItem getGrade() {
        return grade;
    }

    public void setGrade(DictItem grade) {
        this.grade = grade;
    }

    public List<ExamClass> getExamClassList() {
        return examClassList;
    }

    public void setExamClassList(List<ExamClass> examClassList) {
        this.examClassList = examClassList;
    }

    public List<ExamSubjectScore> getExamSubjectList() {
        return examSubjectList;
    }

    public void setExamSubjectList(List<ExamSubjectScore> examSubjectList) {
        this.examSubjectList = examSubjectList;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ClassSubject)
            return equals((ClassSubject)object);

        return false;
    }

    private boolean equals(ClassSubject classSubject) {
        if (null == classSubject) return false;
        if (StringUtils.isEmpty(gradeId) && StringUtils.isEmpty(classSubject.gradeId)) return true;
        return null != gradeId && gradeId.equals(classSubject.gradeId);
    }

    public List<SpecialityClass> getExamSpecialityClassList() {
        return examSpecialityClassList;
    }

    public void setExamSpecialityClassList(List<SpecialityClass> examSpecialityClassList) {
        this.examSpecialityClassList = examSpecialityClassList;
    }
}
