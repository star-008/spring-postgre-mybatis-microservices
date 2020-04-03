package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.api.SysAnalysisScoreLevelMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.service.SysAnalysisScoreLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sys-analysis-score-level")
public class SysAnalysisScoreLevelController extends AbstractScoreCRUDController<SysAnalysisScoreLevel> implements SysAnalysisScoreLevelMicroApi {

    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/batchAdd/{examId}/{gradeId}/{subjectType}/{subjectId}/{volumeId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisScoreLevel> batchAdd(@RequestBody List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList, @PathVariable("examId") String examId, @PathVariable("gradeId") String gradeId, @PathVariable("subjectType") String subjectType, @PathVariable("subjectId") String subjectId, @PathVariable("volumeId") String volumeId) {
        validate(MethodType.BATCHADD, sysAnalysisScoreLevelList);
        sysAnalysisScoreLevelService.batchInsert(sysAnalysisScoreLevelList, examId, gradeId, subjectType, subjectId, volumeId);
        return new Response<>(sysAnalysisScoreLevelList);
    }

    public Response<SysAnalysisScoreLevel> getTypeListByExam(@RequestBody Request request) {
        return new Response<>(sysAnalysisScoreLevelService.getTypeListByExam(request));
    }

    public Response<Integer> getTypeCountByExam(@RequestBody Request request) {
        return new Response<>(sysAnalysisScoreLevelService.getTypeCountByExam(request));
    }
}