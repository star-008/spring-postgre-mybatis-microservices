package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.SysAnalysisConfigMapper;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAnalysisConfigService extends ScoreBaseService<SysAnalysisConfig>
{
    @Autowired
    SysAnalysisConfigMapper sysAnalysisConfigMapper;

    @Override
    public int save(SysAnalysisConfig sysAnalysisConfig) {
        sysAnalysisConfigMapper.deleteByPrimaryKey(sysAnalysisConfig.getExamId());
        return super.save(sysAnalysisConfig);
    }

    @Override
    public int batchInsert(List<SysAnalysisConfig> sysAnalysisConfigList)
    {
        deleteByExamId(sysAnalysisConfigList.get(0).getExamId());

        return super.batchInsert(sysAnalysisConfigList);
    }

    protected int deleteByExamId(String examId) {
        SysAnalysisConfig deleteCondition = new SysAnalysisConfig();
        deleteCondition.setExamId(examId);
        return sysAnalysisConfigMapper.delete(deleteCondition);
    }
}
