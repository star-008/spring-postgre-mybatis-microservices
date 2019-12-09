package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/sys-analysis-score-level")
public interface SysAnalysisScoreLevelMicroApi extends ICRUDMicroApi<SysAnalysisScoreLevel> {

    @RequestMapping(value = "/batchAdd/{examId}/{gradeId}/{subjectType}/{subjectId}/{volumeId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi批量新增")
    Response<SysAnalysisScoreLevel> batchAdd(@RequestBody List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList, @PathVariable("examId") String examId, @PathVariable("gradeId") String gradeId, @PathVariable("subjectType") String subjectType, @PathVariable("subjectId") String subjectId, @PathVariable("volumeId") String volumeId);

    @RequestMapping(value = "/getTypeListByExam", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetTypeListByExam")
    Response<SysAnalysisScoreLevel> getTypeListByExam(@RequestBody Request request);

    @RequestMapping(value = "/getTypeCountByExam", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi GetTypeCountByExam")
    Response<Integer> getTypeCountByExam(@RequestBody Request request);
}
