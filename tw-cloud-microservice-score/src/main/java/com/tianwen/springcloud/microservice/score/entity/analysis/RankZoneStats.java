package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.18.
 */
@Fillable
public class RankZoneStats {

    @Id
    private String id;

    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @ApiModelProperty(name = "totalcount", value = "totalcount")
    @JsonProperty("totalcount")
    private String totalCount;

    @ApiModelProperty(name = "applycount", value = "applycount")
    @JsonProperty("applycount")
    private String applyCount;

    @ApiModelProperty(name = "maxscore", value = "maxscore")
    @JsonProperty("maxscore")
    private String maxScore;

    @ApiModelProperty(name = "minscore", value = "minscore")
    @JsonProperty("minscore")
    private String minScore;

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @ApiModelProperty(name = "diff", value = "diff")
    @JsonProperty("diff")
    private String diff;

    @ApiModelProperty(name = "rankzones", value = "rankzones")
    @JsonProperty("rankzones")
    private List<RankZone> rankZoneList;

    public RankZoneStats() {

    }

    public RankZoneStats(Map<String, String> map, List<RankZone> rankZoneList) {
        classId = map.get("class_id");
        totalCount = String.valueOf(map.get("total_count"));
        applyCount = String.valueOf(map.get("apply_count"));
        maxScore = String.valueOf(map.get("max_score"));
        minScore = String.valueOf(map.get("min_score"));
        score = String.valueOf(map.get("score"));
        diff = String.valueOf(map.get("diff"));

        this.rankZoneList = rankZoneList.stream().map(rankZone ->
                new RankZone(rankZone, String.valueOf(map.get(Zone.COUNT + rankZone.getLevel()))
                )
        ).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getMinScore() {
        return minScore;
    }

    public void setMinScore(String minScore) {
        this.minScore = minScore;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public List<RankZone> getRankZoneList() {
        return rankZoneList;
    }

    public void setRankZoneList(List<RankZone> rankZoneList) {
        this.rankZoneList = rankZoneList;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
