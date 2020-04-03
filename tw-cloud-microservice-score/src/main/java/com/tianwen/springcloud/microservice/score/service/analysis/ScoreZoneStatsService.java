package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.analysis.ScoreZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.ScoreZoneStats;
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

@Service
public class ScoreZoneStatsService extends ScoreBaseService<ScoreZoneStats> {
    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    private static final String CLASS_TOTAL = "TOTAL";

    public List<ScoreZoneStats> getList(QueryTree queryTree) {
        Request request = (Request)queryTree;

        if (!StringUtils.isEmpty(request.getFilter().getSubjectId())) {
            return getListBySubject(request);
        } else if (!CollectionUtils.isEmpty(request.getFilter().getSubjectIdList())) {

            List<ScoreZoneStats> levelZoneStatsList = new ArrayList<>();
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

    public List<ScoreZoneStats> getListBySubject(Request request) {

        request.getFilter().addMore("type", SysAnalysisScoreLevel.TYPE_SCORE_ZONE);
        List<SysAnalysisScoreLevel> scoreZoneConfigList = sysAnalysisScoreLevelService.getListByExam(request);

        request.getFilter().setLevelZoneConfigList(scoreZoneConfigList);
        List<ScoreZoneStats> scoreZoneStatsList = new ArrayList<>();

        List<Map<String, Object>> studentInfoList = new ArrayList<>();
        for (Object obj: super.getList(request)) {
            if (obj == null) continue;
            Map<String, String> scoreZoneMap = (Map<String, String>)obj;
            ScoreZoneStats scoreZoneConfig = new ScoreZoneStats(scoreZoneMap, scoreZoneConfigList);
            scoreZoneStatsList.add(scoreZoneConfig);

            //student info get
            String classid = String.valueOf(scoreZoneMap.get("class_id"));
            if (StringUtils.equals(classid, "TOTAL")) continue;

            Map<String, Object> studentInfo = new HashMap<>();
            studentInfo.put("score", scoreZoneMap.get("score"));
            studentInfo.put("classId", scoreZoneMap.get("class_id"));
            studentInfo.put("studentId", scoreZoneMap.get("student_id"));
            studentInfo.put("studentName", scoreZoneMap.get("student_name"));
            studentInfo.put("rankInGrade", scoreZoneMap.get("rank_in_grade"));
            studentInfo.put("rankInClass", scoreZoneMap.get("rank_in_class"));
            studentInfoList.add(studentInfo);
        }

        filterByClass(scoreZoneStatsList, studentInfoList);

        return scoreZoneStatsList;
    }

    private void filterByClass(List<ScoreZoneStats> scoreZoneStatsList, List<Map<String, Object>> studentInfoList) {
        Map<String, ScoreZoneStats> filterMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(scoreZoneStatsList)) {
            List<ScoreZoneStats> removeList = new ArrayList<>();
            ScoreZoneStats totalStats = null;
            for (ScoreZoneStats scoreZoneStats : scoreZoneStatsList) {
                String classid = scoreZoneStats.getClassId();
                if (StringUtils.equals(CLASS_TOTAL, classid)) {
                    totalStats = scoreZoneStats;
                    removeList.add(totalStats);
                    continue;
                }
                ScoreZoneStats object = filterMap.get(classid);
                if (object != null) {
                    float avgScore = Float.parseFloat(object.getAvgScore());
                    avgScore += Float.parseFloat(scoreZoneStats.getAvgScore());
                    object.setAvgScore(String.valueOf(avgScore));
                    removeList.add(scoreZoneStats);
                    continue;
                }

                filterMap.put(classid, scoreZoneStats);
            }

            scoreZoneStatsList.removeAll(removeList);

            if (totalStats != null)
                addScoreZoneStudents(totalStats.getScoreZoneList(), studentInfoList);

            if (!CollectionUtils.isEmpty(scoreZoneStatsList)) {
                for (ScoreZoneStats scoreZoneStats : scoreZoneStatsList) {
                    float avgScore = Float.valueOf(scoreZoneStats.getAvgScore());
                    int count = Integer.valueOf(scoreZoneStats.getCount());
                    avgScore = Math.round(avgScore * 100 / count) / 100.0f;
                    if (count != 0) scoreZoneStats.setAvgScore(String.valueOf(avgScore));
                }
                scoreZoneStatsList.add(totalStats);
            }
        }
    }

    private void addScoreZoneStudents(List<ScoreZone> scoreZoneList, List<Map<String, Object>> maps) {

        if (!CollectionUtils.isEmpty(scoreZoneList)) {

            boolean isFirst = true;

            for (ScoreZone scoreZone : scoreZoneList) {
                Integer lowScore = Integer.parseInt(scoreZone.getLowScore());
                Integer highScore = Integer.parseInt(scoreZone.getHighScore());
                if (!CollectionUtils.isEmpty(maps)) {
                    List<Map<String, Object>> scoreZoneStuInfos = new ArrayList<>();
                    for (Map<String, Object> stuInfo : maps) {
                        Double score = Double.parseDouble(stuInfo.get("score").toString());
                        if (score >= lowScore && (highScore>score || (isFirst && highScore>=score)))
                            scoreZoneStuInfos.add(stuInfo);
                    }
                    scoreZone.setStudentInfo(scoreZoneStuInfos);
                }

                isFirst = false;
            }
        }
    }
}
