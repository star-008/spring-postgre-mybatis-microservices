package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by R.JinHyok on 2018.11.26.
 */
public class RankZone extends Zone {

    public static final String TYPE_TOP = "top";
    public static final String TYPE_LAST = "last";

    @ApiModelProperty(name = "type", value = "type")
    @JsonProperty("type")
    private String type;

    public RankZone() {

    }

    public RankZone(String level, String lowScore, String highScore, String type, String count) {
        super(level, lowScore, highScore, count);
        this.type = type;
    }

    public RankZone(String level, String lowScore, String highScore, String type) {
        this(level, lowScore, highScore, type, null);
    }

    public RankZone(RankZone rankZone, String count) {
        this(rankZone.getLevel(), rankZone.getLowScore(), rankZone.getHighScore(), rankZone.getType(), count);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
