package com.tianwen.springcloud.scoreapi.api.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.entity.analysis.StudentStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface StudentStatsApi {

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    Response<StudentStats> getList(@RequestBody Request request);
}


