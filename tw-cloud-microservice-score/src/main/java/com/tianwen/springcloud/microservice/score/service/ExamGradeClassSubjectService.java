package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.ExamGradeClassSubjectMapper;
import com.tianwen.springcloud.microservice.score.entity.ExamGradeClassSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamGradeClassSubjectService extends ScoreBaseService<ExamGradeClassSubject>
{
    @Autowired
    ExamGradeClassSubjectMapper examGradeClassSubjectMapper;

    @Override
    public int batchInsert(List<ExamGradeClassSubject> examGradeClassSubjectList)
    {
        //deleteByExamId(examGradeClassSubjectList.get(0).getExamId());

        return super.batchInsert(examGradeClassSubjectList);
    }

//    protected int deleteByExamId(String examId) {
//
//        ExamGradeClassSubject deleteCondition = new ExamGradeClassSubject();
//        deleteCondition.setExamId(examId);
//        return examGradeClassSubjectMapper.delete(deleteCondition);
//    }
}
