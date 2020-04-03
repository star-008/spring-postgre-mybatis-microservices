package com.tianwen.springcloud.scoreapi.controller.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuExamCountStats;
import com.tianwen.springcloud.scoreapi.api.analysis.StuExamCountStatsApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.analysis.StuExamCountStatsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/stu-exam-count-stats")
public class StuExamCountStatsController extends AbstractController implements StuExamCountStatsApi {

    @Autowired
    private StuExamCountStatsService stuExamCountStatsService;

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StuExamCountStats> get(@PathVariable("studentId") String studentId) {
        return stuExamCountStatsService.get(studentId);
    }
}
