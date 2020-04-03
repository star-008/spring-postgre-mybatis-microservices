package com.tianwen.springcloud.scoreapi.controller.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.analysis.EqualityStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.api.analysis.EqualityStatsApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.analysis.EqualityStatsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping(value = "/equality-stats")
public class EqualityStatsController extends AbstractController implements EqualityStatsApi {

    @Autowired
    private EqualityStatsService equalityStatsService;

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<EqualityStats> getList(@RequestBody Request request) {
        return equalityStatsService.getList(request);
    }

    @Override
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Response<String> exportTo(@RequestBody Request request) throws IOException {
        return exportToExcel(request);
    }

    @Override
    @RequestMapping(value = "/export/excel", method = RequestMethod.POST)
    public Response<String> exportToExcel(@RequestBody Request request) throws IOException {
        return new Response<>(equalityStatsService.exportToExcel(request));
    }

    @Override
    @RequestMapping(value = "/export/class", method = RequestMethod.POST)
    public Response<String> exportToByClass(@RequestBody Request request) throws IOException {
        return exportToExcelByClass(request);
    }

    @Override
    @RequestMapping(value = "/export/class/excel", method = RequestMethod.POST)
    public Response<String> exportToExcelByClass(@RequestBody Request request) throws IOException {
        return new Response<>(equalityStatsService.exportToExcelByClass(request));
    }

    @Override
    @RequestMapping(value = "/export/compare", method = RequestMethod.POST)
    public Response<String> exportForCompare(@RequestBody Request request) throws IOException {
        return exportToExcelForCompare(request);
    }

    @Override
    @RequestMapping(value = "/export/compare/excel", method = RequestMethod.POST)
    public Response<String> exportToExcelForCompare(@RequestBody Request request) throws IOException {
        return new Response<>(equalityStatsService.exportToExcelForCompare(request));
    }
}
