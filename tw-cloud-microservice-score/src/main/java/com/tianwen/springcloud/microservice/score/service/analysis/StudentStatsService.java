package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisConfig;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.DegreeZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.StudentStats;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisConfigService;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisScoreLevelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentStatsService extends ScoreBaseService<StudentStats> {

    @Autowired
    private SysAnalysisConfigService sysAnalysisConfigService;

    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    public List<StudentStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        String type = getDegreeType(request.getFilter().getExamId());
        String missedExamIncluded = isMissedExamIncluded(request.getFilter().getExamId());
        request.getFilter().addMore("type", type);
        request.getFilter().addMore("inclusion", missedExamIncluded);

        request.getFilter().setZoneList(getDegreeZoneList(request.getFilter().getExamId(), type));

        return super.getList(request);
    }

    public String getDegreeType(String examId) {

        String type = SysAnalysisScoreLevel.TYPE_DEGREE_ZONE_BY_SCORE;

        SysAnalysisConfig sysAnalysisConfig = sysAnalysisConfigService.selectByKey(examId);
        if (sysAnalysisConfig != null && !StringUtils.isEmpty(sysAnalysisConfig.getDegreeType())) {
            type = sysAnalysisConfig.getDegreeType();
        }

        if (!type.equals(SysAnalysisScoreLevel.TYPE_DEGREE_ZONE_BY_COUNT) && !type.equals(SysAnalysisScoreLevel.TYPE_DEGREE_ZONE_BY_SCORE)) {
            type = SysAnalysisScoreLevel.TYPE_DEGREE_ZONE_BY_SCORE;
        }

        return type;
    }

    public String isMissedExamIncluded(String examId) {

        String inclusion = SysAnalysisConfig.IS_MISSEDEXAM_EXCLUDED;

        SysAnalysisConfig sysAnalysisConfig = sysAnalysisConfigService.selectByKey(examId);
        if (sysAnalysisConfig != null && !StringUtils.isEmpty(sysAnalysisConfig.getMissedExamIncluded())) {
            inclusion = sysAnalysisConfig.getMissedExamIncluded();
        }

        if (!inclusion.equals(SysAnalysisConfig.IS_MISSEDEXAM_INCLUDED) && !inclusion.equals(SysAnalysisConfig.IS_MISSEDEXAM_EXCLUDED)) {
            inclusion = sysAnalysisConfig.IS_MISSEDEXAM_EXCLUDED;
        }

        return inclusion;
    }

    public List<DegreeZone> getDegreeZoneList(String examId, String type) {
        List<DegreeZone> degreeZoneList = new ArrayList<>();

        if (StringUtils.isEmpty(examId)) {
            return degreeZoneList;
        }

        Request request = new Request(new Filter(examId));
        request.getFilter().getMore().put("type", type);
        degreeZoneList.addAll(sysAnalysisScoreLevelService.getListByExam(request).stream().map(DegreeZone::new).collect(Collectors.toList()));

        return degreeZoneList;
    }
}
