package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.StudentPartScoreMapper;
import com.tianwen.springcloud.microservice.score.entity.StudentPartScore;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPartScoreService extends ScoreBaseService<StudentPartScore>
{
    @Autowired
    StudentPartScoreMapper studentPartScoreMapper;

    @Override
    public int batchInsert(List<StudentPartScore> studentPartScoreList)
    {
        StudentPartScore studentPartScore = studentPartScoreList.get(0);
        if (StringUtils.equals(studentPartScore.getClassType(), ClassInfo.CLASS_TYPE_NORMAL)) {
            deleteBy(studentPartScore.getExamId(), studentPartScore.getGradeId(), studentPartScore.getClassType());
        } else {
            deleteBy(studentPartScore.getExamId(), studentPartScore.getGradeId(), studentPartScore.getClassType(), studentPartScore.getSubjectId());
        }

        return super.batchInsert(studentPartScoreList);
    }

    protected int deleteBy(String examId, String gradeId) {
        return deleteBy(examId, gradeId, null);
    }
    protected int deleteBy(String examId, String gradeId, String classType) {
        return deleteBy(examId, gradeId, classType, null);
    }
    protected int deleteBy(String examId, String gradeId, String classType, String subjectId) {
        StudentPartScore deleteCondition = new StudentPartScore();
        deleteCondition.setExamId(examId);
        deleteCondition.setGradeId(gradeId);
        deleteCondition.setClassType(classType);
        deleteCondition.setSubjectId(subjectId);
        return studentPartScoreMapper.delete(deleteCondition);
    }
}