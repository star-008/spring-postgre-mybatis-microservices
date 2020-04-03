package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.api.analysis.StuExamCountStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuExamCountStats;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class StuExamCountStatsService extends BaseService {

    @Autowired
    private StuExamCountStatsMicroApi stuExamCountStatsMicroApi;

    public Response<StuExamCountStats> get(String studentId) {
        return new ApiResponse<>(stuExamCountStatsMicroApi.get(studentId));
    }
}
