package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by R.JinHyok on 2018.11.27.
 */
public class LevelZone extends Zone {

    private static final LevelZone[] DEFAULT_LEVEL_ZONES = new LevelZone[] {
            new LevelZone("90", "100"),
            new LevelZone("75", "90"),
            new LevelZone("60", "75"),
            new LevelZone("40", "60"),
            new LevelZone("20", "40"),
            new LevelZone("0", "20"),
    };

    @ApiModelProperty(name = "percent", value = "percent")
    @JsonProperty("percent")
    private String percent;

    public LevelZone() {

    }

    public LevelZone(String lowScore, String highScore) {
        super(lowScore, highScore);
    }
    
    public LevelZone(String level, String lowScore, String highScore) {
        super(level, lowScore, highScore);
    }

    public LevelZone(String level, String lowScore, String highScore, String count, String percent) {
        super(level, lowScore, highScore, count);
        this.percent = percent;
    }

    public LevelZone(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        super(sysAnalysisScoreLevel);
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public static LevelZone[] getDefaultLevelZones() {
        return DEFAULT_LEVEL_ZONES;
    }
}
