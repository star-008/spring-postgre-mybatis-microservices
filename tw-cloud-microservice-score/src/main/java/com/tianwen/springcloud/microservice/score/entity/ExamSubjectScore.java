package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_e_cj_exam_subject_score")
@Fillable
public class ExamSubjectScore extends BaseEntity {
    private static final String TABLE_ID_PREFIX = "EXSBJ";

    public static final String DEFAULT_SCORE = "100";

    public static final String STATUS_INITIAL = "0";
    public static final String STATUS_PART_SCORE_SAVED = "1";
    public static final String STATUS_FULL_SCORE_SAVED = "2";

    public static final String PUB_STATUS_INITIAL = "0";
    public static final String PUB_STATUS_PUBLISH_CANCELED = "1";
    public static final String PUB_STATUS_PUBLISHED = "2";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT generate_id('"+ TABLE_ID_PREFIX + "', 'seq_exam_subject')")
    @Column(name = "examsubjectscoreid")
    @ApiModelProperty(name = "examsubjectscoreid", value = "examsubjectscoreid")
    @JsonProperty("examsubjectscoreid")
    private String id;

    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    private Exam exam;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @Fill
    private DictItem grade;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "rules")
    @ApiModelProperty(name = "rules", value = "rules")
    @JsonProperty("rules")
    private String rules;

    @Column(name = "score")
    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @Column(name = "managerid")
    @ApiModelProperty(name = "managerid", value = "managerid")
    @JsonProperty("managerid")
    private String managerId;

    @Column(name = "managername")
    @ApiModelProperty(name = "managername", value = "managername")
    @JsonProperty("managername")
    private String managerName;

    @Column(name = "submittime")
    @ApiModelProperty(name = "submittime", value = "submittime")
    @JsonProperty("submittime")
    private Timestamp submitTime;

    @Column(name = "volumes")
    @ApiModelProperty(name = "volumes", value = "volumes")
    @JsonProperty("volumes")
    private String volumes;

    @Column(name = "questioncount")
    @ApiModelProperty(name = "questioncount", value = "questioncount")
    @JsonProperty("questioncount")
    private String questionCount;

    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "publisherid")
    @ApiModelProperty(name = "publisherid", value = "publisherid")
    @JsonProperty("publisherid")
    private String publisherId;

    @Column(name = "publishername")
    @ApiModelProperty(name = "publishername", value = "publishername")
    @JsonProperty("publishername")
    private String publisherName;

    @Column(name = "pubtime")
    @ApiModelProperty(name = "pubtime", value = "pubtime")
    @JsonProperty("pubtime")
    private Timestamp pubTime;

    @ApiModelProperty(name = "scoreenteredcount", value = "scoreenteredcount")
    @JsonProperty("scoreenteredcount")
    private String scoreEnteredCount;

    @ApiModelProperty(name = "scoreunenteredcount", value = "scoreunenteredcount")
    @JsonProperty("scoreunenteredcount")
    private String scoreUnenteredCount;

    @ApiModelProperty(name = "exammissedcount", value = "exammissedcount")
    @JsonProperty("exammissedcount")
    private String examMissedCount = "0";

    @Column(name = "pubstatus")
    @ApiModelProperty(name = "pubstatus", value = "pubstatus")
    @JsonProperty("pubstatus")
    private String pubStatus;

    public ExamSubjectScore() {

    }

    public ExamSubjectScore(String examId) {
        this.examId = examId;
    }

    public ExamSubjectScore(String examId, String gradeId, String subjectId) {
        this.examId = examId;
        this.gradeId = gradeId;
        this.subjectId = subjectId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExamSubjectScore)
            return equals((ExamSubjectScore)o);

        return false;
    }
    
    private boolean equals(ExamSubjectScore examSubjectScore) {
        return ((StringUtils.isEmpty(this.getExamId()) && StringUtils.isEmpty(examSubjectScore.getExamId())) || (StringUtils.equals(this.getExamId(), examSubjectScore.getExamId()))
                && (StringUtils.isEmpty(this.getGradeId()) && StringUtils.isEmpty(examSubjectScore.getGradeId())) || (StringUtils.equals(this.getGradeId(), examSubjectScore.getGradeId()))
                && (StringUtils.isEmpty(this.getSubjectId()) && StringUtils.isEmpty(examSubjectScore.getSubjectId())) || (StringUtils.equals(this.getSubjectId(), examSubjectScore.getSubjectId()))
        ); 
    }

    public DictItem getGrade() {
        return grade;
    }

    public void setGrade(DictItem grade) {
        this.grade = grade;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public String getVolumes() {
        return volumes;
    }

    public void setVolumes(String volumes) {
        this.volumes = volumes;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Timestamp getPubTime() {
        return pubTime;
    }

    public void setPubTime(Timestamp pubTime) {
        this.pubTime = pubTime;
    }

    public String getScoreEnteredCount() {
        return scoreEnteredCount;
    }

    public void setScoreEnteredCount(String scoreEnteredCount) {
        this.scoreEnteredCount = scoreEnteredCount;
    }

    public String getScoreUnenteredCount() {
        return scoreUnenteredCount;
    }

    public void setScoreUnenteredCount(String scoreUnenteredCount) {
        this.scoreUnenteredCount = scoreUnenteredCount;
    }

    public String getExamMissedCount() {
        return examMissedCount;
    }

    public void setExamMissedCount(String examMissedCount) {
        this.examMissedCount = examMissedCount;
    }

    public String getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(String pubStatus) {
        this.pubStatus = pubStatus;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}