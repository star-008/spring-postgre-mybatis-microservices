package com.tianwen.springcloud.microservice.base.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Table(name = "t_e_student")
public class Student extends BaseEntity
{
    @Id
    @Column(name = "userId")
    @ApiModelProperty(value = "", required = true)
    private String userId;

    @Column(name = "classId")
    @ApiModelProperty(value = "", required = true)
    private String classId;

    @Transient
    private String className;

    @Column(name = "name")
    @ApiModelProperty(value = "name", name = "name", required = true)
    private String name;

    @Column(name = "createTime")
    @ApiModelProperty(value = "", required = true)
    private Timestamp createTime;

    @Column(name = "lastModifyTime")
    @ApiModelProperty(value = "", required = true)
    private Timestamp lastModifyTime;

    @Column(name = "studentcode")
    @ApiModelProperty("")
    private String studentCode;

    @Column(name = "studentno")
    @ApiModelProperty("")
    @JsonProperty("studentno")
    private String studentNo;

    @Transient
    private Map<String, String> extInfo;

    @Transient
    private UserLoginInfo userLoginInfo;

    @Transient
    private String orgId;

    @Transient
    private String orgName;

    private String loginName;

    @Column(name = "sex")
    @ApiModelProperty("sex")
    private String sex;

    @Column(name = "sexname")
    @ApiModelProperty("sexname")
    @JsonProperty("sexname")
    private String sexName;

    private String status;

    public Student(){}

    public Student(String classId) {
        this.classId = classId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserLoginInfo getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserLoginInfo(UserLoginInfo userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Date getCreateTime() {
        return createTime;
    }

//    public void setCreateTime(Timestamp createTime) {
//        this.createTime = createTime;
//    }

    public void setCreateTime(Object createTime) {
        if (createTime instanceof Timestamp) {
            this.createTime = (Timestamp)createTime;
        } else if (createTime instanceof Date) {
            this.createTime = new Timestamp(((Date) createTime).getTime());
        } else {
            this.createTime = Timestamp.valueOf((String) createTime);
        }
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

//    public void setLastModifyTime(Timestamp lastModifyTime) {
//        this.lastModifyTime = lastModifyTime;
//    }

    public void setLastModifyTime(Object lastModifyTime) {
        if (lastModifyTime instanceof Timestamp) {
            this.lastModifyTime = (Timestamp)lastModifyTime;
        } else if (lastModifyTime instanceof Date) {
            this.lastModifyTime = new Timestamp(((Date) lastModifyTime).getTime());
        } else {
            this.lastModifyTime = Timestamp.valueOf((String) lastModifyTime);
        }
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    public Student consumeExtInfo() {
        if (extInfo != null) {
            studentNo = extInfo.get("XH");
        }

        return this;
    }
}
