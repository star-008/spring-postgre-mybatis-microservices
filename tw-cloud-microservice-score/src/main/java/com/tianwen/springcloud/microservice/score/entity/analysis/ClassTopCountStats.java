package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.18.
 */
@Fillable
public class ClassTopCountStats {

    public static final String COUNT_LEVEL_TOP = "top";
    public static final String COUNT_LEVEL_LAST = "last";

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

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @ApiModelProperty(name = "avgscore", value = "avgscore")
    @JsonProperty("avgscore")
    private String avgScore;

    @ApiModelProperty(name = "rank", value = "rank")
    @JsonProperty("rank")
    private String rank;

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

    @ApiModelProperty(name = "counts", value = "counts")
    @JsonProperty("counts")
    private List<Count> countList;

    public ClassTopCountStats() {

    }

    public ClassTopCountStats(Map<String, String> map, List<String> topLevelCountList, List<String> lastLevelCountList) {
        examId = map.get("examid");
        examName = map.get("examname");
        subjectId = map.get("subjectid");
        classId = map.get("classid");
        totalCount = String.valueOf(map.get("total_count"));
        applyCount = String.valueOf(map.get("apply_count"));
        teacherId = map.get("teacher_id");
        teacherName = map.get("teacher_name");
        score = String.valueOf(map.get("score"));
        rank = String.valueOf(map.get("rank"));
        avgScore = String.valueOf(map.get("avg_score"));

        countList = new ArrayList<>();

        countList.addAll(topLevelCountList.stream().map(level -> new Count(COUNT_LEVEL_TOP, level, String.valueOf(map.get(COUNT_LEVEL_TOP + level)))).collect(Collectors.toList()));
        countList.addAll(lastLevelCountList.stream().map(level -> new Count(COUNT_LEVEL_LAST, level, String.valueOf(map.get(COUNT_LEVEL_LAST + level)))).collect(Collectors.toList()));
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<Count> getCountList() {
        return countList;
    }

    public void setCountList(List<Count> countList) {
        this.countList = countList;
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

    public static class Count {
        private String type;
        private String level;
        private String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Count() {

        }

        public Count(String type, String level, String value) {
            this.type = type;
            this.level = level;
            this.value = value;
        }
    }
}
