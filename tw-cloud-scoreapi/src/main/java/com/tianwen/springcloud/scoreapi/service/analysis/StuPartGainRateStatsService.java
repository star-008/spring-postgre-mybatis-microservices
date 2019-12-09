package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.api.analysis.StuPartGainRateStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.analysis.StuPartGainRateStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class StuPartGainRateStatsService extends AnalysisService {

    @Autowired
    private StuPartGainRateStatsMicroApi stuPartGainRateStatsMicroApi;

    public Response<StuPartGainRateStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(StuPartGainRateStats.class, stuPartGainRateStatsMicroApi, request));
    }
}
