package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.LevelZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.LevelZoneStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisScoreLevelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LevelZoneStatsService extends ScoreBaseService<LevelZoneStats> {

    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    public List<LevelZoneStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        if (!StringUtils.isEmpty(request.getFilter().getSubjectId())) {
            return getListBySubject(request);

        } else if (!CollectionUtils.isEmpty(request.getFilter().getSubjectIdList())) {

            List<LevelZoneStats> levelZoneStatsList = new ArrayList<>();
            List<String> subjectIdList = request.getFilter().getSubjectIdList();

            request.getFilter().setSubjectIdList(null);

            for (String subjectId: subjectIdList) {
                request.getFilter().setSubjectId(subjectId);

                levelZoneStatsList.addAll(getListBySubject(request));
            }

            return levelZoneStatsList;
        }

        return null;
    }

    public List<LevelZoneStats> getListBySubject(Request request) {

        List<LevelZone> levelZoneList = getLevelZoneList(request);

        request.getFilter().setZoneList(levelZoneList);

        List<LevelZoneStats> levelZoneStatsList = new ArrayList<>();
        for (Object obj: super.getList(request)) {
            levelZoneStatsList.add(new LevelZoneStats(request.getFilter().getSubjectId(), (Map<String, String>)obj, levelZoneList));
        }

        return levelZoneStatsList;
        //return super.getList(request).stream().map(obj -> new LevelZoneStats((Map<String, String>)obj, topLevelCountList, lastLevelCountList)).collect(Collectors.toList());
    }
    
    public List<LevelZone> getLevelZoneList(Request request) {
        List<LevelZone> levelZoneList = new ArrayList<>();

        request.getFilter().addMore("type", SysAnalysisScoreLevel.TYPE_LEVEL_ZONE);
        levelZoneList.addAll(sysAnalysisScoreLevelService.getListByExam(request).stream().map(LevelZone::new).collect(Collectors.toList()));

        return levelZoneList;

    }
}
