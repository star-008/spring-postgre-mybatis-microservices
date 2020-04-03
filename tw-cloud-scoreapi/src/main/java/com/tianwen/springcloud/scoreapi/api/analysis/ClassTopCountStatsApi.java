package com.tianwen.springcloud.scoreapi.api.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.ClassTopCountStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

public interface ClassTopCountStatsApi {

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    Response<ClassTopCountStats> getList(@RequestBody Request request);

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    Response<String> exportTo(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    Response<String> exportToExcel(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/export/compare", method = RequestMethod.POST)
    Response<String> exportToForCompare(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/export/compare/excel", method = RequestMethod.POST)
    Response<String> exportToExcelForCompare(@RequestBody Request request) throws IOException;
}


