package com.tianwen.springcloud.microservice.score.entity.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PartDifficultyStats {

    @Id
    private String id;

    @ApiModelProperty(name = "count", value = "count")
    @JsonProperty("count")
    private String count;

    @ApiModelProperty(name = "maxscore", value = "maxscore")
    @JsonProperty("maxscore")
    private String maxScore;

    @ApiModelProperty(name = "minscore", value = "minscore")
    @JsonProperty("minscore")
    private String minScore;

    @ApiModelProperty(name = "score", value = "score")
    @JsonProperty("score")
    private String score;

    @ApiModelProperty(name = "rate", value = "rate")
    @JsonProperty("rate")
    private String rate;

    @ApiModelProperty(name = "fullscorerate", value = "fullscorerate")
    @JsonProperty("fullscorerate")
    private String fullScoreRate;

    @ApiModelProperty(name = "zeroscorerate", value = "zeroscorerate")
    @JsonProperty("zeroscorerate")
    private String zeroScoreRate;

    @ApiModelProperty(name = "difficulty", value = "difficulty")
    @JsonProperty("difficulty")
    private String difficulty;

    @ApiModelProperty(name = "smallno", value = "smallno")
    @JsonProperty("smallno")
    private String smallNo;

    @ApiModelProperty(name = "questiontypeid", value = "questiontypeid")
    @JsonProperty("questiontypeid")
    private String questionTypeId;

    @Fill(entityType = Fill.ENTITY_TYPE_QUESTION_TYPE)
    private DictItem questionType;

    @ApiModelProperty(name = "questioncategoryid", value = "questioncategoryid")
    @JsonProperty("questioncategoryid")
    private String questionCategoryId;

    @Fill(entityType = Fill.ENTITY_TYPE_QUESTION_CATEGORY)
    private DictItem questionCategory;

    @ApiModelProperty(name = "questionno", value = "questionno")
    @JsonProperty("questionno")
    private String questionNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFullScoreRate() {
        return fullScoreRate;
    }

    public void setFullScoreRate(String fullScoreRate) {
        this.fullScoreRate = fullScoreRate;
    }

    public String getZeroScoreRate() {
        return zeroScoreRate;
    }

    public void setZeroScoreRate(String zeroScoreRate) {
        this.zeroScoreRate = zeroScoreRate;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public DictItem getQuestionType() {
        return questionType;
    }

    public void setQuestionType(DictItem questionType) {
        this.questionType = questionType;
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

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }
}
