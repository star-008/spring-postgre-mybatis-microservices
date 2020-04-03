package com.tianwen.springcloud.scoreapi.entity.response;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kimchh on 11/7/2018.
 */
public class ECOUserInfo {

    private String userId;
    private String realName;
    private String loginName;
    private String loginEmail;
    private String loginMobile;
    private String staticPassword;
    private String orgId;
    private Timestamp birthday;
    private String imagePath;
    private String fileId;
    private String idCardNo;
    private String sex;

    private List<String> roleIdList;

//    public boolean hasRole(String roleName) {
//        if (roleName == null || roleName.isEmpty()) {
//            return false;
//        }
//
//        try {
//            ECORoleId roleId = ECORoleId.valueOf(roleName.toUpperCase());
//            if (roleId == null) {
//                return false;
//            }
//
//            return getRoleIdList() != null && getRoleIdList().contains(roleId.value());
//        } catch (Exception ex) {
//            return false;
//        }
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginMobile() {
        return loginMobile;
    }

    public void setLoginMobile(String loginMobile) {
        this.loginMobile = loginMobile;
    }

    public String getStaticPassword() {
        return staticPassword;
    }

    public void setStaticPassword(String staticPassword) {
        this.staticPassword = staticPassword;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
