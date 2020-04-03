package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.DifficultyStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.DifficultyStats;
import com.tianwen.springcloud.microservice.score.entity.analysis.PassZone;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class DifficultyStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/difficulty-stats.xls";

    public static final String EXPORT_FILE_NAME = "{examName}{gradeName}--班级科目难度分析.xls";

    public static final String SUBJECT_TITLE = "{gradeName}-{examName}--{subjectName}难度分析";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int DEFAULT_COLUMN_WIDTH = 1800;
    protected static final int DEFAULT_COLUMN_CLASS_WIDTH = 4000;
    
    protected static final int ROW_INDEX_SUBJECT_START = 3;
    protected static final int COL_INDEX_PASS_ZONE_START = 10;

    @Autowired
    private DifficultyStatsMicroApi difficultyStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<DifficultyStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(DifficultyStats.class, difficultyStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        return new ExcelExporter(request).run();
    }

    class ExcelExporter {

        private Request request;

        private Exam exam;
        private DictItem grade;

        private SimpleSheet sheet;

        private List<String> subjectIdList = new ArrayList<>();
        private Map<String, List<DifficultyStats>> difficultyStatsMap = new HashMap<>();

        private int rowIndexSubject = ROW_INDEX_SUBJECT_START;

        private int colSpanSubject = 17;

        private String[][] columnNames = {
                new String[] {
                        ICommonConstants.CLASS_NAME,
                        ICommonConstants.STUDENT_TOTAL_COUNT,
                        ICommonConstants.STUDENT_APPlY_COUNT,
                        ICommonConstants.MAX_SCORE,
                        ICommonConstants.MIN_SCORE,
                        ICommonConstants.AVG_SCORE,
                        null,null,
                        ICommonConstants.DIFFERENCE,
                        ICommonConstants.PERCENT,
                        null,null,null,null,null,null,
                        ICommonConstants.DIFFICULTY,

                },
                new String[] {
                        null,null,null,null,null,
                        ICommonConstants.ALL,
                        ICommonConstants.TOP_N.replace("{count}", String.valueOf(27)) + "%",
                        ICommonConstants.LAST_N.replace("{count}", String.valueOf(27)) + "%",
                        null,
                        ICommonConstants.FULL_SCORE,
                        ICommonConstants.BEST_SCORE,
                        ICommonConstants.BETTER_SCORE,
                        ICommonConstants.PASS_SCORE,
                        ICommonConstants.BAD_SCORE,
                        ICommonConstants.AVG_SCORE_RATE_OVER,
                        ICommonConstants.AVG_SCORE_RATE
                }
        };

        ExcelExporter(Request request) {
            this.request = request;
        }

        String run() {
            return writeToExcel();
        }

        String writeToExcel() {

            getReady();

            try {
                sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

                writeHeader();

                subjectIdList.forEach(this::writeSubjectStats);

                ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                        EXPORT_FILE_NAME.replace("{examName}", exam.getName()).replace("{gradeName}", grade.getDictname()), ICommonConstants.CONTENT_TYPE_EXCEL);
                writeSheetData(sheet, exportedFile);

                return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());
            } catch (IOException | InvalidFormatException e) {

            }

            return null;
        }

        private void getReady() {
            exam = examMicroApi.get(request.getFilter().getExamId()).getResponseEntity();
            grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

            subjectIdList = request.getFilter().getSubjectIdList();

            List<DifficultyStats> difficultyStatsList = getList(request).getPageInfo().getList();

            List<DifficultyStats> subjectDifficultyStatsList;
            for (DifficultyStats difficultyStats : difficultyStatsList) {

                String subjectId = difficultyStats.getSubjectId();

                subjectDifficultyStatsList = difficultyStatsMap.get(subjectId);
                if (subjectDifficultyStatsList == null) {
                    subjectDifficultyStatsList = new ArrayList<>();
                    difficultyStatsMap.put(subjectId, subjectDifficultyStatsList);
                }
                subjectDifficultyStatsList.add(difficultyStats);
            }
        }

        private void writeHeader() {

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName());
            sheet.setData(CELL_POS_EXAM_ID, request.getFilter().getExamId());
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname());
        }

        private void writeSubjectStats(String subjectId) {

            List<DifficultyStats> difficultyStatsList = difficultyStatsMap.get(subjectId);
            if (difficultyStatsList == null) {
                return;
            }

            rowIndexSubject++;

            writeSubjectHeader(subjectId);

            int col;
            for (DifficultyStats difficultyStats: difficultyStatsList) {

                String[] values = {
                        StringUtils.equals(difficultyStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(repositoryService.key2entity(difficultyStats.getClassId(), RepositoryService.EntityType.CLASS)),
                        difficultyStats.getTotalCount(),
                        difficultyStats.getApplyCount(),
                        difficultyStats.getMaxScore(),
                        difficultyStats.getMinScore(),
                        difficultyStats.getScore(),
                        difficultyStats.getTopNScore(),
                        difficultyStats.getLastNScore(),
                        difficultyStats.getDiff(),
                        difficultyStats.getFullScorePercent(),
                        null, null, null, null,
                        StringUtils.equals(difficultyStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? null : difficultyStats.getAvgScorePercentDiff(),
                        StringUtils.equals(difficultyStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? null : difficultyStats.getAvgScorePercent(),
                        difficultyStats.getDifficulty()
                };

                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null) {
                        continue;
                    }
                    sheet.setData(rowIndexSubject, i, values[i]);
                }

                col = COL_INDEX_PASS_ZONE_START;
                for (PassZone passZone: difficultyStats.getPassZoneList()) {
                    sheet.setData(rowIndexSubject, col++, passZone.getPercent());
                }

                rowIndexSubject++;
            }

            rowIndexSubject++;
        }

        private void writeSubjectHeader(String subjectId) {

            sheet.setData(rowIndexSubject, 0, colSpanSubject, SUBJECT_TITLE.replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()).replace("{subjectName}", StringUtils.equals(subjectId, ICommonConstants.TOTAL_SUBJECT_ID) ? ICommonConstants.TOTAL : DifficultyStatsService.this.getSubjectName(repositoryService.key2entity(subjectId, RepositoryService.EntityType.SUBJECT))))
                    .setCellAlignCenter(new CellPos(rowIndexSubject, 0))
                    .setColumnWidth(0, DEFAULT_COLUMN_CLASS_WIDTH);

            rowIndexSubject++;

            int col;
            for (int i = 0; i < columnNames.length; i++) {
                col = 0;
                for (int j = 0; j < columnNames[i].length; j++) {
                    sheet.setData(rowIndexSubject + i, col, columnNames[i][j])
                            .setCellAlignCenter(new CellPos(rowIndexSubject, col))
                            .setCellVerticalAlign(new CellPos(rowIndexSubject, col), CellStyle.VERTICAL_CENTER)
                            .setColumnWidth(col, DEFAULT_COLUMN_WIDTH);

                    if (i == 0) {
                        if (j < 5 || j == 8 || j == 16) {
                            sheet.mergeCells(rowIndexSubject, 2, col, 1);
                        } else if (j == 5) {
                            sheet.mergeCells(rowIndexSubject, col, 3);
                        } else if (j == 9) {
                            sheet.mergeCells(rowIndexSubject, col, 7);
                        }
                    }

                    col ++;
                }
            }

            rowIndexSubject += 2;
        }
    }
}
