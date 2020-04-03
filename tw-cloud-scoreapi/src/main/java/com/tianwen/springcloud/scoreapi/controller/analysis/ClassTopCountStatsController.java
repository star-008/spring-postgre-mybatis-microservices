package com.tianwen.springcloud.scoreapi.controller.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.analysis.ClassTopCountStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.api.analysis.ClassTopCountStatsApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.analysis.ClassTopCountStatsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping(value = "/class-top-count-stats")
public class ClassTopCountStatsController extends AbstractController implements ClassTopCountStatsApi {

    @Autowired
    private ClassTopCountStatsService classTopCountStatsService;

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<ClassTopCountStats> getList(@RequestBody Request request) {
        return classTopCountStatsService.getList(request);
    }

    @Override
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Response<String> exportTo(@RequestBody Request request) throws IOException {
        return exportToExcel(request);
    }

    @Override
    @RequestMapping(value = "/export/excel", method = RequestMethod.POST)
    public Response<String> exportToExcel(@RequestBody Request request) throws IOException {
        return new Response<>(classTopCountStatsService.exportToExcel(request));
    }

    @Override
    @RequestMapping(value = "/export/compare", method = RequestMethod.POST)
    public Response<String> exportToForCompare(@RequestBody Request request) throws IOException {
        return exportToExcelForCompare(request);
    }

    @Override
    @RequestMapping(value = "/export/compare/excel", method = RequestMethod.POST)
    public Response<String> exportToExcelForCompare(@RequestBody Request request) throws IOException {
        return new Response<>(classTopCountStatsService.exportToExcelForCompare(request));
    }

}
