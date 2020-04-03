package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.StudentSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.StudentSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.*;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class ExamSubjectScoreService extends BaseService {

    @Autowired
    private ExamSubjectScoreMicroApi examSubjectScoreMicroApi;

    @Autowired
    private StudentSubjectScoreMicroApi studentSubjectScoreMicroApi;

    @Autowired
    @Lazy
    private SysAnalysisScoreLevelService sysAnalysisScoreLevelService;

    @Autowired
    @Lazy
    private SysAnalysisConfigService sysAnalysisConfigService;

    public Response<ExamSubjectScore> save(ExamSubjectScore examSubjectScore) {

        //examSubjectScore.setStatus(ExamSubjectScore.STATUS_FULL_SCORE_SAVED);

        Response<ExamSubjectScore> response = examSubjectScoreMicroApi.update(examSubjectScore);

        if (!StringUtils.isEmpty(examSubjectScore.getExamId())) {
            startExamEditing(examSubjectScore.getExamId());
        }

        return response;

    }

    public Response<ExamSubjectScore> batchUpdate(List<ExamSubjectScore> examSubjectScoreList) {

        Response<ExamSubjectScore> response = examSubjectScoreMicroApi.batchUpdate(examSubjectScoreList);

        startExamEditing(examSubjectScoreList.get(0).getExamId());

        return response;
    }

    public Response<ExamSubjectScore> search(QueryTree querytree) {
        return new ApiResponse<>(examSubjectScoreMicroApi.search(querytree));
    }

    public Response<ExamSubjectScore> getPubStatusList(QueryTree queryTree) {
        return new ApiResponse<>(examSubjectScoreMicroApi.getPubStatusList(queryTree));
    }

    public Response<ExamSubjectScore> publish(ExamSubjectFilter filter) {

        if (!BeanHolder.getBean(ExamService.class).isAccountExamOwner(filter.getExamId())) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_PUBLISH_DENIED,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_PUBLISH_DENIED
            );
        }

        boolean hasScoreUnentered, hasSysAnalysisScoreConfig, hasSysAnalysisScoreLevels;

        StudentSubjectFilter studentSubjectFilter = new StudentSubjectFilter(filter.getExamId(), filter.getGradeId());
        if (hasScoreUnentered = (studentSubjectScoreMicroApi.getScoreUnenteredCount(studentSubjectFilter).getResponseEntity() > 0)) {
            if (!filter.isForcing()) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_STUDENT_SCORE_EMPTY,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_EMPTY
                );
            } else {
//                if ((studentSubjectScoreMicroApi.getScoreEnteredCount(studentSubjectFilter).getResponseEntity() <= 0)) {
//                    return new Response<>(
//                            IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_STUDENT_SCORE_NON_ENTERED,
//                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_NON_ENTERED
//                    );
//                }
            }
        }

        List<StudentSubjectScore> scoreInvalidList = studentSubjectScoreMicroApi.getScoreInvalidList(studentSubjectFilter).getPageInfo().getList(); 
        if (scoreInvalidList.size() > 0) {
            StudentSubjectScore studentSubjectScore = scoreInvalidList.get(0);
            repositoryService.fill(studentSubjectScore);
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_STUDENT_SCORE_INVALID,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_INVALID
                            .replace("{className}", studentSubjectScore.getClassInfo() != null ? studentSubjectScore.getClassInfo().getFullName() : "")
                            .replace("{studentName}", studentSubjectScore.getStudentName())
                            .replace("{subjectName}", studentSubjectScore.getSubject() != null ? studentSubjectScore.getSubject().getDictname() : "")
            );
        }

        if (studentSubjectScoreMicroApi.getExamMissedCount(studentSubjectFilter).getResponseEntity() > 0) {
            if (!filter.isForcing()) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_STUDENT_EXAM_MISSED,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_EXAM_MISSED
                );
            }
        }
        filter.downForce();

        if (!(hasSysAnalysisScoreConfig = sysAnalysisConfigService.hasSysAnalysisConfig(filter.getExamId()))
                && !filter.isForcing()) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_SYS_ANALYSIS_NOT_READY,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_INVALID
            );
        }

        List<ExamSubjectScore> examSubjectList = examSubjectScoreMicroApi.searchByEntity(new ExamSubjectScore(filter.getExamId())).getPageInfo().getList();
        if (!(hasSysAnalysisScoreLevels = sysAnalysisScoreLevelService.hasSysAnalysisScoreLevels(filter.getExamId(), getSubjectVolumeCountByExam(examSubjectList)))
                && !filter.isForcing()) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_SYS_ANALYSIS_NOT_READY,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_STUDENT_SCORE_INVALID
            );
        }

        if (hasScoreUnentered) {
            BeanHolder.getBean(StudentSubjectScoreService.class).fillUnenteredScores(studentSubjectFilter);
        }
        if (!hasSysAnalysisScoreConfig) {
            sysAnalysisConfigService.fillDefaultSysAnalysisConfig(filter.getExamId());
        }
        if (!hasSysAnalysisScoreLevels) {
            sysAnalysisScoreLevelService.fillDefaultSysAnalysisScoreLevels(filter.getExamId(), examSubjectList);
        }

        ExamSubjectScore condition = new ExamSubjectScore();
        condition.setExamId(filter.getExamId());
        condition.setGradeId(filter.getGradeId());

        List<ExamSubjectScore> examSubjectScoreList = examSubjectScoreMicroApi.searchByEntity(condition).getPageInfo().getList();

        for (ExamSubjectScore examSubjectScore: examSubjectScoreList) {
            examSubjectScore.setPublisherId(sessionBean.getAccount().getUserId());
            examSubjectScore.setPublisherName(sessionBean.getAccount().getRealName());
            examSubjectScore.setPubTime(new Timestamp(System.currentTimeMillis()));
            examSubjectScore.setPubStatus(ExamSubjectScore.PUB_STATUS_PUBLISHED);
        }

        Response<ExamSubjectScore> response = examSubjectScoreMicroApi.batchUpdate(examSubjectScoreList);
        if (IStateCode.HTTP_200.equals(response.getServerResult().getResultCode())) {
            response.setServerResult(BeanHolder.getBean(ExamService.class).publish(filter.getExamId()).getServerResult());
        }

        return response;
    }

    public Response<ExamSubjectScore> unpublish(ExamSubjectFilter filter) {

        if (!BeanHolder.getBean(ExamService.class).isAccountExamOwner(filter.getExamId())) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_UNPUBLISH_DENIED,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_UNPUBLISH_DENIED
            );
        }

        ExamSubjectScore condition = new ExamSubjectScore();
        condition.setExamId(filter.getExamId());
        condition.setGradeId(filter.getGradeId());

        List<ExamSubjectScore> examSubjectScoreList = examSubjectScoreMicroApi.searchByEntity(condition).getPageInfo().getList();
        for (ExamSubjectScore examSubjectScore: examSubjectScoreList) {
            examSubjectScore.setPublisherId(sessionBean.getAccount().getUserId());
            examSubjectScore.setPublisherName(sessionBean.getAccount().getRealName());
            examSubjectScore.setPubTime(new Timestamp(System.currentTimeMillis()));
            examSubjectScore.setPubStatus(ExamSubjectScore.PUB_STATUS_PUBLISH_CANCELED);
        }

        Response<ExamSubjectScore> response = examSubjectScoreMicroApi.batchUpdate(examSubjectScoreList);
        if (IStateCode.HTTP_200.equals(response.getServerResult().getResultCode())) {

            BeanHolder.getBean(StudentSubjectScoreService.class).clearChanges(filter.getExamId(), filter.getGradeId());
            response.setServerResult(BeanHolder.getBean(ExamService.class).cancelPublishing(filter.getExamId()).getServerResult());
        }

        return response;
    }

    public boolean hasPublishedOne(String examId) {

        QueryTree queryTree = new QueryTree().page(0,1);

        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
        queryTree.addCondition(new QueryCondition("pt.pubstatus", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, ExamSubjectScore.PUB_STATUS_PUBLISHED));

        return examSubjectScoreMicroApi.search(queryTree).getPageInfo().getSize() > 0;

    }

    public boolean hasUnpublishedOne(String examId) {

        QueryTree queryTree = new QueryTree().page(0,1);

        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, examId));
        queryTree.addCondition(new QueryCondition("pt.pubstatus", QueryCondition.Prepender.AND, QueryCondition.Operator.NOT_EQUAL, ExamSubjectScore.PUB_STATUS_PUBLISHED));

        return examSubjectScoreMicroApi.search(queryTree).getPageInfo().getSize() > 0;

    }

    public int getSubjectVolumeCountByExam(List<ExamSubjectScore> examSubjectList) {
        int count = 0;

        List<String> gradeIdList = new ArrayList<>();
        examSubjectList.stream().filter(examSubjectScore -> gradeIdList.indexOf(examSubjectScore.getGradeId()) < 0).forEach(examSubjectScore -> gradeIdList.add(examSubjectScore.getGradeId()));

        count += gradeIdList.size(); //For 'TOTAL'

        for(ExamSubjectScore examSubject: examSubjectList) {

            count ++; //For this subject

            if (!StringUtils.isEmpty(examSubject.getVolumes())) {
                count += examSubject.getVolumes().split(",").length; //For volumes
            }
        }

        return count;
    }

    public List<String> getSubjectVolumeListByExam(List<ExamSubjectScore> examSubjectist) {
        List<String> subjectVolumeList = new ArrayList<>();

        for (ExamSubjectScore examSubject: examSubjectist) {

        }

        return subjectVolumeList;
    }
}
