package com.tianwen.springcloud.scoreapi.api.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RankStatsApi {

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    Response<RankStats> getList(@RequestBody Request request);

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    Response<String> exportTo(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    Response<String> exportToExcel(@RequestBody Request request) throws IOException;
    @RequestMapping(value = "/export/compare", method = RequestMethod.POST)
    Response<String> exportToForCompare(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/export/excel/compare", method = RequestMethod.POST)
    Response<String> exportToExcelForCompare(@RequestBody Request request) throws IOException;
}
