package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.api.StudentSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.StudentSubjectFilter;
import com.tianwen.springcloud.microservice.score.service.StudentSubjectScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student-subject-score")
public class StudentSubjectScoreController extends AbstractScoreCRUDController<StudentSubjectScore> implements StudentSubjectScoreMicroApi {

    @Autowired
    protected StudentSubjectScoreService studentSubjectScoreService;

    public StudentSubjectScoreController() {
        useGetListForSearch();
    }

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    @RequestMapping(value = "/getScoreEnteredList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getScoreEnteredList")
    public Response<StudentSubjectScore> getScoreEnteredList(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getScoreEnteredList(filter));
    }

    @Override
    @RequestMapping(value = "/getScoreUnenteredList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getIndividualList")
    public Response<StudentSubjectScore> getScoreUnenteredList(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getScoreUnenteredList(filter));
    }

    @Override
    @RequestMapping(value = "/getExamMissedList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getIndividualList")
    public Response<StudentSubjectScore> getExamMissedList(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getExamMissedList(filter));
    }

    @Override
    @RequestMapping(value = "/getScoreInvalidList", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getScoreInvalidList")
    public Response<StudentSubjectScore> getScoreInvalidList(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getScoreInvalidList(filter));
    }

    @Override
    @RequestMapping(value = "/getScoreEnteredCount", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getIndividualList")
    public Response<Integer> getScoreEnteredCount(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getScoreEnteredCount(filter));
    }

    @Override
    @RequestMapping(value = "/getScoreUnenteredCount", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getIndividualList")
    public Response<Integer> getScoreUnenteredCount(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getScoreUnenteredCount(filter));
    }

    @Override
    @RequestMapping(value = "/getExamMissedCount", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getExamMissedCount")
    public Response<Integer> getExamMissedCount(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getExamMissedCount(filter));
    }

    @Override
    @RequestMapping(value = "/getScoreInvalidCount", method = RequestMethod.POST)
    @SystemControllerLog(description = "ICRUDMicroAp getIndividualList")
    public Response<Integer> getScoreInvalidCount(@RequestBody StudentSubjectFilter filter) {
        return new Response<>(studentSubjectScoreService.getScoreInvalidCount(filter));
    }
}