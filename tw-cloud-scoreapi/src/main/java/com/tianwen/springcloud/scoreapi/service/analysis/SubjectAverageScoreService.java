package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.SubjectAverageScoreMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.SubjectAverageScore;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.URLService;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class SubjectAverageScoreService extends AnalysisService {
    // default export in subject average score
    public static final String TEMPLATE_FILE_PATH = "templates/subject-average-score.xls";
    public static final String EXPORT_FILE_NAME = "{gradeName}{examName}--班级科目均分排位统计.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    // export by classid in subject average score
    public static final String TEMPLATE_FILE_PATH_BY_CLASS = "templates/subject-average-score-class.xls";
    public static final String EXPORT_FILE_NAME_BY_CLASS = "{gradeName}{examName}--班级科目均分排位统计-按班级.xls";

    protected static final CellPos CELL_POS_SUBJECT_NAME = new CellPos(2, 2);

    protected static final int ROW_INDEX_STUDENT_START = 3;

    @Autowired
    private SubjectAverageScoreMicroApi subjectAverageScoreMicroApi;

    @Autowired
    private ExamSubjectScoreMicroApi examSubjectScoreMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<SubjectAverageScore> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(SubjectAverageScore.class, subjectAverageScoreMicroApi, request));
    }

    public String exportToExcel(Request request) {

        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<SubjectAverageScore> subjectAverageScoreList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName());
            sheet.setData(CELL_POS_EXAM_ID, examId);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname());

            int rowStudent = ROW_INDEX_STUDENT_START;

            String subjectId = null;
            int classCount = 0;
            for (SubjectAverageScore subjectAverageScore: subjectAverageScoreList) {
                if (!StringUtils.equals(subjectId, subjectAverageScore.getSubjectId())) {
                    subjectId = subjectAverageScore.getSubjectId();
                    if (classCount > 1) {
                        sheet.mergeCells(rowStudent - classCount, classCount, 0, 1);
                    }
                    classCount = 0;
                }
                classCount ++;

                sheet.setData(rowStudent,0, StringUtils.equals(subjectAverageScore.getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)  ? ICommonConstants.TOTAL_SCORE_NAME : getSubjectName(subjectAverageScore.getSubject()));
                sheet.setData(rowStudent, 1, StringUtils.equals(subjectAverageScore.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(subjectAverageScore.getClassInfo()));
                sheet.setData(rowStudent, 2, subjectAverageScore.getApplyCount());
                sheet.setData(rowStudent, 3, subjectAverageScore.getTotalCount());
                sheet.setData(rowStudent, 4, subjectAverageScore.getClassInfo() == null ? null : subjectAverageScore.getClassInfo().getAdviserNames());
                sheet.setData(rowStudent, 5, subjectAverageScore.getTeacherName());
                sheet.setData(rowStudent, 6, subjectAverageScore.getMaxScore());
                sheet.setData(rowStudent, 7, subjectAverageScore.getMinScore());
                sheet.setData(rowStudent, 8, subjectAverageScore.getScore());
                sheet.setData(rowStudent, 9, subjectAverageScore.getDiff());
                sheet.setData(rowStudent, 10, subjectAverageScore.getRank());

                for (int i = 0; i <= 9; i++) {
                    sheet.setCellAlignCenter(new CellPos(rowStudent, i));
                }

                sheet.setCellVerticalAlign(new CellPos(rowStudent, 0), CellStyle.VERTICAL_CENTER);

                rowStudent ++;
            }
            if (classCount > 1) {
                sheet.mergeCells(rowStudent - classCount, classCount, 0, 1);
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME.replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());
        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    public String exportToExcelByClass(Request request) {

        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<SubjectAverageScore> subjectAverageScoreList = getList(request).getPageInfo().getList();

        List<String> subjectIdList = request.getFilter().getSubjectIdList();
        List<DictItem> subjectList = repositoryService.key2entity(subjectIdList, RepositoryService.EntityType.SUBJECT);


        Map<String, String> subjectFullScoreMap = new HashMap<>();

        QueryTree queryTree = new QueryTree();
        queryTree.addCondition(new QueryCondition("pt.examid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, request.getFilter().getExamId()));
        queryTree.addCondition(new QueryCondition("pt.gradeid", QueryCondition.Prepender.AND, QueryCondition.Operator.EQUAL, request.getFilter().getGradeId()));
        examSubjectScoreMicroApi.search(queryTree).getPageInfo().getList().stream().forEach(examSubjectScore -> subjectFullScoreMap.put(examSubjectScore.getSubjectId(), examSubjectScore.getScore()));

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH_BY_CLASS));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName());
            sheet.setData(CELL_POS_EXAM_ID, examId);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname());

            CellPos subjectNamePos = new CellPos(CELL_POS_SUBJECT_NAME);
            float totalScore = 0;
            for (DictItem subject: subjectList) {
                sheet.setData(subjectNamePos, subject.getDictname() + subjectFullScoreMap.get(subject.getDictvalue()) +  ICommonConstants.SCORE);
                totalScore += NumberUtils.toFloat(subjectFullScoreMap.get(subject.getDictvalue()));
                subjectNamePos.increaseColumn();
            }
            sheet.setData(subjectNamePos, ICommonConstants.TOTAL_SCORE + String.valueOf(totalScore) + ICommonConstants.SCORE);
            subjectNamePos.increaseColumn();
            sheet.setData(subjectNamePos, ICommonConstants.RANK);

            List<String> classIdList = request.getFilter().getClassIdList();

            for (int i = 0; i < classIdList.size(); i++) {
                sheet.setData(ROW_INDEX_STUDENT_START + i, 0, String.valueOf(i + 1))
                        .setCellAlignCenter((new CellPos(ROW_INDEX_STUDENT_START + i, 0)));
            }

            int rowStudent = 0;
            int classIndex;
            int subjectIndex;

            for (SubjectAverageScore subjectAverageScore: subjectAverageScoreList) {

                if ((classIndex = classIdList.indexOf(subjectAverageScore.getClassId())) >= 0) {
                    rowStudent = ROW_INDEX_STUDENT_START + classIndex;
                }

                sheet.setData(rowStudent, 1, StringUtils.equals(subjectAverageScore.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(subjectAverageScore.getClassInfo()))
                        .setCellAlignCenter((new CellPos(rowStudent, 1)));

                if ((subjectIndex = subjectIdList.indexOf(subjectAverageScore.getSubjectId())) >= 0) {
                    sheet.setData(rowStudent, CELL_POS_SUBJECT_NAME.getColIndex() + subjectIndex, subjectAverageScore.getScore())
                            .setCellAlignCenter((new CellPos(rowStudent, CELL_POS_SUBJECT_NAME.getColIndex() + subjectIndex)));
                }

                if (StringUtils.equals(subjectAverageScore.getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)) {
                    sheet.setData(rowStudent, CELL_POS_SUBJECT_NAME.getColIndex() + subjectIndex + 1, subjectAverageScore.getRank())
                            .setCellAlignCenter((new CellPos(rowStudent, CELL_POS_SUBJECT_NAME.getColIndex() + subjectIndex + 1)));
                }
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME_BY_CLASS.replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());
        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }
}
