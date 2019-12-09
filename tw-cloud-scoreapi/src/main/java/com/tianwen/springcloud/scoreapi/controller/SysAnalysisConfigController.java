package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.scoreapi.service.SysAnalysisConfigService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import com.tianwen.springcloud.scoreapi.api.SysAnalysisConfigApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sys/analysis-config")
public class SysAnalysisConfigController extends AbstractController implements SysAnalysisConfigApi {

    @Autowired
    private SysAnalysisConfigService sysAnalysisConfigService;

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/addUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisConfig> addUpdate(@RequestBody SysAnalysisConfig sysAnalysisConfig) {
        return sysAnalysisConfigService.addUpdate(sysAnalysisConfig);
    }

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisConfig> batchAdd(@RequestBody List<SysAnalysisConfig> sysAnalysisConfigList) {
        return sysAnalysisConfigService.batchAdd(sysAnalysisConfigList);
    }

    @Override
    @ApiOperation(value = "更新", notes = "根据实体中ID单个更新")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据实体中ID单个更新")
    public Response<SysAnalysisConfig> delete(@RequestBody SysAnalysisConfig sysAnalysisConfig) {
        return sysAnalysisConfigService.delete(sysAnalysisConfig);
    }

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisConfig> search(@RequestBody QueryTree queryTree) {
        return sysAnalysisConfigService.search(queryTree);
    }

}
