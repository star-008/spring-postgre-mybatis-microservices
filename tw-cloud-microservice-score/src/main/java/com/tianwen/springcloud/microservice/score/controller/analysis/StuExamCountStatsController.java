package com.tianwen.springcloud.microservice.score.controller.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.api.analysis.StuExamCountStatsMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuExamCountStats;
import com.tianwen.springcloud.microservice.score.service.analysis.StuExamCountStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stu-exam-count-stats")
public class StuExamCountStatsController extends AbstractScoreCRUDController<StuExamCountStats> implements StuExamCountStatsMicroApi {

    @Autowired
    private StuExamCountStatsService stuExamCountStatsService;

    @Override
    public void validate(MethodType methodType, Object p) {

    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public Response<StuExamCountStats> get(@PathVariable("studentId") String studentId) {
        return new Response<>(stuExamCountStatsService.get(studentId));
    }

}