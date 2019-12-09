package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import com.tianwen.springcloud.scoreapi.api.SysAnalysisScoreLevelApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.SysAnalysisScoreLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sys/analysis-score-level")
public class SysAnalysisScoreLevelController extends AbstractController implements SysAnalysisScoreLevelApi {

    @Autowired
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisScoreLevel> add(@RequestBody SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        return sysAnalysisScoreLevelService.add(sysAnalysisScoreLevel);
    }

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/batchAdd/{examId}/{gradeId}/{subjectType}/{subjectId}/{volumeId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<SysAnalysisScoreLevel> batchAdd(@RequestBody List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList, @PathVariable("examId") String examId, @PathVariable("gradeId") String gradeId, @PathVariable("subjectType") String subjectType, @PathVariable("subjectId") String subjectId, @PathVariable("volumeId") String volumeId) {
        return sysAnalysisScoreLevelService.batchAdd(sysAnalysisScoreLevelList, examId, gradeId, subjectType, subjectId, volumeId);
    }

    @Override
    @ApiOperation(value = "更新", notes = "根据实体中ID单个更新")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据实体中ID单个更新")
    public Response<SysAnalysisScoreLevel> delete(@RequestBody SysAnalysisScoreLevel sysAnalysisScoreLevel) {
        return sysAnalysisScoreLevelService.delete(sysAnalysisScoreLevel);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<SysAnalysisScoreLevel> search(@RequestBody QueryTree querytree) {
        return sysAnalysisScoreLevelService.search(querytree);
    }

}
