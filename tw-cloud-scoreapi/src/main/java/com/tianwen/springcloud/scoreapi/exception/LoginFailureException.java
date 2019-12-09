package com.tianwen.springcloud.scoreapi.exception;

import com.tianwen.springcloud.commonapi.base.response.ServerResult;
import com.tianwen.springcloud.scoreapi.base.BaseException;

/**
 * Created by kimchh on 11/14/2018.
 */
public class LoginFailureException extends BaseException {
    public LoginFailureException(ServerResult serverResult) {
        super(serverResult);
    }
}
