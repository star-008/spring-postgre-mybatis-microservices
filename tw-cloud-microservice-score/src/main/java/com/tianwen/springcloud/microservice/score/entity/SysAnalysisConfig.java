package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_e_sys_analysis_config")
public class SysAnalysisConfig extends BaseEntity {

    public static final String IS_MISSEDEXAM_INCLUDED = "1";
    public static final String IS_MISSEDEXAM_EXCLUDED = "0";

    @Id
    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    @Column(name = "scoredivisionenabled")
    @ApiModelProperty(name = "scoredivisionenabled", value = "scoredivisionenabled")
    @JsonProperty("scoredivisionenabled")
    private String scoreDivisionEnabled;

    @Column(name = "scoredivisionbysubject")
    @ApiModelProperty(name = "scoredivisionbysubject", value = "scoredivisionbysubject")
    @JsonProperty("scoredivisionbysubject")
    private String scoreDivisionBySubject;

    @Column(name = "scoredivisionbytotal")
    @ApiModelProperty(name = "scoredivisionbytotal", value = "scoredivisionbytotal")
    @JsonProperty("scoredivisionbytotal")
    private String scoreDivisionByTotal;

    @Column(name = "gradetopcount")
    @ApiModelProperty(name = "gradetopcount", value = "gradetopcount")
    @JsonProperty("gradetopcount")
    private String gradeTopCount;

    @Column(name = "gradelastcount")
    @ApiModelProperty(name = "gradelastcount", value = "gradelastcount")
    @JsonProperty("gradelastcount")
    private String gradeLastCount;

    @Column(name = "parellelsumenabled")
    @ApiModelProperty(name = "parellelsumenabled", value = "parellelsumenabled")
    @JsonProperty("parellelsumenabled")
    private String parellelSumEnabled;

    @Column(name = "mobileviewmode")
    @ApiModelProperty(name = "mobileviewmode", value = "mobileviewmode")
    @JsonProperty("mobileviewmode")
    private String mobileViewMode;

    @Column(name = "degreetype")
    @ApiModelProperty(name = "degreetype", value = "degreetype")
    @JsonProperty("degreetype")
    private String degreeType;

    @Column(name = "missedexamincluded")
    @ApiModelProperty(name = "missedexamincluded", value = "missedexamincluded")
    @JsonProperty("missedexamincluded")
    private String missedExamIncluded;

    public SysAnalysisConfig() {

    }

    public SysAnalysisConfig(String examId) {
        this.examId = examId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getScoreDivisionEnabled() {
        return scoreDivisionEnabled;
    }

    public void setScoreDivisionEnabled(String scoreDivisionEnabled) {
        this.scoreDivisionEnabled = scoreDivisionEnabled;
    }

    public String getScoreDivisionBySubject() {
        return scoreDivisionBySubject;
    }

    public void setScoreDivisionBySubject(String scoreDivisionBySubject) {
        this.scoreDivisionBySubject = scoreDivisionBySubject;
    }

    public String getScoreDivisionByTotal() {
        return scoreDivisionByTotal;
    }

    public void setScoreDivisionByTotal(String scoreDivisionByTotal) {
        this.scoreDivisionByTotal = scoreDivisionByTotal;
    }

    public String getGradeTopCount() {
        return gradeTopCount;
    }

    public void setGradeTopCount(String gradeTopCount) {
        this.gradeTopCount = gradeTopCount;
    }

    public String getGradeLastCount() {
        return gradeLastCount;
    }

    public void setGradeLastCount(String gradeLastCount) {
        this.gradeLastCount = gradeLastCount;
    }

    public String getParellelSumEnabled() {
        return parellelSumEnabled;
    }

    public void setParellelSumEnabled(String parellelSumEnabled) {
        this.parellelSumEnabled = parellelSumEnabled;
    }

    public String getMobileViewMode() {
        return mobileViewMode;
    }

    public void setMobileViewMode(String mobileViewMode) {
        this.mobileViewMode = mobileViewMode;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getMissedExamIncluded() {
        return missedExamIncluded;
    }

    public void setMissedExamIncluded(String missedExamIncluded) {
        this.missedExamIncluded = missedExamIncluded;
    }
}
