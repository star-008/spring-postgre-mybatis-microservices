package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.SysExamSubjectMapper;
import com.tianwen.springcloud.microservice.score.entity.SysExamSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysExamSubjectService extends ScoreBaseService<SysExamSubject>
{
    @Autowired
    SysExamSubjectMapper sysExamSubjectMapper;

    @Override
    public int batchInsert(List<SysExamSubject> sysExamSubjectList)
    {
        deleteByGradeId(sysExamSubjectList.get(0).getGradeId());

        return super.batchInsert(sysExamSubjectList);
    }

    protected int deleteByGradeId(String gradeId) {
        SysExamSubject deleteCondition = new SysExamSubject();
        deleteCondition.setGradeId(gradeId);
        return sysExamSubjectMapper.delete(deleteCondition);
    }
}
