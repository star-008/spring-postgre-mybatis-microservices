package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.ScoreZoneStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.ScoreZone;
import com.tianwen.springcloud.microservice.score.entity.analysis.ScoreZoneStats;
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
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class ScoreZoneStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/score-zone-stats.xls";
    public static final String TEMPLATE_FILE_PATH_BY_REGULAR = "templates/score-zone-stats-regular.xls";

    public static final String EXPORT_FILE_NAME = "{gradeName}{examName}--{subjectName}分数段分析-精简型.xls";
    public static final String EXPORT_FILE_NAME_BY_REGULAR = "{gradeName}{examName}--{subjectName}分数段分析-标准型.xls";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int SCORE_ZONE_TITLE_COL_INDEX = 0;
    protected static final int ROW_INDEX_SCORE_ZONE_START = 4;
    protected static final int COL_INDEX_CLASS_START = 1;

    protected static final String STR_TOTAL = "全体";
    protected final String[] LEVEL_PROPERTY_NAMES = new String[] {"本段", "累计"};

    protected static final int COLUMN_WIDTH_SCORE_ZONE = 2500;

    @Autowired
    private ScoreZoneStatsMicroApi scoreZoneStatsMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<ScoreZoneStats> getList(Request request) {
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(ScoreZoneStats.class, scoreZoneStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);
        DictItem subject = repositoryService.key2entity(request.getFilter().getSubjectId(), RepositoryService.EntityType.SUBJECT);

        List<ScoreZoneStats> scoreZoneStatsList = getList(request).getPageInfo().getList();
        repositoryService.fill(scoreZoneStatsList);

        boolean isSimpleMode = request.getFilter().isSimpleMode();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(isSimpleMode ? TEMPLATE_FILE_PATH : TEMPLATE_FILE_PATH_BY_REGULAR));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            if (isSimpleMode) {
                writeSimpleModeData(sheet, scoreZoneStatsList);
            } else {
                writeRegularModeData(sheet, scoreZoneStatsList);
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    (isSimpleMode ? EXPORT_FILE_NAME : EXPORT_FILE_NAME_BY_REGULAR).replace("{gradeName}", grade.getDictname()).replace("{examName}", exam.getName()).replace("{subjectName}", subject != null ? subject.getDictname() : ICommonConstants.TOTAL), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    public void writeSimpleModeData(SimpleSheet sheet, List<ScoreZoneStats> scoreZoneStatsList) {

        if (!CollectionUtils.isEmpty(scoreZoneStatsList)) {

            List<ScoreZone> scoreZoneList = scoreZoneStatsList.get(0).getScoreZoneList();
            CellPos scoreZonePos = new CellPos(ROW_INDEX_SCORE_ZONE_START, 0);

            for (ScoreZone scoreZone : scoreZoneList) {
                sheet.setData(scoreZonePos, "[" + scoreZone.getLowScore() + "," + scoreZone.getHighScore() + ")")
                        .setCellAlignCenter(scoreZonePos)
                        .setColumnWidth(scoreZonePos.getColIndex(), COLUMN_WIDTH_SCORE_ZONE);
                scoreZonePos.increaseRow();
            }

            //Adding rows field
            int row = scoreZonePos.getRowIndex();
            sheet.setData(row++, 0, ICommonConstants.COUNT);
            sheet.setData(row++, 0, ICommonConstants.MAX_SCORE);
            sheet.setData(row++, 0, ICommonConstants.MIN_SCORE);
            sheet.setData(row++, 0, ICommonConstants.AVG_SCORE);
            sheet.setData(row, 0, ICommonConstants.DIFFERENCE);
        }

        int rowScoreZone;
        int colClass = COL_INDEX_CLASS_START;

        for (ScoreZoneStats scoreZoneStats : scoreZoneStatsList) {

            rowScoreZone = ROW_INDEX_SCORE_ZONE_START;

            //Adding columns field
            sheet.setData(ROW_INDEX_SCORE_ZONE_START - 2, colClass, 2, StringUtils.equals(scoreZoneStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID)  ? STR_TOTAL : getClassFullName(scoreZoneStats.getClassInfo()));

            sheet.setData(ROW_INDEX_SCORE_ZONE_START - 1, colClass, LEVEL_PROPERTY_NAMES[0]);
            sheet.setData(ROW_INDEX_SCORE_ZONE_START - 1, colClass + 1, LEVEL_PROPERTY_NAMES[1]);

            for (ScoreZone scoreZone : scoreZoneStats.getScoreZoneList()) {
                sheet.setData(rowScoreZone, colClass, scoreZone.getCount());
                sheet.setData(rowScoreZone++, colClass + 1, scoreZone.getAccumCount());
            }
            sheet.setData(rowScoreZone++, colClass, scoreZoneStats.getCount());
            sheet.setData(rowScoreZone++, colClass, scoreZoneStats.getMaxScore());
            sheet.setData(rowScoreZone++, colClass, scoreZoneStats.getMinScore());
            sheet.setData(rowScoreZone++, colClass, scoreZoneStats.getAvgScore());
            sheet.setData(rowScoreZone, colClass, scoreZoneStats.getDiff());

            colClass += 2;
        }
    }

    public void writeRegularModeData(SimpleSheet sheet,List<ScoreZoneStats> scoreZoneStatsList) {
        if (!CollectionUtils.isEmpty(scoreZoneStatsList)) {

            CellPos scoreZonePos = new CellPos(ROW_INDEX_SCORE_ZONE_START, SCORE_ZONE_TITLE_COL_INDEX);

            Map<String, Integer> rowSpanCounts = new HashMap<>();
            Map<String, Integer> eachZoneStartRowMap = new HashMap<>();
            Map<String, List<Map<String, Object>>> studentInfoMap = new HashMap<>();

            //score zone title set
            ScoreZoneStats totalScoreZoneStats = scoreZoneStatsList.get(scoreZoneStatsList.size()-1);
            if (totalScoreZoneStats != null) {
                List<ScoreZone> scoreZoneList = totalScoreZoneStats.getScoreZoneList();
                for (ScoreZone scoreZone : scoreZoneList) {
                    studentInfoMap.put(scoreZone.getHighScore(), scoreZone.getStudentInfo());
                    eachZoneStartRowMap.put(scoreZone.getLowScore(), scoreZonePos.getRowIndex());

                    int rowSpan = scoreZone.getStudentInfo().size();
                    if (rowSpan <= 0) {
                        continue;
                    }
                    rowSpanCounts.put(scoreZone.getLowScore(), rowSpan);

                    sheet.setData(scoreZonePos, "[" + scoreZone.getLowScore() + "," + scoreZone.getHighScore() + ")")
                            .setCellAlignCenter(scoreZonePos)
                            .setColumnWidth(scoreZonePos.getColIndex(), COLUMN_WIDTH_SCORE_ZONE);

                    sheet.mergeCells(scoreZonePos.getRowIndex(), rowSpan, SCORE_ZONE_TITLE_COL_INDEX, 1);
                    sheet.setCellVerticalAlignCenter(new CellPos(scoreZonePos.getRowIndex(), SCORE_ZONE_TITLE_COL_INDEX));
                    scoreZonePos.increaseRow(rowSpan);
                }

                //Adding rows field
                scoreZonePos.increaseRow();
                sheet.setData(scoreZonePos, ICommonConstants.COUNT).setCellAlignCenter(scoreZonePos);

                scoreZonePos.increaseRow();
                sheet.setData(scoreZonePos, ICommonConstants.MAX_SCORE).setCellAlignCenter(scoreZonePos);

                scoreZonePos.increaseRow();
                sheet.setData(scoreZonePos, ICommonConstants.MIN_SCORE).setCellAlignCenter(scoreZonePos);

                scoreZonePos.increaseRow();
                sheet.setData(scoreZonePos, ICommonConstants.AVG_SCORE).setCellAlignCenter(scoreZonePos);

                scoreZonePos.increaseRow();
                sheet.setData(scoreZonePos, ICommonConstants.DIFFERENCE).setCellAlignCenter(scoreZonePos);


                //Adding columns field
                scoreZonePos.setPos(ROW_INDEX_SCORE_ZONE_START, COL_INDEX_CLASS_START);
                //sheet.setData(scoreZonePos.getRowIndex()- 1, scoreZonePos.getColIndex(), ICommonConstants.RANK);
                scoreZonePos.increaseColumn();

                //score zone class data
                Map<String, Integer> eachClassColIndexMap = new HashMap<>();
                for (ScoreZoneStats scoreZoneStats : scoreZoneStatsList) {
                    eachClassColIndexMap.put(scoreZoneStats.getClassId(), scoreZonePos.getColIndex());

                    scoreZonePos.setRowIndex(ROW_INDEX_SCORE_ZONE_START);

                    //set class header data
                    boolean isTotal = StringUtils.equals(ICommonConstants.TOTAL_CLASS_ID, scoreZoneStats.getClassId());
                    if (!isTotal) {
                        sheet.setData(scoreZonePos.getRowIndex() - 2, scoreZonePos.getColIndex(), 2, getClassFullName(scoreZoneStats.getClassInfo()));
                        sheet.setData(scoreZonePos.getRowIndex() - 1, scoreZonePos.getColIndex(), LEVEL_PROPERTY_NAMES[0]);
                        sheet.setData(scoreZonePos.getRowIndex() - 1, scoreZonePos.getColIndex() + 1, LEVEL_PROPERTY_NAMES[1]);

                    } else {
                        sheet.setData(scoreZonePos.getRowIndex()- 2, scoreZonePos.getColIndex(), ICommonConstants.SUB_TOTAL)
                                .mergeCells(scoreZonePos.getRowIndex()- 2, 2, scoreZonePos.getColIndex(), 1);
                        sheet.setCellVerticalAlignCenter(new CellPos(scoreZonePos.getRowIndex()- 2, scoreZonePos.getColIndex()));
                        sheet.setData(scoreZonePos.getRowIndex()- 2, scoreZonePos.getColIndex()+1, ICommonConstants.TOTAL_AMOUNT)
                                .mergeCells(scoreZonePos.getRowIndex()- 2, 2, scoreZonePos.getColIndex() + 1, 1);
                        sheet.setCellVerticalAlignCenter(new CellPos(scoreZonePos.getRowIndex()- 2, scoreZonePos.getColIndex() + 1));
                    }

                    for (ScoreZone scoreZone : scoreZoneStats.getScoreZoneList()) {
                        Integer rowSpan = rowSpanCounts.get(scoreZone.getLowScore());
                        if (rowSpan == null) {
                            continue;
                        }

                        sheet.setData(scoreZonePos, scoreZone.getCount())
                                .setCellAlignCenter(scoreZonePos)
                                .setColumnWidth(scoreZonePos.getColIndex(), COLUMN_WIDTH_SCORE_ZONE);
                        sheet.mergeCells(scoreZonePos.getRowIndex(), rowSpan, scoreZonePos.getColIndex(), 1);

                        if (isTotal) {
                            sheet.setData(scoreZonePos.getRowIndex(), scoreZonePos.getColIndex()+1, scoreZone.getAccumCount());
                            sheet.mergeCells(scoreZonePos.getRowIndex(), rowSpan, scoreZonePos.getColIndex()+1, 1);
                            sheet.setCellVerticalAlignCenter(new CellPos(scoreZonePos.getRowIndex(), scoreZonePos.getColIndex()+1));
                        }
                        scoreZonePos.increaseRow(rowSpan==0?1:rowSpan);
                    }

                    scoreZonePos.increaseRow();
                    sheet.setData(scoreZonePos, scoreZoneStats.getCount());

                    scoreZonePos.increaseRow();
                    sheet.setData(scoreZonePos, scoreZoneStats.getMaxScore());

                    scoreZonePos.increaseRow();
                    sheet.setData(scoreZonePos, scoreZoneStats.getMinScore());

                    scoreZonePos.increaseRow();
                    sheet.setData(scoreZonePos, scoreZoneStats.getAvgScore());

                    scoreZonePos.increaseRow();
                    sheet.setData(scoreZonePos, scoreZoneStats.getDiff());

                    scoreZonePos.increaseColumn(2);
                }

                if (!CollectionUtils.isEmpty(scoreZoneList)) {
                    for (ScoreZone scoreZone : scoreZoneList) {
                        List<Map<String, Object>> studentInfoList = studentInfoMap.get(scoreZone.getHighScore());
                        if (!CollectionUtils.isEmpty(studentInfoList)) {
                            for (Map<String, Object> studentInfo : studentInfoList) {
                                String classid = String.valueOf(studentInfo.get("classId").toString());
                                String studentName = String.valueOf(studentInfo.get("studentName"));
                                String rankInGrade = String.valueOf(studentInfo.get("rankInGrade"));
                                String rankInClass = String.valueOf(studentInfo.get("rankInClass"));

                                int colOfClass = eachClassColIndexMap.get(classid);
                                int rowOfScoreZone = eachZoneStartRowMap.get(scoreZone.getLowScore());

                                scoreZonePos.setPos(rowOfScoreZone+studentInfoList.indexOf(studentInfo), colOfClass+1);
                                sheet.setData(scoreZonePos, studentName + ":" + rankInClass).setCellAlignCenter(scoreZonePos);

                                scoreZonePos.setColIndex(COL_INDEX_CLASS_START);
                                sheet.setData(scoreZonePos, rankInGrade).setCellAlignCenter(scoreZonePos).
                                        setColumnWidth(scoreZonePos.getColIndex(), COLUMN_WIDTH_SCORE_ZONE);
                            }
                        }
                    }
                }
            }
        }

    }
}
