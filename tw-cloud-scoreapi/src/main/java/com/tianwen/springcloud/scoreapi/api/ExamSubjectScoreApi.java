package com.tianwen.springcloud.scoreapi.api;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.ExamSubjectFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ExamSubjectScoreApi {

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    Response<ExamSubjectScore> save(@RequestBody ExamSubjectScore examSubjectScore);

    @RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
    Response<ExamSubjectScore> batchUpdate(@RequestBody List<ExamSubjectScore> examSubjectScoreList);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    Response<ExamSubjectScore> search(@RequestBody QueryTree querytree);

    @RequestMapping(value = "/getPubStatusList", method = RequestMethod.POST)
    Response<ExamSubjectScore> getPubStatusList(@RequestBody QueryTree querytree);

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    Response<ExamSubjectScore> publish(@RequestBody ExamSubjectFilter examSubjectScore);

    @RequestMapping(value = "/unpublish", method = RequestMethod.POST)
    Response<ExamSubjectScore> unpublish(@RequestBody ExamSubjectFilter examSubjectScore);
}
