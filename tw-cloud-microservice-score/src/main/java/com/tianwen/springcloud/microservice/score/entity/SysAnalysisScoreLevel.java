package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@Table(name = "t_e_sys_analysis_score_level")
public class SysAnalysisScoreLevel extends BaseEntity {

    public static final String TYPE_PASS_ZONE = "0";
    public static final String TYPE_SCORE_ZONE = "1";
    public static final String TYPE_LEVEL_ZONE = "2";

    public static final String TYPE_DEGREE_ZONE_BY_COUNT = "3";
    public static final String TYPE_DEGREE_ZONE_BY_SCORE = "4";

    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    @Column(name = "lowscore")
    @ApiModelProperty(name = "lowscore", value = "lowscore")
    @JsonProperty("lowscore")
    private String lowScore;

    @Column(name = "highscore")
    @ApiModelProperty(name = "highscore", value = "highscore")
    @JsonProperty("highscore")
    private String highScore;

    @Column(name = "level")
    @ApiModelProperty(name = "level", value = "level")
    @JsonProperty("level")
    private String level;

    @Column(name = "type")
    @ApiModelProperty(name = "type", value = "type")
    @JsonProperty("type")
    private String type;

    @Column(name = "subjecttype")
    @ApiModelProperty(name = "subjecttype", value = "subjecttype")
    @JsonProperty("subjecttype")
    private String subjectType;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Column(name = "volumeid")
    @ApiModelProperty(name = "volumeid", value = "volumeid")
    @JsonProperty("volumeid")
    private String volumeId;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @Column(name = "configname")
    @ApiModelProperty(name = "configname", value = "configname")
    @JsonProperty("configname")
    private String configName;

    @Column(name = "itemname")
    @ApiModelProperty(name = "itemname", value = "itemname")
    @JsonProperty("itemname")
    private String itemName;

    public SysAnalysisScoreLevel() {

    }

    public SysAnalysisScoreLevel(String examId) {
        this.examId = examId;
    }

//    public SysAnalysisScoreLevel(String examId, String gradeId, String subjectId) {
//        this(examId);
//        this.gradeId = gradeId;
//        this.subjectId = subjectId;
//    }

    public SysAnalysisScoreLevel(String examId, String gradeId, String subjectId, String volumeId) {
        this(examId);
        this.gradeId = gradeId;
        this.subjectId = subjectId;
        this.volumeId = volumeId;
    }

    public SysAnalysisScoreLevel(String examId, String gradeId, String subjectId, String volumeId, String type) {
        this(examId, gradeId, subjectId, volumeId);
        this.type = type;
    }

    public SysAnalysisScoreLevel(String examId, String gradeId, String subjectId, String volumeId, String type, String level, String lowScore, String highScore) {
        this(examId, gradeId, subjectId, volumeId, type);
        this.level = level;
        this.lowScore = lowScore;
        this.highScore = highScore;
    }

    public SysAnalysisScoreLevel(String examId, String gradeId, String subjectId, String volumeId, String type, String level, String lowScore, String highScore, String configName, String itemName) {
        this(examId, gradeId, subjectId, volumeId, type, level, lowScore, highScore);
        this.configName = configName;
        this.itemName = itemName;
    }

    public SysAnalysisScoreLevel(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        this(
                sysAnalysisScoreLevel.examId,
                sysAnalysisScoreLevel.gradeId,
                sysAnalysisScoreLevel.subjectId,
                sysAnalysisScoreLevel.volumeId,
                sysAnalysisScoreLevel.type,
                sysAnalysisScoreLevel.level,
                sysAnalysisScoreLevel.lowScore,
                sysAnalysisScoreLevel.highScore
        );
    }

    public SysAnalysisScoreLevel(SysAnalysisScoreLevel sysAnalysisScoreLevel, String examId) {
        this(sysAnalysisScoreLevel);
        this.examId = examId;
    }

    public SysAnalysisScoreLevel(SysAnalysisScoreLevel sysAnalysisScoreLevel, String examId, String gradeId, String subjectId) {
        this(sysAnalysisScoreLevel, examId);
        this.gradeId = gradeId;
        this.subjectId = subjectId;
    }

    public SysAnalysisScoreLevel(SysAnalysisScoreLevel sysAnalysisScoreLevel, String examId, String gradeId, String subjectId, String type) {
        this(sysAnalysisScoreLevel, examId, gradeId, subjectId);
        this.type = type;
    }

    public SysAnalysisScoreLevel(SysAnalysisScoreLevel sysAnalysisScoreLevel, String examId, String gradeId, String subjectId, String type, String level, String lowScore, String highScore) {
        this(sysAnalysisScoreLevel, examId, gradeId, subjectId, type);
        this.level = level;
        this.lowScore = lowScore;
        this.highScore = highScore;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getLowScore() {
        return lowScore;
    }

    public void setLowScore(String lowScore) {
        this.lowScore = lowScore;
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
