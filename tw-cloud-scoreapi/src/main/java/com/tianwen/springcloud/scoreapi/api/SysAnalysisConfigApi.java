package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface SysAnalysisConfigApi {

    @RequestMapping(value = "/addUpdate", method = RequestMethod.POST)
    Response addUpdate(@RequestBody SysAnalysisConfig sysAnalysisConfig);

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    Response batchAdd(@RequestBody List<SysAnalysisConfig> sysAnalysisConfigList);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    Response delete(@RequestBody SysAnalysisConfig sysAnalysisConfig);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response search(@RequestBody QueryTree queryTree);

}
