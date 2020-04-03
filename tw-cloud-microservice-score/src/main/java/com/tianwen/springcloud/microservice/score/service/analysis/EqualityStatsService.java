package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.*;
import com.tianwen.springcloud.microservice.score.entity.analysis.EqualityStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisScoreLevelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EqualityStatsService extends ScoreBaseService<EqualityStats> {

    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    public List<EqualityStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        if (!StringUtils.isEmpty(request.getFilter().getExamId())) {
            return getListByExam(request);

        } else if (!CollectionUtils.isEmpty(request.getFilter().getExamIdList())) {

            List<EqualityStats> equalityStatsList = new ArrayList<>();
            List<String> examIdList = request.getFilter().getExamIdList();

            request.getFilter().setExamIdList(null);

            for (String examId: examIdList) {
                request.getFilter().setExamId(examId);

                equalityStatsList.addAll(getListByExam(request.copy()));
            }

            return equalityStatsList;
        }

        return null;
    }

    public List<EqualityStats> getListByExam(Request request) {

        if (!StringUtils.isEmpty(request.getFilter().getSubjectId())) {
            return getListBySubject(request, getScoreZoneList(request));

        } else if (!CollectionUtils.isEmpty(request.getFilter().getSubjectIdList())) {

            List<EqualityStats> equalityStatsList = new ArrayList<>();
            List<String> subjectIdList = request.getFilter().getSubjectIdList();

            List<ScoreZone> scoreZoneList = getScoreZoneList(request);

            request.getFilter().setSubjectIdList(null);

            for (String subjectId: subjectIdList) {
                request.getFilter().setSubjectId(subjectId);

                equalityStatsList.addAll(getListBySubject(request, scoreZoneList));
            }

            return equalityStatsList;
        }

        return null;
    }

    public List<EqualityStats> getListBySubject(Request request, List<ScoreZone> scoreZoneList) {

        List<PassZone> passZoneList = getPassZoneList(request.getFilter().getExamId(), request.getFilter().getGradeId(), request.getFilter().getSubjectId());

        List<Zone> zoneList = new ArrayList<>();
        zoneList.addAll(passZoneList);
        //zoneList.addAll(scoreZoneList);
        request.getFilter().setZoneList(zoneList);

        List<EqualityStats> equalityStatsList = new ArrayList<>();
        for (Object obj: super.getList(request)) {
            equalityStatsList.add(new EqualityStats(request.getFilter().getSubjectId(), (Map<String, String>)obj, passZoneList, scoreZoneList));
        }

        return equalityStatsList;
    }

    public List<ScoreZone> getScoreZoneList(Request request) {
        List<ScoreZone> scoreZoneList = new ArrayList<>();
        if (true) { //We don't use score zone anymore in this module.
            return scoreZoneList;
        }

        request.getFilter().addMore("type", SysAnalysisScoreLevel.TYPE_SCORE_ZONE);
        scoreZoneList.addAll(sysAnalysisScoreLevelService.getListByExam(request).stream().map(ScoreZone::new).collect(Collectors.toList()));

        if (!CollectionUtils.isEmpty(scoreZoneList)) {
            scoreZoneList.add(0, new ScoreZone("0", scoreZoneList.get(0).getHighScore(), null, null, null));
        }
        int level = 1;
        for (ScoreZone scoreZone: scoreZoneList) {
            scoreZone.setLevel("" + level ++);
        }

        return scoreZoneList;
    }

    public List<PassZone> getPassZoneList(String examId, String gradeId, String subjectId) {
        List<PassZone> passZoneList = new ArrayList<>();
        
        SysAnalysisScoreLevel condition = new SysAnalysisScoreLevel(examId, gradeId, subjectId, "0", SysAnalysisScoreLevel.TYPE_PASS_ZONE);
        Map<String, SysAnalysisScoreLevel> sysAnalysisScoreLevelMap = new HashMap<>();
        for (SysAnalysisScoreLevel sysAnalysisScoreLevel: sysAnalysisScoreLevelService.select(condition, 0, 0)) {
            sysAnalysisScoreLevelMap.put(sysAnalysisScoreLevel.getLevel(), sysAnalysisScoreLevel);
        }
        
        SysAnalysisScoreLevel sysAnalysisScoreLevel;

        if ((sysAnalysisScoreLevel = sysAnalysisScoreLevelMap.get(PassZone.LEVEL_PASS)) != null) {
            passZoneList.add(new PassZone(PassZone.TYPE_TOP, PassZone.LEVEL_PASS, sysAnalysisScoreLevel.getLowScore(), null, null));
        }

        if ((sysAnalysisScoreLevel = sysAnalysisScoreLevelMap.get(PassZone.LEVEL_BETTER)) != null) {
            passZoneList.add(new PassZone(PassZone.TYPE_TOP, PassZone.LEVEL_BETTER, sysAnalysisScoreLevel.getLowScore(), null, null));
        }

        if ((sysAnalysisScoreLevel = sysAnalysisScoreLevelMap.get(PassZone.LEVEL_BAD)) != null) {
            passZoneList.add(new PassZone(PassZone.TYPE_LAST, PassZone.LEVEL_BAD, sysAnalysisScoreLevel.getLowScore(), null, null));
        }

        return passZoneList;
    }
}
