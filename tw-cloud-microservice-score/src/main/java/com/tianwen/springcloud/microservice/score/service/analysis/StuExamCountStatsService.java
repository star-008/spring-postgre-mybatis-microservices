package com.tianwen.springcloud.microservice.score.service.analysis;

import com.tianwen.springcloud.microservice.score.base.ScoreBaseService;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuExamCountStats;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import org.springframework.stereotype.Service;

@Service
public class StuExamCountStatsService extends ScoreBaseService<StuExamCountStats> {

    public StuExamCountStats get(Object id) {
        Filter filter = new Filter();
        filter.setPubStatusPublished();
        filter.setStudentId(String.valueOf(id));
        return getBaseMapper().get(filter.toQueryTreeMap());
    }
}
