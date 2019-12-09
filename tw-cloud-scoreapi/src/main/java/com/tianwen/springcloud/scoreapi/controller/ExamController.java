package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.base.entity.Term;
import com.tianwen.springcloud.microservice.score.entity.*;
import com.tianwen.springcloud.microservice.score.entity.request.*;
import com.tianwen.springcloud.scoreapi.api.ExamApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.entity.request.ExamSyncInfo;
import com.tianwen.springcloud.scoreapi.service.ExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kimchh on 11/5/2018.
 */
@RestController
@RequestMapping(value = "/exam")
public class ExamController extends AbstractController implements ExamApi {

    @Autowired
    private ExamService examService;

    @Override
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增")
    public Response<Exam> add(@RequestBody Exam exam) {
        return examService.save(exam, true);
    }

    @Override
    @ApiOperation(value = "更新", notes = "根据实体中ID单个更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据实体中ID单个更新")
    public Response<Exam> update(@RequestBody Exam exam) {
        return examService.save(exam);
    }

    @Override
    @ApiOperation(value = "同步考试", notes = "同步考试")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    @SystemControllerLog(description = "同步考试")
    public Response<Exam> sync(@RequestBody ExamSyncInfo examSyncInfo) {
        return examService.sync(examSyncInfo);
    }

    @Override
    @ApiOperation(value = "根据ID删除", notes = "根据ID单个删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @SystemControllerLog(description = "根据ID删除")
    public Response<Exam> delete(@PathVariable("id") String id)
    {
        return examService.delete(id);
    }

    @Override
    @ApiOperation(value = "关闭更新", notes = "关闭更新")
    @RequestMapping(value = "/close/{id}", method = RequestMethod.GET)
    @SystemControllerLog(description = "关闭更新")
    public Response<Exam> close(@PathVariable("id") String id)
    {
        return examService.closeSync(id);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<Exam> search(@RequestBody QueryTree queryTree) {
        return examService.search(queryTree);
    }

    @Override
    @ApiOperation(value = "RecentExamList", notes = "Shows most recent exam items.")
    @SystemControllerLog(description = "RecentExamList")
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public Response<Exam> recent(@RequestBody QueryTree queryTree) {
        return recent(ICommonConstants.EXAM_DEFAULT_RECENT_COUNT, queryTree);
    }

    @Override
    @ApiOperation(value = "RecentExamList", notes = "Shows most recent exam items.")
    @SystemControllerLog(description = "RecentExamList")
    @RequestMapping(value = "/recent/{limit}", method = RequestMethod.POST)
    public Response<Exam> recent(@PathVariable Integer limit, @RequestBody QueryTree queryTree) {

        return examService.recent(limit, queryTree);
    }

    @Override
    @ApiOperation(value = "根据ID查询", notes = "根据ID单个查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @SystemControllerLog(description = "根据ID查询")
    public Response<Exam> get(@PathVariable("id") String id)
    {
        return examService.get(id);
    }

    @Override
    public Response<Exam> getSimpleList(@RequestBody Filter filter) {
        return examService.getSimpleList(filter);
    }

    @Override
    public Response<Term> getTermList(@RequestBody Filter filter) {
        return examService.getTermList(filter);
    }

    @Override
    public Response<DictItem> getTypeList(@RequestBody ExamTypeFilter filter) {
        return examService.getTypeList(filter);
    }

    @Override
    public Response<DictItem> getGradeList(@RequestBody ExamGradeFilter filter) {
        return examService.getGradeList(filter);
    }

    @Override
    public Response<DictItem> getSubjectList(@RequestBody ExamSubjectFilter filter) {
        return examService.getSubjectList(filter);
    }

    @Override
    public Response<ClassInfo> getClassList(@RequestBody ExamClassFilter filter) {
        return examService.getClassList(filter);
    }
}
