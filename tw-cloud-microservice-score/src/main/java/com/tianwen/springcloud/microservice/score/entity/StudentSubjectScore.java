package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_e_cj_stu_subject_score")
@Fillable
public class StudentSubjectScore extends BaseEntity {
    public static final String STATUS_INITIAL = "0";
    public static final String STATUS_SAVED = "1";
    public static final String STATUS_PART_SCORE_SAVED = "2";

    private static final String TABLE_ID_PREFIX = "STSBJ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT generate_id('" + TABLE_ID_PREFIX + "', 'seq_student_subject')")
    @Column(name = "stusubjectscoreid")
    @ApiModelProperty(name = "stusubjectscoreid", value = "stusubjectscoreid")
    @JsonProperty("stusubjectscoreid")
    private String id;

    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty( "examid")
    private String examId;

    private Exam exam;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @Fill
    private  DictItem grade;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @Column(name = "classid")
    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

    @Column(name = "stuid")
    @ApiModelProperty(name = "stuid", value = "stuid")
    @JsonProperty("stuid")
    private String studentId;

    @Column(name = "stuname")
    @ApiModelProperty(name = "stuname", value = "stuname")
    @JsonProperty( "stuname")
    private String studentName;

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

    @Column(name = "score")
    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty( "score")
    private String score;

    @Column(name = "newscore")
    @ApiModelProperty(name = "newscore", value = "newscore")
    @JsonProperty( "newscore")
    private String newScore;

    @Column(name = "reason")
    @ApiModelProperty(name = "reason", value = "reason")
    @JsonProperty("reason")
    private String reason;

    @Column(name = "schoolid")
    @ApiModelProperty(name = "schoolid", value = "schoolid")
    @JsonProperty( "schoolid")
    private String schoolId;

    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "status")
    @JsonProperty( "status")
    private String status;

    @Column(name = "sex")
    @ApiModelProperty(name = "sex", value = "sex")
    @JsonProperty( "sex")
    private String sex;

    @Column(name = "sexname")
    @ApiModelProperty("sexname")
    @JsonProperty("sexname")
    private String sexName;

    @Column(name = "stuno")
    @ApiModelProperty(name = "stuno", value = "stuno")
    @JsonProperty("stuno")
    private String studentNo;

    @Column(name = "teacherid")
    @ApiModelProperty(name = "teacherid", value = "teacherid")
    @JsonProperty("teacherid")
    private String teacherId;

    @Column(name = "teachername")
    @ApiModelProperty(name = "teachername", value = "teachername")
    @JsonProperty("teachername")
    private String teacherName;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

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

    public StudentSubjectScore() {

    }

    public StudentSubjectScore(String id, String score) {
        this.id = id;
        this.score = score;
    }

    public StudentSubjectScore(String examId) {
        this.examId = examId;
    }

    public StudentSubjectScore(String examId, String gradeId, String subjectId, String studentId) {
        this.examId = examId;
        this.gradeId = gradeId;
        this.subjectId = subjectId;
        this.studentId = studentId;
    }

    public StudentSubjectScore(String examId, String gradeId, String classType, String subjectId, String classId, String studentNo, String studentName) {
        this.examId = examId;
        this.gradeId = gradeId;
        this.classType = classType;
        this.subjectId = subjectId;
        this.classId = classId;
        this.studentNo = studentNo;
        this.studentName = studentName;
    }

    public StudentSubjectScore(String examId, String gradeId, String classId, String classType, String subjectId, String studentId, String studentName, String studentNo, String sex, String sexName) {
        this(examId, gradeId, classType, subjectId, classId, studentNo, studentName);
        this.studentId = studentId;
        this.sex = sex;
        this.sexName = sexName;
    }

    public StudentSubjectScore(String examId, String gradeId, String classId, String classType, String subjectId, String studentId, String studentName, String studentNo, String sex, String sexName, String teacherId, String teacherName) {
        this(examId, gradeId, classId, classType, subjectId, studentId, studentName, studentNo, sex, sexName);
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public StudentSubjectScore(ExamGradeClassSubject examGradeClassSubject, String studentId, String studentName, String studentNo, String sex, String sexName) {
        this(examGradeClassSubject.getExamId(), examGradeClassSubject.getGradeId(), examGradeClassSubject.getClassId(), examGradeClassSubject.getClassType(), examGradeClassSubject.getSubjectId(), studentId, studentName, studentNo, sex, sexName);
        this.teacherId = examGradeClassSubject.getTeacherId();
        this.teacherName = examGradeClassSubject.getTeacherName();
    }

    public StudentSubjectScore(ExamGradeClassSubject examGradeClassSubject, Student student) {
        this(examGradeClassSubject.getExamId(), examGradeClassSubject.getGradeId(), examGradeClassSubject.getClassId(), examGradeClassSubject.getClassType(), examGradeClassSubject.getSubjectId(), student.getUserId(), student.getName(), student.getStudentNo(), student.getSex(), student.getSexName());
        this.teacherId = examGradeClassSubject.getTeacherId();
        this.teacherName = examGradeClassSubject.getTeacherName();
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
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

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof StudentSubjectScore)
            return equals((StudentSubjectScore)obj);

        return false;
    }

    private boolean equals(StudentSubjectScore studentSubjectScore) {
        return StringUtils.equals(examId, studentSubjectScore.examId)
                && StringUtils.equals(classType, studentSubjectScore.classType)
                && (StringUtils.equals(studentId, studentSubjectScore.studentId) || StringUtils.equals(studentNo, studentSubjectScore.studentNo) ||
                    (StringUtils.equals(classId, studentSubjectScore.classId) && StringUtils.equals(studentName, studentSubjectScore.studentName)))
                && StringUtils.equals(subjectId, studentSubjectScore.subjectId);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }
}