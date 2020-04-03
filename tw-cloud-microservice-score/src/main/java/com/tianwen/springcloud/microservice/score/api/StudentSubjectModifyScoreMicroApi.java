package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import org.springframework.cloud.netflix.feign.FeignClient;


@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/student-subject-modify-score")
public interface StudentSubjectModifyScoreMicroApi extends ICRUDMicroApi<StudentSubjectModifyScore>
{

}
