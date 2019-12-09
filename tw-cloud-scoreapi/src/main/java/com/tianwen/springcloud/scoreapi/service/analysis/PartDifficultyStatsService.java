package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.PartDifficultyStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartDifficultyStats;
import com.tianwen.springcloud.microservice.score.entity.request.Request;
import com.tianwen.springcloud.scoreapi.base.ApiResponse;
import com.tianwen.springcloud.scoreapi.constant.ICommonConstants;
import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import com.tianwen.springcloud.scoreapi.entity.excel.SimpleSheet;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.URLService;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import com.tianwen.springcloud.scoreapi.service.util.repo.RepositoryService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class PartDifficultyStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/part-difficulty-stats.xls";

    public static final String EXPORT_FILE_NAME = "{gradeName}{subjectName}--小 题难度分析.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int ROW_INDEX_QUESTION_START = 3;


    @Autowired
    private PartDifficultyStatsMicroApi partDifficultyStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<PartDifficultyStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(PartDifficultyStats.class, partDifficultyStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        DictItem subject = repositoryService.key2entity(request.getFilter().getSubjectId(), RepositoryService.EntityType.SUBJECT);

        List<PartDifficultyStats> partDifficultyStatsList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            int rowIndexQuestion = ROW_INDEX_QUESTION_START;
            int col;

            for (PartDifficultyStats partDifficultyStats : partDifficultyStatsList) {

                col = 0;
                sheet.setData(rowIndexQuestion, col ++, (partDifficultyStats.getQuestionCategory() != null ? partDifficultyStats.getQuestionCategory().getDictname() : "") + partDifficultyStats.getQuestionNo());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getSmallNo());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getCount());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getMaxScore());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getMinScore());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getScore());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getRate());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getFullScoreRate());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getZeroScoreRate());
                sheet.setData(rowIndexQuestion, col ++, partDifficultyStats.getDifficulty());

                rowIndexQuestion ++;
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME.replace("{gradeName}", grade.getDictname()).replace("{subjectName}", subject.getDictname()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

}
