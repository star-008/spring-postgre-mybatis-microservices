package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.annotation.Fill;
import com.tianwen.springcloud.microservice.score.annotation.Fillable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by R.JinHyok on 2018.11.28.
 */
@Fillable
public class PartGainRateStats {

    @Id
    private String id;

    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @Fill(idFieldName = "classId", entityType = Fill.ENTITY_TYPE_CLASS)
    private ClassInfo classInfo;

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

    @ApiModelProperty(name = "percent", value = "percent")
    @JsonProperty("percent")
    private String percent;

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;


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

    public String getSmallNo() {
        return smallNo;
    }

    public void setSmallNo(String smallNo) {
        this.smallNo = smallNo;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public DictItem getQuestionType() {
        return questionType;
    }

    public void setQuestionType(DictItem questionType) {
        this.questionType = questionType;
    }
}
