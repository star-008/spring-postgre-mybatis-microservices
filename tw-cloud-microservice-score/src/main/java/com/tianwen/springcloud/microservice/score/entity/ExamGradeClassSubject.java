package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_con_cj_exam_grade_class_subject")
public class ExamGradeClassSubject extends BaseEntity {
    @Id
    private String id;

    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty( "examid")
    private String examId;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Column(name = "classid")
    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Column(name = "teacherid")
    @ApiModelProperty(name = "teacherid", value = "teacherid")
    @JsonProperty("teacherid")
    private String teacherId;

    @Transient
    private String teacherName;

    public ExamGradeClassSubject() {

    }

    public ExamGradeClassSubject(String examId) {
        this.examId = examId;
    }

    public ExamGradeClassSubject(String examId, String gradeId, String classId, String subjectId, String classType, String teacherId) {
        this.examId = examId;
        this.gradeId = gradeId;
        this.classId = classId;
        this.subjectId = subjectId;
        this.classType = classType;
        this.teacherId = teacherId;
    }

    public ExamGradeClassSubject(String examId, String gradeId, String classId, String subjectId, String classType, String teacherId, String teacherName) {
        this(examId, gradeId, classId, subjectId, classType, teacherId);
        this.teacherName = teacherName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExamGradeClassSubject)
            return equals((ExamGradeClassSubject)obj);

        return false;
    }

    private boolean equals(ExamGradeClassSubject examGradeClassSubject) {
        return StringUtils.equals(examId, examGradeClassSubject.examId)
                && StringUtils.equals(gradeId, examGradeClassSubject.gradeId)
                && StringUtils.equals(classId, examGradeClassSubject.classId)
                && StringUtils.equals(subjectId, examGradeClassSubject.subjectId);
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}