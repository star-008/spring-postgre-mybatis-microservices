package com.tianwen.springcloud.scoreapi.controller.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.analysis.SubjectAverageScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.api.analysis.SubjectAverageScoreApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.analysis.SubjectAverageScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/subject-average-score")
public class SubjectAverageScoreController extends AbstractController implements SubjectAverageScoreApi {

    @Autowired
    private SubjectAverageScoreService subjectAverageScoreService;

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<SubjectAverageScore> getList(@RequestBody Request request) {
        return subjectAverageScoreService.getList(request);
    }

    @Override
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Response<String> exportTo(@RequestBody Request request) throws IOException {
        return exportToExcel(request);
    }

    @Override
    @RequestMapping(value = "/export/excel", method = RequestMethod.POST)
    public Response<String> exportToExcel(@RequestBody Request request) throws IOException {

        return new Response<>(subjectAverageScoreService.exportToExcel(request));
    }

    @Override
    @RequestMapping(value = "/export/class", method = RequestMethod.POST)
    public Response<String> exportToByClass(@RequestBody Request request) throws IOException {
        return exportToExcelByClass(request);
    }

    @Override
    @RequestMapping(value = "/export/class/excel", method = RequestMethod.POST)
    public Response<String> exportToExcelByClass(@RequestBody Request request) throws IOException {

        return new Response<>(subjectAverageScoreService.exportToExcelByClass(request));
    }
}
