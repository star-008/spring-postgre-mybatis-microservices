package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by R.JinHyok on 2018.11.26.
 */
public class PassZone extends Zone {

    public static final String CATEGORY = "pz";

    public static final String TYPE_TOP = "top";
    public static final String TYPE_LAST = "last";

    public static final String LEVEL_BEST = "1";
    public static final String LEVEL_BETTER = "2";
    //public static final String LEVEL_GOOD = "3";
    public static final String LEVEL_PASS = "4";
    public static final String LEVEL_BAD = "5";

    private static final Integer[] DEFAULT_PASS_VALUES = new Integer[] {
            90,
            80,
            null,
            60,
            40
    };

    @ApiModelProperty(name = "type", value = "type")
    @JsonProperty("type")
    private String type;

    @ApiModelProperty(name = "percent", value = "percent")
    @JsonProperty("percent")
    private String percent;

    public PassZone() {

    }

    public PassZone(String type,  String level, String lowScore, String count, String percent) {
        super(level, lowScore, null, count);
        this.type = type;
        this.percent = percent;
    }

    public PassZone(PassZone passZone, String count, String percent) {
        super(passZone.getLevel(), passZone.getLowScore(), passZone.getHighScore(), count);
        this.percent = percent;
    }

    public PassZone(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        super(sysAnalysisScoreLevel);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getCategory() {
        return CATEGORY;
    }

    public static Integer[] getDefaultPassValues() {
        return DEFAULT_PASS_VALUES;
    }
}
