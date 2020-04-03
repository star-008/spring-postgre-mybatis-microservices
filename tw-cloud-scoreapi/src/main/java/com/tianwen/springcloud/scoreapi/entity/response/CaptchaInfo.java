package com.tianwen.springcloud.scoreapi.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kimchh on 11/7/2018.
 */
public class CaptchaInfo {
    private String url;
    
    @JsonProperty("verifykey")
    private String verifyKey;

    public CaptchaInfo() {
    }

    public CaptchaInfo(String url, String verifyKey) {
        this.url = url;
        this.verifyKey = verifyKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }
}
