package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.ExamClassMapper;
import com.tianwen.springcloud.microservice.score.dao.ExamGradeClassSubjectMapper;
import com.tianwen.springcloud.microservice.score.entity.ExamClass;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamClassService extends ScoreBaseService<ExamClass>
{
    @Autowired
    ExamClassMapper examClassMapper;

    @Autowired
    ExamGradeClassSubjectMapper examGradeClassSubjectMapper;

    @Override
    public int batchInsert(List<ExamClass> examClassList)
    {
        //deleteByExamId(examClassList.get(0).getExamId());

        return super.batchInsert(examClassList);
    }

//    protected int deleteByExamId(String examId) {
//        ExamClass deleteCondition = new ExamClass();
//        deleteCondition.setExamId(examId);
//        return examClassMapper.delete(deleteCondition);
//    }

    public List<String> getClassIdList(Filter filter) {
        return examGradeClassSubjectMapper.getClassIdList(filter.toQueryTreeMap());
    }
}
