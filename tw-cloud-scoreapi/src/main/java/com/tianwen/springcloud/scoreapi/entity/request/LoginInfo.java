package com.tianwen.springcloud.scoreapi.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kimchh on 11/7/2018.
 */
public class LoginInfo {

    private String username;

    private String password;

    private String captcha;

    private String appId;

    private String appKey;

    @JsonProperty("verifykey")
    private String captchaVerifyKey;

    @JsonProperty("kind")
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getCaptchaVerifyKey() {
        return captchaVerifyKey;
    }

    public void setCaptchaVerifyKey(String captchaVerifyKey) {
        this.captchaVerifyKey = captchaVerifyKey;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
