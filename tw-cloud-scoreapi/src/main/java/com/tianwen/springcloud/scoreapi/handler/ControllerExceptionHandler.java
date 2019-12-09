package com.tianwen.springcloud.scoreapi.handler;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.base.BaseException;
import com.tianwen.springcloud.scoreapi.exception.LoginFailureException;
import com.tianwen.springcloud.scoreapi.exception.InvalidTokenException;
import com.tianwen.springcloud.scoreapi.service.util.CaptchaService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice(assignableTypes = {AbstractController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public Response handleBaseException(BaseException ex) {
        return new ApiResponse<>(ex.getServerResult());
    }

    @ExceptionHandler({InvalidTokenException.class})
    public Response handleTokenException(InvalidTokenException ex) {
        return new ApiResponse<>(ex.getServerResult());
    }

    @ExceptionHandler({LoginFailureException.class})
    public Response handleLoginFailureException(LoginFailureException ex) {
        BeanHolder.getBean(CaptchaService.class).saveLastLoginFailureTime();
        return new ApiResponse<>(ex.getServerResult());
    }
}
