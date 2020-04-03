package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.microservice.score.entity.SysExamSubject;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/sys-exam-subject")
public interface SysExamSubjectMicroApi extends ICRUDMicroApi<SysExamSubject>
{
}
