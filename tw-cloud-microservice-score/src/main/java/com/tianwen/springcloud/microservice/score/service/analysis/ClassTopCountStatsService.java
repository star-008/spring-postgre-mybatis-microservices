package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import com.tianwen.springcloud.microservice.score.entity.analysis.ClassTopCountStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ClassTopCountStatsService extends ScoreBaseService<ClassTopCountStats> {

    @Autowired
    private SysAnalysisConfigService sysAnalysisConfigService;

    public List<ClassTopCountStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        if (!StringUtils.isEmpty(request.getFilter().getExamId()) && CollectionUtils.isEmpty(request.getFilter().getTopLevelCountList())) {
            fillLevelCountList(request);
        }

        List<ClassTopCountStats> classTopCountStatsList = new ArrayList<>();
        for (Object obj: super.getList(request)) {
            classTopCountStatsList.add(new ClassTopCountStats((Map<String, String>)obj, request.getFilter().getTopLevelCountList(), request.getFilter().getLastLevelCountList()));
        }

        return classTopCountStatsList;
        //return super.getList(request).stream().map(obj -> new ClassTopCountStats((Map<String, String>)obj, topLevelCountList, lastLevelCountList)).collect(Collectors.toList());
    }

    public void fillLevelCountList(Request request) {
        List<String> topLevelCountList = new ArrayList<>();
        List<String> lastLevelCountList = new ArrayList<>();

        SysAnalysisConfig sysAnalysisConfig = sysAnalysisConfigService.selectByKey(request.getFilter().getExamId());
        if (sysAnalysisConfig != null) {
            if (!StringUtils.isEmpty(sysAnalysisConfig.getGradeTopCount())) {
                topLevelCountList.addAll(Arrays.asList(sysAnalysisConfig.getGradeTopCount().split(",")));
            }
            if (!StringUtils.isEmpty(sysAnalysisConfig.getGradeLastCount())) {
                lastLevelCountList.addAll(Arrays.asList(sysAnalysisConfig.getGradeLastCount().split(",")));
            }
        }

        request.getFilter().setTopLevelCountList(topLevelCountList);
        request.getFilter().setLastLevelCountList(lastLevelCountList);
    }
}
