package com.tianwen.springcloud.microservice.score.service;

import com.github.pagehelper.Page;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.datasource.util.QueryUtils;
import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.ExamGradeClassSubjectMapper;
import com.tianwen.springcloud.microservice.score.dao.ExamPartScoreMapper;
import com.tianwen.springcloud.microservice.score.dao.ExamSubjectScoreMapper;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ExamSubjectScoreService extends ScoreBaseService<ExamSubjectScore>
{
    @Autowired
    ExamSubjectScoreMapper examSubjectScoreMapper;

    @Autowired
    ExamGradeClassSubjectMapper examGradeClassSubjectMapper;

    @Autowired
    ExamPartScoreMapper examPartScoreMapper;

    @Override
    public int batchInsert(List<ExamSubjectScore> examSubjectScoreList)
    {
        //deleteByExamId(examSubjectScoreList.get(0).getExamId());

        return super.batchInsert(examSubjectScoreList);
    }

//    protected int deleteByExamId(String examId) {
//        ExamPartScore examPartScoreDeleteCondition = new ExamPartScore();
//        examPartScoreDeleteCondition.setExamId(examId);
//        examPartScoreMapper.delete(examPartScoreDeleteCondition);
//
//        ExamSubjectScore deleteCondition = new ExamSubjectScore();
//        deleteCondition.setExamId(examId);
//        return examSubjectScoreMapper.delete(deleteCondition);
//    }


    public List<String> getGradeIdList(Filter filter) {
        return examGradeClassSubjectMapper.getGradeIdList(filter.toQueryTreeMap());
    }

    public List<String> getSubjectIdList(Filter filter) {
        return examGradeClassSubjectMapper.getSubjectIdList(filter.toQueryTreeMap());
    }

    public List<ExamSubjectScore> getPubStatusList(QueryTree queryTree) {
        Map<String, Object> filter = QueryUtils.queryTree2Map(queryTree);

        Page<ExamSubjectScore> page = new Page<>(queryTree.getPagination().getPageNo(), queryTree.getPagination().getNumPerPage());

        page.addAll(examSubjectScoreMapper.selectPubStatusList(filter));
        page.setTotal(examSubjectScoreMapper.countPubStatusList(filter));

        return page;
    }
}
