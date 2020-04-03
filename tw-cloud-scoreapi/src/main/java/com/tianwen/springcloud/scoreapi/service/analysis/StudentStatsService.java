package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.microservice.score.api.analysis.StudentStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.analysis.StudentStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class StudentStatsService extends AnalysisService {

    @Autowired
    private StudentStatsMicroApi studentStatsMicroApi;


    public Response<StudentStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(StudentStats.class, studentStatsMicroApi, request));
    }

}
