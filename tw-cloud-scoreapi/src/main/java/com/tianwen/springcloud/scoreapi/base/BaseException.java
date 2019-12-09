package com.tianwen.springcloud.scoreapi.base;

import com.tianwen.springcloud.commonapi.base.response.ServerResult;

/**
 * Created by kimchh on 11/14/2018.
 */
public class BaseException extends RuntimeException {

    private ServerResult serverResult;

    public BaseException(ServerResult serverResult) {
        super(serverResult.getResultMessage());
        this.serverResult = serverResult;
    }

    public ServerResult getServerResult() {
        return serverResult;
    }
}
