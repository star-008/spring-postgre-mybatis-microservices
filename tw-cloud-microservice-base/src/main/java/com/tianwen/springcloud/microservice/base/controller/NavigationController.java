package com.tianwen.springcloud.microservice.base.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.datasource.base.AbstractCRUDController;
import com.tianwen.springcloud.microservice.base.api.NavigationMicroApi;
import com.tianwen.springcloud.microservice.base.constant.IBaseMicroConstants;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Navigation;
import com.tianwen.springcloud.microservice.base.service.NavigationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/navigation")
public class NavigationController extends AbstractCRUDController<Navigation> implements NavigationMicroApi
{
    @Autowired
    private NavigationService navigationService;

    @Override
    @ApiOperation(value = "获取学段列表", notes = "获取学段列表")
    @SystemControllerLog(description = "获取学段列表")
    public Response<DictItem> getSchoolSectionList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getSchoolSectionList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取学科列表", notes = "获取学科列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取学科列表")
    public Response<DictItem> getSubjectList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getSubjectList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取年级列表", notes = "获取年级列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取年级列表")
    public Response<DictItem> getGradeList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getGradeList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取学科列表", notes = "获取学科列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取学科列表")
    public Response<DictItem> getTermList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getTermList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取学科列表", notes = "获取学科列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取学科列表")
    public Response<DictItem> getExamTypeList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getExamTypeList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取学科列表", notes = "获取学科列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取学科列表")
    public Response<DictItem> getQuestionCategoryList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getQuestionCategoryList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取学科列表", notes = "获取学科列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取学科列表")
    public Response<DictItem> getQuestionTypeList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getQuestionTypeList(param);
        return new Response<>(result);
    }

    @Override
    @ApiOperation(value = "获取学科列表", notes = "获取学科列表")
    @ApiImplicitParam(name = "param", value = "课程导航信息", required = false, dataType = "Map<String, String>", paramType = "body")
    @SystemControllerLog(description = "获取学科列表")
    public Response<DictItem> getPaperVolumeList(@RequestBody Navigation param)
    {
        if (StringUtils.isEmpty(param.getLang()))
            param.setLang(IBaseMicroConstants.zh_CN);
        List<DictItem> result = navigationService.getPaperVolumeList(param);
        return new Response<>(result);
    }

    @Override
    public void validate(MethodType methodType, Object p) {}
}
