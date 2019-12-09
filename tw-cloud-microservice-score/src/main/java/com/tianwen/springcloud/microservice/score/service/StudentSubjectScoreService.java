package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.StudentPartScoreMapper;
import com.tianwen.springcloud.microservice.score.dao.StudentSubjectModifyScoreMapper;
import com.tianwen.springcloud.microservice.score.dao.StudentSubjectScoreMapper;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
public class StudentSubjectScoreService extends ScoreBaseService<StudentSubjectScore>
{
    @Autowired
    StudentSubjectScoreMapper studentSubjectScoreMapper;

    @Autowired
    StudentPartScoreMapper studentPartScoreMapper;

    @Autowired
    StudentSubjectModifyScoreMapper studentSubjectModifyScoreMapper;

    @Override
    public int batchInsert(List<StudentSubjectScore> studentSubjectScoreList)
    {
        //deleteByExamId(studentSubjectScoreList.get(0).getExamId());

        for(StudentSubjectScore studentSubjectScore : studentSubjectScoreList) {
            studentSubjectScore.setSubmitTime(new Timestamp((new Date()).getTime()));
        }
        return super.batchInsert(studentSubjectScoreList);
    }

//    protected int deleteByExamId(String examId) {
//        StudentPartScore studentPartScoreDeleteCondition = new StudentPartScore();
//        studentPartScoreDeleteCondition.setExamId(examId);
//        studentPartScoreMapper.delete(studentPartScoreDeleteCondition);
//
//        StudentSubjectModifyScore studentSubjectModifyScoreDeleteCondition = new StudentSubjectModifyScore();
//        studentSubjectModifyScoreDeleteCondition.setExamId(examId);
//        studentSubjectModifyScoreMapper.delete(studentSubjectModifyScoreDeleteCondition);
//
//        StudentSubjectScore deleteCondition = new StudentSubjectScore();
//        deleteCondition.setExamId(examId);
//        return studentSubjectScoreMapper.delete(deleteCondition);
//    }
//
    public List<StudentSubjectScore> getScoreEnteredList(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getScoreEnteredList(filter.toQueryTreeMap());
    }

    public List<StudentSubjectScore> getScoreUnenteredList(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getScoreUnenteredList(filter.toQueryTreeMap());
    }

    public List<StudentSubjectScore> getExamMissedList(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getExamMissedList(filter.toQueryTreeMap());
    }

    public List<StudentSubjectScore> getScoreInvalidList(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getScoreInvalidList(filter.toQueryTreeMap());
    }

    public Integer getScoreEnteredCount(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getScoreEnteredCount(filter.toQueryTreeMap());
    }

    public Integer getScoreUnenteredCount(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getScoreUnenteredCount(filter.toQueryTreeMap());
    }

    public Integer getExamMissedCount(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getExamMissedCount(filter.toQueryTreeMap());
    }

    public Integer getScoreInvalidCount(StudentSubjectFilter filter) {
        return studentSubjectScoreMapper.getScoreInvalidCount(filter.toQueryTreeMap());
    }
}
