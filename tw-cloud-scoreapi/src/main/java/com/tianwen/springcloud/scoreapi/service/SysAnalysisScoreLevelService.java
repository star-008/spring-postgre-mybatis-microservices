package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.SysAnalysisScoreLevelMicroApi;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.DegreeZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.LevelZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.PassZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.ScoreZone;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.service.util.repo.LocalNameRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysAnalysisScoreLevelService extends BaseService {

    @Autowired
    private SysAnalysisScoreLevelMicroApi sysAnalysisScoreLevelMicroApi;

    public Response<SysAnalysisScoreLevel> add(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        return sysAnalysisScoreLevelMicroApi.add(sysAnalysisScoreLevel);
    }

    public Response<SysAnalysisScoreLevel> batchAdd(List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList, String examId, String gradeId, String subjectType, String subjectId, String volumeId) {
        return sysAnalysisScoreLevelMicroApi.batchAdd(sysAnalysisScoreLevelList, examId, gradeId, subjectType, subjectId, volumeId);
    }

    public Response<SysAnalysisScoreLevel> delete(SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        return sysAnalysisScoreLevelMicroApi.deleteByEntity(sysAnalysisScoreLevel);
    }

    public Response<SysAnalysisScoreLevel> search(QueryTree querytree) {
        return sysAnalysisScoreLevelMicroApi.search(querytree);
    }

    public boolean hasSysAnalysisScoreLevels(String examId, Integer examSubjectCount) {
        Request request = new Request();
        request.getFilter().setExamId(examId);
        Integer count = sysAnalysisScoreLevelMicroApi.getTypeCountByExam(request).getResponseEntity();
        return examSubjectCount * 3 + 1 <= (count != null ? count : 0);
    }

    public void fillDefaultSysAnalysisScoreLevels(String examId, List<ExamSubjectScore> examSubjectScoreAllList) {

        List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList = new ArrayList<>();

        Map<String, List<ExamSubjectScore>> examSubjectScoreMap = new HashMap<>();

        List<SysAnalysisScoreLevel> typeEntityList = sysAnalysisScoreLevelMicroApi.getTypeListByExam(new Request(new Filter(examId))).getPageInfo().getList();
        List<String> typeList = typeEntityList.stream().map(
                sysAnalysisScoreLevel ->
                        sysAnalysisScoreLevel.getExamId() + "-" + sysAnalysisScoreLevel.getGradeId() + "-" + sysAnalysisScoreLevel.getSubjectId() + "-" + sysAnalysisScoreLevel.getVolumeId() + "-" + sysAnalysisScoreLevel.getType()
        ).collect(Collectors.toList());

        for (ExamSubjectScore examSubjectScore: examSubjectScoreAllList) {
            List<ExamSubjectScore> subList = examSubjectScoreMap.get(examSubjectScore.getGradeId());
            if (subList == null) {
                subList = new ArrayList<>();
                examSubjectScoreMap.put(examSubjectScore.getGradeId(), subList);
            }
            subList.add(examSubjectScore);
        }

        LocalNameRepositoryService nameRepositoryService = new LocalNameRepositoryService(examId);

        for (Map.Entry<String, List<ExamSubjectScore>> entry: examSubjectScoreMap.entrySet()) {
            String gradeId = entry.getKey();
            List<ExamSubjectScore> examSubjectScoreList = entry.getValue();

            float fullScore, totalFullScore = 0;
            for (ExamSubjectScore examSubjectScore : examSubjectScoreList) {
                String subjectId = examSubjectScore.getSubjectId();
                fullScore = NumberUtils.toFloat(examSubjectScore.getScore());
                totalFullScore += fullScore;

                sysAnalysisScoreLevelList.addAll(createDefaultZoneListForASubject(typeList, examId, gradeId, subjectId, "0", fullScore));

                if (!StringUtils.isEmpty(examSubjectScore.getVolumes())) {

                    List<DictItem> volumeList = nameRepositoryService.key2entity(Arrays.asList(examSubjectScore.getVolumes().split("\\s*,\\s*")), RepositoryService.EntityType.PAPER_VOLUME);
                    List<String> volumeIdList = volumeList.stream().map(DictItem::getDictvalue).collect(Collectors.toList());

                    if (!CollectionUtils.isEmpty(volumeIdList)) {
                        for (String volumeId: volumeIdList) {
                            sysAnalysisScoreLevelList.addAll(createDefaultZoneListForASubject(typeList, examId, gradeId, subjectId, volumeId, fullScore));
                        }
                    }
                }
            }

            sysAnalysisScoreLevelList.addAll(createDefaultZoneListForASubject(typeList, examId, gradeId, "TOTAL", "0", totalFullScore));
        }

        if (typeList.indexOf(examId + "-0-0-0-" + SysAnalysisScoreLevel.TYPE_DEGREE_ZONE_BY_SCORE) < 0) {
            sysAnalysisScoreLevelList.addAll(createDefaultDegreeZoneList(examId));
        }

        sysAnalysisScoreLevelMicroApi.batchAdd(sysAnalysisScoreLevelList);
    }

    private List<SysAnalysisScoreLevel> createDefaultZoneListForASubject(List<String> typeList, String examId, String gradeId, String subjectId, String volumeId, float fullScore) {
        List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList = new ArrayList<>();

        if (typeList.indexOf(examId + "-" + gradeId + "-" + subjectId + "-" + volumeId + "-" + SysAnalysisScoreLevel.TYPE_PASS_ZONE) < 0) {
            sysAnalysisScoreLevelList.addAll(createDefaultPassZoneList(examId, gradeId, subjectId, volumeId, fullScore));
        }
        if (typeList.indexOf(examId + "-" + gradeId + "-" + subjectId + "-" + volumeId + "-" + SysAnalysisScoreLevel.TYPE_SCORE_ZONE) < 0) {
            sysAnalysisScoreLevelList.addAll(createDefaultScoreZoneList(examId, gradeId, subjectId, volumeId, fullScore));
        }
        if (typeList.indexOf(examId + "-" + gradeId + "-" + subjectId + "-" + volumeId + "-" + SysAnalysisScoreLevel.TYPE_LEVEL_ZONE) < 0) {
            sysAnalysisScoreLevelList.addAll(createDefaultLevelZoneList(examId, gradeId, subjectId, volumeId));
        }

        return sysAnalysisScoreLevelList;
    }

    private List<SysAnalysisScoreLevel> createDefaultPassZoneList(String examId, String gradeId, String subjectId, String volumeId, float fullScore) {
        List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList = new ArrayList<>();

        for (int i = 0; i < PassZone.getDefaultPassValues().length; i++) {
            Integer value = PassZone.getDefaultPassValues()[i];
            if (value == null) {
                continue;
            }
            sysAnalysisScoreLevelList.add(new SysAnalysisScoreLevel(
                    examId,
                    gradeId,
                    subjectId,
                    volumeId,
                    SysAnalysisScoreLevel.TYPE_PASS_ZONE,
                    String.valueOf(i + 1), String.valueOf((int)((float)value / (float)100 * fullScore)), null
            ));
        }

        return sysAnalysisScoreLevelList;
    }

    private List<SysAnalysisScoreLevel> createDefaultScoreZoneList(String examId, String gradeId, String subjectId, String volumeId, float fullScore) {
        List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList = new ArrayList<>();

        int span = subjectId.equals("TOTAL") ? ScoreZone.DEFAULT_SPAN_FOR_SUBJECT_TOTAL : ScoreZone.DEFAULT_SPAN_FOR_SUBJECT;
        int count = (int)Math.ceil(fullScore / (float)span);

        int level = 0, lowScore, highScore;
        for (int i = count - 1; i > 0; i--) {
            level ++;
            lowScore = i * span;
            highScore = (i + 1) * span;
            if (highScore > fullScore) {
                highScore = (int)fullScore;
            }
            sysAnalysisScoreLevelList.add(new SysAnalysisScoreLevel(
                    examId,
                    gradeId,
                    subjectId,
                    volumeId,
                    SysAnalysisScoreLevel.TYPE_SCORE_ZONE,
                    String.valueOf(level), String.valueOf(lowScore), String.valueOf(highScore),
                    ICommonConstants.FULL_SCORE + String.valueOf((int)fullScore) + ICommonConstants.SCORE + "，" + ICommonConstants.SPAN + span + ICommonConstants.SCORE,
                    String.valueOf(lowScore) + " - " + String.valueOf(highScore)
            ));
        }

        return sysAnalysisScoreLevelList;
    }

    private List<SysAnalysisScoreLevel> createDefaultLevelZoneList(String examId, String gradeId, String subjectId, String volumeId) {
        List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList = new ArrayList<>();

        for (int i = 0; i < LevelZone.getDefaultLevelZones().length; i++) {
            LevelZone zone = LevelZone.getDefaultLevelZones()[i];
            sysAnalysisScoreLevelList.add(new SysAnalysisScoreLevel(
                    examId,
                    gradeId,
                    subjectId,
                    volumeId,
                    SysAnalysisScoreLevel.TYPE_LEVEL_ZONE,
                    String.valueOf(i + 1), zone.getLowScore(), zone.getHighScore()
            ));
        }

        return sysAnalysisScoreLevelList;
    }

    private List<SysAnalysisScoreLevel> createDefaultDegreeZoneList(String examId) {
        List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList = new ArrayList<>();

        for (int i = 0; i < DegreeZone.getDefaultDegreeValues().length; i++) {
            sysAnalysisScoreLevelList.add(new SysAnalysisScoreLevel(
                    examId,
                    "0",
                    "0",
                    "0",
                    SysAnalysisScoreLevel.TYPE_DEGREE_ZONE_BY_SCORE,
                    String.valueOf(i + 1), DegreeZone.getDefaultDegreeValues()[i], null
            ));
        }

        return sysAnalysisScoreLevelList;
    }
}
