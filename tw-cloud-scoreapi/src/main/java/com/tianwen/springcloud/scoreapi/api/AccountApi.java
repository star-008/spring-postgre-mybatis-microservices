package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.base.entity.UserLoginInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface AccountApi
{
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    Response<UserLoginInfo> changePassword(@RequestBody Map<String, Object> param);

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    Response<UserLoginInfo> resetPassword(@RequestBody Map<String, Object> param);
}
