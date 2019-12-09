package com.tianwen.springcloud.microservice.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tianwen.springcloud.commonapi.base.BaseEntity;
import com.tianwen.springcloud.commonapi.constant.ISystemConstants;
import com.tianwen.springcloud.commonapi.serialize.CustomerDateAndTimeDeserialize;
import com.tianwen.springcloud.microservice.base.constant.IBaseMicroConstants;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户账号信息实体类(对应t_e_user_logininfo表)
 * 
 * @author wangbin
 * @version [版本号, 2017年4月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Table(name = "t_e_user_logininfo")
public class UserLoginInfo extends BaseEntity
{
    
    /**
     * <属性变量说明>
     * 
     */
    private static final long serialVersionUID = 6806799806995005200L;
    
    /**
     * 用户Id
     */
    @Id
    @Column(name = "userid")
    @ApiModelProperty(value = "用户Id", required = true)
    private String userId;
    
    /**
     * 自定义登录名
     */
    @Column(name = "loginname")
    @ApiModelProperty(value = "自定义登录名", required = true)
    private String loginName;
    
    /**
     * 登录Email
     */
    @Column(name = "loginemail")
    @ApiModelProperty("登录邮箱")
    private String loginEmail;
    
    /**
     * 登录手机号
     */
    @Column(name = "loginmobile")
    @ApiModelProperty("登录手机号")
    private String loginMobile;
    
    /**
     * 静态密码/初始密码为123456
     */
    @Column(name = "staticpassword")
    @ApiModelProperty(value = "密码")
    private String staticPassword;
    
    /**
     * 创建时间
     */
    @Column(name = "createtime")
    @ApiModelProperty("创建时间")
    private Date createTime;
    
    /**
     * 本次登录成功时间
     */
    @Column(name = "currentlogintime")
    @ApiModelProperty("本次登录成功时间")
    private Date currentLoginTime;
    
    /**
     * 上次登录成功时间
     */
    @Column(name = "lastlogintime")
    @ApiModelProperty("上次登录成功时间")
    private Date lastLoginTime;
    
    /**
     * 账号资料最后修改时间（新建账号时为空）
     */
    @Column(name = "lastmodifytime")
    @ApiModelProperty(value = "最后修改时间")
    private Date lastModifyTime;
    
    /**
     * 账号状态 1:正常 2:待审核 3: 待激活 4:审核不通过 7:冻结 9:删除
     */
    @Column(name = "status")
    @ApiModelProperty(value = "账号状态", allowableValues = "1,2,3,4,7,9")
    private String status;
    
    /**
     * 最后被锁定时间
     */
    @Column(name = "lastlockedtime")
    @ApiModelProperty("最后被锁定时间")
    private Date lastLockedTime;
    
    /**
     * 最后登录失败时间
     */
    @Column(name = "lastloginfailedtime")
    @ApiModelProperty("最后登录失败时间")
    private Date lastLoginFailedTime;
    
    /**
     * 登录失败总次数
     */
    @Column(name = "loginfailedcount")
    @ApiModelProperty("登录失败总次数")
    private Integer loginFailedCount;
    
    /**
     * 真实姓名
     */
    @Column(name = "realname")
    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName;
    
    /**
     * 账号是否被锁定
     */
    @Column(name = "islocked")
    @ApiModelProperty("账号是否被锁定")
    private Boolean isLocked;

    /**
     * 机构ID
     */
    @Column(name = "orgid")
    @ApiModelProperty("机构ID")
    private String orgId;
    
    /**
     * 机构名称
     */
    @Transient
    /*
     * @CacheField(keyName = "orgId", cacheType = CacheType.ENTITYINFO_ALL, ignoreKeys = { "0"}, mappedClass =
     * OrgEdu.class, mappedFieldName = "orgName")
     */
    @ApiModelProperty("机构名称")
    private String orgName;
    
    @Transient
    @ApiModelProperty(value = "logo文件id")
    private String logoFileId;
    
    /**
     * 登录身份证号码
     */
    @Column(name = "idcardno")
    @ApiModelProperty("登录身份证号码")
    private String idCardNo;
    
    @Column(name = "sex")
    @ApiModelProperty(value = "性别", allowableValues = "0,1")
    private String sex;
    
    @Column(name = "birthday")
    @ApiModelProperty(value = "生日")
    @JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = ISystemConstants.TIMEZONE)
    private Date birthday;
    
    /**
     * 所在区域ID
     */
    @Transient
    @ApiModelProperty("所在区域ID")
    private String areaId;
    
    /**
     * 所在区域名称
     */
    @Transient
    @ApiModelProperty("所在区域名称")
    private String areaName;
    
    @Transient
    @ApiModelProperty("角色ID")
    private String roleid;

    @Transient
    @ApiModelProperty("角色ID列表")
    // @CacheField(keyName = "userId", cacheType = CacheType.ENTITYINFO_ALL, mappedClass = UserRole.class,
    // associatedFieldName = "userId", mappedFieldName = "roleId")
    private List<String> roleIdList;
    
    @Transient
    // @CacheField(keyName = "roleId", cacheType = CacheType.ENTITYINFO_ALL, mappedClass = Role.class, mappedFieldName =
    // "roleName")
    @ApiModelProperty("角色名称")
    private String rolename;
    
    @Column(name = "fileid")
    @ApiModelProperty(value = "头像文件ID")
    private String fileId;
    
    @Transient
    // @CacheField(keyName = "fileId", cacheType = CacheType.ENTITYINFO_ALL, mappedClass = FileInfo.class,
    // mappedFieldName = "localPath")
    @ApiModelProperty(value = "头像文件路径")
    private String imagePath;
    
    @Transient
    @ApiModelProperty(value = "租户局点编码")
    private String branchCode;

    @Column(name = "token")
    @ApiModelProperty(value = "Token")
    private String token;

    @Column(name = "expiresin")
    @ApiModelProperty(value = "")
    private Long expiresin;

    public Long getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(Long expiresin) {
        this.expiresin = expiresin;
    }

    @Column(name = "refreshtoken")
    @ApiModelProperty(value = "Refresh_Token")
    private String refreshtoken;

    @Column(name = "tokenrefreshedtime")
    @ApiModelProperty("")
    private Date tokenrefreshedtime;

    @Transient
    private int uploadedResourceCount;

    @Transient
    private double chargeScore;

    @Transient
    private ClassInfo classinfo;

    private Object member;

    private List<Area> areaList;

    private int ordercount;

    @Transient
    private Integer contribution;

    @Transient
    private List<ClassInfo> classList = null;

    @Transient
    private List<String> classIdList = null;

    public int getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(int ordercount) {
        this.ordercount = ordercount;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public Object getMember() {
        return member;
    }

    public void setMember(Object member) {
        this.member = member;
    }

    public ClassInfo getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(ClassInfo classinfo) {
        this.classinfo = classinfo;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getBranchCode()
    {
        return branchCode;
    }
    
    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }
    
    public String getImagePath()
    {
        return imagePath;
    }
    
    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }
    
    public String getFileId()
    {
        return fileId;
    }
    
    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getLoginName()
    {
        return loginName;
    }
    
    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }
    
    public String getLoginEmail()
    {
        return loginEmail;
    }
    
    public void setLoginEmail(String loginEmail)
    {
        this.loginEmail = loginEmail;
    }
    
    public String getLoginMobile()
    {
        return loginMobile;
    }
    
    public void setLoginMobile(String loginMobile)
    {
        this.loginMobile = loginMobile;
    }
    
    public String getStaticPassword()
    {
        return staticPassword;
    }
    
    public void setStaticPassword(String staticPassword)
    {
        this.staticPassword = staticPassword;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getCurrentLoginTime()
    {
        return currentLoginTime;
    }
    
    public void setCurrentLoginTime(Date currentLoginTime)
    {
        this.currentLoginTime = currentLoginTime;
    }
    
    public Date getLastLoginTime()
    {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(Date lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }
    
    public Date getLastModifyTime()
    {
        return lastModifyTime;
    }
    
    public void setLastModifyTime(Date lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Date getLastLockedTime()
    {
        return lastLockedTime;
    }
    
    public void setLastLockedTime(Date lastLockedTime)
    {
        this.lastLockedTime = lastLockedTime;
    }
    
    public Date getLastLoginFailedTime()
    {
        return lastLoginFailedTime;
    }
    
    public void setLastLoginFailedTime(Date lastLoginFailedTime)
    {
        this.lastLoginFailedTime = lastLoginFailedTime;
    }
    
    public Integer getLoginFailedCount()
    {
        return loginFailedCount;
    }
    
    public void setLoginFailedCount(Integer loginFailedCount)
    {
        this.loginFailedCount = loginFailedCount;
    }

    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public Boolean getIsLocked()
    {
        return isLocked;
    }
    
    public void setIsLocked(Boolean isLocked)
    {
        this.isLocked = isLocked;
    }
    
    public String getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }
    
    public String getIdCardNo()
    {
        return idCardNo;
    }
    
    public void setIdCardNo(String idCardNo)
    {
        this.idCardNo = idCardNo;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    public String getAreaId()
    {
        return areaId;
    }
    
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }
    
    public String getAreaName()
    {
        return areaName;
    }
    
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public List<String> getRoleIdList()
    {
        return roleIdList;
    }
    
    public void setRoleIdList(List<String> roleIdList)
    {
        this.roleIdList = roleIdList;
    }
    
    public String getRoleName()
    {
        return rolename;
    }
    
    public void setRoleName(String roleName)
    {
        this.rolename = roleName;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public Date getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }
    
    public String getLogoFileId()
    {
        return logoFileId;
    }
    
    public void setLogoFileId(String logoFileId)
    {
        this.logoFileId = logoFileId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    public Date getTokenrefreshedtime() {
        return tokenrefreshedtime;
    }

    public void setTokenrefreshedtime(Date tokenrefreshedtime) {
        this.tokenrefreshedtime = tokenrefreshedtime;
    }

    public int getUploadedResourceCount() {
        return uploadedResourceCount;
    }

    public void setUploadedResourceCount(int uploadedResourceCount) {
        this.uploadedResourceCount = uploadedResourceCount;
    }

    public double getChargeScore() {
        return chargeScore;
    }

    public void setChargeScore(double chargeScore) {
        this.chargeScore = chargeScore;
    }

    @Override
    public String toString()
    {
        return "UserLoginInfo [userId=" + userId + ", loginName=" + loginName + ", loginEmail=" + loginEmail
            + ", loginMobile=" + loginMobile + ", staticPassword=" + staticPassword + ", createTime=" + createTime
            + ", currentLoginTime=" + currentLoginTime + ", lastLoginTime=" + lastLoginTime + ", lastModifyTime="
            + lastModifyTime + ", status=" + status + ", lastLockedTime=" + lastLockedTime + ", lastLoginFailedTime="
            + lastLoginFailedTime + ", loginFailedCount=" + loginFailedCount + ", realName=" + realName + ", isLocked="
            + isLocked + ", orgId=" + orgId + ", orgName=" + orgName + ", logoFileId=" + logoFileId + ", idCardNo="
            + idCardNo + ", sex=" + sex + ", birthday=" + birthday + ", areaId=" + areaId + ", areaName=" + areaName
            + ", roleId=" + roleid + ", roleIdList=" + roleIdList + ", roleName=" + rolename     + ", fileId=" + fileId
            + ", imagePath=" + imagePath + ", branchCode=" + branchCode + ", token=" + token + "]";
    }

    public boolean belongsToManagerGeneral() {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return false;
        }

        List<String> managerRoleIdList = Arrays.asList(
                IBaseMicroConstants.USER_ROLE_ID_MANAGER,
                IBaseMicroConstants.USER_ROLE_ID_TIANWEN_MANAGER,
                IBaseMicroConstants.USER_ROLE_ID_REGION_MANAGER,
                IBaseMicroConstants.USER_ROLE_ID_DEVELOPER,
                IBaseMicroConstants.USER_ROLE_ID_PRINCIPAL,
                IBaseMicroConstants.USER_ROLE_ID_EDU_MANAGER,
                IBaseMicroConstants.USER_ROLE_ID_SCORE_MANAGER
        );

        return CollectionUtils.containsAny(roleIdList, managerRoleIdList);
    }

    public boolean isTeacher() {
        return !CollectionUtils.isEmpty(roleIdList)
                && CollectionUtils.containsAny(roleIdList, Arrays.asList(IBaseMicroConstants.USER_ROLE_ID_TEACHER, IBaseMicroConstants.USER_ROLE_ID_SCHOOL_MANAGER, IBaseMicroConstants.USER_ROLE_ID_PRINCIPAL));

    }

    public boolean isStudent() {
        return !CollectionUtils.isEmpty(roleIdList)
                && roleIdList.contains(IBaseMicroConstants.USER_ROLE_ID_STUDENT);

    }

    public boolean isParent() {
        return !CollectionUtils.isEmpty(roleIdList)
                && roleIdList.contains(IBaseMicroConstants.USER_ROLE_ID_PARENT);

    }

    public boolean isVisitor() {
        return !CollectionUtils.isEmpty(roleIdList)
                && roleIdList.contains(IBaseMicroConstants.USER_ROLE_ID_VISITOR);

    }

    public List<ClassInfo> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassInfo> classList) {
        this.classList = classList;
    }

    public List<String> getClassIdList() {
        return classIdList;
    }

    public void setClassIdList(List<String> classIdList) {
        this.classIdList = classIdList;
    }
}
