package com.tianwen.springcloud.microservice.score.service;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.dao.ExamMapper;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
public class ExamService extends ScoreBaseService<Exam>
{
    @Autowired
    private ExamMapper examMapper;

    @Override
    public int save(Exam entity) {
        entity.setCreatedTime(new Timestamp((new Date()).getTime()));
        entity.setLastModifiedTime(entity.getCreatedTime());
        return super.save(entity);
    }

    @Override
    public int updateNotNull(Exam entity) {
        entity.setLastModifiedTime(entity.getCreatedTime());
        return super.updateNotNull(entity);
    }

    public List<Exam> getSimpleList(Request request) {
        return examMapper.getSimpleList(request.toMap());
    }

    public List<String> getTermIdList(Filter filter) {
        return examMapper.getTermIdList(filter.toQueryTreeMap());
    }

    public List<String> getTypeIdList(Filter filter) {
        return examMapper.getTypeIdList(filter.toQueryTreeMap());
    }
}
