package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.RankZoneStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RankZoneStatsService extends ScoreBaseService<RankZoneStats> {


    @Autowired
    private SysAnalysisConfigService sysAnalysisConfigService;

    public List<RankZoneStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        List<RankZone> rankZoneList = new ArrayList<>();
        SysAnalysisConfig sysAnalysisConfig = sysAnalysisConfigService.selectByKey(request.getFilter().getExamId());
        if (sysAnalysisConfig != null) {
            if (!StringUtils.isEmpty(sysAnalysisConfig.getGradeTopCount())) {
                rankZoneList.addAll(Arrays.asList(sysAnalysisConfig.getGradeTopCount().split(",")).stream().map(lowScore -> new RankZone(lowScore, lowScore, null, RankZone.TYPE_TOP)).collect(Collectors.toList()));
            }
        }

        request.getFilter().setZoneList(rankZoneList);

        List<RankZoneStats> rankZoneStatsList = new ArrayList<>();
        for (Object obj: super.getList(request)) {
            rankZoneStatsList.add(new RankZoneStats((Map<String, String>)obj, rankZoneList));
        }

        return rankZoneStatsList;
        //return super.getList(request).stream().map(obj -> new RankZoneStats((Map<String, String>)obj, topLevelCountList, lastLevelCountList)).collect(Collectors.toList());
    }
}
