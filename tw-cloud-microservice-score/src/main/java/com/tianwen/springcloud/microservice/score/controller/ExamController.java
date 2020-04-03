package com.tianwen.springcloud.microservice.score.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.exception.ParameterException;
import com.tianwen.springcloud.commonapi.utils.ValidatorUtil;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.request.ExamTypeFilter;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exam")
public class ExamController extends AbstractScoreCRUDController<Exam> implements ExamMicroApi {

    @Autowired
    private ExamService examService;

    public ExamController() {
        useGetListForSearch();
    }

    @Override
    public void validate(MethodType methodType, Object p) {
        switch (methodType) {
            case ADD:
            case UPDATE:
                if (null == p) {
                    throw new ParameterException(IStateCode.PARAMETER_IS_EMPTY, "请求体为空");
                }
                Exam exam = (Exam) p;
                ValidatorUtil.parameterValidate(exam);

                if (methodType == MethodType.UPDATE && (null == exam.getId() || exam.getId().isEmpty())) {
                    try {
                        String fieldName = exam.getClass().getDeclaredField("id").getAnnotation(JsonProperty.class).value();
                        throw new ParameterException(IStateCode.PARAMETER_IS_EMPTY,
                                getMessageService().getMessage(IStateCode.PARAMETER_IS_EMPTY, new String[] {fieldName}, "参数[" + fieldName + "]的值为空"), fieldName);
                    } catch (NoSuchFieldException e) {

                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public Response<Exam> getSimpleList(@RequestBody Request request) {
        return new Response<>(examService.getSimpleList(request));
    }

    @Override
    public Response<String> getTermIdList(@RequestBody Filter filter) {
        return new Response<>(examService.getTermIdList(filter));
    }

    @Override
    public Response<String> getTypeIdList(@RequestBody ExamTypeFilter filter) {
        return new Response<>(examService.getTypeIdList(filter));
    }
}
