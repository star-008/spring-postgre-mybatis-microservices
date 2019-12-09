package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by R.JinHyok on 2018.11.26.
 */
public class ScoreZone extends Zone {

    public static final String CATEGORY = "sz";

    public static final String ACCUM_COUNT = "accum_count";

    public static final int DEFAULT_SPAN_FOR_SUBJECT = 5;
    public static final int DEFAULT_SPAN_FOR_SUBJECT_TOTAL = 20;

    @ApiModelProperty(name = "accumcount", value = "accumcount")
    @JsonProperty("accumcount")
    private String accumCount;

    private List<Map<String, Object>> studentInfo = new ArrayList<>();

    public ScoreZone() {

    }

    public ScoreZone(String level, String lowScore, String highScore, String count, String accumCount) {
        super(level, lowScore, highScore, count);
        this.accumCount = accumCount;
    }

    public ScoreZone(ScoreZone scoreZone, String count, String accumCount) {
        super(scoreZone.getLevel(), scoreZone.getLowScore(), scoreZone.getHighScore(), count);
        this.accumCount = accumCount;
    }

    public ScoreZone(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        super(sysAnalysisScoreLevel);
    }

    public String getAccumCount() {
        return accumCount;
    }

    public void setAccumCount(String accumCount) {
        this.accumCount = accumCount;
    }

    public String getCategory() {
        return CATEGORY;
    }

    public String toString() {
        String name = "";
        if (!StringUtils.isEmpty(getHighScore())) {
            name += getHighScore() + "-";
        }
        name += getLowScore();

        return name;
    }

    public List<Map<String, Object>> getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(List<Map<String, Object>> studentInfo) {
        this.studentInfo = studentInfo;
    }
}
