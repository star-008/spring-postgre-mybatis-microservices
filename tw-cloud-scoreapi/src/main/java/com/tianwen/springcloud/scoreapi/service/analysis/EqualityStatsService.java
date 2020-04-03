package com.tianwen.springcloud.scoreapi.service.analysis;

import com.tianwen.springcloud.commonapi.base.response.Response;
import com.tianwen.springcloud.commonapi.query.Pagination;
import com.tianwen.springcloud.commonapi.query.QueryCondition;
import com.tianwen.springcloud.commonapi.query.QueryTree;
import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.microservice.base.entity.ClassInfo;
import com.tianwen.springcloud.microservice.base.entity.DictItem;
import com.tianwen.springcloud.microservice.score.api.ExamMicroApi;
import com.tianwen.springcloud.microservice.score.api.ExamSubjectScoreMicroApi;
import com.tianwen.springcloud.microservice.score.api.analysis.EqualityStatsMicroApi;
import com.tianwen.springcloud.microservice.score.entity.Exam;
import com.tianwen.springcloud.microservice.score.entity.analysis.*;
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
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by R.JinHyok on 2018.11.17.
 */
@Service
public class EqualityStatsService extends AnalysisService {

    public static final String TEMPLATE_FILE_PATH = "templates/equality-stats.xls";
    public static final String TEMPLATE_FILE_PATH_FOR_COMPARE = "templates/equality-stats-compare.xls";
    public static final String TEMPLATE_FILE_PATH_BY_CLASS = "templates/equality-stats-class.xls";

    public static final String EXPORT_FILE_NAME = "{gradeName}-{examName}--班级科目均衡性分析.xls";
    public static final String EXPORT_FILE_NAME_FOR_COMPARE = "{gradeName}-{examName1}和{examName2}--班级科目均衡性对比.xls";
    public static final String EXPORT_FILE_NAME_BY_CLASS = "{gradeName}-{examName}--班级科目均衡性分析-按班级.xls";

    public static final String CLASS_TITLE = "{className}科目均衡性分析";

    protected static final CellPos CELL_POS_EXAM_NAME = new CellPos(0, 1);
    protected static final CellPos CELL_POS_EXAM_ID = new CellPos(0, 3);
    protected static final CellPos CELL_POS_EXAM_NAME2 = new CellPos(0, 5);
    protected static final CellPos CELL_POS_EXAM_ID2 = new CellPos(0, 7);
    protected static final CellPos CELL_POS_GRADE_NAME = new CellPos(1, 1);

    protected static final int CELL_POS_PASSZONE_COL = 6;
    protected static final int CELL_POS_GOODZONE_COL = 11;
    protected static final int CELL_POS_LOWZONE_COL = 16;

    protected static final int DEFAULT_COLUMN_WIDTH = 1800;
    protected static final int DEFAULT_COLUMN_SCORE_WIDTH = 1200;

    protected static final int ROW_INDEX_CLASS_START = 3;
    protected static final int ROW_INDEX_CLASS_START2 = 4;

    protected static final CellPos CELL_POS_SCORE_ZONE_START = new CellPos(2, 15);

    @Autowired
    private EqualityStatsMicroApi equalityStatsMicroApi;

    @Autowired
    private ExamSubjectScoreMicroApi examSubjectScoreMicroApi;

    @Autowired
    private ExamMicroApi examMicroApi;

    public Response<EqualityStats> getList(Request request) {
        request.getPagination().setPageNo(Pagination.QUERY_ALL);
        request.getFilter().setPubStatusPublished();

        return new ApiResponse<>(loadAnalysisListData(EqualityStats.class, equalityStatsMicroApi, request));
    }

    public String exportToExcel(Request request) {
        String examId = request.getFilter().getExamId();
        Exam exam = examMicroApi.get(examId).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<EqualityStats> equalityStatsList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH));

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, examId)
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);


//            if (!CollectionUtils.isEmpty(equalityStatsList)) {
//
//                List<ScoreZone> scoreZoneList = equalityStatsList.get(0).getScoreZoneList();
//                CellPos scoreZonePos = new CellPos(CELL_POS_SCORE_ZONE_START);
//
//                for (ScoreZone scoreZone : scoreZoneList) {
//                    sheet.setData(scoreZonePos, scoreZone.toString())
//                            .setCellAlignCenter(scoreZonePos)
//                            .setColumnWidth(scoreZonePos.getColIndex(), DEFAULT_COLUMN_SCORE_WIDTH);
//                    scoreZonePos.increaseColumn();
//                }
//            }

            int rowStart = ROW_INDEX_CLASS_START;

            String subjectId = null;
            int classCount = 0;
            int col;
            for (EqualityStats equalityStats : equalityStatsList) {
                if (!StringUtils.equals(subjectId, equalityStats.getSubjectId())) {
                    subjectId = equalityStats.getSubjectId();
                    if (classCount > 1) {
                        sheet.mergeCells(rowStart - classCount, classCount, 0, 1);
                        sheet.setCellVerticalAlign(new CellPos(rowStart - classCount, 0), CellStyle.VERTICAL_CENTER);
                    }
                    classCount = 0;
                }
                classCount ++;
                col = 0;
                sheet.setData(rowStart, col ++, StringUtils.equals(equalityStats.getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)  ? ICommonConstants.TOTAL_SCORE_NAME : getSubjectName(equalityStats.getSubject()));
                sheet.setData(rowStart, col ++, StringUtils.equals(equalityStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(equalityStats.getClassInfo()));
                sheet.setData(rowStart, col ++, equalityStats.getTotalCount());
                sheet.setData(rowStart, col ++, equalityStats.getApplyCount());
                sheet.setData(rowStart, col ++, equalityStats.getClassInfo() == null ? null : equalityStats.getClassInfo().getAdviserNames());
                sheet.setData(rowStart, col ++, equalityStats.getTeacherName());
                sheet.setData(rowStart, col ++, equalityStats.getMaxScore());
                sheet.setData(rowStart, col ++, equalityStats.getMinScore());
                sheet.setData(rowStart, col ++, equalityStats.getScore());

                for (PassZone passZone: equalityStats.getPassZoneList()) {
                    sheet.setData(rowStart, col ++, passZone.getCount());
                    sheet.setData(rowStart, col ++, passZone.getPercent() + "%");
                }
//                for (ScoreZone scoreZone: equalityStats.getScoreZoneList()) {
//                    sheet.setData(rowStart, col ++, scoreZone.getCount());
//                }

                rowStart ++;
            }

            if (classCount > 1) {
                sheet.mergeCells(rowStart - classCount, classCount, 0, 1);
                sheet.setCellVerticalAlign(new CellPos(rowStart - classCount, 0), CellStyle.VERTICAL_CENTER);
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME.replace("{examName}", exam.getName()).replace("{gradeName}", grade.getDictname()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    public String exportToExcelForCompare(Request request) {
        List<String> examIdList = request.getFilter().getExamIdList();
        if (examIdList.size() < 2) return null;
        Exam exam1 = examMicroApi.get(examIdList.get(0)).getResponseEntity();
        Exam exam2 = examMicroApi.get(examIdList.get(1)).getResponseEntity();
        DictItem grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

        List<EqualityStats> equalityStatsList = getList(request).getPageInfo().getList();

        try {
            SimpleSheet sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH_FOR_COMPARE));

            sheet.setData(CELL_POS_EXAM_NAME, exam1.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME);
            sheet.setData(CELL_POS_EXAM_ID, exam1.getId())
                    .setCellAlignCenter(CELL_POS_EXAM_ID);
            sheet.setData(CELL_POS_EXAM_NAME2, exam2.getName())
                    .setCellAlignCenter(CELL_POS_EXAM_NAME2);
            sheet.setData(CELL_POS_EXAM_ID2, exam2.getId())
                    .setCellAlignCenter(CELL_POS_EXAM_ID2);
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname())
                    .setCellAlignCenter(CELL_POS_GRADE_NAME);

            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_PASSZONE_COL), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_PASSZONE_COL + 2), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_GOODZONE_COL), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_GOODZONE_COL + 2), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_LOWZONE_COL), exam1.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_LOWZONE_COL + 2), exam1.getName());

            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_PASSZONE_COL + 1), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_PASSZONE_COL + 3), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_GOODZONE_COL + 1), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_GOODZONE_COL + 3), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_LOWZONE_COL + 1), exam2.getName());
            sheet.setData(new CellPos(ROW_INDEX_CLASS_START2 - 1, CELL_POS_LOWZONE_COL + 3), exam2.getName());

            int rowStart = ROW_INDEX_CLASS_START2;

            Map<String, Map<String, List<EqualityStats>>> statsBySubjectAndClass = equalityStatsList.stream().collect(Collectors.groupingBy(EqualityStats::getSubjectId, Collectors.groupingBy(EqualityStats::getClassId)));

            List<String> keyList = new ArrayList<>();
            for (String key : statsBySubjectAndClass.keySet()) {
                keyList.add(key);
            }
            keyList.sort((o1, o2) -> o1.compareTo(o2));
            for (String key : keyList) {
                Map<String, List<EqualityStats>> stringListMap = statsBySubjectAndClass.get(key);
                int rows = renderForSubject(sheet, rowStart, stringListMap, exam1.getId(), exam2.getId());
                sheet.mergeCells(rowStart, rows, 0, 1);
                sheet.setCellVerticalAlign(new CellPos(rowStart, 0), CellStyle.VERTICAL_CENTER);
                rowStart += rows;
            }

            ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(
                    EXPORT_FILE_NAME_FOR_COMPARE.replace("{examName1}", exam1.getName()).replace("{examName2}", exam2.getName()).replace("{gradeName}", grade.getDictname()), ICommonConstants.CONTENT_TYPE_EXCEL);
            writeSheetData(sheet, exportedFile);

            return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());

        } catch (IOException | InvalidFormatException e) {

        }

        return null;
    }

    private int renderForSubject(SimpleSheet sheet, int rowStart, Map<String, List<EqualityStats>> stringListMap, String exam1, String exam2) {
        int rows = 0;
        List<String> keyList = new ArrayList<>();
        for (String key : stringListMap.keySet()) {
            keyList.add(key);
        }
        keyList.sort((o1, o2) -> o1.equals(ICommonConstants.TOTAL_CLASS_ID)?1:(o1.compareTo(o2)));

        for (String key : keyList) {
            List<EqualityStats> statForOneClass = stringListMap.get(key);
            if (statForOneClass.size() == 0) continue;
            sheet.setData(rowStart + rows, 0, StringUtils.equals(statForOneClass.get(0).getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)  ? ICommonConstants.TOTAL_SCORE_NAME : getSubjectName(statForOneClass.get(0).getSubject()));
            sheet.setData(rowStart + rows, 1, StringUtils.equals(statForOneClass.get(0).getClassId(), ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(statForOneClass.get(0).getClassInfo()));
            sheet.setData(rowStart + rows, 2, statForOneClass.get(0).getTotalCount());
            sheet.setData(rowStart + rows, 3, statForOneClass.get(0).getApplyCount());
            sheet.setData(rowStart + rows, 4, statForOneClass.get(0).getClassInfo() == null ? null : statForOneClass.get(0).getClassInfo().getAdviserNames());
            sheet.setData(rowStart + rows, 5, statForOneClass.get(0).getTeacherName());

            if (statForOneClass.size() == 1) {
                EqualityStats equalityStats = new EqualityStats();
                List<PassZone> passZoneList = new ArrayList<>();
                passZoneList.add(new PassZone(statForOneClass.get(0).getPassZoneList().get(0), "0", "0"));
                passZoneList.add(new PassZone(statForOneClass.get(0).getPassZoneList().get(1), "0", "0"));
                passZoneList.add(new PassZone(statForOneClass.get(0).getPassZoneList().get(2), "0", "0"));
                equalityStats.setPassZoneList(passZoneList);

                if (statForOneClass.get(0).getExamId().equals(exam1)) {
                    equalityStats.setExamId(exam2);
                    statForOneClass.add(equalityStats);
                } else {
                    equalityStats.setExamId(exam1);
                    statForOneClass.add(0, equalityStats);
                }
            }

            for (EqualityStats equalityStats : statForOneClass) {
                int colIdx;
                if (equalityStats.getExamId().equals(exam1)) colIdx = CELL_POS_PASSZONE_COL;
                else colIdx = CELL_POS_PASSZONE_COL + 1;

                for (PassZone passZone: equalityStats.getPassZoneList()) {
                    sheet.setData(rowStart + rows, colIdx, passZone.getCount());
                    sheet.setData(rowStart + rows, colIdx + 2, passZone.getPercent() + "%");
                    colIdx += 5;
                }
            }
            sheet.setData(rowStart + rows, CELL_POS_PASSZONE_COL + 4, String.valueOf(Float.valueOf(statForOneClass.get(1).getPassZoneList().get(0).getPercent()) - Float.valueOf(statForOneClass.get(0).getPassZoneList().get(0).getPercent())) + "%");
            sheet.setData(rowStart + rows, CELL_POS_PASSZONE_COL + 9, String.valueOf(Float.valueOf(statForOneClass.get(1).getPassZoneList().get(1).getPercent()) - Float.valueOf(statForOneClass.get(0).getPassZoneList().get(1).getPercent())) + "%");
            sheet.setData(rowStart + rows, CELL_POS_PASSZONE_COL + 14, String.valueOf(Float.valueOf(statForOneClass.get(1).getPassZoneList().get(2).getPercent()) - Float.valueOf(statForOneClass.get(0).getPassZoneList().get(2).getPercent())) + "%");
            rows += 1;
        }
        return rows;
    }

    public String exportToExcelByClass(Request request) {
        return new ExcelExporterByClass(request).run();
    }

    class ExcelExporterByClass {

        private Request request;

        private Exam exam;
        private DictItem grade;

        private SimpleSheet sheet;

        private List<String> classIdList = new ArrayList<>();
        private Map<String, List<EqualityStats>> equalityStatsMap = new HashMap<>();

        private int rowIndexClass = ROW_INDEX_CLASS_START - 1;

        private int colSpanClass = 14;

        private String[] columnNames = {
                ICommonConstants.SUBJECT_NAME,
                ICommonConstants.TEACHER_NAME,
                ICommonConstants.EXAM_TOTAL_COUNT,
                ICommonConstants.EXAM_APPlY_COUNT,
                ICommonConstants.FULL_SCORE,
                ICommonConstants.MAX_SCORE,
                ICommonConstants.MIN_SCORE,
                ICommonConstants.AVG_SCORE,
                ICommonConstants.GOOD_SCORE_COUNT,
                ICommonConstants.GOOD_SCORE_PERCENT,
                ICommonConstants.BETTER_SCORE_COUNT,
                ICommonConstants.BETTER_SCORE_PERCENT,
                ICommonConstants.BAD_SCORE_COUNT,
                ICommonConstants.BAD_SCORE_PERCENT,
        };

        ExcelExporterByClass(Request request) {
            this.request = request;
        }

        String run() {
            return writeToExcel();
        }

        String writeToExcel() {

            getReady();

            try {
                sheet = new SimpleSheet(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_FILE_PATH_BY_CLASS));

                writeHeader();

                classIdList.forEach(this::writeClassStats);

                ExportedFile exportedFile = BeanHolder.getBean(ExportedFileRepositoryService.class).createNewExportedFile(EXPORT_FILE_NAME_BY_CLASS.replace("{examName}", exam.getName()).replace("{gradeName}", grade.getDictname()), ICommonConstants.CONTENT_TYPE_EXCEL);
                writeSheetData(sheet, exportedFile);

                return BeanHolder.getBean(URLService.class).getExportedFileURL(exportedFile.getKey());
            } catch (IOException | InvalidFormatException e) {

            }

            return null;
        }

        private void getReady() {
            exam = examMicroApi.get(request.getFilter().getExamId()).getResponseEntity();
            grade = repositoryService.key2entity(request.getFilter().getGradeId(), RepositoryService.EntityType.GRADE);

            classIdList = request.getFilter().getClassIdList();

            List<EqualityStats> equalityStatsList = getList(request).getPageInfo().getList();

            List<EqualityStats> classEqualityStatsList;
            for (EqualityStats equalityStats : equalityStatsList) {

                String classId = equalityStats.getClassId();

                classEqualityStatsList = equalityStatsMap.get(classId);
                if (classEqualityStatsList == null) {
                    classEqualityStatsList = new ArrayList<>();
                    equalityStatsMap.put(classId, classEqualityStatsList);
                }
                classEqualityStatsList.add(equalityStats);
            }
        }

        private void writeHeader() {

            sheet.setData(CELL_POS_EXAM_NAME, exam.getName());
            sheet.setData(CELL_POS_EXAM_ID, request.getFilter().getExamId());
            sheet.setData(CELL_POS_GRADE_NAME, grade.getDictname());
        }

        private void writeClassStats(String classId) {

            List<EqualityStats> equalityStatsList = equalityStatsMap.get(classId);
            if (equalityStatsList == null) {
                return;
            }

            rowIndexClass ++;

            writeClassHeader(classId);

            int col;
            for (EqualityStats equalityStats: equalityStatsList) {
                if (StringUtils.equals(equalityStats.getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)
                        && !(StringUtils.equals(equalityStats.getClassId(), ICommonConstants.TOTAL_CLASS_ID))
                        && !(equalityStats.getClassInfo() != null && StringUtils.equals(equalityStats.getClassInfo().getClassType(), ClassInfo.CLASS_TYPE_NORMAL))) {
                    continue;
                }

                String[] values = {
                        StringUtils.equals(equalityStats.getSubjectId(), ICommonConstants.TOTAL_SUBJECT_ID)  ? ICommonConstants.TOTAL_SCORE_NAME : getSubjectName(repositoryService.key2entity(equalityStats.getSubjectId(), RepositoryService.EntityType.SUBJECT)),
                        equalityStats.getTeacherName(),
                        equalityStats.getTotalCount(),
                        equalityStats.getApplyCount(),
                        equalityStats.getFullScore(),
                        equalityStats.getMaxScore(),
                        equalityStats.getMinScore(),
                        equalityStats.getScore(),
                };

                col = 0;
                for (String value : values) {
                    sheet.setData(rowIndexClass, col++, value);
                }

                for (PassZone passZone: equalityStats.getPassZoneList()) {
                    sheet.setData(rowIndexClass, col++, passZone.getCount());
                    sheet.setData(rowIndexClass, col++, passZone.getPercent());
                }

                rowIndexClass ++;
            }

            rowIndexClass ++;
        }

        private void writeClassHeader(String classId) {

            sheet.setData(rowIndexClass, 0, colSpanClass, CLASS_TITLE.replace("{className}", StringUtils.equals(classId, ICommonConstants.TOTAL_CLASS_ID) ? ICommonConstants.YEAR_TOTAL_NAME : getClassFullName(repositoryService.key2entity(classId, RepositoryService.EntityType.CLASS))))
                    .setCellAlignCenter(new CellPos(rowIndexClass, 0))
                    .setColumnWidth(0, DEFAULT_COLUMN_WIDTH);
            rowIndexClass++;

            for (int i = 0; i < columnNames.length; i++) {
                sheet.setData(rowIndexClass, i, columnNames[i])
                        .setCellAlignCenter(new CellPos(rowIndexClass, i))
                        .setColumnWidth(i, DEFAULT_COLUMN_WIDTH);
            }

            rowIndexClass ++;
        }
    }
}
