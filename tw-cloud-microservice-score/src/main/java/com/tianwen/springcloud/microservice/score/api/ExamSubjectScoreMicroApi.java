package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.ExamGradeFilter;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/exam-subject-score")
public interface ExamSubjectScoreMicroApi extends ICRUDMicroApi<ExamSubjectScore> {

    @RequestMapping(value = "/getGradeIdList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp GetGradeList")
    Response<String> getGradeIdList(@RequestBody ExamGradeFilter filter);

    @RequestMapping(value = "/getSubjectIdList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetSubjectList")
    Response<String> getSubjectIdList(@RequestBody ExamSubjectFilter filter);

    @RequestMapping(value = "/getPubStatusList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetSubjectList")
    Response<ExamSubjectScore> getPubStatusList(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/getCount", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetCount")
    Response<Integer> getCount(@RequestBody Request request);
}
