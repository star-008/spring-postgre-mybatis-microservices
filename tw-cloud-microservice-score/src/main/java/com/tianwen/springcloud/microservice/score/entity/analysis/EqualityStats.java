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
 * Created by R.JinHyok on 2018.11.18.
 */
@Fillable
public class EqualityStats {

    @Id
    private String id;

    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    @ApiModelProperty(name = "examname", value = "examname")
    @JsonProperty("examname")
    private String examName;

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

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @ApiModelProperty(name = "fullscore", value = "fullscore")
    @JsonProperty("fullscore")
    private String fullScore;

    @ApiModelProperty(name = "passzones", value = "passzones")
    @JsonProperty("passzones")
    private List<PassZone> passZoneList;

    @ApiModelProperty(name = "scorezones", value = "scorezones")
    @JsonProperty("scorezones")
    private List<ScoreZone> scoreZoneList;

    public EqualityStats() {

    }

    public EqualityStats(String subjectId, Map<String, String> map, List<PassZone> passZoneList, List<ScoreZone> scoreZoneList) {
        this.subjectId = subjectId;
        examId = map.get("exam_id");
        examName = map.get("exam_name");
        classId = map.get("class_id");
        applyCount = String.valueOf(map.get("apply_count"));
        totalCount = String.valueOf(map.get("total_count"));
        teacherId = map.get("teacher_id");
        teacherName = map.get("teacher_name");
        maxScore = String.valueOf(map.get("max_score"));
        minScore = String .valueOf(map.get("min_score"));
        score = String.valueOf(map.get("score"));
        setFullScore(String.valueOf(map.get("full_score")));

        this.passZoneList = passZoneList.stream().map(passZone ->
                new PassZone(
                        passZone,
                        String.valueOf(map.get(passZone.getCategory() + "_" + Zone.COUNT + "_" + passZone.getLevel())),
                        String.valueOf(map.get(passZone.getCategory() + "_" + Zone.PERCENT + "_" + passZone.getLevel()))
                )
        ).collect(Collectors.toList());

        this.scoreZoneList = scoreZoneList.stream().map(scoreZone ->
                new ScoreZone(
                        scoreZone,
                        String.valueOf(map.get(scoreZone.getCategory() + "_" + Zone.COUNT + "_" + scoreZone.getLevel())),
                        null
                )
        ).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<PassZone> getPassZoneList() {
        return passZoneList;
    }

    public void setPassZoneList(List<PassZone> passZoneList) {
        this.passZoneList = passZoneList;
    }

    public List<ScoreZone> getScoreZoneList() {
        return scoreZoneList;
    }

    public void setScoreZoneList(List<ScoreZone> scoreZoneList) {
        this.scoreZoneList = scoreZoneList;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }
}
