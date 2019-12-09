package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.SysAnalysisScoreLevelMapper;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAnalysisScoreLevelService extends ScoreBaseService<SysAnalysisScoreLevel>
{
    @Autowired
    SysAnalysisScoreLevelMapper sysAnalysisScoreLevelMapper;

    public int batchInsert(List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList, String examId, String gradeId, String subjectType, String subjectId, String volumeId)
    {
        deleteBy(examId, gradeId, subjectType, subjectId, volumeId);

        return super.batchInsert(sysAnalysisScoreLevelList);
    }

    protected int deleteBy(String examId, String gradeId, String subjectType, String subjectId, String volumeId) {
        SysAnalysisScoreLevel deleteCondition = new SysAnalysisScoreLevel();
        deleteCondition.setExamId(examId);
        deleteCondition.setGradeId(gradeId);
        deleteCondition.setSubjectType(subjectType);
        deleteCondition.setSubjectId(subjectId);
        deleteCondition.setVolumeId(volumeId);
        return sysAnalysisScoreLevelMapper.delete(deleteCondition);
    }

    public List<SysAnalysisScoreLevel> getListByExam(Request request) {

        return sysAnalysisScoreLevelMapper.getListByExam(request);
    }

    public List<SysAnalysisScoreLevel> getTypeListByExam(Request request) {

        return sysAnalysisScoreLevelMapper.getTypeListByExam(request);
    }

    public Integer getTypeCountByExam(Request request) {

        return sysAnalysisScoreLevelMapper.getTypeCountByExam(request);
    }
}
