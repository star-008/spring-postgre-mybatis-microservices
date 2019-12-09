package com.tianwen.springcloud.microservice.score.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.util.QueryUtils;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.Zone;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kimchh on 11/22/2018.
 */
public class Filter implements Serializable {

    //For backward compatibility
    @ApiModelProperty(name = "termid", value = "学期")
    @JsonProperty("termid")
    private String termId;

    @ApiModelProperty(name = "examtypeid", value = "examtypeid")
    @JsonProperty("examtypeid")
    private String examTypeId;

    @ApiModelProperty(name = "examid", value = "examid")
    @JsonProperty("examid")
    private String examId;

    @ApiModelProperty(name = "examids", value = "examids")
    @JsonProperty("examids")
    private List<String> examIdList;

    @ApiModelProperty(name = "gradeid", value = "gradeid")
    @JsonProperty("gradeid")
    private String gradeId;

    @ApiModelProperty(name = "gradeids", value = "gradeids")
    @JsonProperty("gradeids")
    private List<String> gradeIdList;

    @ApiModelProperty(name = "classid", value = "classid")
    @JsonProperty("classid")
    private String classId;

    @ApiModelProperty(name = "classids", value = "classids")
    @JsonProperty("classids")
    private List<String> classIdList;

    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @ApiModelProperty(name = "subjectid", value = "subjectid")
    @JsonProperty("subjectid")
    private String subjectId;

    @ApiModelProperty(name = "subjectids", value = "subjectids")
    @JsonProperty("subjectids")
    private List<String> subjectIdList;

    @ApiModelProperty(name = "volumeid", value = "volumeid")
    @JsonProperty("volumeid")
    private String volumeId = "0";

    @ApiModelProperty(name = "volumeids", value = "volumeids")
    @JsonProperty("volumeids")
    private List<String> volumeIdList;

    @ApiModelProperty(name = "stuid", value = "stuid")
    @JsonProperty("stuid")
    private String studentId;

    @ApiModelProperty(name = "stuids", value = "stuids")
    @JsonProperty("stuids")
    private List<String> studentIdList;

    @ApiModelProperty(name = "pubstatus", value = "pubstatus")
    @JsonProperty("pubstatus")
    private String pubStatus;

    private Map<String, Object> more = new HashMap<>();

    @ApiModelProperty(name = "showmode", value = "showmode")
    @JsonProperty("showmode")
    private Integer showMode;

    @ApiModelProperty(name = "stutopcount", value = "stutopcount")
    @JsonProperty("stutopcount")
    private Integer studentTopCount;

    @ApiModelProperty(name = "issimplemode", value = "issimplemode")
    @JsonProperty("issimplemode")
    private boolean isSimpleMode=true;

    private String sex;

    @ApiModelProperty(name = "force", value = "force")
    @JsonProperty("force")
    private Integer force;

    @ApiModelProperty(name = "checkteacherscope", value = "checkteacherscope")
    @JsonProperty("checkteacherscope")
    private Integer checkTeacherScope;

    private List<String> teacherClassIdList;

    private boolean belongsToManagerGeneral;

    private String teacherId;

    private List<String> topLevelCountList = new ArrayList<>();
    private List<String> lastLevelCountList = new ArrayList<>();

    private List<SysAnalysisScoreLevel> levelZoneConfigList;

    private List<? extends Zone> zoneList;

    public Filter() {

    }

    public Filter(String examId) {
        this.examId = examId;
    }

    public Filter(String examId, String gradeId) {
        this.examId = examId;
        this.gradeId = gradeId;
    }

    public String getExamTypeId() {
        return examTypeId;
    }

    public void setExamTypeId(String examTypeId) {
        this.examTypeId = examTypeId;
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

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public List<String> getExamIdList() {
        return examIdList;
    }

    public void setExamIdList(List<String> examIdList) {
        this.examIdList = examIdList;
    }

    public List<String> getGradeIdList() {
        return gradeIdList;
    }

    public void setGradeIdList(List<String> gradeIdList) {
        this.gradeIdList = gradeIdList;
    }

    public List<String> getClassIdList() {
        return classIdList;
    }

    public void setClassIdList(List<String> classIdList) {
        this.classIdList = classIdList;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<String> getSubjectIdList() {
        return subjectIdList;
    }

    public void setSubjectIdList(List<String> subjectIdList) {
        this.subjectIdList = subjectIdList;
    }

    public List<String> getStudentIdList() {
        return studentIdList;
    }

    public void setStudentIdList(List<String> studentIdList) {
        this.studentIdList = studentIdList;
    }

    public Map<String, Object> toQueryTreeMap() {
        QueryTree queryTree = new QueryTree();

        queryTree.setConditions(toQueryConditionList());

        Map<String, Object> map = QueryUtils.queryTree2Map(queryTree);
        map.put("filter", this);

        return map;
    }
    
    public List<QueryCondition> toQueryConditionList() {
        List<QueryCondition> queryConditionList = new ArrayList<>();

        if (!StringUtils.isEmpty(termId)) {
            queryConditionList.add(new QueryCondition("exam.termid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, termId));
        }

        if (!StringUtils.isEmpty(examTypeId)) {
            queryConditionList.add(new QueryCondition("exam.examtypeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examTypeId));
        }

        if (!StringUtils.isEmpty(examId)) {
            queryConditionList.add(new QueryCondition("exam.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
        }

        if (!CollectionUtils.isEmpty(examIdList)) {
            queryConditionList.add(new QueryCondition("exam.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, examIdList));
        }

        if (!StringUtils.isEmpty(gradeId)) {
            queryConditionList.add(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, gradeId));
        }

        if (!CollectionUtils.isEmpty(gradeIdList)) {
            queryConditionList.add(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, gradeIdList));
        }

        if (!StringUtils.isEmpty(classType)) {
            queryConditionList.add(new QueryCondition("pt.classtype", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, classType));
        }

//        if (!StringUtils.isEmpty(classId)) {
//            queryConditionList.add(new QueryCondition("pt.classid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, classId));
//        }
//
//        if (!CollectionUtils.isEmpty(classIdList)) {
//            queryConditionList.add(new QueryCondition("pt.classid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, classIdList));
//        }
//
//        if (!StringUtils.isEmpty(subjectId)) {
//            queryConditionList.add(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, subjectId));
//        }
//
//        if (!CollectionUtils.isEmpty(subjectIdList)) {
//            queryConditionList.add(new QueryCondition("pt.subjectid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, subjectIdList));
//        }
//
//        if (!StringUtils.isEmpty(studentId)) {
//            queryConditionList.add(new QueryCondition("pt.stuid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, studentId));
//        }
//
//        if (!CollectionUtils.isEmpty(studentIdList)) {
//            queryConditionList.add(new QueryCondition("pt.stuid", QueryCondition.Prepender.AND, QueryCondition.Operator.IN, studentIdList));
//        }

        if (!StringUtils.isEmpty(pubStatus)) {
            queryConditionList.add(new QueryCondition("eg.pubstatus", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, pubStatus));
        }

        queryConditionList.add(new QueryCondition("exam.status", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, Exam.STATUS_DELETED));

        return queryConditionList;

    }

    public boolean isSimpleMode() {
        return isSimpleMode;
    }

    public void setSimpleMode(boolean simpleMode) {
        isSimpleMode = simpleMode;
    }

    public Integer getShowMode() {
        return showMode;
    }

    public void setShowMode(Integer showMode) {
        this.showMode = showMode;
    }

    public Integer getStudentTopCount() {
        return studentTopCount;
    }

    public void setStudentTopCount(Integer studentTopCount) {
        this.studentTopCount = studentTopCount;
    }

    public List<String> getTopLevelCountList() {
        return topLevelCountList;
    }

    public void setTopLevelCountList(List<String> topLevelCountList) {
        this.topLevelCountList = topLevelCountList;
    }

    public List<String> getLastLevelCountList() {
        return lastLevelCountList;
    }

    public void setLastLevelCountList(List<String> lastLevelCountList) {
        this.lastLevelCountList = lastLevelCountList;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<SysAnalysisScoreLevel> getLevelZoneConfigList() {
        return levelZoneConfigList;
    }

    public void setLevelZoneConfigList(List<SysAnalysisScoreLevel> levelZoneConfigList) {
        this.levelZoneConfigList = levelZoneConfigList;
    }

    public List<? extends Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<? extends Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public Map<String, Object> getMore() {
        return more;
    }

    public void setMore(Map<String, Object> more) {
        this.more = more;
    }

    public Object addMore(String name, Object value) {
        if (more == null) {
            more = new HashMap<>();
        }

        return more.put(name, value);
    }

    public String getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(String pubStatus) {
        this.pubStatus = pubStatus;
    }

    public void setPubStatusPublished() {
        this.pubStatus = ExamSubjectScore.PUB_STATUS_PUBLISHED;
    }

    public List<String> getTeacherClassIdList() {
        return teacherClassIdList;
    }

    public void setTeacherClassIdList(List<String> teacherClassIdList) {
        this.teacherClassIdList = teacherClassIdList;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getCheckTeacherScope() {
        return checkTeacherScope;
    }

    public void setCheckTeacherScope(Integer checkTeacherScope) {
        this.checkTeacherScope = checkTeacherScope;
    }

    public boolean doesCheckTeacherScope() {
        return checkTeacherScope != null && checkTeacherScope > 0;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

    public boolean isForcing() {
        return force != null && force > 0;
    }

    public void downForce() {
        force --;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public List<String> getVolumeIdList() {
        return volumeIdList;
    }

    public void setVolumeIdList(List<String> volumeIdList) {
        this.volumeIdList = volumeIdList;
    }

    public boolean isBelongsToManagerGeneral() {
        return belongsToManagerGeneral;
    }

    public void setBelongsToManagerGeneral(boolean belongsToManagerGeneral) {
        this.belongsToManagerGeneral = belongsToManagerGeneral;
    }
}