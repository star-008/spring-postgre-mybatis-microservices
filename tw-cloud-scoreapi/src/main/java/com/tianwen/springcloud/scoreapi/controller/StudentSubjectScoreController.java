package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import com.tianwen.springcloud.scoreapi.api.StudentSubjectScoreApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.service.StudentSubjectScoreService;
import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by kimchh on 11/5/2018.
 */
@RestController
@RequestMapping(value = "/student-subject-score")
public class StudentSubjectScoreController extends AbstractController implements StudentSubjectScoreApi {

    @Autowired
    private StudentSubjectScoreService studentSubjectScoreService;

    @Override
    @ApiOperation(value = "导出", notes = "导出")
    @SystemControllerLog(description = "导出")
    public Response<String> exportToWord(@RequestBody QueryTree queryTree, HttpServletResponse response) throws IOException, XmlException {
        prepareResponseForDownload(response, ICommonConstants.CONTENT_TYPE_WORD, studentSubjectScoreService.EXPORT_WORD_FILE_NAME);
        return new Response<>(studentSubjectScoreService.exportToWord(queryTree));
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectScore> search(@RequestBody QueryTree querytree) {
        return studentSubjectScoreService.search(querytree);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectScore> save(@RequestBody List<StudentSubjectScore> studentSubjectScoreList) {
        return studentSubjectScoreService.save(studentSubjectScoreList);
    }

    @Override
    public Response<Student> getStudentListForChange(@RequestBody Filter filter) {
        return studentSubjectScoreService.getStudentListForChange(filter);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/searchForChange", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectScore> searchForChange(@RequestBody QueryTree querytree) {
        return studentSubjectScoreService.searchForChange(querytree);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/review/request", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectModifyScore> requestChange(@RequestBody StudentSubjectModifyScore studentSubjectModifyScore) {
        return studentSubjectScoreService.requestChange(studentSubjectModifyScore);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/review/batchAccept", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectModifyScore> acceptChanges(@RequestBody List<StudentSubjectModifyScore> studentSubjectModifyScoreList) {
        return studentSubjectScoreService.acceptChanges(studentSubjectModifyScoreList);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/review/batchReject", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectModifyScore> rejectChanges(@RequestBody List<StudentSubjectModifyScore> studentSubjectModifyScoreList) {
        return studentSubjectScoreService.rejectChanges(studentSubjectModifyScoreList);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/review/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectModifyScore> searchChanges(@RequestBody QueryTree querytree) {
        return studentSubjectScoreService.searchChanges(querytree);
    }

    @Override
    @ApiOperation(value = "批量硬删除实体对象", notes = "批量硬删除实体对象")
    @RequestMapping(value = "/review/batchDelete", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量硬删除实体对象")
    public Response<StudentSubjectModifyScore> removeChanges(@RequestBody List<StudentSubjectModifyScore> studentSubjectModifyScoreList) {
        return studentSubjectScoreService.removeChanges(studentSubjectModifyScoreList);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getScoreEnteredList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectScore> getScoreEnteredList(@RequestBody StudentSubjectFilter filter) {
        return studentSubjectScoreService.getScoreEnteredList(filter);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getScoreUnenteredList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectScore> getScoreUnenteredList(@RequestBody StudentSubjectFilter filter) {
        return studentSubjectScoreService.getScoreUnenteredList(filter);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/getExamMissedList", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentSubjectScore> getExamMissedList(@RequestBody StudentSubjectFilter filter) {
        return studentSubjectScoreService.getExamMissedList(filter);
    }
}
