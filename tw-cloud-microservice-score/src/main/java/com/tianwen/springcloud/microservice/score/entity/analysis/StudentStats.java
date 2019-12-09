package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by R.JinHyok on 2018.11.18.
 */
@Fillable
public class StudentStats {

    @Id
    private String id;

    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    @ApiModelProperty(name = "examname", value = "examname")
    @JsonProperty("examname")
    private String examName;

    @ApiModelProperty(name = "examstartday", value = "考试时间")
    @JsonProperty("examstartday")
    private String startDate;

    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @ApiModelProperty(name = "applycount", value = "applycount")
    @JsonProperty("applycount")
    private String applyCount;

    @ApiModelProperty(name = "totalcount", value = "totalcount")
    @JsonProperty("totalcount")
    private String totalCount;

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @ApiModelProperty(name = "fullscore", value = "fullscore")
    @JsonProperty("fullscore")
    private String fullScore;

    @ApiModelProperty(name = "standardscore", value = "standardscore")
    @JsonProperty("standardscore")
    private String standardScore;

    @ApiModelProperty(name = "classmaxscore", value = "classmaxscore")
    @JsonProperty("classmaxscore")
    private String classMaxScore;

    @ApiModelProperty(name = "grademaxscore", value = "grademaxscore")
    @JsonProperty("grademaxscore")
    private String gradeMaxScore;

    @ApiModelProperty(name = "classavgscore", value = "classavgscore")
    @JsonProperty("classavgscore")
    private String classAvgScore;

    @ApiModelProperty(name = "gradeavgscore", value = "gradeavgscore")
    @JsonProperty("gradeavgscore")
    private String gradeAvgScore;

    @ApiModelProperty(name = "stucountinclass", value = "stucountinclass")
    @JsonProperty("stucountinclass")
    private String stuCountInClass;

    @ApiModelProperty(name = "stucountingrade", value = "stucountingrade")
    @JsonProperty("stucountingrade")
    private String stuCountInGrade;

    @ApiModelProperty(name = "sturankinclass", value = "sturankinclass")
    @JsonProperty("sturankinclass")
    private String stuRankInClass;

    @ApiModelProperty(name = "sturankingrade", value = "sturankingrade")
    @JsonProperty("sturankingrade")
    private String stuRankInGrade;

    @ApiModelProperty(name = "degree", value = "degree")
    @JsonProperty("degree")
    private String degree;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(String standardScore) {
        this.standardScore = standardScore;
    }

    public String getClassMaxScore() {
        return classMaxScore;
    }

    public void setClassMaxScore(String classMaxScore) {
        this.classMaxScore = classMaxScore;
    }

    public String getGradeMaxScore() {
        return gradeMaxScore;
    }

    public void setGradeMaxScore(String gradeMaxScore) {
        this.gradeMaxScore = gradeMaxScore;
    }

    public String getClassAvgScore() {
        return classAvgScore;
    }

    public void setClassAvgScore(String classAvgScore) {
        this.classAvgScore = classAvgScore;
    }

    public String getGradeAvgScore() {
        return gradeAvgScore;
    }

    public void setGradeAvgScore(String gradeAvgScore) {
        this.gradeAvgScore = gradeAvgScore;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public String getStuCountInClass() {
        return stuCountInClass;
    }

    public void setStuCountInClass(String stuCountInClass) {
        this.stuCountInClass = stuCountInClass;
    }

    public String getStuCountInGrade() {
        return stuCountInGrade;
    }

    public void setStuCountInGrade(String stuCountInGrade) {
        this.stuCountInGrade = stuCountInGrade;
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

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
