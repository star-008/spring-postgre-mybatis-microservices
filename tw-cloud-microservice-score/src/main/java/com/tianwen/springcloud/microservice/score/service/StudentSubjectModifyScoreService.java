package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.StudentSubjectModifyScoreMapper;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentSubjectModifyScoreService extends ScoreBaseService<StudentSubjectModifyScore>
{
    @Autowired
    private StudentSubjectModifyScoreMapper studentSubjectModifyScoreMapper;


}
