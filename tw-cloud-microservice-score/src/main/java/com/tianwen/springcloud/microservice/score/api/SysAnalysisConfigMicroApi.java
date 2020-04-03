package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/sys-analysis-config")
public interface SysAnalysisConfigMicroApi extends ICRUDMicroApi<SysAnalysisConfig> {
    @RequestMapping(value = "/addUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroApi新增")
    Response<SysAnalysisConfig> addUpdate(@RequestBody SysAnalysisConfig entity);
}
