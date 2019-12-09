package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/exam-part-score")
public interface ExamPartScoreMicroApi extends ICRUDMicroApi<ExamPartScore>
{
    @RequestMapping(value = "/batchAdd/{examSubjectId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi批量新增")
    Response<ExamPartScore> batchAdd(@PathVariable("examSubjectId") String examSubjectId, @RequestBody List<ExamPartScore> studentPartScoreList);
}
