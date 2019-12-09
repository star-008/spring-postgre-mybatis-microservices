package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * Created by R.JinHyok on 2018.11.18.
 */

@Fillable
public class RankStats {

    @Id
    private String id;

    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    @ApiModelProperty(name = "examname", value = "examname")
    @JsonProperty("examname")
    private String examName;

    @ApiModelProperty(name = "stuid", value = "stuid")
    @JsonProperty("stuid")
    private String studentId;

    @ApiModelProperty(name = "stuname", value = "stuname")
    @JsonProperty( "stuname")
    private String studentName;

    @ApiModelProperty(name = "stuno", value = "stuno")
    @JsonProperty( "stuno")
    private String studentNo;

    @ApiModelProperty(name = "stuexamno", value = "stuexamno")
    @JsonProperty( "stuexamno")
    private String studentExamNo;

    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @ApiModelProperty(name = "realscore", value = "realscore")
    @JsonProperty( "realscore")
    private String realscore;

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty( "score")
    private String score;

    @ApiModelProperty(name = "sturankinclass", value = "sturankinclass")
    @JsonProperty("sturankinclass")
    private String stuRankInClass;

    @ApiModelProperty(name = "sturankingrade", value = "sturankingrade")
    @JsonProperty("sturankingrade")
    private String stuRankInGrade;

    @ApiModelProperty(name = "degree", value = "degree")
    @JsonProperty("degree")
    private String degree;

    @ApiModelProperty(name = "sex", value = "sex")
    @JsonProperty("sex")
    private String sex;

    @ApiModelProperty("sexname")
    @JsonProperty("sexname")
    private String sexName;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRealscore() {
        return realscore;
    }

    public void setRealscore(String realScore) {
        this.realscore = realScore;
    }

    public String getStudentExamNo() {
        return studentExamNo;
    }

    public void setStudentExamNo(String studentExamNo) {
        this.studentExamNo = studentExamNo;
    }

    public String getStuRankInClass() {
        return stuRankInClass;
    }

    public void setStuRankInClass(String stuRankInClass) {
        this.stuRankInClass = stuRankInClass;
    }

    public String getStuRankInGrade() {
        return stuRankInGrade;
    }

    public void setStuRankInGrade(String stuRankInGrade) {
        this.stuRankInGrade = stuRankInGrade;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
