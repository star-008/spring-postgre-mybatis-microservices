package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_con_cj_exam_class")
@Fillable
public class ExamClass extends BaseEntity {
    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    private Exam exam;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid", required = true)
    @JsonProperty("gradeid")
    private String gradeId;

    @Fill
    private DictItem grade;

    @Column(name = "classid")
    @ApiModelProperty(name = "classid", value = "classid", required = true)
    @JsonProperty("classid")
    private String classId;

    @Column(name = "gradeidx")
    @ApiModelProperty(name = "gradeidx", value = "gradeidx")
    @JsonProperty("gradeidx")
    private String gradeIdx;

    @Column(name = "classidx")
    @ApiModelProperty(name = "classidx", value = "classidx")
    @JsonProperty("classidx")
    private String classIdx;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "classtag")
    @ApiModelProperty(name = "classtag", value = "classtag")
    @JsonProperty("classtag")
    private String classTag;

    @Column(name = "htid")
    @ApiModelProperty(name = "htid", value = "htid")
    @JsonProperty("htid")
    private String htId;

    @Column(name = "managerid")
    @ApiModelProperty(name = "managerid", value = "managerid")
    @JsonProperty("managerid")
    private String managerId;

    @Column(name = "managername")
    @ApiModelProperty(name = "managername", value = "managername")
    @JsonProperty("managername")
    private String managerName;

    @Column(name = "submittime")
    @ApiModelProperty(name = "submittime", value = "submittime")
    @JsonProperty("submittime")
    private Timestamp submitTime;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    public ExamClass() {

    }

    public ExamClass(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public DictItem getGrade() {
        return grade;
    }

    public void setGrade(DictItem grade) {
        this.grade = grade;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getGradeIdx() {
        return gradeIdx;
    }

    public void setGradeIdx(String gradeIdx) {
        this.gradeIdx = gradeIdx;
    }

    public String getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(String classIdx) {
        this.classIdx = classIdx;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassTag() {
        return classTag;
    }

    public void setClassTag(String classTag) {
        this.classTag = classTag;
    }

    public String getHtId() {
        return htId;
    }

    public void setHtId(String htId) {
        this.htId = htId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }
}