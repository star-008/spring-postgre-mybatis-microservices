package com.tianwen.springcloud.scoreapi.base;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.base.response.ServerResult;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.scoreapi.service.util.repo.IdRepositoryService;

import java.util.List;

/**
 * Created by kimchh on 11/14/2018.
 */
public class ApiResponse<T> extends com.tianwen.springcloud.commonapi.base.response.Response<T> {

    public ApiResponse() {
        super();
    }

    public ApiResponse(ServerResult serverResult) {
        setServerResult(serverResult);
    }

    public ApiResponse(T t) {
        super(t);
        getRepositoryService().fill(t);
    }

    public ApiResponse(List<T> list) {
        super(list);
        getRepositoryService().fill(list);
    }

    public ApiResponse(String resultCode, String resultMessage, String internalMessage) {
        super(resultCode, resultMessage, internalMessage);
    }

    public ApiResponse(String resultCode, String resultMessage) {
        super(resultCode, resultMessage);
    }

    public ApiResponse(Response<T> response) {
        this.setServerResult(response.getServerResult());
        this.setResponseEntity(response.getResponseEntity());
        this.setPageInfo(response.getPageInfo());

        if (!(response instanceof ApiResponse)) { //We don't need to fill again, if the response is instance of ApiResponse
            if (this.getResponseEntity() != null) {
                getRepositoryService().fill(this.getResponseEntity());
            }
            if (this.getPageInfo() != null && this.getPageInfo().getList() != null) {
                getRepositoryService().fill(this.getPageInfo().getList());
            }
        }
    }

    protected IdRepositoryService getRepositoryService() {
        return BeanHolder.getBean(IdRepositoryService.class);
    }
}
