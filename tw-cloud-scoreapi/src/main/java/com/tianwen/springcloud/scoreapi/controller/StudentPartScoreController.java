package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.StudentPartScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.StudentPartScore;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.scoreapi.api.StudentPartScoreApi;
import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.service.StudentPartScoreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by kimchh on 11/5/2018.
 */
@RestController
@RequestMapping(value = "/student-part-score")
public class StudentPartScoreController extends AbstractController implements StudentPartScoreApi {

    @Autowired
    private StudentPartScoreService studentPartScoreService;

    @Autowired
    private ExamMicroApi examMicroApi;

    @Override
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "body")
    @RequestMapping(value = "/import/{examId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "文件上传")
    public Response<StudentPartScore> importFrom(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file, @RequestPart("force") String force) throws IOException {
        return importFromExcel(examId, file, force);
    }

    @Override
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "body")
    @RequestMapping(value = "/import/{examId}/excel", method = RequestMethod.POST)
    @SystemControllerLog(description = "文件上传")
    public Response<StudentPartScore> importFromExcel(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file, @RequestPart("force") String force) throws IOException {
        Filter filter = new Filter();
        filter.setForce(NumberUtils.parseNumber(force, Integer.class));
        return studentPartScoreService.importFromExcel(examId, file.getInputStream(), filter);
    }

    @Override
    @RequestMapping(value = "/export/{examId}", method = RequestMethod.GET)
    public void exportTo(@PathVariable("examId") String examId, HttpServletResponse response) throws IOException {
        exportToExcel(examId, response);
    }

    @Override
    @RequestMapping(value = "/export/{examId}/excel", method = RequestMethod.GET)
    public void exportToExcel(@PathVariable("examId") String examId, HttpServletResponse response) throws IOException {

        Exam exam = examMicroApi.get(examId).getResponseEntity();
        if (exam == null) {
            return;
        }

        prepareResponseForDownload(response, ICommonConstants.CONTENT_TYPE_EXCEL, studentPartScoreService.EXPORT_ZIP_FILE_NAME.replace("{examName}", exam.getName()));
        studentPartScoreService.exportToExcel(exam, response.getOutputStream());
    }

    @Override
    @ApiOperation(value = "条件查询", notes = "根据查询条件树批量查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @SystemControllerLog(description = "根据查询条件树批量查询")
    public Response<StudentPartScore> search(@RequestBody QueryTree querytree) {
        return studentPartScoreService.search(querytree);
    }
}
