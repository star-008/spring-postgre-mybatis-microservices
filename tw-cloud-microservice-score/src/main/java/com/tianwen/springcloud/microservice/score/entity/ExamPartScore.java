package com.tianwen.springcloud.microservice.score.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_e_cj_exam_part_score")
@Fillable
public class ExamPartScore extends BaseEntity {
    private static final String TABLE_ID_PREFIX = "EXPS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT generate_id('" + TABLE_ID_PREFIX + "', 'seq_exam_part_score')")
    @Column(name = "exampartscoreid")
    @ApiModelProperty(name = "exampartscoreid", value = "exampartscoreid")
    @JsonProperty("exampartscoreid")
    private String id;

    @Column(name = "examid")
    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    private Exam exam;

    @Column(name = "subjectid")
    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @Fill
    private DictItem subject;

    @Column(name = "gradeid")
    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @Fill
    private DictItem grade;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "questionno")
    @ApiModelProperty(name = "questionno", value = "questionno")
    @JsonProperty("questionno")
    private String questionNo;

    @Column(name = "bigno")
    @ApiModelProperty(name = "bigno", value = "bigno")
    @JsonProperty("bigno")
    private String bigNo;

    @Column(name = "score")
    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @Column(name = "examsubjectid")
    @ApiModelProperty(name = "examsubjectid", value = "examsubjectid")
    @JsonProperty("examsubjectid")
    private String examSubjectId;

    @Column(name = "volumeid")
    @ApiModelProperty(name = "volumeid", value = "volumeid")
    @JsonProperty("volumeid")
    private String volumeId;

    @Fill(entityType = Fill.ENTITY_TYPE_PAPER_VOLUME)
    private DictItem volume;

    @Column(name = "questioncategoryid")
    @ApiModelProperty(name = "questioncategoryid", value = "questioncategoryid")
    @JsonProperty("questioncategoryid")
    private String questionCategoryId;

    @Fill(entityType = Fill.ENTITY_TYPE_QUESTION_CATEGORY)
    private DictItem questionCategory;

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
    @JsonProperty("smallno")
    private String smallNo;

    @Column(name = "questiontypeid")
    @ApiModelProperty(name = "questiontypeid", value = "questiontypeid")
    @JsonProperty("questiontypeid")
    private String questionTypeId;

    @Fill(entityType = Fill.ENTITY_TYPE_QUESTION_TYPE)
    private DictItem questionType;

    public ExamPartScore() {

    }

    public ExamPartScore(String examId) {
        this.examId = examId;
    }

    public ExamPartScore(String examId, String examSubjectId, String gradeId, String subjectId, String volumeId, String bigNo, String questionNo, String questionCategoryId, String smallNo, String questionTypeId, String score) {
        this.examId = examId;
        this.examSubjectId = examSubjectId;
        this.gradeId = gradeId;
        this.subjectId = subjectId;
        this.setVolumeId(volumeId);
        this.bigNo = bigNo;
        this.questionNo = questionNo;
        this.questionCategoryId = questionCategoryId;
        this.smallNo = smallNo;
        this.questionTypeId = questionTypeId;
        this.score = score;
    }

    public ExamPartScore(String examId, String examSubjectId, String gradeId, String subjectId) {
        this.examId = examId;
        this.examSubjectId = examSubjectId;
        this.gradeId = gradeId;
        this.subjectId = subjectId;
    }

    public ExamPartScore(ExamSubjectScore examSubjectScore) {
        this.examId = examSubjectScore.getExamId();
        this.examSubjectId = examSubjectScore.getId();
        this.gradeId = examSubjectScore.getGradeId();
        this.classType = examSubjectScore.getClassType();
        this.subjectId = examSubjectScore.getSubjectId();
    }

    public ExamPartScore(String volumeId, String bigNo, String questionNo, String questionCategoryId, String smallNo, String questionTypeId, String score) {
        this.volumeId = volumeId;
        this.bigNo = bigNo;
        this.questionNo = questionNo;
        this.questionCategoryId = questionCategoryId;
        this.smallNo = smallNo;
        this.questionTypeId = questionTypeId;
        this.score = score;
    }

    public ExamPartScore(DictItem volume, String bigNo, String questionNo, DictItem questionCategory, String smallNo, DictItem questionType, String score) {
        this.volume = volume;
        this.volumeId = volume != null ? volume.getDictvalue() : null;
        this.bigNo = bigNo;
        this.questionNo = questionNo;
        this.questionCategory = questionCategory;
        this.questionCategoryId = questionCategory != null ? questionCategory.getDictvalue() : null;
        this.smallNo = smallNo;
        this.questionType = questionType;
        this.questionTypeId = questionType != null ? questionType.getDictvalue() : null;
        this.score = score;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
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

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public DictItem getSubject() {
        return subject;
    }

    public void setSubject(DictItem subject) {
        this.subject = subject;
    }

    public DictItem getGrade() {
        return grade;
    }

    public void setGrade(DictItem grade) {
        this.grade = grade;
    }

    public String getExamSubjectId() {
        return examSubjectId;
    }

    public void setExamSubjectId(String examSubjectId) {
        this.examSubjectId = examSubjectId;
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

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public String getQuestionCategoryId() {
        return questionCategoryId;
    }

    public void setQuestionCategoryId(String questionCategoryId) {
        this.questionCategoryId = questionCategoryId;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public DictItem getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(DictItem questionCategory) {
        this.questionCategory = questionCategory;
    }

    public DictItem getQuestionType() {
        return questionType;
    }

    public void setQuestionType(DictItem questionType) {
        this.questionType = questionType;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public DictItem getVolume() {
        return volume;
    }

    public void setVolume(DictItem volume) {
        this.volume = volume;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ExamPartScore)
            return equals((ExamPartScore) obj);

        return false;
    }

    private boolean equals(ExamPartScore examPartScore) {
        return StringUtils.equals(examId, examPartScore.examId)
                && StringUtils.equals(gradeId, examPartScore.gradeId)
                && StringUtils.equals(subjectId, examPartScore.subjectId)
                && StringUtils.equals(smallNo, examPartScore.smallNo);
    }

    public int compareTo(Object object) {
        if (object instanceof ExamPartScore) {
            return this.compareTo((ExamPartScore)object);
        }

        return -1;
    }

    public int compareTo(ExamPartScore examPartScore2) {
        String[] snList = smallNo.split("\\.");
        int si = NumberUtils.toInt(snList[0]);
        int sf = snList.length > 1 ? NumberUtils.toInt(snList[1]) : 0;

        String[] snList1 = examPartScore2.smallNo.split("\\.");
        int si1 = NumberUtils.toInt(snList1[0]);
        int sf1 = snList1.length > 1 ? NumberUtils.toInt(snList1[1]) : 0;

        return si == si1 ? NumberUtils.compare(sf, sf1) : NumberUtils.compare(si, si1);
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}