package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by R.JinHyok on 2018.11.18.
 */
@Fillable
public class IndividualStudentScore {

    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

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
}
