package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_e_cj_stu_part_score")
@Fillable
public class StudentPartScore extends BaseEntity {

    private static final String TABLE_ID_PREFIX = "STPS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT generate_id('"+ TABLE_ID_PREFIX + "', 'seq_student_part_score')")
    @Column(name = "stupartscoreid")
    @ApiModelProperty(name = "stupartscoreid", value = "stupartscoreid")
    @JsonProperty( "stupartscoreid")
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
    private  DictItem subject;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "classid")
    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty( "classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @Column(name = "stuid")
    @ApiModelProperty(name = "stuid", value = "stuid")
    @JsonProperty( "stuid")
    private String studentId;

    @Column(name = "stuname")
    @ApiModelProperty(name = "stuname", value = "stuname")
    @JsonProperty( "stuname")
    private String studentName;

    @Column(name = "stuno")
    @ApiModelProperty(name = "stuno", value = "stuno")
    @JsonProperty("stuno")
    private String studentNo;

    @Column(name = "examroomno")
    @ApiModelProperty(name = "examroomno", value = "examroomno")
    @JsonProperty( "examroomno")
    private String examRoomNo;

    @Column(name = "setno")
    @ApiModelProperty(name = "setno", value = "setno")
    @JsonProperty( "setno")
    private String setNo;

    @Column(name = "examcard")
    @ApiModelProperty(name = "examcard", value = "examcard")
    @JsonProperty( "examcard")
    private String examCard;

    @Column(name = "studentexamno")
    @ApiModelProperty(name = "studentexamno", value = "studentexamno")
    @JsonProperty( "studentexamno")
    private String studentExamNo;

    @Column(name = "schoolno")
    @ApiModelProperty(name = "schoolno", value = "schoolno")
    @JsonProperty( "schoolno")
    private String schoolNo;

    @Column(name = "volumeid")
    @ApiModelProperty(name = "volumeid", value = "volumeid")
    @JsonProperty("volumeid")
    private String volumeId;

    @Column(name = "questionno")
    @ApiModelProperty(name = "questionno", value = "questionno")
    @JsonProperty("questionno")
    private String questionNo;

    @Column(name = "bigno")
    @ApiModelProperty(name = "bigno", value = "bigno")
    @JsonProperty( "bigno")
        private String bigNo;

    @Column(name = "questioncategoryid")
    @ApiModelProperty(name = "questioncategoryid", value = "questioncategoryid")
    @JsonProperty( "questioncategoryid")
    private String questionCategoryId;

    @Fill(entityType = Fill.ENTITY_TYPE_QUESTION_CATEGORY)
    private DictItem questionCategory;

    @Column(name = "score")
    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty( "score")
    private String score;

    @Column(name = "schoolid")
    @ApiModelProperty(name = "schoolid", value = "schoolid")
    @JsonProperty("schoolid")
    private String schoolId;

    @Column(name = "stusubjectid")
    @ApiModelProperty(name = "stusubjectid", value = "stusubjectid")
    @JsonProperty("stusubjectid")
    private String studentSubjectId;

    @Column(name = "newscore")
    @ApiModelProperty(name = "newscore", value = "newscore")
    @JsonProperty("newscore")
    private String newScore;

    @Column(name = "reason")
    @ApiModelProperty(name = "reason", value = "reason")
    @JsonProperty("reason")
    private String reason;

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

    @Column(name = "smallno")
    @ApiModelProperty(name = "smallno", value = "smallno")
    @JsonProperty( "smallno")
    private String smallNo;

    @Column(name = "questiontypeid")
    @ApiModelProperty(name = "questiontypeid", value = "questiontypeid")
    @JsonProperty( "questiontypeid")
    private String questionTypeId;

    @Fill(entityType = Fill.ENTITY_TYPE_QUESTION_TYPE)
    private DictItem questionType;

    public StudentPartScore() {

    }

    public StudentPartScore(String examId) {
        this.examId = examId;
    }

    public StudentPartScore(String id, String score) {
        this.id = id;
        this.score = score;
    }

    public StudentPartScore(String examId, String gradeId, String classType, String subjectId, String studentId, String smallNo) {
        this.examId = examId;
        this.gradeId = gradeId;
        this.classType = classType;
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.smallNo = smallNo;
    }

    public StudentPartScore(String examId, String gradeId, String classType, String classId, String subjectId, String studentId, String studentName, String studentNo, String bigNo, String questionNo, String smallNo) {
        this.examId = examId;
        this.gradeId = gradeId;
        this.classType = classType;
        this.classId = classId;
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentNo = studentNo;
        this.bigNo = bigNo;
        this.questionNo = questionNo;
        this.smallNo = smallNo;
    }

    public StudentPartScore(StudentSubjectScore studentSubjectScore) {
        this.examId = studentSubjectScore.getExamId();
        this.gradeId = studentSubjectScore.getGradeId();
        this.classType = studentSubjectScore.getClassType();
        this.classId = studentSubjectScore.getClassId();
        this.subjectId = studentSubjectScore.getSubjectId();
        this.studentId = studentSubjectScore.getStudentId();
        this.studentName = studentSubjectScore.getStudentName();
        this.studentNo = studentSubjectScore.getStudentNo();
        this.studentExamNo = studentSubjectScore.getStudentExamNo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
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

    public String getExamRoomNo() {
        return examRoomNo;
    }

    public void setExamRoomNo(String examRoomNo) {
        this.examRoomNo = examRoomNo;
    }

    public String getSetNo() {
        return setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getStudentExamNo() {
        return studentExamNo;
    }

    public void setStudentExamNo(String studentExamNo) {
        this.studentExamNo = studentExamNo;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public String getBigNo() {
        return bigNo;
    }

    public void setBigNo(String bigNo) {
        this.bigNo = bigNo;
    }

    public String getSmallNo() {
        return smallNo;
    }

    public void setSmallNo(String smallNo) {
        this.smallNo = smallNo;
    }

    public String getStudentSubjectId() {
        return studentSubjectId;
    }

    public void setStudentSubjectId(String studentSubjectId) {
        this.studentSubjectId = studentSubjectId;
    }

    public String getNewScore() {
        return newScore;
    }

    public void setNewScore(String newScore) {
        this.newScore = newScore;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getQuestionCategoryId() {
        return questionCategoryId;
    }

    public void setQuestionCategoryId(String questionCategoryId) {
        this.questionCategoryId = questionCategoryId;
    }

    public DictItem getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(DictItem questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public DictItem getQuestionType() {
        return questionType;
    }

    public void setQuestionType(DictItem questionType) {
        this.questionType = questionType;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof StudentPartScore)
            return equals((StudentPartScore)obj);

        return false;
    }

    private boolean equals(StudentPartScore studentPartScore) {
        return StringUtils.equals(examId, studentPartScore.examId)
                && StringUtils.equals(gradeId, studentPartScore.gradeId)
                && StringUtils.equals(studentId, studentPartScore.studentId)
                && StringUtils.equals(subjectId, studentPartScore.subjectId)
                && StringUtils.equals(smallNo, studentPartScore.smallNo);
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }
}