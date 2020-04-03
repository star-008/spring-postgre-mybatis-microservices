package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.StudentPartScore;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kimchh on 11/5/2018.
 */
public interface StudentPartScoreApi {

    @RequestMapping(value = "/import/{examId}", method = RequestMethod.POST)
    Response<StudentPartScore> importFrom(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file, @RequestPart("force") String force) throws IOException;

    @RequestMapping(value = "/import/{examId}/excel", method = RequestMethod.POST)
    Response<StudentPartScore> importFromExcel(@PathVariable("examId") String examId, @RequestPart("file") MultipartFile file, @RequestPart("force") String force) throws IOException;

    @RequestMapping(value = "/export/{examId}", method = RequestMethod.GET)
    void exportTo(@PathVariable("examId") String examId, HttpServletResponse response) throws IOException;

    @RequestMapping(value = "/export/{examId}/excel", method = RequestMethod.GET)
    void exportToExcel(@PathVariable("examId") String examId, HttpServletResponse response) throws IOException;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response<StudentPartScore> search(@RequestBody QueryTree querytree);
}
