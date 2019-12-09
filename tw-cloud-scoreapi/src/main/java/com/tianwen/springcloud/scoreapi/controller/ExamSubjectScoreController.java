package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import com.tianwen.springcloud.scoreapi.api.ExamSubjectScoreApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.service.ExamSubjectScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/exam-subject-score")
public class ExamSubjectScoreController extends AbstractController implements ExamSubjectScoreApi {

    @Autowired
    private ExamSubjectScoreService examSubjectScoreService;

    @Override
    @ApiOperation(value = "更新", notes = "根据实体中ID单个更新")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据实体中ID单个更新")
    public Response<ExamSubjectScore> save(@RequestBody ExamSubjectScore examSubjectScore) {
        return examSubjectScoreService.save(examSubjectScore);
    }

    @Override
    @ApiOperation(value = "批量修改实体对象", notes = "批量修改实体对象")
    @RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量修改实体对象")
    public Response<ExamSubjectScore> batchUpdate(@RequestBody List<ExamSubjectScore> entityList) {
        return examSubjectScoreService.batchUpdate(entityList);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<ExamSubjectScore> search(@RequestBody QueryTree querytree) {
        return examSubjectScoreService.search(querytree);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getPubStatusList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<ExamSubjectScore> getPubStatusList(@RequestBody QueryTree queryTree) {
        return examSubjectScoreService.getPubStatusList(queryTree);
    }

    @Override
    @ApiOperation(value = "批量修改实体对象", notes = "批量修改实体对象")
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量修改实体对象")
    public Response<ExamSubjectScore> publish(@RequestBody ExamSubjectFilter filter) {
        return examSubjectScoreService.publish(filter);
    }

    @Override
    @ApiOperation(value = "批量修改实体对象", notes = "批量修改实体对象")
    @RequestMapping(value = "/unpublish", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量修改实体对象")
    public Response<ExamSubjectScore> unpublish(@RequestBody ExamSubjectFilter filter) {
        return examSubjectScoreService.unpublish(filter);
    }
}
