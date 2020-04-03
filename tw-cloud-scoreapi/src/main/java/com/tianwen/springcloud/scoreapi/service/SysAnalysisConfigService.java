package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.api.SysAnalysisConfigMicroApi;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysAnalysisConfigService extends BaseService {

    @Autowired
    private SysAnalysisConfigMicroApi sysAnalysisConfigMicroApi;

    public Response<SysAnalysisConfig> addUpdate(SysAnalysisConfig sysAnalysisConfig) {
        return sysAnalysisConfigMicroApi.addUpdate(sysAnalysisConfig);
    }

    public Response<SysAnalysisConfig> batchAdd(List<SysAnalysisConfig> sysAnalysisConfigList) {
        return sysAnalysisConfigMicroApi.batchAdd(sysAnalysisConfigList);
    }

    public Response<SysAnalysisConfig> delete(SysAnalysisConfig sysAnalysisConfig) {
        return sysAnalysisConfigMicroApi.deleteByEntity(sysAnalysisConfig);
    }

    public Response<SysAnalysisConfig> search(QueryTree queryTree) {
        return sysAnalysisConfigMicroApi.search(queryTree);
    }

    public boolean hasSysAnalysisConfig(String examId) {
        SysAnalysisConfig sysAnalysisConfig = sysAnalysisConfigMicroApi.get(examId).getResponseEntity();
        return  sysAnalysisConfig != null
                && !StringUtils.isEmpty(sysAnalysisConfig.getGradeTopCount())
                && !StringUtils.isEmpty(sysAnalysisConfig.getGradeLastCount())
                && !StringUtils.isEmpty(sysAnalysisConfig.getDegreeType());
    }

    public void fillDefaultSysAnalysisConfig(String examId) {

        SysAnalysisConfig sysAnalysisConfig = sysAnalysisConfigMicroApi.get(examId).getResponseEntity();
        if (sysAnalysisConfig != null) {
            if (StringUtils.isEmpty(sysAnalysisConfig.getGradeTopCount())) {
                sysAnalysisConfig.setGradeTopCount("10,20,50,80,100,150,200,250,300,350,400,450,500");
            }
            if (StringUtils.isEmpty(sysAnalysisConfig.getGradeLastCount())) {
                sysAnalysisConfig.setGradeLastCount("10,20,50,80,100");
            }
            if (StringUtils.isEmpty(sysAnalysisConfig.getDegreeType())) {
                sysAnalysisConfig.setDegreeType("4");
            }

            sysAnalysisConfigMicroApi.update(sysAnalysisConfig);

        } else {
            sysAnalysisConfig = new SysAnalysisConfig();

            sysAnalysisConfig.setExamId(examId);
            sysAnalysisConfig.setScoreDivisionEnabled("1");
            sysAnalysisConfig.setScoreDivisionBySubject("5");
            sysAnalysisConfig.setScoreDivisionByTotal("20");
            sysAnalysisConfig.setGradeTopCount("10,20,50,80,100,150,200,250,300,350,400,450,500");
            sysAnalysisConfig.setGradeLastCount("10,20,50,80,100");
            sysAnalysisConfig.setParellelSumEnabled("0");
            sysAnalysisConfig.setMobileViewMode("1");
            sysAnalysisConfig.setDegreeType("4");
            sysAnalysisConfig.setMissedExamIncluded("0");

            sysAnalysisConfigMicroApi.add(sysAnalysisConfig);
        }
    }
}
