package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.base.entity.UserLoginInfo;
import com.tianwen.springcloud.scoreapi.entity.request.LoginInfo;
import com.tianwen.springcloud.scoreapi.entity.request.SSOLoginInfo;
import com.tianwen.springcloud.scoreapi.entity.response.CaptchaInfo;
import org.springframework.web.bind.annotation.*;

public interface GuestApi
{
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    Response<CaptchaInfo> requestCaptcha() throws Exception;

    @RequestMapping(value = "/captcha/{force}", method = RequestMethod.GET)
    Response<CaptchaInfo> requestCaptcha(@PathVariable Integer force) throws Exception;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Response<UserLoginInfo> login(@RequestBody LoginInfo loginInfo);

    @RequestMapping(value = "/login/sso", method = RequestMethod.POST)
    Response<UserLoginInfo> loginSSO(@RequestBody SSOLoginInfo loginInfo);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    Response logout(@RequestHeader(value = "token") String token);
}
