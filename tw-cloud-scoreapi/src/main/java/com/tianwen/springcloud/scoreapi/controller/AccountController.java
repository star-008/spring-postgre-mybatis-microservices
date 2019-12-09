package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.base.entity.UserLoginInfo;
import com.tianwen.springcloud.scoreapi.api.AccountApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by kimchh on 11/7/2018.
 */
@RestController
public class AccountController extends AbstractController implements AccountApi {

    @Override
    public Response<UserLoginInfo> changePassword(@RequestBody Map<String, Object> param) {
        return userMicroApi.changePassword(param);
    }

    @Override
    public Response<UserLoginInfo> resetPassword(@RequestBody Map<String, Object> param) {
        return userMicroApi.resetPassword(param);
    }
}
