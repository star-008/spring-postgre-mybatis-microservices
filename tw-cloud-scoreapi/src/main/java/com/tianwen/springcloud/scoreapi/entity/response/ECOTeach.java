package com.tianwen.springcloud.scoreapi.entity.response;

import org.apache.commons.lang.StringUtils;

/**
 * Created by kimchh on 11/28/2018.
 */
public class ECOTeach {

    private String teachId;
    private String teacherName;

    private String orgId;
    private String orgName;

    private String userId;

    private String gradeName;

    private String classId;
    private String className;
    private String classStatus;

    private String subjectId;
    private String subjectName;

    private String createTime;

    public ECOTeach() {

    }

    public ECOTeach(String classId, String subjectId) {
        this.classId = classId;
        this.subjectId = subjectId;
    }

    public String getTeachId() {
        return teachId;
    }

    public void setTeachId(String teachId) {
        this.teachId = teachId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ECOTeach)
            return equals((ECOTeach) obj);
        else
            return false;
    }

    private boolean equals(ECOTeach teach) {
        return StringUtils.equals(classId, teach.classId)
                && StringUtils.equals(subjectId, teach.subjectId);
    }

}
