package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.api.ExamPartScoreApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.service.ExamPartScoreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/exam-part-score")
public class ExamPartScoreController extends AbstractController implements ExamPartScoreApi {

    @Autowired
    private ExamPartScoreService examPartScoreService;

    @Override
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "body")
    @RequestMapping(value = "/import/{examId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "文件上传")
    public Response<ExamPartScore> importFrom(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file) throws IOException {
        return importFromExcel(examId, file);
    }

    @Override
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "body")
    @RequestMapping(value = "/import/{examId}/excel", method = RequestMethod.POST)
    @SystemControllerLog(description = "文件上传")
    public Response<ExamPartScore> importFromExcel(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file) throws IOException {
        return examPartScoreService.importFromExcel(examId, file.getInputStream());
    }

    @Override
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Response<String> exportTo(@RequestBody Request request) throws IOException {
        return exportToExcel(request);
    }

    @Override
    @RequestMapping(value = "/export/excel", method = RequestMethod.POST)
    public Response<String> exportToExcel(@RequestBody Request request) throws IOException {
        return new Response<>(examPartScoreService.exportToExcel(request));
    }

    @Override
    @ApiOperation(value = "批量修改实体对象", notes = "批量修改实体对象")
    @RequestMapping(value = "/batchAdd/{examSubjectId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量修改实体对象")
    public Response<ExamPartScore> batchAdd(@PathVariable("examSubjectId") String examSubjectId, @RequestBody List<ExamPartScore> examPartScoreList) {
        return examPartScoreService.batchAdd(examSubjectId, examPartScoreList);
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<ExamPartScore> search(@RequestBody QueryTree querytree) {
        return examPartScoreService.search(querytree);
    }
}
