package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.base.entity.Student;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectModifyScore;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.Filter;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import org.apache.xmlbeans.XmlException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by kimchh on 11/5/2018.
 */
public interface StudentSubjectScoreApi {

//    @RequestMapping(value = "/import", method = RequestMethod.POST)
//    Response<StudentSubjectScore> importFrom(@RequestPart("file") MultipartFile file) throws IOException;
//
//    @RequestMapping(value = "/import/excel", method = RequestMethod.POST)
//    Response<StudentSubjectScore> importFromExcel(@RequestPart("file") MultipartFile file) throws IOException;
//
//    @RequestMapping(value = "/export/{examId}", method = RequestMethod.GET)
//    void exportTo(@PathVariable("examId") String examId, HttpServletResponse response) throws IOException;
//
//    @RequestMapping(value = "/export/{examId}/excel", method = RequestMethod.GET)
//    void exportToExcel(@PathVariable("examId") String examId, HttpServletResponse response) throws IOException;

    @RequestMapping(value = "/export/book/word", method = RequestMethod.POST)
    Response<String> exportToWord(@RequestBody QueryTree queryTree, HttpServletResponse response) throws IOException, XmlException;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response<StudentSubjectScore> search(@RequestBody QueryTree querytree);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    Response<StudentSubjectScore> save(@RequestBody List<StudentSubjectScore> studentSubjectScoreList);

    @RequestMapping(value = "/review/getStudentList", method = RequestMethod.POST)
    Response<Student> getStudentListForChange(@RequestBody Filter filter);

    @RequestMapping(value = "/searchForChange", method = RequestMethod.POST)
    Response<StudentSubjectScore> searchForChange(@RequestBody QueryTree querytree);

    @RequestMapping(value = "/review/request", method = RequestMethod.POST)
    Response<StudentSubjectModifyScore> requestChange(@RequestBody StudentSubjectModifyScore studentSubjectModifyScore);

    @RequestMapping(value = "/review/batchAccept", method = RequestMethod.POST)
    Response<StudentSubjectModifyScore> acceptChanges(@RequestBody List<StudentSubjectModifyScore> studentSubjectModifyScoreList);

    @RequestMapping(value = "review/batchReject", method = RequestMethod.POST)
    Response<StudentSubjectModifyScore> rejectChanges(@RequestBody List<StudentSubjectModifyScore> studentSubjectModifyScoreList);

    @RequestMapping(value = "review/search", method = RequestMethod.POST)
    Response<StudentSubjectModifyScore> searchChanges(@RequestBody QueryTree queryTree);

    @RequestMapping(value = "/review/batchDelete", method = RequestMethod.POST)
    Response<StudentSubjectModifyScore> removeChanges(@RequestBody List<StudentSubjectModifyScore> studentSubjectModifyScoreList);

    @RequestMapping(value = "/getScoreEnteredList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getScoreEnteredList(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getExamMissedList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getExamMissedList(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getScoreUnenteredList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getScoreUnenteredList(@RequestBody StudentSubjectFilter filter);
}
