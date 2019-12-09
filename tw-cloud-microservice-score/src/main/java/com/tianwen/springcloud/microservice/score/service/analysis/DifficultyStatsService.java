package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.*;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisScoreLevelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DifficultyStatsService extends ScoreBaseService<DifficultyStats> {

    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    private static final int AVERAGE_COUNT_PERCENT = 27;

    public List<DifficultyStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        if (!StringUtils.isEmpty(request.getFilter().getSubjectId())) {
            return getListBySubject(request);

        } else if (!CollectionUtils.isEmpty(request.getFilter().getSubjectIdList())) {

            List<DifficultyStats> difficultyStatsList = new ArrayList<>();
            List<String> subjectIdList = request.getFilter().getSubjectIdList();

            request.getFilter().setSubjectIdList(null);

            for (String subjectId: subjectIdList) {
                request.getFilter().setSubjectId(subjectId);

                difficultyStatsList.addAll(getListBySubject(request));
            }

            return difficultyStatsList;
        }

        return null;
    }

    public List<DifficultyStats> getListBySubject(Request request) {

        List<PassZone> passZoneList = getPassZoneList(request.getFilter().getExamId(), request.getFilter().getGradeId(), request.getFilter().getSubjectId());

        List<Zone> zoneList = new ArrayList<>();
        zoneList.addAll(passZoneList);
        request.getFilter().setZoneList(zoneList);
        request.getFilter().getMore().put("avgCountPercent", AVERAGE_COUNT_PERCENT);

        List<DifficultyStats> difficultyStatsList = new ArrayList<>();
        for (Object obj: super.getList(request)) {
            difficultyStatsList.add(new DifficultyStats(request.getFilter().getSubjectId(), (Map<String, String>)obj, passZoneList));
        }

        return difficultyStatsList;
    }


    public List<PassZone> getPassZoneList(String examId, String gradeId, String subjectId) {
        List<PassZone> passZoneList = new ArrayList<>();
        
        SysAnalysisScoreLevel condition = new SysAnalysisScoreLevel(examId, gradeId, subjectId, "0", SysAnalysisScoreLevel.TYPE_PASS_ZONE);
        Map<String, SysAnalysisScoreLevel> sysAnalysisScoreLevelMap = new HashMap<>();
        for (SysAnalysisScoreLevel sysAnalysisScoreLevel: sysAnalysisScoreLevelService.select(condition, 0, 0)) {
            sysAnalysisScoreLevelMap.put(sysAnalysisScoreLevel.getLevel(), sysAnalysisScoreLevel);
        }
        
        List<String> levelList = Arrays.asList(
                PassZone.LEVEL_BEST,
                PassZone.LEVEL_BETTER,
                PassZone.LEVEL_PASS
        );

        SysAnalysisScoreLevel sysAnalysisScoreLevel;
        String level;

        for (int i = 0; i < levelList.size(); i++) {
            level = levelList.get(i);
            if ((sysAnalysisScoreLevel = sysAnalysisScoreLevelMap.get(level)) != null) {
                passZoneList.add(new PassZone(PassZone.TYPE_TOP, level, sysAnalysisScoreLevel.getLowScore(), null, null));
            }
        }

        if ((sysAnalysisScoreLevel = sysAnalysisScoreLevelMap.get(PassZone.LEVEL_BAD)) != null) {
            passZoneList.add(new PassZone(PassZone.TYPE_LAST, PassZone.LEVEL_BAD, sysAnalysisScoreLevel.getLowScore(), null, null));
        }

        return passZoneList;
    }
}
