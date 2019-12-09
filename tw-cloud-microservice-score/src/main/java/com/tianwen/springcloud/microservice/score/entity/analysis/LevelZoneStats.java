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
public class LevelZoneStats {

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

    @ApiModelProperty(name = "teacherid", value = "teacherid")
    @JsonProperty("teacherid")
    private String teacherId;

    @ApiModelProperty(name = "teachername", value = "teachername")
    @JsonProperty("teachername")
    private String teacherName;

    @ApiModelProperty(name = "maxscore", value = "maxscore")
    @JsonProperty("maxscore")
    private String maxScore;

    @ApiModelProperty(name = "minscore", value = "minscore")
    @JsonProperty("minscore")
    private String minScore;

    @ApiModelProperty(name = "levelzones", value = "levelzones")
    @JsonProperty("levelzones")
    private List<LevelZone> levelZoneList;

    public LevelZoneStats() {

    }

    public LevelZoneStats(String subjectId, Map<String, String> map, List<LevelZone> zoneList) {
        this.subjectId = subjectId;
        classId = map.get("class_id");
        applyCount = String.valueOf(map.get("apply_count"));
        totalCount = String.valueOf(map.get("total_count"));
        teacherId = map.get("teacher_id");
        teacherName = map.get("teacher_name");
        maxScore = String.valueOf(map.get("max_score"));
        minScore = String.valueOf(map.get("min_score"));

        levelZoneList = zoneList.stream().map(zone ->
                new LevelZone(zone.getLevel(),
                        zone.getLowScore(),
                        zone.getHighScore(),
                        String.valueOf(map.get(Zone.COUNT + zone.getLevel())),
                        String.valueOf(map.get(Zone.PERCENT + zone.getLevel()))
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public List<LevelZone> getLevelZoneList() {
        return levelZoneList;
    }

    public void setLevelZoneList(List<LevelZone> levelZoneList) {
        this.levelZoneList = levelZoneList;
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
