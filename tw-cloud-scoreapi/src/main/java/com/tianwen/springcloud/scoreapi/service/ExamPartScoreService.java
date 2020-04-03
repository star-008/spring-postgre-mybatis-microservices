package com.tianwen.springcloud.scoreapi.service;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.constant.IStateCode;
import com.tianwen.springcloud.commonapi.query.Pagination;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.ExamPartScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.ExamPartScore;
import com.tianwen.springcloud.microservice.score.entity.ExamSubjectScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.base.BaseService;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.URLService;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.LocalNameRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class ExamPartScoreService extends BaseService {

    public static final String TEMPLATE_FILE_PATH = "templates/exam-part-score.xls";
    public static final String EXPORT_ZIP_FILE_NAME = "{examName}-试卷信息.zip";
    public static final String EXPORT_EXCEL_FILE_NAME = "{gradeName}-{subjectName}-{classTypeName}-试卷信息.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 6);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);
    protected static final CellPos CELL_POS_SUBJECT_NAME = new CellPos(1, 3);
    protected static final CellPos CELL_POS_TOTAL_SCORE = new CellPos(1, 5);

    @Autowired
    private ExamMicroApi examMicroApi;

    @Autowired
    private ExamSubjectScoreMicroApi examSubjectMicroApi;

    @Autowired
    private ExamPartScoreMicroApi examPartScoreMicroApi;

    public Response<ExamPartScore> importFromExcel(String examId, InputStream inputStream) {

        try {
            SimpleSheet sheet = new SimpleSheet(inputStream);

            String examId0 = sheet.getData(CELL_POS_EXAM_ID);
            if (StringUtils.isEmpty(examId0)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_ID_NOT_FOUND
                );
            }
            if (!StringUtils.equals(examId0, examId)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_ID_NO_MATCH,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_ID_NO_MATCH
                );
            }

            Exam exam = examMicroApi.get(examId).getResponseEntity();
            if (exam == null) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_NOT_FOUND
                );
            }

            if (!StringUtils.equals(exam.getName(), sheet.getData(CELL_POS_EXAM_NAME))) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_EXAM_NAME_NO_MATCH,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_EXAM_NAME_NO_MATCH
                );
            }

            LocalNameRepositoryService nameRepositoryService = new LocalNameRepositoryService(examId);

            DictItem grade = nameRepositoryService.key2entity(sheet.getData(CELL_POS_GRADE_NAME), RepositoryService.EntityType.GRADE);
            if (grade == null) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_GRADE_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_GRADE_NOT_FOUND
                );
            }
            String gradeId = grade.getDictvalue();
            nameRepositoryService.setGradeId(gradeId);

            DictItem subject = nameRepositoryService.key2entity(sheet.getData(CELL_POS_SUBJECT_NAME), RepositoryService.EntityType.SUBJECT);
            if (subject == null) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_SUBJECT_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_SUBJECT_NOT_FOUND
                );
            }
            String subjectId = subject.getDictvalue();

            ExamSubjectScore examSubjectSearchCondition = new ExamSubjectScore(examId, gradeId, subjectId);
            Response<ExamSubjectScore> examSubjectSearchRes = examSubjectMicroApi.searchByEntity(examSubjectSearchCondition);
            ExamSubjectScore examSubject = examSubjectSearchRes.getPageInfo() != null && !CollectionUtils.isEmpty(examSubjectSearchRes.getPageInfo().getList()) ? examSubjectSearchRes.getPageInfo().getList().get(0) : null;
            if (examSubject == null) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_NOT_FOUND,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_NOT_FOUND
                );
            }
            if (StringUtils.equals(examSubject.getRules(), "0")) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_IMPORT_DISABLED,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_IMPORT_DISABLED
                );
            }
            //[2018-12-31] Checking if the grade in the exam has been published already.
            if (StringUtils.equals(examSubject.getPubStatus(), ExamSubjectScore.PUB_STATUS_PUBLISHED)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_ALREADY_PUBLISHED,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_ALREADY_PUBLISHED
                );
            }

            examSubject.setGrade(grade);
            examSubject.setSubject(subject);
            
            List<ExamPartScore> examPartScoreList = new ArrayList<>();

            float totalScore = 0;
            String questionNo;
            String smallNo;
            String questionType;
            String score;
            for (int i = 3; i < sheet.getRowCount(); i++) {

                questionNo = sheet.getData(i, 2);
                smallNo = sheet.getData(i, 4);
                questionType= sheet.getData(i, 5);
                score = sheet.getData(i, 6);

                if (StringUtils.isEmpty(smallNo) && StringUtils.isEmpty(questionType) && StringUtils.isEmpty(score)) {
                    continue;
                }

                if (StringUtils.isNotEmpty(questionNo) && !NumberUtils.isNumber(questionNo)) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_INVALID_NUMBER,
                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_INVALID_NUMBER
                                    .replace("{cellName}", sheet.getCellName(i, 2))
                    );
                }

                if (StringUtils.isEmpty(smallNo)) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_EMPTY_CELL,
                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_EMPTY_CELL
                                    .replace("{cellName}", sheet.getCellName(i, 4))
                    );
                }

                if (!NumberUtils.isNumber(questionNo)) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_INVALID_NUMBER,
                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_INVALID_NUMBER
                                    .replace("{cellName}", sheet.getCellName(i, 4))
                    );
                }

                if (StringUtils.isEmpty(questionType)) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_EMPTY_CELL,
                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_EMPTY_CELL
                                    .replace("{cellName}", sheet.getCellName(i, 5))
                    );
                }

                ExamPartScore examPartScore = new ExamPartScore(
                        (DictItem)nameRepositoryService.key2entity(sheet.getData(i, 0), RepositoryService.EntityType.PAPER_VOLUME),
                        sheet.getData(i, 1),
                        questionNo,
                        nameRepositoryService.key2entity(sheet.getData(i, 3), RepositoryService.EntityType.QUESTION_CATEGORY),
                        smallNo,
                        nameRepositoryService.key2entity(questionType, RepositoryService.EntityType.QUESTION_TYPE),
                        score
                );

                if (examPartScoreList.indexOf(examPartScore) >= 0) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_SMALLNO_DUPLICATED,
                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_SMALLNO_DUPLICATED
                                    .replace("{cellName}", sheet.getCellName(i, 4))
                    );
                }

                examPartScoreList.add(examPartScore);

                totalScore += NumberUtils.toFloat(examPartScore.getScore());
            }

            String totalScoreStr = sheet.getData(CELL_POS_TOTAL_SCORE);
            if (!NumberUtils.isNumber(totalScoreStr)) {
                return new Response<>(
                        IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_TOTAL_SCORE_INVALID,
                        IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_TOTAL_SCORE_INVALID
                );
            }
            if (examPartScoreList.size() != 0) {
                if (NumberUtils.compare(totalScore, NumberUtils.toFloat(totalScoreStr)) != 0) {
                    return new Response<>(
                            IErrorMessageConstants.ERR_CODE_EXAM_PART_SCORE_TOTAL_SCORE_NO_MATCH,
                            IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_PART_SCORE_TOTAL_SCORE_NO_MATCH
                    );
                }
            }

            Collections.sort(examPartScoreList, ExamPartScore::compareTo);
            //examPartScoreMicroApi.batchAdd(examPartScoreList);

            Response response = new Response<>(examPartScoreList);

            examSubject.setVolumes(examSubject.getScore()); //Revision 1853
            examSubject.setScore(totalScoreStr);
            response.setResponseEntity(examSubject);

            return response;
        } catch (IOException | InvalidFormatException e) {
            return new Response<>(
                    "-1",
                    e.getMessage()
            );
        }
    }

    public String exportToExcel(Request request) throws IOException {

        String zipFileName = null;

        Exam exam = examMicroApi.get(request.getFilter().getExamId()).getResponseEntity();
        if (exam == null) {
            return null;
        }

        QueryTree queryTree = new QueryTree().page(Pagination.QUERY_ALL, 0);
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, exam.getId()));
        List<ExamSubjectScore> examSubjectList = examSubjectMicroApi.search(queryTree).getPageInfo().getList();

        if (CollectionUtils.isEmpty(examSubjectList)) {
            return null;
        }

        ZipOutputStream zipOutputStream = null;
        try {
            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_ZIP_FILE_NAME.replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_ZIP);
            OutputStream stream = new FileOutputStream(exportedFile.getPath());
            zipOutputStream = new ZipOutputStream(new CheckedOutputStream(stream, new CRC32()));

            if (zipOutputStream != null) {
                zipFileName = BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

                try {
                    for (ExamSubjectScore examSubject : examSubjectList) {
                        repositoryService.fill(examSubject);

                        SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));
                        sheet.setData(CELL_POS_EXAM_NAME, examSubject.getExam().getName());
                        sheet.setData(CELL_POS_EXAM_ID, examSubject.getExam().getId());
                        sheet.setData(CELL_POS_GRADE_NAME, examSubject.getGrade().getDictname());
                        sheet.setData(CELL_POS_SUBJECT_NAME, examSubject.getSubject().getDictname());
                        sheet.setData(CELL_POS_TOTAL_SCORE, examSubject.getScore());

                        ZipEntry entry = new ZipEntry(EXPORT_EXCEL_FILE_NAME
                                .replace("{gradeName}", examSubject.getGrade().getDictname())
                                .replace("{subjectName}", examSubject.getSubject().getDictname())
                                .replace("{classTypeName}", ICommonConstants.getClassTypeName(NumberUtils.toInt(examSubject.getClassType(), 1) - 1))
                        );

                        zipOutputStream.putNextEntry(entry);
                        sheet.write(zipOutputStream);
                        zipOutputStream.flush();
                        zipOutputStream.closeEntry();
                    }
                } catch (InvalidFormatException e) {
                }
            }
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.flush();
                zipOutputStream.close();
            }
        }

        return zipFileName;
    }

    public Response<ExamPartScore> batchAdd(String examSubjectId, List<ExamPartScore> examPartScoreList) {

        ExamSubjectScore examSubject = examSubjectMicroApi.get(examSubjectId).getResponseEntity();
        if (examSubject == null) {
            return new Response<>(
                    IErrorMessageConstants.ERR_CODE_EXAM_SUBJECT_NOT_FOUND,
                    IErrorMessageConstants.ERR_MESSAGE_CODE_EXAM_SUBJECT_NOT_FOUND
            );
        }

        float totalScore = 0;
        List<String> volumeIdList = new ArrayList<>();
        for (ExamPartScore examPartScore: examPartScoreList) {
            examPartScore.setExamId(examSubject.getExamId());
            examPartScore.setExamSubjectId(examSubject.getId());
            examPartScore.setGradeId(examSubject.getGradeId());
            examPartScore.setClassType(examSubject.getClassType());
            examPartScore.setSubjectId(examSubject.getSubjectId());

            totalScore += NumberUtils.toFloat(examPartScore.getScore());
            if (volumeIdList.indexOf(examPartScore.getVolumeId()) < 0) {
                volumeIdList.add(examPartScore.getVolumeId());
            }
        }

        examSubject.setScore(Float.toString(totalScore));
        examSubject.setVolumes(StringUtils.join(repositoryService.key2entity(volumeIdList, RepositoryService.EntityType.PAPER_VOLUME).stream().map(entity -> ((DictItem)entity).getDictname()).collect(Collectors.toList()), ", "));
        examSubject.setQuestionCount(Integer.toString(examPartScoreList.size()));
        examSubject.setStatus(ExamSubjectScore.STATUS_PART_SCORE_SAVED);

        Response<ExamPartScore> response = examPartScoreMicroApi.batchAdd(examSubjectId, examPartScoreList);
        if (response.getServerResult().getResultCode().equals(IStateCode.HTTP_200)) {
            examSubjectMicroApi.update(examSubject);
        }

        startExamEditing(examSubject.getExamId());

        return response;
    }

    public Response<ExamPartScore> search(QueryTree querytree) {
        return new ApiResponse<>(examPartScoreMicroApi.search(querytree));
    }
}
