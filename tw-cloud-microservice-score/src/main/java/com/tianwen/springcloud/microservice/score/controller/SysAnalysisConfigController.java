package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.api.SysAnalysisConfigMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sys-analysis-config")
public class SysAnalysisConfigController extends AbstractScoreCRUDController<SysAnalysisConfig> implements SysAnalysisConfigMicroApi {

    @Autowired
    private SysAnalysisConfigService sysAnalysisConfigService;

    @Override
    public void validate(MethodType methodType, Object p) {}


    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/addUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisConfig> addUpdate(@RequestBody SysAnalysisConfig sysAnalysisConfig)
    {
        validate(MethodType.ADD, sysAnalysisConfig);

        SysAnalysisConfig searchCondition = new SysAnalysisConfig();
        searchCondition.setExamId(sysAnalysisConfig.getExamId());
        if (sysAnalysisConfigService.selectByKey(sysAnalysisConfig) != null) {
            sysAnalysisConfigService.updateNotNull(sysAnalysisConfig);
        } else {
            sysAnalysisConfigService.save(sysAnalysisConfig);
        }

        return new Response<>(sysAnalysisConfig);
    }
}