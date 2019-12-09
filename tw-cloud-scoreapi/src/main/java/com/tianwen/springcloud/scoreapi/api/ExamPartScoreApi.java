package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExamPartScoreApi {

    @RequestMapping(value = "/import/{examId}", method = RequestMethod.POST)
    Response<ExamPartScore> importFrom(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file) throws IOException;

    @RequestMapping(value = "/import/{examId}/excel", method = RequestMethod.POST)
    Response<ExamPartScore> importFromExcel(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file) throws IOException;

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    Response<String> exportTo(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/export/excel", method = RequestMethod.POST)
    Response<String> exportToExcel(@RequestBody Request request) throws IOException;

    @RequestMapping(value = "/batchAdd/{examSubjectId}", method = RequestMethod.POST)
    Response<ExamPartScore> batchAdd(@PathVariable("examSubjectId") String examSubjectId, @RequestBody List<ExamPartScore> examPartScoreList);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response<ExamPartScore> search(@RequestBody QueryTree querytree);
}
