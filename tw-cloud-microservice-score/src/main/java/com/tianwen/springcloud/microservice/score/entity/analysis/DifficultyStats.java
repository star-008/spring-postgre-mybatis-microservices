package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.28.
 */
@Fillable
public class DifficultyStats {

    @Id
    private String id;

    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @ApiModelProperty(name = "applycount", value = "applycount")
    @JsonProperty("applycount")
    private String applyCount;

    @ApiModelProperty(name = "totalcount", value = "totalcount")
    @JsonProperty("totalcount")
    private String totalCount;

    @ApiModelProperty(name = "maxscore", value = "maxscore")
    @JsonProperty("maxscore")
    private String maxScore;

    @ApiModelProperty(name = "minscore", value = "minscore")
    @JsonProperty("minscore")
    private String minScore;

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @ApiModelProperty(name = "topnscore", value = "topnscore")
    @JsonProperty("topnscore")
    private String topNScore;

    @ApiModelProperty(name = "lastnscore", value = "lastnscore")
    @JsonProperty("lastnscore")
    private String lastNScore;

    @ApiModelProperty(name = "diff", value = "diff")
    @JsonProperty("diff")
    private String diff;

    @ApiModelProperty(name = "fullscorepercent", value = "fullscorepercent")
    @JsonProperty("fullscorepercent")
    private String fullScorePercent;

    @ApiModelProperty(name = "passzones", value = "passzones")
    @JsonProperty("passzones")
    private List<PassZone> passZoneList;

    @ApiModelProperty(name = "avgscorepercent", value = "avgscorepercent")
    @JsonProperty("avgscorepercent")
    private String avgScorePercent;

    @ApiModelProperty(name = "avgscorepercentdiff", value = "avgscorepercentdiff")
    @JsonProperty("avgscorepercentdiff")
    private String avgScorePercentDiff;

    @ApiModelProperty(name = "difficulty", value = "difficulty")
    @JsonProperty("difficulty")
    private String difficulty;

    public DifficultyStats() {

    }

    public DifficultyStats(String subjectId, Map<String, String> map, List<PassZone> passZoneList) {
        this.subjectId = subjectId;
        classId = map.get("class_id");
        applyCount = String.valueOf(map.get("apply_count"));
        totalCount = String.valueOf(map.get("total_count"));
        maxScore = String.valueOf(map.get("max_score"));
        minScore = String .valueOf(map.get("min_score"));
        score = String.valueOf(map.get("score"));
        topNScore = String.valueOf(map.get("top_n_score"));
        lastNScore = String.valueOf(map.get("last_n_score"));

        diff = String .valueOf(map.get("diff"));
        fullScorePercent = String.valueOf(map.get("full_score_percent"));
        avgScorePercent = String.valueOf(map.get("avg_score_percent"));
        avgScorePercentDiff = String.valueOf(map.get("avg_score_percent_diff"));
        difficulty = String.valueOf(map.get("difficulty"));

        this.passZoneList = passZoneList.stream().map(passZone ->
                new PassZone(
                        passZone,
                        String.valueOf(map.get(passZone.getCategory() + "_" + Zone.COUNT + "_" + passZone.getLevel())),
                        String.valueOf(map.get(passZone.getCategory() + "_" + Zone.PERCENT + "_" + passZone.getLevel()))
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

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTopNScore() {
        return topNScore;
    }

    public void setTopNScore(String topNScore) {
        this.topNScore = topNScore;
    }

    public String getFullScorePercent() {
        return fullScorePercent;
    }

    public void setFullScorePercent(String fullScorePercent) {
        this.fullScorePercent = fullScorePercent;
    }

    public List<PassZone> getPassZoneList() {
        return passZoneList;
    }

    public void setPassZoneList(List<PassZone> passZoneList) {
        this.passZoneList = passZoneList;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public String getAvgScorePercent() {
        return avgScorePercent;
    }

    public void setAvgScorePercent(String avgScorePercent) {
        this.avgScorePercent = avgScorePercent;
    }

    public String getAvgScorePercentDiff() {
        return avgScorePercentDiff;
    }

    public void setAvgScorePercentDiff(String avgScorePercentDiff) {
        this.avgScorePercentDiff = avgScorePercentDiff;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getLastNScore() {
        return lastNScore;
    }

    public void setLastNScore(String lastNScore) {
        this.lastNScore = lastNScore;
    }
}
