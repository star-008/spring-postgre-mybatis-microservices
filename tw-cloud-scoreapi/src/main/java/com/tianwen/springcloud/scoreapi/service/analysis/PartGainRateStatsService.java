package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.PartGainRateStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.PartGainRateStats;
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
public class PartGainRateStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/part-gain-rate-stats.xls";

    public static final String EXPORT_FILE_NAME = "{gradeName}{subjectName}--班级小题得分率分析.xls";

    public static final String CLASS_TITLE = "{className}科目均衡性分析";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int DEFAULT_COLUMN_WIDTH = 1800;
    protected static final int DEFAULT_COLUMN_SCORE_WIDTH = 1200;

    protected static final int ROW_INDEX_CLASS_START = 3;
    protected static final CellPos CELL_POS_QUESTION_START = new CellPos(2, 1);

    @Autowired
    private PartGainRateStatsMicroApi partGainRateStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<PartGainRateStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(PartGainRateStats.class, partGainRateStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        DictItem subject = repositoryService.key2entity(request.getFilter().getSubjectId(), RepositoryService.EntityType.SUBJECT);

        List<PartGainRateStats> partGainRateStatsList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            List<String> smallNoList = new ArrayList<>();
            List<String> questionTypeList = new ArrayList<>();

            partGainRateStatsList.stream().filter(partGainRateStats -> smallNoList.indexOf(partGainRateStats.getSmallNo()) < 0).forEach(partGainRateStats -> {
                smallNoList.add(partGainRateStats.getSmallNo());
                questionTypeList.add(partGainRateStats.getQuestionType() != null ? partGainRateStats.getQuestionType().getDictname() : null );
            });

            int rowClass  = ROW_INDEX_CLASS_START - 1;
            int colQuestionStart = 1;

            for (int i = 0; i <smallNoList.size(); i ++) {
                sheet.setData(rowClass, colQuestionStart, questionTypeList.get(i) + smallNoList.get(i));
                sheet.setData(rowClass, colQuestionStart + 1, questionTypeList.get(i) + smallNoList.get(i) + "%");
                colQuestionStart += 2;
            }


            String  classId = null;
            CellPos cellPosQuestionStart = new CellPos(CELL_POS_QUESTION_START);
            int colIndex;
            int smallNoIndex;

            for (PartGainRateStats partGainRateStats : partGainRateStatsList) {

                if (!StringUtils.equals(classId, partGainRateStats.getClassId())) {
                    classId = partGainRateStats.getClassId();
                    rowClass ++;

                    sheet.setData(rowClass, 0, StringUtils.equals(partGainRateStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(partGainRateStats.getClassInfo()));
                }

                int colSpan = 2;
                if ((smallNoIndex = smallNoList.indexOf(partGainRateStats.getSmallNo())) >= 0) {
                    colIndex = cellPosQuestionStart.getColIndex() + smallNoIndex * colSpan;
                    sheet.setData(rowClass, colIndex, partGainRateStats.getScore());
                    sheet.setData(rowClass, colIndex + 1, partGainRateStats.getPercent());
                }
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
