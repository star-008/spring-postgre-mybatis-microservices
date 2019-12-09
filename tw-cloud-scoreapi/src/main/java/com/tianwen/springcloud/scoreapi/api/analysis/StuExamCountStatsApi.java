package com.tianwen.springcloud.scoreapi.api.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuExamCountStats;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface StuExamCountStatsApi {

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    Response<StuExamCountStats> get(@PathVariable("studentId") String studentId);
}


