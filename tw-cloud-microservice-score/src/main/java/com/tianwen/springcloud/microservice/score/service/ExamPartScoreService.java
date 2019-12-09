package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.ExamPartScoreMapper;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamPartScoreService extends ScoreBaseService<ExamPartScore>
{
    @Autowired
    ExamPartScoreMapper examPartScoreMapper;

    @Autowired
    StudentPartScoreService studentPartScoreService;

    @Override
    public int batchInsert(List<ExamPartScore> examPartScoreList) {
        return batchInsert(examPartScoreList.get(0).getExamSubjectId(), examPartScoreList);
    }

    public int batchInsert(String examSubjectId, List<ExamPartScore> examPartScoreList)
    {
        deleteByExamSubjectId(examSubjectId);

        ExamSubjectScore examSubjectScore = BeanHolder.getBean(ExamSubjectScoreService.class).selectByKey(examSubjectId);
        studentPartScoreService.deleteBy(examSubjectScore.getExamId(), examSubjectScore.getGradeId(), examSubjectScore.getClassType(), examSubjectScore.getSubjectId());

        return super.batchInsert(examPartScoreList);
    }

    protected int deleteByExamSubjectId(String examSubjectId) {
        ExamPartScore deleteCondition = new ExamPartScore();
        deleteCondition.setExamSubjectId(examSubjectId);
        return examPartScoreMapper.delete(deleteCondition);
    }
}
