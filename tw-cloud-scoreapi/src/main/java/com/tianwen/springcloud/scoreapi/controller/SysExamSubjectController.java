package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.api.SysExamSubjectMicroApi;
import com.tianwen.springcloud.microservice.score.entity.SysExamSubject;
import com.tianwen.springcloud.scoreapi.api.SysExamSubjectApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sys/exam-subject")
public class SysExamSubjectController extends AbstractController implements SysExamSubjectApi {

    @Autowired
    private SysExamSubjectMicroApi sysExamSubjectMicroApi;

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysExamSubject> add(@RequestBody SysExamSubject sysExamSubject) {
        return sysExamSubjectMicroApi.add(sysExamSubject);
    }

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysExamSubject> batchAdd(@RequestBody List<SysExamSubject> sysExamSubjectList) {
        return sysExamSubjectMicroApi.batchAdd(sysExamSubjectList);
    }

    @Override
    @ApiOperation(value = "更新", notes = "根据实体中ID单个更新")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据实体中ID单个更新")
    public Response<SysExamSubject> delete(@RequestBody SysExamSubject sysExamSubject) {
        return sysExamSubjectMicroApi.deleteByEntity(sysExamSubject);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<SysExamSubject> search(@RequestBody QueryTree querytree) {
        return new ApiResponse<>(sysExamSubjectMicroApi.search(querytree));
    }

}
