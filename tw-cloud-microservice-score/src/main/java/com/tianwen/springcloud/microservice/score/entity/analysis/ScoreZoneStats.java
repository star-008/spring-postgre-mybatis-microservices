package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
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
public class ScoreZoneStats {

    @Id
    private String id;

    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @ApiModelProperty(name = "count", value = "count")
    @JsonProperty("count")
    private String count;

    @ApiModelProperty(name = "maxscore", value = "maxscore")
    @JsonProperty("maxscore")
    private String maxScore;

    @ApiModelProperty(name = "minscore", value = "minscore")
    @JsonProperty("minscore")
    private String minScore;

    @ApiModelProperty(name = "avgscore", value = "avgscore")
    @JsonProperty("avgscore")
    private String avgScore;

    @ApiModelProperty(name = "diff", value = "diff")
    @JsonProperty("diff")
    private String diff;

    @ApiModelProperty(name = "scorezone", value = "scorezone")
    @JsonProperty("scorezone")
    private List<ScoreZone> scoreZoneList;

    public ScoreZoneStats() {

    }

    public ScoreZoneStats(Map<String, String> map, List<SysAnalysisScoreLevel> scoreZoneConfigList) {
        classId = map.get("class_id");
        count = String.valueOf(map.get("count"));
        maxScore = String.valueOf(map.get("max_score"));
        minScore = String.valueOf(map.get("min_score"));
        avgScore = String.valueOf(map.get("avg_score"));
        diff = String.valueOf(map.get("diff"));

        scoreZoneList = scoreZoneConfigList.stream().map(scoreConfig ->
                new ScoreZone(scoreConfig.getLevel(),
                        scoreConfig.getLowScore(),
                        scoreConfig.getHighScore(),
                        String.valueOf(map.get(ScoreZone.COUNT + scoreConfig.getLowScore())),
                        String.valueOf(map.get(ScoreZone.ACCUM_COUNT + scoreConfig.getHighScore()))
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public List<ScoreZone> getScoreZoneList() {
        return scoreZoneList;
    }

    public void setScoreZoneList(List<ScoreZone> scoreZoneList) {
        this.scoreZoneList = scoreZoneList;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }
}
