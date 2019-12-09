package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;


@Table(name = "t_e_cj_stu_modify_score")
public class StudentSubjectModifyScore extends BaseEntity {

    public static final String STATUS_INITIAL = "0";
    public static final String STATUS_ACCEPTED = "4";
    public static final String STATUS_REJECTED = "5";
    public static final String STATUS_DELETED = "9";

    private static final String TABLE_ID_PREFIX = "STMS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT generate_id('" + TABLE_ID_PREFIX + "', 'seq_student_modify')")
    @Column(name = "stumodifyscoreid")
    @ApiModelProperty(name = "stumodifyscoreid", value = "stumodifyscoreid")
    @JsonProperty("stumodifyscoreid")
    private String id;

    @Column(name = "stusubjectid")
    @ApiModelProperty(name = "stusubjectid", value = "stusubjectid")
    @JsonProperty("stusubjectid")
    private String studentSubjectId;

    private StudentSubjectScore studentSubjectScore;

    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty( "examid")
    private String examId;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Column(name = "classid")
    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Column(name = "stuid")
    @ApiModelProperty(name = "stuid", value = "stuid")
    @JsonProperty("stuid")
    private String studentId;

    @Column(name = "modifytag")
    @ApiModelProperty(name = "modifytag", value = "modifytag")
    @JsonProperty("modifytag")
    private String modifyTag;

    @Column(name = "score")
    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @Column(name = "modifyscore")
    @ApiModelProperty(name = "modifyscore", value = "modifyscore")
    @JsonProperty("modifyscore")
    private String modifyScore;

    @Column(name = "modifyreason")
    @ApiModelProperty(name = "modifyreason", value = "modifyreason")
    @JsonProperty("modifyreason")
    private String modifyReason;

    @Column(name = "applicantid")
    @ApiModelProperty(name = "applicantid", value = "applicantid")
    @JsonProperty("applicantid")
    private String applicantId;

    @Column(name = "applicantname")
    @ApiModelProperty(name = "applicantname", value = "applicantname")
    @JsonProperty("applicantname")
    private String applicantName;

    @Column(name = "appltime")
    @ApiModelProperty(name = "appltime", value = "appltime")
    @JsonProperty("appltime")
    private Timestamp applTime;

    @Column(name = "approverid")
    @ApiModelProperty(name = "approverid", value = "approverid")
    @JsonProperty("approverid")
    private String approverId;

    @Column(name = "approvername")
    @ApiModelProperty(name = "approvername", value = "approvername")
    @JsonProperty("approvername")
    private String approverName;

    @Column(name = "apprtime")
    @ApiModelProperty(name = "apprtime", value = "apprtime")
    @JsonProperty("apprtime")
    private Timestamp apprTime;

    @Column(name = "appview")
    @ApiModelProperty(name = "appview", value = "appview")
    @JsonProperty("appview")
    private String appView;

    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "summary")
    @ApiModelProperty(name = "summary", value = "summary")
    @JsonProperty("summary")
    private String summary;

    public StudentSubjectModifyScore() {

    }

    public StudentSubjectModifyScore(String examId) {
        this.examId = examId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModifyTag() {
        return modifyTag;
    }

    public void setModifyTag(String modifyTag) {
        this.modifyTag = modifyTag;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getModifyScore() {
        return modifyScore;
    }

    public void setModifyScore(String modifyScore) {
        this.modifyScore = modifyScore;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public Timestamp getApplTime() {
        return applTime;
    }

    public void setApplTime(Timestamp applTime) {
        this.applTime = applTime;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public Timestamp getApprTime() {
        return apprTime;
    }

    public void setApprTime(Timestamp apprTime) {
        this.apprTime = apprTime;
    }

    public String getAppView() {
        return appView;
    }

    public void setAppView(String appView) {
        this.appView = appView;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStudentSubjectId() {
        return studentSubjectId;
    }

    public void setStudentSubjectId(String studentSubjectId) {
        this.studentSubjectId = studentSubjectId;
    }

    public StudentSubjectScore getStudentSubjectScore() {
        return studentSubjectScore;
    }

    public void setStudentSubjectScore(StudentSubjectScore studentSubjectScore) {
        this.studentSubjectScore = studentSubjectScore;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
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

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
