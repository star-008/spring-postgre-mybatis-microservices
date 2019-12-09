package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.LevelZoneStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.LevelZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.LevelZoneStats;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class LevelZoneStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/level-zone-stats.xls";
    public static final String EXPORT_FILE_NAME = "{gradeName}{examName}-班级科目分段统计.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final CellPos CELL_POS_LEVEL_NAME = new CellPos(2, 8);

    protected static final int ROW_INDEX_CLASS_START = 4;

    protected static final String STR_ZONE_NAME = "分段";
    protected final String[] LEVEL_PROPERTY_NAMES = new String[] {"人数", "占比"};

    @Autowired
    private LevelZoneStatsMicroApi levelZoneStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<LevelZoneStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(LevelZoneStats.class, levelZoneStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<LevelZoneStats> levelZoneStatsList = getList(request).getPageInfo().getList();


        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);



            final String[] levelLabels = new String[] {"A", "B", "C", "D", "E", "F"};
            List<String> levelList = new ArrayList<>();
            int rowStart = ROW_INDEX_CLASS_START;

            // Adding level field
            int propSpan = 0;
            for (int i = 0; i < levelLabels.length; i++) {
                sheet.setData(CELL_POS_LEVEL_NAME.getRowIndex(), CELL_POS_LEVEL_NAME.getColIndex() + i * 2, levelLabels[i] + STR_ZONE_NAME)
                        .setCellAlignCenter(new CellPos(CELL_POS_LEVEL_NAME.getRowIndex(), CELL_POS_LEVEL_NAME.getColIndex()));

                sheet.mergeCells(CELL_POS_LEVEL_NAME.getRowIndex(), 1, CELL_POS_LEVEL_NAME.getColIndex() + i * 2, 2);

                sheet.setData(CELL_POS_LEVEL_NAME.getRowIndex() + 1, CELL_POS_LEVEL_NAME.getColIndex() + propSpan, LEVEL_PROPERTY_NAMES[0])
                        .setCellAlignCenter(new CellPos(CELL_POS_LEVEL_NAME.getRowIndex(), CELL_POS_LEVEL_NAME.getColIndex()));

                sheet.setData(CELL_POS_LEVEL_NAME.getRowIndex() + 1, CELL_POS_LEVEL_NAME.getColIndex() + propSpan + 1, LEVEL_PROPERTY_NAMES[1])
                        .setCellAlignCenter(new CellPos(CELL_POS_LEVEL_NAME.getRowIndex(), CELL_POS_LEVEL_NAME.getColIndex()));

                propSpan = propSpan + 2;

                levelList.add(String.valueOf(i + 1));
            }

            String subjectId = null;
            int classCount = 0;
            int col;
            int levelZoneIndex;
            for (LevelZoneStats levelZoneStats : levelZoneStatsList) {
                if (!StringUtils.equals(subjectId, levelZoneStats.getSubjectId())) {
                    subjectId = levelZoneStats.getSubjectId();
                    if (classCount > 1) {
                        sheet.mergeCells(rowStart - classCount, classCount, 0, 1);
                        sheet.setCellVerticalAlign(new CellPos(rowStart - classCount, 0), CellStyle.VERTICAL_CENTER);
                    } else {
                        sheet.setCellAlignCenter(new CellPos(rowStart - 1, 0));
                    }
                    classCount = 0;
                }
                classCount ++;
                col = 0;
                sheet.setData(rowStart, col ++, StringUtils.equals(levelZoneStats.getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)  ? ICommonConstants.TOTAL_SCORE_NAME : getSubjectName(levelZoneStats.getSubject()));
                sheet.setData(rowStart, col ++, StringUtils.equals(levelZoneStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(levelZoneStats.getClassInfo()));
                sheet.setData(rowStart, col ++, levelZoneStats.getApplyCount());
                sheet.setData(rowStart, col ++, levelZoneStats.getTotalCount());
                sheet.setData(rowStart, col ++, levelZoneStats.getClassInfo() == null ? null : levelZoneStats.getClassInfo().getAdviserNames());
                sheet.setData(rowStart, col ++, levelZoneStats.getTeacherName());
                sheet.setData(rowStart, col ++, levelZoneStats.getMaxScore());
                sheet.setData(rowStart, col ++, levelZoneStats.getMinScore());

                for (LevelZone levelZone: levelZoneStats.getLevelZoneList()) {
                    if ((levelZoneIndex = levelList.indexOf(levelZone.getLevel())) >= 0) {
                        sheet.setData(rowStart, col + levelZoneIndex * 2, levelZone.getCount());
                        sheet.setData(rowStart, col + levelZoneIndex * 2 + 1, levelZone.getPercent());
                    }
                }

                rowStart ++;
            }

            if (classCount > 1) {
                sheet.mergeCells(rowStart - classCount, classCount, 0, 1);
                sheet.setCellVerticalAlign(new CellPos(rowStart - classCount, 0), CellStyle.VERTICAL_CENTER);
            } else {
                sheet.setCellAlignCenter(new CellPos(rowStart - 1, 0));
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME.replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

}
