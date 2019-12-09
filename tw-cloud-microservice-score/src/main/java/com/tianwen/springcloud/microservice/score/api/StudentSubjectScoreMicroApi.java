package com.tianwen.springcloud.microservice.score.api;

import com.tianwen.springcloud.commonapi.base.ICRUDMicroApi;
import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "score-service", url = "http://localhost:3336/score-service/student-subject-score")
public interface StudentSubjectScoreMicroApi extends ICRUDMicroApi<StudentSubjectScore> {

    @RequestMapping(value = "/getCount", method = RequestMethod.POST)
    Response<Integer> getCount(@RequestBody Request request);

    @RequestMapping(value = "/getScoreEnteredList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getScoreEnteredList(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getScoreUnenteredList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getScoreUnenteredList(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getExamMissedList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getExamMissedList(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getScoreInvalidList", method = RequestMethod.POST)
    Response<StudentSubjectScore> getScoreInvalidList(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getScoreEnteredCount", method = RequestMethod.POST)
    Response<Integer> getScoreEnteredCount(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getScoreUnenteredCount", method = RequestMethod.POST)
    Response<Integer> getScoreUnenteredCount(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getExamMissedCount", method = RequestMethod.POST)
    Response<Integer> getExamMissedCount(@RequestBody StudentSubjectFilter filter);

    @RequestMapping(value = "/getScoreInvalidCount", method = RequestMethod.POST)
    Response<Integer> getScoreInvalidCount(@RequestBody StudentSubjectFilter filter);
}
