package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by R.JinHyok on 2018.11.26.
 */
public class DegreeZone extends Zone {

    private static final String[] DEGREE_NAMES = {"A+", "A", "A-", "B+", "B", "B-", "C", "D"};
    private static final String[] DEFAULT_DEGREE_VALUES = {"95", "90", "80", "60", "40", "20", "10", "5"};

    @ApiModelProperty(name = "degree", value = "degree")
    @JsonProperty("degree")
    private String degree;

    public DegreeZone(String level, String lowScore) {
        super(level, lowScore, null);
        fillDegree();
    }

    public DegreeZone(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        super(sysAnalysisScoreLevel);
        fillDegree();
    }

    public void fillDegree() {
        if (StringUtils.isEmpty(this.getLevel()) || !NumberUtils.isNumber(this.getLevel())) {
            return;
        }
        degree = DEGREE_NAMES[Integer.parseInt(this.getLevel()) - 1];
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public static String[] getDefaultDegreeValues() {
        return DEFAULT_DEGREE_VALUES;
    }
}
