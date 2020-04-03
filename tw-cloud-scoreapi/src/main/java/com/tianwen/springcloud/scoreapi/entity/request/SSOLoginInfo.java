package com.tianwen.springcloud.scoreapi.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kimchh on 11/7/2018.
 */
public class SSOLoginInfo {

    @JsonProperty("accredit_code")
    private String accreditCode;

    @JsonProperty("access_token")
    private String accessToken;

    private String appId;

    private String appKey;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccreditCode() {
        return accreditCode;
    }

    public void setAccreditCode(String accreditCode) {
        this.accreditCode = accreditCode;
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
}
