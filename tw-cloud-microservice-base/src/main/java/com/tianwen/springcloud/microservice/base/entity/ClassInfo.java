package com.tianwen.springcloud.microservice.base.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_e_class")
public class ClassInfo extends BaseEntity{

    public static final String CLASS_TYPE_NORMAL = "1";
    public static final String CLASS_TYPE_SPECIAL = "2";

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT pg_nextval('seq_classid_t_e_class')")
    @Column(name = "classid")
    @ApiModelProperty("")
    private String classid;

    @Column(name = "classtype")
    @ApiModelProperty(name = "classtype", value = "classtype")
    @JsonProperty("classtype")
    private String classType;

    @Column(name = "name")
    @ApiModelProperty(value = "")
    private String name;

    @Column(name = "schoolsectionid")
    @ApiModelProperty(value = "")
    private String schoolsectionid;

    @Transient
    private String schoolsection;

    @Column(name = "gradeid")
    @ApiModelProperty(value = "")
    private String gradeid;

    @Transient
    private String grade;

    @Column(name = "advisernames")
    @ApiModelProperty("")
    @JsonProperty("advisernames")
    private String adviserNames;

    @Column(name = "description")
    @ApiModelProperty("")
    private String description;

    @Column(name = "lastnamemodifytime")
    @ApiModelProperty(value = "")
    private Timestamp lastnamemodifytime;

    @Column(name = "createtime")
    @ApiModelProperty(value = "")
    private Timestamp createtime;

    @Column(name = "creator")
    @ApiModelProperty(value = "")
    private String creator;

    @Column(name = "lastmodifytime")
    @ApiModelProperty(value = "")
    private Timestamp lastmodifytime;

    @Column(name = "orgid")
    @ApiModelProperty(value = "")
    private String orgid;

    @Transient
    private String orgname;

    @Column(name = "schoolstartdate")
    @ApiModelProperty(value = "")
    private Timestamp schoolstartdate;

    @Column(name = "status")
    @ApiModelProperty(value = "")
    private String status;

    @Column(name = "adviserids")
    @ApiModelProperty(value = "")
    @JsonProperty("adviserids")
    private String adviserIds;

    @Column(name = "extinfo")
    @ApiModelProperty(value = "")
    private JSONObject extinfo;

    @Column(name = "bhwllx")
    @ApiModelProperty(value = "")
    private String bhwllx;

    @Transient
    private String subjectid;

    @Transient
    private String subject;

    public ClassInfo() {}

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return name + (StringUtils.isEmpty(bhwllx) ? "" : "(" + bhwllx + ")");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolsectionid() {
        return schoolsectionid;
    }

    public void setSchoolsectionid(String schoolsectionid) {
        this.schoolsectionid = schoolsectionid;
    }

    public String getSchoolsection() {
        return schoolsection;
    }

    public void setSchoolsection(String schoolsection) {
        this.schoolsection = schoolsection;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAdviserNames() {
        return adviserNames;
    }

    public void setAdviserNames(String adviserNames) {
        this.adviserNames = adviserNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getLastnamemodifytime() {
        return lastnamemodifytime;
    }

    public void setLastnamemodifytime(Timestamp lastnamemodifytime) {
        this.lastnamemodifytime = lastnamemodifytime;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(Timestamp lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public Timestamp getSchoolstartdate() {
        return schoolstartdate;
    }

    public void setSchoolstartdate(Timestamp schoolstartdate) {
        this.schoolstartdate = schoolstartdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdviserIds() {
        return adviserIds;
    }

    public void setAdviserIds(String adviserIds) {
        this.adviserIds = adviserIds;
    }

    public JSONObject getExtinfo() {
        return extinfo;
    }

    public void setExtinfo(JSONObject extinfo) {
        this.extinfo = extinfo;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getBhwllx() {
        return bhwllx;
    }

    public void setBhwllx(String bhwllx) {
        this.bhwllx = bhwllx;
    }

    public int compareName(ClassInfo classInfo) {
        return name.compareTo(classInfo.name);
    }
}
