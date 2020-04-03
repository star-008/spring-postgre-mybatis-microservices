package com.tianwen.springcloud.microservice.score.controller;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.log.SystemControllerLog;
import com.tianwen.springcloud.microservice.score.api.ExamPartScoreMicroApi;
import com.tianwen.springcloud.microservice.score.base.AbstractScoreCRUDController;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import com.tianwen.springcloud.microservice.score.service.ExamPartScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/exam-part-score")
public class ExamPartScoreController extends AbstractScoreCRUDController<ExamPartScore> implements ExamPartScoreMicroApi {

    @Autowired
    private ExamPartScoreService examPartScoreService;

    public ExamPartScoreController() {
        useGetListForSearch();
    }

    @Override
    public void validate(MethodType methodType, Object p) {}

    @Override
    @RequestMapping(value = "/batchAdd/{examSubjectId}", method = RequestMethod.POST)
    @SystemControllerLog(description = "批量添加实体对象")
    public Response<ExamPartScore> batchAdd(@PathVariable("examSubjectId") String examSubjectId, @RequestBody List<ExamPartScore> examPartScoreList) {
        validate(MethodType.BATCHADD, examPartScoreList);
        examPartScoreService.batchInsert(examSubjectId, examPartScoreList);
        return new Response<>(examPartScoreList);
    }
}