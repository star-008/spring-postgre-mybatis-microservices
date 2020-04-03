package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by R.JinHyok on 2018.11.26.
 */
public abstract class Zone {

    public static final String COUNT = "count";
    public static final String PERCENT = "percent";

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

    @ApiModelProperty(name = "count", value = "count")
    @JsonProperty("count")
    private String count;

    public Zone(){

    }

    public Zone(String lowScore, String highScore) {
        this.lowScore = lowScore;
        this.highScore = highScore;
    }

    public Zone(String level, String lowScore, String highScore) {
        this(lowScore, highScore);
        this.level = level;
    }

    public Zone(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        this(sysAnalysisScoreLevel.getLevel(), sysAnalysisScoreLevel.getLowScore(), sysAnalysisScoreLevel.getHighScore());
    }

    public Zone(String level, String lowScore, String highScore, String count) {
        this(level, lowScore, highScore);
        this.count = count;
    }

    public Zone(SysAnalysisScoreLevel sysAnalysisScoreLevel, String count) {
        this(sysAnalysisScoreLevel);
        this.count = count;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCategory() {
        return "";
    }
}
