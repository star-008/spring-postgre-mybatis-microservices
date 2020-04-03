package com.tianwen.springcloud.scoreapi.controller.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.analysis.StudentStats;
import com.tianwen.springcloud.microservice.score.entity.analysis.SubjectAverageScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.api.analysis.StudentStatsApi;
import com.tianwen.springcloud.scoreapi.api.analysis.SubjectAverageScoreApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.analysis.StudentStatsService;
import com.tianwen.springcloud.scoreapi.service.analysis.SubjectAverageScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/student-stats")
public class StudentStatsController extends AbstractController implements StudentStatsApi {

    @Autowired
    private StudentStatsService studentStatsService;

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentStats> getList(@RequestBody Request request) {
        return studentStatsService.getList(request);
    }

}
