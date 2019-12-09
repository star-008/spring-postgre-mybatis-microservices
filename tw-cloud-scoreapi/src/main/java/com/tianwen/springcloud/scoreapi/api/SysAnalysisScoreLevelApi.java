package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.SysAnalysisScoreLevel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface SysAnalysisScoreLevelApi {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    Response add(@RequestBody SysAnalysisScoreLevel sysAnalysisScoreLevel);

    @RequestMapping(value = "/batchAdd/{examId}/{gradeId}/{subjectType}/{subjectId}/{volumeId}", method = RequestMethod.POST)
    Response<SysAnalysisScoreLevel> batchAdd(@RequestBody List<SysAnalysisScoreLevel> sysAnalysisScoreLevelList, @PathVariable("examId") String examId, @PathVariable("gradeId") String gradeId, @PathVariable("subjectType") String subjectType, @PathVariable("subjectId") String subjectId, @PathVariable("volumeId") String volumeId);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    Response delete(@RequestBody SysAnalysisScoreLevel sysAnalysisScoreLevel);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response search(@RequestBody QueryTree querytree);
}
